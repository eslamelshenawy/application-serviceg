-- Create table lk_licence_types
CREATE TABLE application.lk_licence_types
(
    id      int8 NOT NULL,
    code    varchar(255) NULL,
    name_ar varchar(255) NULL,
    name_en varchar(255) NULL,
    CONSTRAINT lk_licence_types_pkey PRIMARY KEY (id)
);

-- Insert data into lk_licence_types
INSERT INTO application.lk_licence_types (id, code, name_ar, name_en)
VALUES
    (1, 'CONTRACTUAL', 'تعاقدي', 'contractual'),
    (2, 'MANDATORY', 'إلزامي', 'mandatory'),
    (3, 'EDIT_LICENCE', 'تعديل ترخيص', 'edit licence'),
    (4, 'CANCEL_LICENCE', 'إلغاء ترخيص', 'cancel licence');

-- Create table lk_licence_purposes
CREATE TABLE application.lk_licence_purposes
(
    id      int8 NOT NULL,
    code    varchar(255) NULL,
    name_ar varchar(255) NULL,
    name_en varchar(255) NULL,
    CONSTRAINT lk_licence_purposes_pkey PRIMARY KEY (id)
);

-- Insert data into lk_licence_purposes
INSERT INTO application.lk_licence_purposes (id, code, name_ar, name_en)
VALUES
    (1, 'GOVERNMENT_USE', 'الاستخدام الحكومي', 'government use'),
    (2, 'PRIVATE_SECTOR_USE', 'استخدام القطاع الخاص', 'private sector use'),
    (3, 'EDIT_LICENCE_COVER', 'تحرير غلاف الترخيص', 'edit licence cover'),
    (4, 'EDIT_LICENCE_PERIOD', 'تعديل فترة الترخيص', 'edit licence period');

-- Alter table licence_request to add columns if they do not exist
ALTER TABLE application.licence_request
    ADD COLUMN licence_type_id int8,
    ADD COLUMN licence_purpose_id int8;
-- add constrain in tables-----
alter table application.licence_request add constraint fk_reference_lk_licence_type foreign key (licence_type_id) references application.lk_licence_types;
alter table application.licence_request add constraint fk_reference_lk_licence_purposes foreign key (licence_purpose_id) references application.lk_licence_purposes;

