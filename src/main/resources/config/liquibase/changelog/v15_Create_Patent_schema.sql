--liquibase formatted sql
-- changeset application-service:v15_Create_Patent_schema.sql


CREATE TABLE application.lk_application_collaborative_research (
                                                              id bigint NOT NULL,
                                                              created_by_user character varying(255),
                                                              created_date timestamp without time zone,
                                                              modified_by_user character varying(255),
                                                              modified_date timestamp without time zone,
                                                              is_deleted integer NOT NULL,
                                                              name_ar character varying(255),
                                                              name_en character varying(255)
);



CREATE TABLE application.patent_details (
                                       id bigint NOT NULL,
                                       created_by_user character varying(255),
                                       created_date timestamp without time zone,
                                       modified_by_user character varying(255),
                                       modified_date timestamp without time zone,
                                       is_deleted integer NOT NULL,
                                       ipd_summary_ar character varying(255),
                                       ipd_summary_en character varying(255),
                                       collaborative_research BOOLEAN,
                                       application_Id bigint,
                                       specifications_doc_id bigint,
                                       collaborative_research_id bigint
);


CREATE TABLE application.pct (
                            id bigint NOT NULL,
                            created_by_user character varying(255),
                            created_date timestamp without time zone,
                            modified_by_user character varying(255),
                            modified_date timestamp without time zone,
                            is_deleted integer NOT NULL,
                            application_id bigint,
                            filing_date_gr date ,
                            pct_application_no character varying(255),
                            publish_no character varying(255),
                            wipo_url character varying(255),
                            patent_id bigint
);



ALTER TABLE ONLY application.lk_application_collaborative_research
    ADD CONSTRAINT lk_application_collaborative_research_pkey PRIMARY KEY (id);



ALTER TABLE ONLY application.patent_details
    ADD CONSTRAINT patent_details_pkey PRIMARY KEY (id);

ALTER TABLE ONLY application.pct
    ADD CONSTRAINT pct_pkey PRIMARY KEY (id);


ALTER TABLE ONLY application.patent_details
    ADD CONSTRAINT fk7db1l5pcyum72pttetasxvygb FOREIGN KEY (collaborative_research_id) REFERENCES application.lk_application_collaborative_research(id);


ALTER TABLE ONLY application.pct
    ADD CONSTRAINT fkb0jo095pixah5p8xk2vr7ccwb FOREIGN KEY (patent_id) REFERENCES application.patent_details(id);

alter table application.pct add column international_publication_date date DEFAULT null;


ALTER TABLE application.pct ADD CONSTRAINT unique_patent_id UNIQUE (patent_id);

ALTER TABLE application.patent_details ADD CONSTRAINT unique_application_id UNIQUE (application_id);


INSERT INTO application.lk_application_collaborative_research
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, name_ar, name_en)
VALUES(1, 'test', '1992-04-28 15:52:47.377', 'test', '2004-01-17 16:57:34.581', 0, 'المكتب الكورى', 'korean Office');
