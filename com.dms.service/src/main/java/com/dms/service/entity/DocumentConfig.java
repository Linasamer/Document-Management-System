package com.dms.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DOCUMENT_CONFIG")
@Data
@NoArgsConstructor
public class DocumentConfig {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doc_config_seq_gen")
	@SequenceGenerator(name = "doc_config_seq_gen", sequenceName = "doc_config_seq", allocationSize = 1)
	@Column(name = "ID")
	private Long Id;

	@Column(name = "CODE", nullable = false)
	private String code;

	@Column(name = "VALUE")
	private String value;

	@Column(name = "PROD_VALUE")
	private String productionValue;

}