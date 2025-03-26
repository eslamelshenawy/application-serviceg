-- liquibase formatted sql
-- changeset Application-Service:create_schema.sql


-- DROP SCHEMA application;

CREATE SCHEMA application;

-- DROP SEQUENCE application.applications_info_serial_seq;

CREATE SEQUENCE application.applications_info_serial_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;
-- DROP SEQUENCE application.id_seq;

CREATE SEQUENCE application.id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;-- application.application_relevant definition

-- Drop table

-- DROP TABLE application.application_relevant;

CREATE TABLE application.application_relevant (
                                                  id int8 NOT NULL,
                                                  created_by_user varchar(255) NULL,
                                                  created_date timestamp NULL,
                                                  modified_by_user varchar(255) NULL,
                                                  modified_date timestamp NULL,
                                                  is_deleted int4 NOT NULL,
                                                  address varchar(255) NULL,
                                                  city varchar(255) NULL,
                                                  country_id int8 NULL,
                                                  full_name_ar varchar(255) NULL,
                                                  full_name_en varchar(255) NULL,
                                                  gender varchar(255) NULL,
                                                  identifier varchar(255) NULL,
                                                  identifier_type varchar(255) NULL,
                                                  national_country_id int8 NULL,
                                                  pobox varchar(255) NULL,
                                                  CONSTRAINT application_relevant_pkey PRIMARY KEY (id)
);


-- application.lk_applicant_category definition

-- Drop table

-- DROP TABLE application.lk_applicant_category;

CREATE TABLE application.lk_applicant_category (
                                                   id int8 NOT NULL,
                                                   created_by_user varchar(255) NULL,
                                                   created_date timestamp NULL,
                                                   modified_by_user varchar(255) NULL,
                                                   saip_code varchar(255) NULL,
                                                   modified_date timestamp NULL,
                                                   is_deleted int4 NOT NULL,
                                                   applicant_category_name_ar varchar(255) NULL,
                                                   applicant_category_name_en varchar(255) NULL,
                                                   CONSTRAINT lk_applicant_category_pkey PRIMARY KEY (id)
);


-- application.lk_application_category definition

-- Drop table

-- DROP TABLE application.lk_application_category;

CREATE TABLE application.lk_application_category (
                                                     id int8 NOT NULL,
                                                     created_by_user varchar(255) NULL,
                                                     created_date timestamp NULL,
                                                     modified_by_user varchar(255) NULL,
                                                     modified_date timestamp NULL,
                                                     saip_code varchar(255) NULL,
                                                     abbreviation varchar(255) NULL,
                                                     is_deleted int4 NOT NULL,
                                                     application_category_desc_ar varchar(255) NULL,
                                                     application_category_desc_en varchar(255) NULL,
                                                     application_category_is_active bool NULL,
                                                     CONSTRAINT lk_application_category_pkey PRIMARY KEY (id)
);


-- application.lk_application_priority_status definition

-- Drop table

-- DROP TABLE application.lk_application_priority_status;

CREATE TABLE application.lk_application_priority_status (
                                                            id int8 NOT NULL,
                                                            created_by_user varchar(255) NULL,
                                                            created_date timestamp NULL,
                                                            modified_by_user varchar(255) NULL,
                                                            modified_date timestamp NULL,
                                                            is_deleted int4 NOT NULL,
                                                            ips_status_desc_ar varchar(255) NULL,
                                                            ips_status_desc_en varchar(255) NULL,
                                                            CONSTRAINT lk_application_priority_status_pkey PRIMARY KEY (id)
);


-- application.lk_application_status definition

-- Drop table

-- DROP TABLE application.lk_application_status;

CREATE TABLE application.lk_application_status (
                                                   id int8 NOT NULL,
                                                   created_by_user varchar(255) NULL,
                                                   created_date timestamp NULL,
                                                   modified_by_user varchar(255) NULL,
                                                   modified_date timestamp NULL,
                                                   is_deleted int4 NOT NULL,
                                                   ips_status_desc_ar varchar(255) NULL,
                                                   ips_status_desc_en varchar(255) NULL,
                                                   CONSTRAINT lk_application_status_pkey PRIMARY KEY (id)
);


-- application.lk_document_type definition

-- Drop table

-- DROP TABLE application.lk_document_type;

CREATE TABLE application.lk_document_type (
                                              id int8 NOT NULL,
                                              created_by_user varchar(255) NULL,
                                              created_date timestamp NULL,
                                              modified_by_user varchar(255) NULL,
                                              modified_date timestamp NULL,
                                              is_deleted int4 NOT NULL,
                                              category varchar(255) NULL,
                                              description varchar(255) NULL,
                                              doc_order int8 NULL,
                                              "name" varchar(255) NULL,
                                              CONSTRAINT lk_document_type_pkey PRIMARY KEY (id)
);


