

CREATE TABLE application.lk_classification_versions (
	id int4 NOT NULL,
	code varchar(255) NULL,
	name_ar varchar(255) NULL,
	name_en varchar(255) NULL,
	CONSTRAINT lk_classification_versions_pkey PRIMARY KEY (id)
);

ALTER TABLE application.applications_info ADD version_id int4 NULL;
ALTER TABLE application.classifications ADD version_id int4 NULL;

ALTER TABLE application.applications_info ADD CONSTRAINT fk2i3lbq1n36xepoygrvlsxm1il
      FOREIGN KEY (version_id) REFERENCES application.lk_classification_versions(id);
ALTER TABLE application.classifications ADD CONSTRAINT fkrp0f3iq6q90sxgbe5p2kllxr2
FOREIGN KEY (version_id) REFERENCES application.lk_classification_versions(id);

INSERT INTO application.lk_classification_versions
(id, code, name_ar, name_en)
VALUES(1, '1', '1.0.0', '1.0.0');

update application.classifications set version_id = 1 where version_id is null;