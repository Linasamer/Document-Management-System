package com.dms.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dms.service.entity.DocumentMetadata;

@Repository
public interface DocumentMetadataRepository extends JpaRepository<DocumentMetadata, Long> {

	List<DocumentMetadata> findBydocumentId(Long docId);

	List<DocumentMetadata> findAllByDocumentIdIn(List<Long> docId);

}
