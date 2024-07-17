package com.dms.service.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.dms.service.exceptions.BusinessException;
import com.dms.service.response.AIResponse;
import com.dms.service.response.AIUploadResponse;

@Component
public class RestClientService {
	@Value("${integration.with.ai.off}")
	private boolean intgerationFlag;

	public <T extends Object> AIUploadResponse uploadFile(String base64File, List<String> fileTags, String documentType, String documentName,
			String correlationId, String authorization) throws Exception {
		if (intgerationFlag) {
			return AIUploadResponse.builder().fileId("Doc_Ref_" + UUID.randomUUID().toString()).build();

		}

		try {
			String url = UriComponentsBuilder.fromHttpUrl("http://41.33.183.2:4012/insert-esaal-file").queryParam("esaal_file", base64File)
					.queryParam("file_tags", fileTags).queryParam("document_type", documentType).queryParam("document_name", documentName)
					.toUriString();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("x-correlation-id", correlationId);
			headers.set("Authorization", authorization);

			HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<AIUploadResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, AIUploadResponse.class);

			if (responseEntity.getStatusCode() != HttpStatus.OK) {
				throw new BusinessException("error_webserviceFailed");
			}

			return responseEntity.getBody();
		} catch (Exception e) {
			throw new BusinessException("An error occurred while uploading the file", e);
		}

	}

	public <T extends Object> AIResponse updataFileTags(String Id, List<String> fileTags, String correlationId, String authorization)
			throws Exception {
		if (intgerationFlag) {
			return AIResponse.builder().message("File tags updated successfully").build();

		}

		try {
			String url = UriComponentsBuilder.fromHttpUrl("http://41.33.183.2:4012/update-file-tags").queryParam("file_tags", fileTags)
					.queryParam("file_id ", Id).toUriString();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("x-correlation-id", correlationId);
			headers.set("Authorization", authorization);

			HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<AIResponse> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, AIResponse.class);

			if (responseEntity.getStatusCode() != HttpStatus.OK) {
				throw new BusinessException("error_webserviceFailed");
			}

			return responseEntity.getBody();
		} catch (Exception e) {
			throw new BusinessException("An error occurred during updating file tags", e);
		}

	}

	public <T extends Object> AIResponse deleteFile(String Id, String correlationId, String authorization) throws Exception {
		if (intgerationFlag) {
			return AIResponse.builder().message("File deleted successfully").build();

		}

		try {
			String url = UriComponentsBuilder.fromHttpUrl("http://41.33.183.2:4012/delete-esaal-file/" + Id).toUriString();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("x-correlation-id", correlationId);
			headers.set("Authorization", authorization);

			HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<AIResponse> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, AIResponse.class);

			if (responseEntity.getStatusCode() != HttpStatus.OK) {
				throw new BusinessException("error_webserviceFailed");
			}

			return responseEntity.getBody();
		} catch (Exception e) {
			throw new BusinessException("An error occurred during deletion", e);
		}

	}

}
