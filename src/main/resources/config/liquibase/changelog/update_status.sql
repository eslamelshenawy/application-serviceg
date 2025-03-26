UPDATE application.lk_application_status
SET ips_status_desc_ar='بانتظار التحقق',
    ips_status_desc_en='Awaiting for verifications',
    ips_status_desc_ar_external='بانتظار النشر',
    ips_status_desc_en_external='Waiting for publication'
WHERE code = 'AWAITING_VERIFICATION';

UPDATE application.lk_application_status
SET ips_status_desc_ar_external='سداد رسوم تسجيل العلامة التجارية',
    ips_status_desc_en_external='Pay the trademark registration fees'
WHERE code = 'WAITING_TO_PAY_TRADEMARK_REGISTRATION_FEES';
