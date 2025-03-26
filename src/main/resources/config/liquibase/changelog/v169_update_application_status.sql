UPDATE application.lk_application_status
SET ips_status_desc_ar='مرفوض بسبب قبول الاعتراض',
    ips_status_desc_en='Rejected because the objection was accepted',
    ips_status_desc_ar_external='رفض تسجيل العلامة التجارية بسبب قبول الاعتراض ',
    ips_status_desc_en_external='Refusal to register the trademark due to the acceptance of the objection',
    application_category_id=5
WHERE code = 'CROSSED_OUT_MARK';
