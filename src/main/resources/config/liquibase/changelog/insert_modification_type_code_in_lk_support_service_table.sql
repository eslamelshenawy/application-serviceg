UPDATE application.lk_support_service_type
SET  code='TITLE_MODIFICATION'
WHERE  desc_en='Address modification' and type ='INITIAL_MODIFICATION';

UPDATE application.lk_support_service_type
SET  code='SPECIFICATION_MODIFICATION'
WHERE  desc_en='Modify specifications' and type ='INITIAL_MODIFICATION';

UPDATE application.lk_support_service_type
SET  desc_en='Title modification'
WHERE  desc_en='Address modification' and type ='INITIAL_MODIFICATION';





