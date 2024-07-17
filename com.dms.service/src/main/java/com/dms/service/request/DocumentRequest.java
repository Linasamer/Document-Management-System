package com.dms.service.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentRequest {

	@JsonProperty("esaal_file")
	private String esaalFile;

	@JsonProperty("esaal_file_name")
	private String esaalFileName;

	@JsonProperty("esaal_file_format")
	private String esaalFileFormat;

	@JsonProperty("file_tags")
	private List<String> fileTags;
}