-- application.lk_fast_track_examination_target_area definition

-- Drop table

-- DROP TABLE application.lk_fast_track_examination_target_area;

CREATE TABLE application.lk_fast_track_examination_target_area (
                                                                   id int8 NOT NULL,
                                                                   created_by_user varchar(255) NULL,
                                                                   created_date timestamp NULL,
                                                                   modified_by_user varchar(255) NULL,
                                                                   modified_date timestamp NULL,
                                                                   is_deleted int4 NOT NULL,
                                                                   description_ar varchar(255) NULL,
                                                                   description_en varchar(255) NULL,
                                                                   "show" bool NULL,
                                                                   CONSTRAINT lk_fast_track_examination_target_area_pkey PRIMARY KEY (id)
);


-- application.lk_nexuo_user definition

-- Drop table

-- DROP TABLE application.lk_nexuo_user;

CREATE TABLE application.lk_nexuo_user (
                                           id int8 NOT NULL,
                                           created_by_user varchar(255) NULL,
                                           created_date timestamp NULL,
                                           modified_by_user varchar(255) NULL,
                                           modified_date timestamp NULL,
                                           is_deleted int4 NOT NULL,
                                           "name" varchar(255) NULL,
                                           "type" varchar(255) NULL,
                                           CONSTRAINT lk_nexuo_user_pkey PRIMARY KEY (id)
);


-- application.lk_request_type definition

-- Drop table

-- DROP TABLE application.lk_request_type;

CREATE TABLE application.lk_request_type (
                                             id int8 NOT NULL,
                                             created_by_user varchar(255) NULL,
                                             created_date timestamp NULL,
                                             modified_by_user varchar(255) NULL,
                                             modified_date timestamp NULL,
                                             is_deleted int4 NOT NULL,
                                             code varchar(255) NULL,
                                             saip_code varchar(255) NULL,
                                             is_approval_required bool NULL,
                                             is_internal bool NULL,
                                             "name" varchar(255) NULL,
                                             name_en varchar(255) NULL,
                                             CONSTRAINT lk_request_type_pkey PRIMARY KEY (id)
);


-- application.applications_info definition

-- Drop table

-- DROP TABLE application.applications_info;

CREATE TABLE application.applications_info (
                                               id int8 NOT NULL,
                                               created_by_user varchar(255) NULL,
                                               created_date timestamp NULL,
                                               modified_by_user varchar(255) NULL,
                                               modified_date timestamp NULL,
                                               is_deleted int4 NOT NULL,
                                               accelerated bool NULL,
                                               address varchar(255) NULL,
                                               application_number varchar(255) NULL,
                                               created_by_user_id int8 NULL,
                                               email varchar(255) NULL,
                                               ipc_number varchar(255) NULL,
                                               mobile_code varchar(255) NULL,
                                               mobile_number varchar(255) NULL,
                                               national_security bool NULL,
                                               partial_application bool NULL,
                                               partial_application_number varchar(255) NULL,
                                               serial bigserial NOT NULL,
                                               substantive_examination bool NULL,
                                               title_ar varchar(255) NULL,
                                               title_en varchar(255) NULL,
                                               application_status_id int8 NOT NULL,
                                               lk_category_id int8 NULL,
                                               filing_date timestamp NULL,
                                               by_himself bool NULL,
                                               CONSTRAINT applications_info_pkey PRIMARY KEY (id),
                                               CONSTRAINT fkdkylq9x1hk7694e4yjn8cfrun FOREIGN KEY (application_status_id) REFERENCES application.lk_application_status(id),
                                               CONSTRAINT fkhmpocjo1mkbi3xwfw4r54do79 FOREIGN KEY (lk_category_id) REFERENCES application.lk_application_category(id)
);


-- application.documents definition

-- Drop table

-- DROP TABLE application.documents;

