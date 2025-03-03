CREATE SEQUENCE doc_seq
	  START WITH 1
	  INCREMENT BY 1
	  NOCACHE
	  NOCYCLE;

CREATE SEQUENCE doc_metadata_seq
	  START WITH 1
	  INCREMENT BY 1
	  NOCACHE
	  NOCYCLE;
	  
CREATE SEQUENCE doc_config_seq
	  START WITH 1
	  INCREMENT BY 1
	  NOCACHE
	  NOCYCLE;
	
	CREATE TABLE DOCUMENT (
	  ID NUMBER PRIMARY KEY,
	  DOC_NAME VARCHAR2(255) NOT NULL UNIQUE,
	  DOC_REF VARCHAR2(255) NOT NULL UNIQUE,
	  DOC_PATH VARCHAR2(1000) NOT NULL,
	   DOC_FORMAT VARCHAR2(1000) NOT NULL,
	      	      DOC_BASE64 CLOB NOT NULL,


	);
	
	
	CREATE TABLE DOCUMENT_METADATA (
	  ID NUMBER PRIMARY KEY,
	  DOC_ID NUMBER NOT NULL,
	  UNIT VARCHAR2(255) NOT NULL,
	  CONSTRAINT doc_metadata_doc_id_fk FOREIGN KEY (DOC_ID) REFERENCES DOCUMENT(ID),
	  CONSTRAINT doc_metadata_unique_constraint UNIQUE (DOC_ID, UNIT)
	);
	
	CREATE TABLE DOCUMENT_CONFIG (
	  ID NUMBER PRIMARY KEY,
	  CODE VARCHAR2(255) NOT NULL UNIQUE,
	  VALUE VARCHAR2(255)
	  PROD_VALUE VARCHAR2(255) ,
	  
	);
	
SELECT ID, CODE, VALUE, PROD_VALUE
FROM DMS.DOCUMENT_CONFIG
WHERE ID=1;
SELECT ID, CODE, VALUE, PROD_VALUE
FROM DMS.DOCUMENT_CONFIG
WHERE ID=2;