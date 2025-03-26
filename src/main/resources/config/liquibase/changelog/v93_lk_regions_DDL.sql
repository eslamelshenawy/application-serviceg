--liquibase formatted sql
-- changeset application-service:lk_notes_DDL.sql

CREATE TABLE application.lk_regions (
            id int8 NOT NULL,
            code varchar(255) NULL,
            name_ar varchar(255) NULL,
            name_en varchar(255) NULL,
            CONSTRAINT lk_regions_pkey PRIMARY KEY (id)
);


INSERT INTO application.lk_regions (id, code, name_ar, name_en)
VALUES
    (1, 'RIYADH', 'الرياض', 'Riyadh'),
    (2, 'QASSIM', 'القصيم', 'Qassim'),
    (3, 'MAKKAH', 'مكة المكرمة', 'Makkah'),
    (4, 'AL_MEDINA', 'المدينة المنورة', 'Al-Medina'),
    (5, 'HAIL', 'حائل', 'Hail'),
    (6, 'AL_JOUF', 'الجوف', 'Al-Jouf'),
    (7, 'TABOUK', 'تبوك', 'Tabouk'),
    (8, 'NORTH', 'الحدود الشمالية', 'North'),
    (9, 'ASEER', 'عسير', 'Aseer'),
    (10, 'JAZAN', 'جازان', 'Jazan'),
    (11, 'NAJRAN', 'نجران', 'Najran'),
    (12, 'AL_BAHA', 'الباحة', 'Al-Baha'),
    (13, 'EASTERN', 'الشرقية', 'Eastern');