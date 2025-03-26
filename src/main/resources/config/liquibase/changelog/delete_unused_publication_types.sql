DELETE FROM application.lk_publication_type
WHERE code = 'LICENCE_CANCELLATION' and application_category_id = 5;

DELETE FROM application.lk_publication_type
WHERE code = 'TRADEMARK_PICTURE_EDIT' and application_category_id = 5;

DELETE FROM application.lk_publication_type
WHERE code = 'OWNERORADDRESS_EDIT' and application_category_id = 5;

DELETE FROM application.lk_publication_type
WHERE code = 'TRADEMARK_PLEDGE' and application_category_id = 5;

DELETE FROM application.lk_publication_type
WHERE code = 'UNLOCK_TRADEMARK' and application_category_id = 5;