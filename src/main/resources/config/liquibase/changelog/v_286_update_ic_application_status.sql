update application.lk_application_status set ips_status_desc_ar ='1 بانتظار الرد على تقرير الفحص', ips_status_desc_ar_external='بانتظار الرد على تقرير الفحص'
where code ='INVITATION_FOR_FORMAL_CORRECTION'
and application_category_id = (select id from application.lk_application_category where saip_code ='INTEGRATED_CIRCUITS');

INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, '2 بانتظار الرد على تقرير الفحص', 'Invitation For Formal Correction 2',
'INVITATION_FOR_FORMAL_CORRECTION_2', 'بانتظار الرد على تقرير الفحص', 'Invitation For Formal Correction', (select id from application.lk_application_category where saip_code ='INTEGRATED_CIRCUITS'));

update application.lk_application_status set ips_status_desc_ar ='مرفوض لعدم الرد علي تقرير الفحص 1', ips_status_desc_en ='Rejected for lack response of correction report 1',
ips_status_desc_ar_external='مرفوض', ips_status_desc_en_external='Rejected'
where code ='LACK_RESPONSE_REJECTION'
and application_category_id = (select id from application.lk_application_category where saip_code ='INTEGRATED_CIRCUITS');

INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'مرفوض لعدم الرد علي تقرير الفحص 2', 'Rejected for lack response of correction report 1',
'LACK_RESPONSE_REJECTION_2', 'مرفوض', 'Rejected', (select id from application.lk_application_category where saip_code ='INTEGRATED_CIRCUITS'));

update application.lk_application_status set ips_status_desc_ar ='بإنتظار سداد المقابل المالي التسجيل والنشر', ips_status_desc_en ='Payment of financial fees for registration and publication is pending',
ips_status_desc_ar_external='بإنتظار سداد المقابل المالي التسجيل والنشر', ips_status_desc_en_external='Payment of financial fees for registration and publication is pending'
where code ='PAYMENT_OF_FINANCIAL_GRANTS_FEES_IS_PENDING'
and application_category_id = (select id from application.lk_application_category where saip_code ='INTEGRATED_CIRCUITS');

update application.lk_application_status set ips_status_desc_ar ='ساقط لعدم سداد رسوم التسجيل والنشر', ips_status_desc_en ='Dismissed payment of the financial fees for registration and publication',
ips_status_desc_ar_external='ساقط لعدم سداد رسوم التسجيل والنشر', ips_status_desc_en_external='Dismissed payment of the financial fees for registration and publication'
where code ='DISMISSED_PAYMENT_OF_GRANTS_PUBLICATION_FEES'
and application_category_id = (select id from application.lk_application_category where saip_code ='INTEGRATED_CIRCUITS');

update application.lk_application_status set ips_status_desc_ar ='تم التسجيل', ips_status_desc_en ='Registration Done', ips_status_desc_ar_external='مسجل', ips_status_desc_en_external='Registered'
where code ='ACCEPTANCE'
and application_category_id = (select id from application.lk_application_category where saip_code ='INTEGRATED_CIRCUITS');