CREATE TABLE application.documents (
                                       id int8 NOT NULL,
                                       created_by_user varchar(255) NULL,
                                       created_date timestamp NULL,
                                       modified_by_user varchar(255) NULL,
                                       modified_date timestamp NULL,
                                       is_deleted int4 NOT NULL,
                                       file_name varchar(255) NULL,
                                       nexuo_id varchar(255) NULL,
                                       uploaded_date timestamp NULL,
                                       application_id int8 NULL,
                                       document_type_id int8 NULL,
                                       lk_nexuo_user_id int8 NULL,
                                       CONSTRAINT documents_pkey PRIMARY KEY (id),
                                       CONSTRAINT fkeo9fonpj6jeitf45qhnsney7l FOREIGN KEY (lk_nexuo_user_id) REFERENCES application.lk_nexuo_user(id),
                                       CONSTRAINT fkqkwp1wslyo2q7ibp0oklbn8hb FOREIGN KEY (document_type_id) REFERENCES application.lk_document_type(id),
                                       CONSTRAINT fkt2p23eka8310uk3pbeo51vrvc FOREIGN KEY (application_id) REFERENCES application.applications_info(id)
);


-- application.lk_accelerated_type definition

-- Drop table

-- DROP TABLE application.lk_accelerated_type;

CREATE TABLE application.lk_accelerated_type (
                                                 id int8 NOT NULL,
                                                 created_by_user varchar(255) NULL,
                                                 created_date timestamp NULL,
                                                 modified_by_user varchar(255) NULL,
                                                 modified_date timestamp NULL,
                                                 is_deleted int4 NOT NULL,
                                                 name_ar varchar(255) NULL,
                                                 name_en varchar(255) NULL,
                                                 "show" bool NULL,
                                                 application_category_id int8 NULL,
                                                 CONSTRAINT lk_accelerated_type_pkey PRIMARY KEY (id),
                                                 CONSTRAINT fk8wydyjyv5biwsj5spaj2ak9vv FOREIGN KEY (application_category_id) REFERENCES application.lk_application_category(id)
);


-- application.lk_fee_cost definition

-- Drop table

-- DROP TABLE application.lk_fee_cost;

CREATE TABLE application.lk_fee_cost (
                                         id int8 NOT NULL,
                                         created_by_user varchar(255) NULL,
                                         created_date timestamp NULL,
                                         modified_by_user varchar(255) NULL,
                                         modified_date timestamp NULL,
                                         is_deleted int4 NOT NULL,
                                         "cost" int4 NULL,
                                         pay_later bool NULL,
                                         applicant_category_id int8 NULL,
                                         application_category_id int8 NULL,
                                         request_type_id int8 NULL,
                                         CONSTRAINT lk_fee_cost_pkey PRIMARY KEY (id),
                                         CONSTRAINT fkchiroxrrx9890ag2cbtqiq2p9 FOREIGN KEY (application_category_id) REFERENCES application.lk_application_category(id),
                                         CONSTRAINT fkj8qmba888lc5wd54dyamv49nk FOREIGN KEY (applicant_category_id) REFERENCES application.lk_applicant_category(id),
                                         CONSTRAINT fkoqhbk7n8bhm35yc8n1vqxj6d9 FOREIGN KEY (request_type_id) REFERENCES application.lk_request_type(id)
);


-- application.terms_and_conditions definition

-- Drop table

-- DROP TABLE application.terms_and_conditions;

CREATE TABLE application.terms_and_conditions (
                                                  id int8 NOT NULL,
                                                  created_by_user varchar(255) NULL,
                                                  created_date timestamp NULL,
                                                  modified_by_user varchar(255) NULL,
                                                  modified_date timestamp NULL,
                                                  is_deleted int4 NOT NULL,
                                                  body_ar varchar(255) NULL,
                                                  body_en varchar(255) NULL,
                                                  link varchar(255) NULL,
                                                  sorting int8 NULL,
                                                  title_ar varchar(255) NULL,
                                                  title_en varchar(255) NULL,
                                                  application_category_id int8 NULL,
                                                  request_type_id int8 NULL,
                                                  CONSTRAINT terms_and_conditions_pkey PRIMARY KEY (id),
                                                  CONSTRAINT fklxefgds65x96rbirlytlwqegc FOREIGN KEY (application_category_id) REFERENCES application.lk_application_category(id),
                                                  CONSTRAINT fkpvnwce1p4rff3eqq894eq463k FOREIGN KEY (request_type_id) REFERENCES application.lk_request_type(id)
);


-- application.application_accelerated definition

-- Drop table

-- DROP TABLE application.application_accelerated;

