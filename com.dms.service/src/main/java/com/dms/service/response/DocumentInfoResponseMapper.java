package com.dms.service.response;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.dms.service.entity.Document;
import com.dms.service.entity.DocumentMetadata;

@Mapper
public interface DocumentInfoResponseMapper {

	DocumentInfoResponseMapper INSTANCE = Mappers.getMapper(DocumentInfoResponseMapper.class);

	@Mapping(target = "id", source = "document.id")
	@Mapping(target = "docName", source = "document.docName")
	@Mapping(target = "accessLevel", source = "documentMetadata", qualifiedByName = "mapToUnitList")
	DocumentInfoResponse mapToDocumentInfoResponse(Document document, List<DocumentMetadata> documentMetadata);

	@Named("mapToUnitList")
	default List<String> mapToUnitList(List<DocumentMetadata> documentMetadata) {
		return documentMetadata.stream().map(DocumentMetadata::getUnit).collect(Collectors.toList());
	}

}
