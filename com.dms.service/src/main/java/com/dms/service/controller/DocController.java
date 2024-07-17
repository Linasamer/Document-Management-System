package com.dms.service.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dms.service.request.DocumentRequest;
import com.dms.service.response.DocumentInfoResponse;
import com.dms.service.response.DocumentInfoResponseList;
import com.dms.service.response.DocumentResponse;
import com.dms.service.services.DocumentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/v1/doc")
@CrossOrigin(origins = "*")
public class DocController {
	@Autowired
	private DocumentService documentService;

	@PostMapping(value = "/upload")
	@Operation(description = "Saving doc Data")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Doc Save successfully"),
			@ApiResponse(responseCode = "400", description = "Bad request"), @ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error"),
			@ApiResponse(responseCode = "503", description = "Service Unavailable"),
			@ApiResponse(responseCode = "502", description = "Generic bad gateway error") })
	public ResponseEntity<DocumentInfoResponse> uploadDocument(@NotNull @RequestHeader("x-correlation-id") String correlationId,
			@NotNull @RequestHeader("Authorization") String authorization, @RequestBody DocumentRequest documentRequest) throws Exception {

		DocumentInfoResponse response = documentService.saveDocument(correlationId, authorization, documentRequest);
		return ResponseEntity.ok(response);

	}

	@GetMapping(value = "/retrieve")
	@Operation(description = "Retrieving document data")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Document retrieved successfully"),
			@ApiResponse(responseCode = "400", description = "Bad request"), @ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "404", description = "Document not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error"),
			@ApiResponse(responseCode = "503", description = "Service unavailable"),
			@ApiResponse(responseCode = "502", description = "Generic bad gateway error") })
	public ResponseEntity<DocumentResponse> retrieveDocument(@NotNull @RequestHeader("x-correlation-id") String correlationId,
			@NotNull @RequestHeader("Authorization") String authorization, @RequestParam("DocumentId") Long id) throws IOException {

		DocumentResponse response = documentService.retrieveDocument(correlationId, authorization, id);
		return ResponseEntity.ok(response);

	}

	@GetMapping("/documents")
	@Operation(description = "Updating document metadata")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retrieving document data successfully"),
			@ApiResponse(responseCode = "400", description = "Bad request"), @ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "404", description = "Document not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error"),
			@ApiResponse(responseCode = "503", description = "Service unavailable"),
			@ApiResponse(responseCode = "502", description = "Generic bad gateway error") })
	public ResponseEntity<DocumentInfoResponseList> retrieveDocuments(@NotNull @RequestHeader("x-correlation-id") String correlationId,
			@NotNull @RequestHeader("Authorization") String authorization, @RequestParam(required = false) String name,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

		DocumentInfoResponseList documentInfoResponsesList = documentService.retrevieAllDocumentOrByName(correlationId, authorization, name, page,
				size);
		return ResponseEntity.ok(documentInfoResponsesList);

	}

	@PutMapping(value = "/update")
	@Operation(description = "Updating document metadata")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Document metadata updated successfully"),
			@ApiResponse(responseCode = "400", description = "Bad request"), @ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "404", description = "Document not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error"),
			@ApiResponse(responseCode = "503", description = "Service unavailable"),
			@ApiResponse(responseCode = "502", description = "Generic bad gateway error") })
	public ResponseEntity<DocumentInfoResponse> updateDocument(@NotNull @RequestHeader("x-correlation-id") String correlationId,
			@NotNull @RequestHeader("Authorization") String authorization, @RequestParam("esaal_id") Long id,
			@RequestParam("file_tags") List<String> metaData) throws Exception {

		DocumentInfoResponse documentInfoResponse = documentService.updateDocument(correlationId, authorization, id, metaData);
		return ResponseEntity.ok(documentInfoResponse);

	}

	@DeleteMapping(value = "/delete")
	@Operation(description = "Deleting document")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Document deleted successfully"),
			@ApiResponse(responseCode = "400", description = "Bad request"), @ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "404", description = "Document not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error"),
			@ApiResponse(responseCode = "503", description = "Service unavailable"),
			@ApiResponse(responseCode = "502", description = "Generic bad gateway error") })
	public ResponseEntity<String> deleteDocument(@NotNull @RequestHeader("x-correlation-id") String correlationId,
			@NotNull @RequestHeader("Authorization") String authorization, @RequestParam Long id) throws Exception {

		documentService.deleteDocument(correlationId, authorization, id);
		return ResponseEntity.ok("Document Has Been Deleted Successfully");

	}

}