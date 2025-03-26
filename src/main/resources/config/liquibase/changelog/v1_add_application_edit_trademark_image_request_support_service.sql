-- Add Support Service Code
INSERT INTO application.lk_support_services (id, created_by_user, created_date, modified_by_user, modified_date,
                                             is_deleted, cost, desc_ar, desc_en, name_ar, name_en, code)
VALUES ((select (max(id) + 1) from application.lk_support_services), NULL, NULL, NULL, NULL, 0, 0,
        'خدمة تتيح تعديل صورة العلامة التجارية المسجلة, على ألا يكون التعديل جوهريا',
        'A service that allows modifying the image of a registered trademark, provided that the modification is not fundamental',
        'تعديل صورة العلامة', 'Edit Trademark Image', 'EDIT_TRADEMARK_IMAGE');


-- Add Support Service Application Categories

INSERT INTO application.support_service_application_categories
(support_service_id, category_id)
VALUES ((select max(id) from application.lk_support_services), 5);

-- Create Application Edit Trademark Image Request

CREATE TABLE IF NOT EXISTS application.application_edit_trademark_image_request (
    id int8 NOT NULL PRIMARY KEY,
    old_document_id int8,
    new_document_id int8,
    old_description VARCHAR(255),
    new_description VARCHAR(255),
    old_name_ar VARCHAR(255),
    new_name_ar VARCHAR(255),
    old_name_en VARCHAR(255),
    new_name_en VARCHAR(255),
    FOREIGN KEY (old_document_id) REFERENCES application.documents(id),
    FOREIGN KEY (new_document_id) REFERENCES application.documents(id)
    );

-- Add Support Service Publication Type

INSERT INTO application.lk_publication_type
(id, code, name_ar, name_en, application_category_id)
VALUES((select max(id) + 1 from application.lk_publication_type),
       'EDIT_TRADEMARK_IMAGE', 'تعديل صورة علامة', 'Edit Trademark Image', 5);

-- Add document Type

INSERT INTO application.lk_document_type
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES
    ((SELECT (MAX(id) + 1) FROM application.lk_document_type), NULL, NULL, NULL, NULL, 0, 'APPLICATION', 'تعديل صورة علامة', 1, 'EDIT_TRADEMARK_IMAGE', 'تعديل صورة علامة', 'EDIT_TRADEMARK_IMAGE');







