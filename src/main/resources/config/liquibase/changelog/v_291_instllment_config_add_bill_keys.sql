alter table application.application_installments_config add column if not exists  bill_request_type_saip_code varchar(255);
alter table application.application_installments_config add column if not exists  publication_bill_request_type_saip_code varchar(255);

update  application.application_installments_config set bill_request_type_saip_code = 'RENEWAL_FEES_PAY' where application_category in ('INDUSTRIAL_DESIGN', 'TRADEMARK');

update  application.application_installments_config aic set publication_bill_request_type_saip_code  = 'TRADEMARK_RENEWAL_PUBLICATION_PAYMENT' where application_category = 'TRADEMARK';
