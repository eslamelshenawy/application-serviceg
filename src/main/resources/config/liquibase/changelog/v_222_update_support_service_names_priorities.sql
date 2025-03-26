update
    application.lk_support_services
set
    desc_ar = 'إضافة أو تعديل ألأسبقية',
    desc_en = 'Add or Edit Priority',
    name_ar = 'إضافة أو تعديل ألأسبقية',
    name_en = 'Add or Edit Priority'
where
        code = 'PATENT_PRIORITY_REQUEST';

update
    application.lk_support_services
set

    desc_ar = 'طلب التماس تصحيح أو إضافة أسبقية',
    desc_en = 'Priority Modification or Addition Request',
    name_ar = 'طلب التماس تصحيح أو إضافة أسبقية',
    name_en = 'Priority Modification or Addition Request'
where
        code = 'PATENT_PRIORITY_MODIFY';