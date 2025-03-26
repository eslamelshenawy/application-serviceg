UPDATE application.application_installments_config
SET desertion_duration=12, grace_duration=3, installment_type='ANNUAL', last_running_date='2023-08-09 22:48:49.616', notification_duration=3, payment_duration=3, payment_interval_years=1, application_desertion_status_id=(select id from application.lk_application_status las where code = 'WAIVED'), open_renewal_duration=0
WHERE id=1;

UPDATE application.application_installments_config
SET  desertion_duration=12, grace_duration=6, installment_type='RENEWAL', last_running_date='2023-08-09 22:48:49.729', notification_duration=3, payment_duration=6, payment_interval_years=5, application_desertion_status_id=(select id from application.lk_application_status las where code = 'WAIVED'), open_renewal_duration=12
WHERE id=2;

UPDATE application.application_installments_config
set desertion_duration=12, grace_duration=6, installment_type='RENEWAL', last_running_date='2023-08-09 22:48:49.805', notification_duration=3, payment_duration=6, payment_interval_years=10, application_desertion_status_id=47
WHERE id=3;
