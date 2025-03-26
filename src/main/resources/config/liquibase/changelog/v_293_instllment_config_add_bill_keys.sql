alter table application.application_installments_config add column if not exists  bill_request_type_saip_code_penalty varchar(255);

update  application.application_installments_config set bill_request_type_saip_code_penalty = 'RENEWAL_FEES_PAY_PENALTY' where application_category in ('TRADEMARK');

