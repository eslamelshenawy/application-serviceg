--liquibase formatted sql
-- changeset application-service:lk_applicant_category.sql


INSERT INTO application.lk_applicant_category
(id, is_deleted, applicant_category_name_ar, applicant_category_name_en)
VALUES (1, 0, 'فرد مواطن', 'NATURAL_PERSON_WITH_NATIONALITY'),
       (2, 0, 'شركة مواطنة', 'CORPORATION_WITH_NATIONALITY'),
       (3, 0, 'فرد أجنبي', 'NATURAL_PERSON_WITH_FOREIGN_NATIONALITY'),
       (4, 0, 'شركة أجنبية', 'FOREIGN_CORPORATION');

