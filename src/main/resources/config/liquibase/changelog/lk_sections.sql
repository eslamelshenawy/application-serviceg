--liquibase formatted sql
-- changeset application-service:lk_sections.sql

INSERT INTO application.lk_sections (id, code, name_ar, name_en)
VALUES
    (1, 'SEC001', 'قسم 1', 'Section 1'),
    (2, 'SEC002', 'قسم 2', 'Section 2'),
    (3, 'SEC003', 'قسم 3', 'Section 3'),
    (4, 'SEC004', 'قسم 4', 'Section 4'),
    (5, 'SEC005', 'قسم 5', 'Section 5'),
    (6, 'SEC006', 'قسم 6', 'Section 6');
