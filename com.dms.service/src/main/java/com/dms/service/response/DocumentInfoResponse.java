package com.dms.service.response;

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
public class DocumentInfoResponse {

	@JsonProperty("esaal_file_id")
	private Long id;

	@JsonProperty("esaal_file_name")
	private String docName;

	@JsonProperty("file_tags")
	private List<String> accessLevel;

}
