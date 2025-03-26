--liquibase formatted sql
-- changeset application-service:lk_application_services.sql

CREATE TABLE application.lk_application_services (
                                                     id int8 NOT NULL,
                                                     created_by_user varchar(255) NULL,
                                                     created_date timestamp NULL,
                                                     modified_by_user varchar(255) NULL,
                                                     modified_date timestamp NULL,
                                                     is_deleted int4 NOT NULL,
                                                     code varchar(255) NULL,
                                                     name_ar varchar(255) NULL,
                                                     name_en varchar(255) NULL,
                                                     operation_number varchar(255) NULL,
                                                     category_id int8 NULL,
                                                     CONSTRAINT lk_application_services_pkey PRIMARY KEY (id),
                                                     CONSTRAINT fk7srp1hlfd0imh5ygpj42h2hw FOREIGN KEY (category_id) REFERENCES application.lk_application_category(id)
);

INSERT INTO application.lk_application_services (id, created_by_user, created_date, modified_by_user, modified_date,
                                                 is_deleted, code, name_ar, name_en, operation_number, category_id)
VALUES (1, NULL, NULL, NULL, NULL, 0, 'PATENTS_REGISTRATION', 'Patents registration', 'Patents registration', '01', 1),
       (2, NULL, NULL, NULL, NULL, 0, 'INTEGRATED_CIRCUITS_REGISTRATION', 'Integrated circuits registration',
        'Integrated circuits registration', '01', 2),
       (3, NULL, NULL, NULL, NULL, 0, 'PLANT_VARIETIES_REGISTRATION', 'Plant varieties registration',
        'Plant varieties registration', '01', 3),
       (4, NULL, NULL, NULL, NULL, 0, 'INDUSTRIAL_DESIGNS_REGISTRATION', 'Industrial designs registration',
        'Industrial designs registration', '01', 4),
       (5, NULL, NULL, NULL, NULL, 0, 'TRADEMARK_REGISTRATION', 'Trademark registration', 'Trademark registration',
        '01', 5),
       (6, NULL, NULL, NULL, NULL, 0, 'TRADEMARKS_LICENSING', 'Trademarks Licensing', 'Trademarks Licensing', '02', 5),
       (7, NULL, NULL, NULL, NULL, 0, 'COPYRIGHT_REGISTRATION', 'Copyright registration', 'Copyright registration',
        '01', 6),
       (8, NULL, NULL, NULL, NULL, 0, 'COPYRIGHT_LICENSING', 'Copyright Licensing', 'Copyright Licensing', '02', 6),
       (9, NULL, NULL, NULL, NULL, 0, 'COPYRIGHT_AMENDMENT', 'Copyright amendment', 'Copyright amendment', '03', 6),
       (10, NULL, NULL, NULL, NULL, 0, 'REGISTERING_AND_FOLLOWING_UP_ON_PATENTS_REQUESTS',
        'Registering and following-up on Patents requests', 'Registering and following-up on Patents requests', '01',
        7);
INSERT INTO application.lk_application_services (id, created_by_user, created_date, modified_by_user, modified_date,
                                                 is_deleted, code, name_ar, name_en, operation_number, category_id)
VALUES (11, NULL, NULL, NULL, NULL, 0, 'REGISTERING_AND_FOLLOWING_UP_ON_TRADEMARKS_REQUESTS',
        'Registering and following-up on Trademarks requests', 'Registering and following-up on Trademarks requests',
        '02', 7),
       (12, NULL, NULL, NULL, NULL, 0, 'REGISTERING_AND_FOLLOWING_UP_ON_INDUSTRIAL_DESIGNS_REQUESTS',
        'Registering and following-up on Industrial designs requests',
        'Registering and following-up on Industrial designs requests', '03', 7),
       (13, NULL, NULL, NULL, NULL, 0, 'REGISTERING_AND_FOLLOWING_UP_ON_COPYRIGHT_REQUESTS',
        'Registering and following-up on Copyright requests', 'Registering and following-up on Copyright requests',
        '04', 7),
       (14, NULL, NULL, NULL, NULL, 0, 'REGISTERING_AND_FOLLOWING_UP_ON_PLANT_VARIETIES_REQUESTS',
        'Registering and following-up on Plant varieties requests',
        'Registering and following-up on Plant varieties requests', '05', 7),
       (15, NULL, NULL, NULL, NULL, 0, 'REGISTERING_AND_FOLLOWING_UP_ON_INTEGRATED_CIRCUITS_REQUESTS',
        'Registering and following-up on Integrated circuits requests',
        'Registering and following-up on Integrated circuits requests', '06', 7),
       (16, NULL, NULL, NULL, NULL, 0, 'COPYRIGHT_COMPLAIN', 'Copyright complain', 'Copyright complain', '01', 8),
       (17, NULL, NULL, NULL, NULL, 0, 'TRADEMARKS_COMPLAIN', 'Trademarks complain', 'Trademarks complain', '02', 8);