update application.lk_application_status set ips_status_desc_ar_external='بانتظار سداد تكاليف الفحص الموضوعي',
                                             ips_status_desc_en_external='Pending payment of substantive examination',
                                             ips_status_desc_ar ='بانتظار سداد تكاليف الفحص الموضوعي',
                                             ips_status_desc_en= 'Pending payment of substantive examination'

where code='PENDING_PAYMENT_OF_SUBSTANTIVE_EXAMINATION_PUBLICATION_COSTS' and application_category_id = '1';