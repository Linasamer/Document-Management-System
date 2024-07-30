package com.dms.service.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dms.service.entity.Document;
import com.dms.service.entity.DocumentMetadata;
import com.dms.service.exceptions.BusinessException;
import com.dms.service.exceptions.DataNotFoundException;
import com.dms.service.repository.DocumentMetadataRepository;
import com.dms.service.repository.DocumentRepository;
import com.dms.service.request.DocumentRequest;
import com.dms.service.response.AIUploadResponse;
import com.dms.service.response.DocumentInfoResponse;
import com.dms.service.response.DocumentInfoResponseList;
import com.dms.service.response.DocumentInfoResponseMapper;
import com.dms.service.response.DocumentResponse;

@Service
@Transactional
public class DocumentService {

	@Autowired
	public DocumentMetadataRepository documentMetadataRepository;

	@Autowired
	public DocumentRepository documentRepository;

	@Autowired
	public RestClientService restClientService;

	public DocumentInfoResponse saveDocument(String correlationId, String authorization, DocumentRequest documentRequest) throws Exception {
		if (documentRequest.getEsaalFile() == null || documentRequest.getEsaalFile().isEmpty()) {
			throw new BusinessException("File is empty or null");
		}
		Optional<Document> doc = documentRepository.findByDocName(documentRequest.getEsaalFileName());
		if (doc.isPresent()) {
			throw new BusinessException("Doc is already exist");
		}

		byte[] decodedBytes;
		try {
			decodedBytes = Base64.getDecoder().decode(documentRequest.getEsaalFile());
		} catch (IllegalArgumentException e) {
			throw new Exception("Invalid Base64 string", e);
		}

		String completeFileName = documentRequest.getEsaalFileName() + "." + documentRequest.getEsaalFileFormat();

		AIUploadResponse docRef = restClientService.uploadFile(documentRequest.getEsaalFile(), documentRequest.getFileTags(),
				documentRequest.getEsaalFileFormat(), documentRequest.getEsaalFileName(), correlationId, authorization);

		Document document = new Document();
		document.setDocName(documentRequest.getEsaalFileName());
		document.setDocPath(completeFileName);
		document.setDocRef(docRef.getFileId());
		document.setDocFormat(documentRequest.getEsaalFileFormat());
		document.setDocBase64(documentRequest.getEsaalFile());

		try {
			document = documentRepository.save(document);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		List<DocumentMetadata> savedDocumentMetadatas = saveListDoumentMetaData(documentRequest.getFileTags(), document);

		return DocumentInfoResponseMapper.INSTANCE.mapToDocumentInfoResponse(document, savedDocumentMetadatas);
	}

	public DocumentResponse retrieveDocument(String correlationId, String authorization, Long id) throws IOException {
		Optional<Document> documentOptional = documentRepository.findById(id);
		if (!documentOptional.isPresent()) {
			throw new DataNotFoundException("Document not found");
		}

		Document document = documentOptional.get();
		List<DocumentMetadata> documentMetadataList = documentMetadataRepository.findBydocumentId(document.getId());

		DocumentInfoResponse documentInfoResponse = DocumentInfoResponseMapper.INSTANCE.mapToDocumentInfoResponse(document, documentMetadataList);

		return DocumentResponse.builder().document(documentInfoResponse).fileBase64(documentOptional.get().getDocBase64()).build();
	}

	public DocumentInfoResponseList retrevieAllDocumentOrByName(String correlationId, String authorization, String name, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);

		if (name == null || name.isEmpty()) {
			return retrevieAllDocument(pageable);
		}
		return retrevieByName(name, pageable);
	}