CREATE TABLE application.application_accelerated (
                                                     id int8 NOT NULL,
                                                     created_by_user varchar(255) NULL,
                                                     created_date timestamp NULL,
                                                     modified_by_user varchar(255) NULL,
                                                     modified_date timestamp NULL,
                                                     is_deleted int4 NOT NULL,
                                                     accelerated_examination bool NULL,
                                                     fast_track_examination bool NULL,
                                                     pph_examination bool NULL,
                                                     application_info_id int8 NULL,
                                                     closest_prior_art_documents_related_to_cited_references_file_id int8 NULL,
                                                     comparative_id int8 NULL,
                                                     fast_track_examination_target_area_id int8 NULL,
                                                     latest_patentable_claims_file_id int8 NULL,
                                                     CONSTRAINT application_accelerated_pkey PRIMARY KEY (id),
                                                     CONSTRAINT fk8xs24dkdp45yv6ftfc6scwesv FOREIGN KEY (fast_track_examination_target_area_id) REFERENCES application.lk_fast_track_examination_target_area(id),
                                                     CONSTRAINT fkawhrodvm6cvwhiltuhujsq48w FOREIGN KEY (latest_patentable_claims_file_id) REFERENCES application.documents(id),
                                                     CONSTRAINT fke6x2x33v961g9a2djdlkc9ppy FOREIGN KEY (closest_prior_art_documents_related_to_cited_references_file_id) REFERENCES application.documents(id),
                                                     CONSTRAINT fkl1sn4jwvifvkdfmoamak6dgo9 FOREIGN KEY (comparative_id) REFERENCES application.documents(id),
                                                     CONSTRAINT fkm2e9cx2kfeyskbitvevic7y6v FOREIGN KEY (application_info_id) REFERENCES application.applications_info(id)
);


-- application.application_priority definition

-- Drop table

-- DROP TABLE application.application_priority;

CREATE TABLE application.application_priority (
                                                  id int8 NOT NULL,
                                                  created_by_user varchar(255) NULL,
                                                  created_date timestamp NULL,
                                                  modified_by_user varchar(255) NULL,
                                                  modified_date timestamp NULL,
                                                  is_deleted int4 NOT NULL,
                                                  application_class varchar(255) NULL,
                                                  country_id int8 NULL,
                                                  das_code varchar(255) NULL,
                                                  filing_date date NULL,
                                                  priority_application_number varchar(255) NULL,
                                                  provide_doc_later bool NULL,
                                                  application_info_id int8 NULL,
                                                  priority_document_id int8 NULL,
                                                  priority_status_id int8 NULL,
                                                  translated_document_id int8 NULL,
                                                  CONSTRAINT application_priority_pkey PRIMARY KEY (id),
                                                  CONSTRAINT fk265j1j27h3klqqsrqf4cem2q9 FOREIGN KEY (priority_document_id) REFERENCES application.documents(id),
                                                  CONSTRAINT fk7ntn0qvvp747aojsq3w63moul FOREIGN KEY (application_info_id) REFERENCES application.applications_info(id),
                                                  CONSTRAINT fkk49ysqyh0cw1dj7wpq0u658p8 FOREIGN KEY (priority_status_id) REFERENCES application.lk_application_priority_status(id),
                                                  CONSTRAINT fkl95v6ypmr4u5l5g4bli1nsoqt FOREIGN KEY (translated_document_id) REFERENCES application.documents(id)
);


-- application.application_relevant_type definition

-- Drop table

-- DROP TABLE application.application_relevant_type;

CREATE TABLE application.application_relevant_type (
                                                       id int8 NOT NULL,
                                                       created_by_user varchar(255) NULL,
                                                       created_date timestamp NULL,
                                                       modified_by_user varchar(255) NULL,
                                                       modified_date timestamp NULL,
                                                       is_deleted int4 NOT NULL,
                                                       customer_code varchar(255) NULL,
                                                       inventor bool NULL,
                                                       "type" varchar(255) NULL,
                                                       application_info_id int8 NULL,
                                                       application_relevant_id int8 NULL,
                                                       waiver_document_id int8 NULL,
                                                       CONSTRAINT application_relevant_type_pkey PRIMARY KEY (id),
                                                       CONSTRAINT fk6pb2q1xufylslyfoarom2g5jv FOREIGN KEY (application_relevant_id) REFERENCES application.application_relevant(id),
                                                       CONSTRAINT fkmsrgmbdsoyhnm2m2mkr670m9t FOREIGN KEY (application_info_id) REFERENCES application.applications_info(id),
                                                       CONSTRAINT fko0g4acq2gdaq4n3lnj4cjs8db FOREIGN KEY (waiver_document_id) REFERENCES application.documents(id)
);


ALTER TABLE application.application_accelerated  ADD CONSTRAINT UNIQUE_ACCE_application_id UNIQUE (application_info_id);
ALTER TABLE application.application_relevant_type  ADD CONSTRAINT application_relevant_type_customer_code_application_info_id_key UNIQUE (customer_code, application_info_id, application_relevant_id);
;
