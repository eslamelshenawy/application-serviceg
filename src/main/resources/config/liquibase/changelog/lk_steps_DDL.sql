--liquibase formatted sql
-- changeset application-service:lk_steps_DDL.sql

CREATE TABLE IF NOT EXISTS application.lk_steps (
                                      id int4 NOT NULL,
                                      code varchar(255) NULL,
                                      name_ar varchar(255) NULL,
                                      name_en varchar(255) NULL,
                                      CONSTRAINT lk_steps_pkey PRIMARY KEY (id)
);