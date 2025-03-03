package com.dms.service.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dms.service.entity.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

	Optional<Document> findByDocName(String docName);

	Page<Document> findAll(Pageable pageable);
	
	@Query("SELECT d FROM Document d " +
		       "JOIN DocumentMetadata dm ON d.id = dm.documentId " +
		       "WHERE d.docName LIKE %:docName% " +
		       "AND (:tag IS NULL OR :tag = '' OR dm.unit = :tag)")
	Page<Document> findByDocNameAndTag(@Param("docName") String docName, @Param("tag") String tag, Pageable pageable);

}
