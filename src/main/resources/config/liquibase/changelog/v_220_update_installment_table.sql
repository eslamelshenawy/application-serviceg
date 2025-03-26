alter table application.application_installments add column if not exists installment_count INT DEFAULT 1 not null;
alter table application.application_installments add column if not exists support_service_id int8;
alter table application.application_installments drop constraint if exists FKrojlgj1vin6ivsnsbvwv8jtgk;
alter table application.application_installments add constraint FKrojlgj1vin6ivsnsbvwv8jtgk foreign key (support_service_id) references application.application_support_services_type;
ALTER TABLE application.application_installments DROP COLUMN if exists is_dates_updated;
