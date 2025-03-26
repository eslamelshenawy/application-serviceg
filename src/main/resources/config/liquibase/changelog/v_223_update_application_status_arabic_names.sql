update
    application.lk_application_status
set
    ips_status_desc_ar = 'معاد لمقدم الطلب من الفحص الشكلى'
where
    code = 'INVITATION_FOR_FORMAL_CORRECTION';



update
    application.lk_application_status
set
    ips_status_desc_ar = 'معاد لمقدم الطلب من الفحص الموضوعى'
where
    code = 'INVITATION_FOR_OBJECTIVE_CORRECTION';