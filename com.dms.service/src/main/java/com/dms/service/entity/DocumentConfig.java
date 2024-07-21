package com.dms.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DOCUMENT_CONFIG")
@Data
@NoArgsConstructor
public class DocumentConfig {
	@Id
	@Column(name = "ID")
	private Long Id;

	@Column(name = "CODE", nullable = false)
	private String code;

	@Column(name = "VALUE")
	private String value;

}