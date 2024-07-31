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
	
INSERT INTO DMS.DOCUMENT_CONFIG
(ID, CODE, VALUE)
VALUES(1, 'AI_URL', 'http://41.33.183.2:4012/insert-esaal-file');
INSERT INTO DMS.DOCUMENT_CONFIG
(ID, CODE, VALUE)
VALUES(2, 'INTEGRATION_WITH_AI_OFF', 'true');
INSERT INTO DMS.DOCUMENT_CONFIG
(ID, CODE, VALUE)
VALUES(3, 'LDAP_CONNECTION_TYPE', 'ldap');
INSERT INTO DMS.DOCUMENT_CONFIG
(ID, CODE, VALUE)
VALUES(4, 'LDAP_IPS', '192.168.201.103');
INSERT INTO DMS.DOCUMENT_CONFIG
(ID, CODE, VALUE)
VALUES(5, 'LDAP_PORT', '389');
INSERT INTO DMS.DOCUMENT_CONFIG
(ID, CODE, VALUE)
VALUES(6, 'LDAP_DOMAIN', 'ejada.com');


CREATE TABLE vacations.users (
	id numeric(18) NOT NULL,
	username varchar(50) NULL,
	"password" varchar(100) NOT NULL,
	email varchar(50) NOT NULL,
	active varchar(1) NOT NULL,
	roles varchar(50) NOT NULL,
	CONSTRAINT users_email_key UNIQUE (email),
	CONSTRAINT users_pkey PRIMARY KEY (id),
	CONSTRAINT users_username_key UNIQUE (username)
);