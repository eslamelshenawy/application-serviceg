UPDATE application.lk_application_status
SET ips_status_desc_ar='بانتظار سداد رسوم تقديم الطلب', ips_status_desc_en='Waiting for payment of the application fee',
    ips_status_desc_ar_external='سداد رسوم تقديم الطلب', ips_status_desc_en_external='Pay the application submission fee'
WHERE code='WAITING_FOR_APPLICATION_FEE_PAYMENT';
