

UPDATE application.lk_support_services set
                                           name_ar = 'الشطب الطوعي',
                                           desc_ar = 'خدمة تتيح شطب العلامة التجارية المسجلة بناءً على رغبة مالك العلامة التجارية'
WHERE code = 'VOLUNTARY_REVOKE' ;

UPDATE application.lk_support_services set
                                           name_ar='تجديد علامة',
                                           desc_ar='خدمة تتيح للمستفيد تجديد حماية العلامة التجارية لمدة 10 سنوات'
WHERE code='RENEWAL_FEES_PAY' ;

UPDATE application.lk_support_services set
                                           name_ar='الشطب بأمر المحكمة',
                                           desc_ar='خدمة تتيح شطب العلامة التجارية المسجلة بناءً على حكم قضائي صادر من المحكمة المختصة'
WHERE code='REVOKE_BY_COURT_ORDER' ;

UPDATE application.lk_support_services set
                                           name_ar='تظلم امام لجنة التظلمات على العلامات التجارية',
                                           desc_ar='خدمة تتيح للمستفيد التظلم امام لجنة التظلمات على العلامات التجارية  على قرار رفض تسجيل العلامة او تعليقه بشرط او رفض طلب تعديل فى شكل العلامة'
WHERE code='TRADEMARK_APPEAL_REQUEST' ;

UPDATE application.lk_support_services set
                                           name_ar='شطب الترخيص التعاقدي',
                                           desc_ar='خدمة تمكن مالك العلامة او المستفيد من الترخيص بشطب قيد عقد الترخيص باستعمال العلامة التجارية'
WHERE code='REVOKE_LICENSE_REQUEST' ;

UPDATE application.lk_support_services set
                                           name_ar='قصر المنتجات',
                                           desc_ar='خدمة تتيح شطب جزء من السلع أو الخدمات للعلامة التجارية المسجلة'
WHERE code='REVOKE_PRODUCTS' ;

UPDATE application.lk_support_services set
                                           name_ar='البحث عن علامة تجارية',
                                           desc_ar='خدمة البحث عن علامة تجارية بفئة محددة باختيار المستفيد بغرض التأكد من أن العلامة لم تسجل بالفئة المطلوبة'
WHERE code='TRADEMARK_APPLICATION_SEARCH' ;

UPDATE application.lk_support_services set
                                           name_ar='تعديل بيانات علامة',
                                           desc_ar='خدمة تتيح تعديل بيانات العلامة التجارية المسجلة:اسم المالك ، عنوان مالك العلامة ،تغيير اسم وعنوان مالك العلامة أو الوكيل ، تغيير اسم وكيل التسجيل'
WHERE code='EDIT_TRADEMARK_NAME_ADDRESS' ;

UPDATE application.lk_support_services set
                                           name_ar='تسجيل ترخيص تعاقدي',
                                           desc_ar='خدمة تتيح لمالك العلامة ترخيص أي شخص طبيعي أو معنوي باستعمال العلامة التجارية عن كل أو بعض السلع أو الخدمات المسجلة عنها العلامة'
WHERE code='LICENSING_REGISTRATION' ;

UPDATE application.lk_support_services set
                                           name_ar='الاعتراض',
                                           desc_ar='خدمة تتيح لكل ذي شأن تقديم الاعتراض على تسجيل علامة تجارية لال فترة نشرها صحيفة الملكية الفكرية'
WHERE code='OPPOSITION' ;

UPDATE application.lk_support_services set
                                           name_ar='نقل الملكية',
                                           desc_ar='خدمة تتيح لمن انتقلت اليه ملكية العلامة التجارية بتقديم طلب لنقل ملكية العلامة'
WHERE code='OWNERSHIP_CHANGE' ;

DELETE FROM application.support_service_application_categories
WHERE support_service_id = 11 and category_id = 5;

DELETE FROM application.support_service_application_categories
WHERE support_service_id = 10 and category_id = 5;
