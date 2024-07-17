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
@Table(name = "DOCUMENT_METADATA")
@Data
@NoArgsConstructor
public class DocumentMetadata {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doc_metadata_seq_gen")
	@SequenceGenerator(name = "doc_metadata_seq_gen", sequenceName = "doc_metadata_seq", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	@Column(name = "DOC_ID")
	private Long documentId;

	@Column(name = "UNIT", nullable = false)
	private String unit;

}
