------------------------------------------
INSERT INTO application.lk_support_services (id, created_by_user, created_date, modified_by_user, modified_date,
                                             is_deleted, cost, desc_ar, desc_en, name_ar, name_en, code)
VALUES ((select (max(id) + 1) from application.lk_support_services), NULL, NULL, NULL, NULL, 0, 0,
        'إتاحة تعديل اسم المالك أو العنوان للعلامة التجارية بناء على طلب المالك',
        'Providing the owners name or title for the trademark to be modified upon the owners request',
        'EDIT_TRADEMARK_NAME_ADDRESS', 'Edit Trademark Data', 'EDIT_TRADEMARK_NAME_ADDRESS');


---------------------------------------

INSERT INTO application.support_service_application_categories
    (support_service_id, category_id)
VALUES ((select max(id) from application.lk_support_services), 5);

-----------------------------------------

ALTER TABLE application.applications_info
    ADD COLUMN owner_name_ar VARCHAR(255);

ALTER TABLE application.applications_info
    ADD COLUMN owner_name_en VARCHAR(255);

ALTER TABLE application.applications_info
    ADD COLUMN owner_address_ar VARCHAR(255);

ALTER TABLE application.applications_info
    ADD COLUMN owner_address_en VARCHAR(255);

-----------------------------------------

CREATE TABLE application.application_edit_name_address_request
(
    id                   int8 NOT NULL PRIMARY KEY,
    edit_type            VARCHAR(255),
    old_owner_name_ar    VARCHAR(255),
    new_owner_name_ar    VARCHAR(255),
    old_owner_name_en    VARCHAR(255),
    new_owner_name_en    VARCHAR(255),
    old_owner_address_ar VARCHAR(255),
    new_owner_address_ar VARCHAR(255),
    old_owner_address_en VARCHAR(255),
    new_owner_address_en VARCHAR(255),
    notes                TEXT
);



