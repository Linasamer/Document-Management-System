package com.dms.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dms.service.entity.DocumentConfig;

@Repository
public interface DocumentConfigRepository extends JpaRepository<DocumentConfig, Long> {

	DocumentConfig findByCode(String code);

}
