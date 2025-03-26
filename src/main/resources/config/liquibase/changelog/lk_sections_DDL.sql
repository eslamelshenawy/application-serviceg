--liquibase formatted sql
-- changeset application-service:lk_sections_DDL.sql

CREATE TABLE IF NOT EXISTS application.lk_sections (
                                         id int4 NOT NULL,
                                         code varchar(255) NULL,
                                         name_ar varchar(255) NULL,
                                         name_en varchar(255) NULL,
                                         CONSTRAINT lk_sections_pkey PRIMARY KEY (id)
);