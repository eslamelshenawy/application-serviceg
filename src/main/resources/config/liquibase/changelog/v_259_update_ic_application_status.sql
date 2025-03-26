delete from application.lk_application_status where code ='UNDER_FORMAL_PROCESS' and ips_status_desc_ar ='تحت الدراسة من مسؤول الفحص الشكلي'
and application_category_id = (select id from application.lk_application_category where saip_code ='INTEGRATED_CIRCUITS');

update application.lk_application_status set ips_status_desc_ar ='تحت الفحص', ips_status_desc_ar_external='تحت الفحص' where code ='UNDER_FORMAL_PROCESS' and ips_status_desc_ar ='تحت الفحص الشكلي'
       and application_category_id = (select id from application.lk_application_category where saip_code ='INTEGRATED_CIRCUITS');

update application.lk_application_status set ips_status_desc_ar ='تحت التدقيق', ips_status_desc_ar_external='تحت الفحص' where code ='UNDER_REVIEW_BY_AN_CHECKER_AUDITOR'
       and application_category_id = (select id from application.lk_application_category where saip_code ='INTEGRATED_CIRCUITS');

update application.lk_application_status set ips_status_desc_ar ='معاد لمقدم الطلب' where code ='INVITATION_FOR_FORMAL_CORRECTION'
       and application_category_id = (select id from application.lk_application_category where saip_code ='INTEGRATED_CIRCUITS');

update application.lk_application_status set ips_status_desc_ar ='مرفوض', ips_status_desc_ar_external='مرفوض' where code ='FORMAL_REJECTION'
       and application_category_id = (select id from application.lk_application_category where saip_code ='INTEGRATED_CIRCUITS');