	public DocumentInfoResponseList retrevieByName(String name, Pageable pageable) {
		List<DocumentInfoResponse> documentInfoResponses = new ArrayList<DocumentInfoResponse>();
		Page<Document> documentList = documentRepository.findByDocName(name, pageable);
		if (documentList.getContent().isEmpty()) {
			throw new DataNotFoundException("Doc Not Found");
		}
		List<Long> documentIds = documentList.stream().map(Document::getId).collect(Collectors.toList());
		List<DocumentMetadata> documentMetadataList = documentMetadataRepository.findAllByDocumentIdIn(documentIds);
		for (Document document : documentList) {
			List<DocumentMetadata> metadataForDocument = documentMetadataList.stream()
					.filter(metadata -> metadata.getDocumentId().equals(document.getId())).collect(Collectors.toList());

			documentInfoResponses.add(DocumentInfoResponseMapper.INSTANCE.mapToDocumentInfoResponse(document, metadataForDocument));
		}
		return DocumentInfoResponseList.builder().documents(documentInfoResponses).build();
	}

	public DocumentInfoResponseList retrevieAllDocument(Pageable pageable) {
		List<DocumentInfoResponse> documentInfoResponses = new ArrayList<DocumentInfoResponse>();
		Page<Document> documentList = documentRepository.findAll(pageable);
		List<Long> documentIds = documentList.stream().map(Document::getId).collect(Collectors.toList());
		List<DocumentMetadata> documentMetadataList = documentMetadataRepository.findAllByDocumentIdIn(documentIds);
		for (Document document : documentList) {
			List<DocumentMetadata> metadataForDocument = documentMetadataList.stream()
					.filter(metadata -> metadata.getDocumentId().equals(document.getId())).collect(Collectors.toList());

			documentInfoResponses.add(DocumentInfoResponseMapper.INSTANCE.mapToDocumentInfoResponse(document, metadataForDocument));
		}
		return DocumentInfoResponseList.builder().documents(documentInfoResponses).build();

	}

	public DocumentInfoResponse updateDocument(String correlationId, String authorization, Long id, List<String> metadate) throws Exception {
		Optional<Document> updateDoc = documentRepository.findById(id);
		if (!updateDoc.isPresent()) {
			throw new DataNotFoundException("Doc Not Found");
		}
		restClientService.updataFileTags(id.toString(), metadate, correlationId, authorization);

		deleteListDoumentMetaData(updateDoc.get());
		List<DocumentMetadata> documentMetadatas = saveListDoumentMetaData(metadate, updateDoc.get());
		return DocumentInfoResponseMapper.INSTANCE.mapToDocumentInfoResponse(updateDoc.get(), documentMetadatas);
	}

	public void deleteDocument(String correlationId, String authorization, Long id) throws Exception {
		Optional<Document> document = documentRepository.findById(id);
		if (!document.isPresent()) {
			throw new DataNotFoundException("Document not found");
		}

		restClientService.deleteFile(id.toString(), correlationId, authorization);

		deleteListDoumentMetaData(document.get());

		try {
			documentRepository.delete(document.get());
		} catch (Exception e) {
			throw new BusinessException("Error deleting document", e);
		}

		// Path path = Paths.get(document.get().getDocPath());
		// try {
		// Files.deleteIfExists(path);
		// } catch (IOException e) {
		// throw new BusinessException("Error deleting file from disk", e);
		// }
	}

	private List<DocumentMetadata> saveListDoumentMetaData(List<String> metaData, Document document) {
		List<DocumentMetadata> documentMetadataList = new ArrayList<DocumentMetadata>();
		for (String meta : metaData) {
			DocumentMetadata documentMetadata = new DocumentMetadata();
			documentMetadata.setDocumentId(document.getId());
			documentMetadata.setUnit(meta);
			documentMetadataList.add(documentMetadata);
		}

		try {
			return documentMetadataRepository.saveAll(documentMetadataList);
		} catch (Exception e) {
			throw new BusinessException("Error saving document metadata to repository", e);
		}
	}

	private void deleteListDoumentMetaData(Document document) {
		List<DocumentMetadata> documentMetadataList = documentMetadataRepository.findBydocumentId(document.getId());
		try {
			documentMetadataRepository.deleteAll(documentMetadataList);
			documentMetadataRepository.flush();
		} catch (Exception e) {
			throw new BusinessException("Error deleting document metadata", e);
		}

	}

}