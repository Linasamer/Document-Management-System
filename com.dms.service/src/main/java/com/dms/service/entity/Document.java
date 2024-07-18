package com.dms.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DOCUMENT")
@Data
@NoArgsConstructor
public class Document {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doc_seq_gen")
	@SequenceGenerator(name = "doc_seq_gen", sequenceName = "doc_seq", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	@Column(name = "DOC_NAME", nullable = false, unique = true)
	private String docName;

	@Column(name = "DOC_REF", nullable = false, unique = true)
	private String docRef;

	@Column(name = "DOC_PATH", nullable = false)
	private String docPath;

	@Column(name = "DOC_FORMAT", nullable = false)
	private String docFormat;
	@Lob
	@Column(name = "DOC_BASE64", nullable = false, columnDefinition = "CLOB")
	private String docBase64;

}
