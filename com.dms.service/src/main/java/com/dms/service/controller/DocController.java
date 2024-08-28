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

import com.dms.service.exceptions.ErrorResponse;
import com.dms.service.request.DocumentRequest;
import com.dms.service.response.DocumentInfoResponse;
import com.dms.service.response.DocumentInfoResponseList;
import com.dms.service.response.DocumentResponse;
import com.dms.service.services.DocumentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Doc Save successfully", content = @Content(schema = @Schema(implementation = DocumentInfoResponse.class))),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "502", description = "Generic bad gateway error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	public ResponseEntity<DocumentInfoResponse> uploadDocument(@NotNull @RequestHeader("x-correlation-id") String correlationId,
			@RequestBody DocumentRequest documentRequest) throws Exception {

		DocumentInfoResponse response = documentService.saveDocument(correlationId, documentRequest);
		return ResponseEntity.ok(response);

	}

	@GetMapping
	@Operation(description = "Retrieving document data")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Document retrieved successfully", content = @Content(schema = @Schema(implementation = DocumentResponse.class))),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "Document not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "503", description = "Service unavailable", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "502", description = "Generic bad gateway error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	public ResponseEntity<DocumentResponse> retrieveDocument(@NotNull @RequestHeader("x-correlation-id") String correlationId,
			 @RequestParam("DocumentId") Long id)
			throws IOException {

		DocumentResponse response = documentService.retrieveDocument(correlationId, id);
		return ResponseEntity.ok(response);

	}

	@GetMapping("/documents")
	@Operation(description = "Updating document metadata")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retrieving document data successfully", content = @Content(schema = @Schema(implementation = DocumentInfoResponseList.class))),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "Document not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "503", description = "Service unavailable", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "502", description = "Generic bad gateway error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	public ResponseEntity<DocumentInfoResponseList> retrieveDocuments(@NotNull @RequestHeader("x-correlation-id") String correlationId,
			@RequestParam(required = false) String name, @RequestParam(required = false) String tag, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

		DocumentInfoResponseList documentInfoResponsesList = documentService.retrevieAllDocumentOrByName(correlationId, name, tag, page,
				size);
		return ResponseEntity.ok(documentInfoResponsesList);

	}

	@PutMapping(value = "/update")
	@Operation(description = "Updating document metadata")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Document metadata updated successfully", content = @Content(schema = @Schema(implementation = DocumentInfoResponse.class))),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "Document not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "503", description = "Service unavailable", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "502", description = "Generic bad gateway error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	public ResponseEntity<DocumentInfoResponse> updateDocument(@NotNull @RequestHeader("x-correlation-id") String correlationId,
		    @RequestParam("esaal_id") Long id,
			@RequestParam("file_tags") List<String> metaData) throws Exception {

		DocumentInfoResponse documentInfoResponse = documentService.updateDocument(correlationId, id, metaData);
		return ResponseEntity.ok(documentInfoResponse);

	}

	@DeleteMapping(value = "/delete")
	@Operation(description = "Deleting document")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Document deleted successfully", content = @Content(schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "Document not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "503", description = "Service unavailable", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "502", description = "Generic bad gateway error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
	public ResponseEntity<String> deleteDocument(@NotNull @RequestHeader("x-correlation-id") String correlationId,
			 @RequestParam Long id)
			throws Exception {

		documentService.deleteDocument(correlationId, id);
		return ResponseEntity.ok("Document Has Been Deleted Successfully");

	}

}