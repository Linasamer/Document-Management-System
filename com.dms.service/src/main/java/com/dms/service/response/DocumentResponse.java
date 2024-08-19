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
public class DocumentResponse {

	@JsonProperty("Document")
	private DocumentInfoResponse document;
	
	@JsonProperty("DocumentFormat")
	private String documentFormat;


	@JsonProperty("FileBase64")
	private String fileBase64;
	

}
