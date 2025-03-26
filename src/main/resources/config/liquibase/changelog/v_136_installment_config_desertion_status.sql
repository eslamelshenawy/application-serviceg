update application.application_installments_config
set application_desertion_status_id =
        (select s.id from application.lk_application_status s where s.code = 'REVOKED_FOR_NON_RENEWAL_OF_REGISTRATION' and s.application_category_id  = 5 limit 1)
where application_category = 'TRADEMARK' and installment_type = 'RENEWAL';


update application.application_installments_config
set application_desertion_status_id =
        (select s.id from application.lk_application_status s where s.code = 'WAIVED' and s.application_category_id  = 1 limit 1)
where application_category = 'PATENT' and installment_type = 'ANNUAL';


update application.application_installments_config
set application_desertion_status_id =
        (select s.id from application.lk_application_status s where s.code = 'WAIVED' and s.application_category_id  = 2 limit 1)
where application_category = 'INDUSTRIAL_DESIGN' and installment_type = 'RENEWAL';
