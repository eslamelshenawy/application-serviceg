UPDATE application.lk_application_status
SET ips_status_desc_ar_external='بانتظار سداد رسوم تقديم الطلب'
WHERE code='WAITING_FOR_APPLICATION_FEE_PAYMENT' and application_category_id = (select id FROM application.lk_application_category x
         WHERE x.saip_code = 'INTEGRATED_CIRCUITS');

UPDATE application.lk_application_status
SET ips_status_desc_ar='بانتظار استكمال مستندات الأسبقية', ips_status_desc_ar_external='بانتظار استكمال مستندات الأسبقية'
WHERE code='IC_WAITING_DOCUMENTS_ATTACHMENT';

UPDATE application.lk_application_status
SET ips_status_desc_ar='معاد للفاحص', ips_status_desc_en='Return to checker',  ips_status_desc_ar_external='تحت الفحص', ips_status_desc_en_external='under examination'
WHERE code='RETURN_TO_THE_FORMAL_EXAMINER' and application_category_id = (select id FROM application.lk_application_category x
         WHERE x.saip_code = 'INTEGRATED_CIRCUITS');

UPDATE application.lk_application_status
SET ips_status_desc_ar='بانتظار الرد على تقرير الفحص الشكلي', ips_status_desc_en='Waiting for a response to the formal examination report', 
ips_status_desc_ar_external='بانتظار الرد على تقرير الفحص الشكلي', ips_status_desc_en_external='Waiting for a response to the formal examination report'
WHERE code='INVITATION_FOR_FORMAL_CORRECTION' and application_category_id = (select id FROM application.lk_application_category x
         WHERE x.saip_code = 'INTEGRATED_CIRCUITS');

UPDATE application.lk_application_status
SET ips_status_desc_ar='مرفوض لعدم الرد', ips_status_desc_en='Rejected for lack of response',
ips_status_desc_ar_external='مرفوض لعدم الرد على تقرير الفحص الشكلي', ips_status_desc_en_external='Rejected for not responding to the formal examination report'
WHERE code='LACK_RESPONSE_REJECTION';

UPDATE application.lk_application_status
SET ips_status_desc_ar='ساقط لعدم سداد رسوم المنح والنشر', ips_status_desc_ar_external='ساقط لعدم سداد رسوم المنح والنشر'
WHERE code='DISMISSED_PAYMENT_OF_GRANTS_PUBLICATION_FEES';

UPDATE application.lk_application_status
SET ips_status_desc_ar='ساقط لعدم سداد رسوم المقابل المالي السنوي', ips_status_desc_ar_external='ساقط لعدم سداد رسوم المقابل المالي السنوي'
WHERE code='DISMISSED_PAYMENT_OF_ANNUAL_FEES';