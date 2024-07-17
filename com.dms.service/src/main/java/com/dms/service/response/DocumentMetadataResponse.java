package com.dms.service.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentMetadataResponse {

	@JsonProperty("Id")
	private Long id;

	@JsonProperty("DocumentId")
	private Long documentId;

	@JsonProperty("Unit")
	private String unit;

}
