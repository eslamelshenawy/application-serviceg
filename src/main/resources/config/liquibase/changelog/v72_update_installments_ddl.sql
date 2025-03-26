--liquibase formatted sql
-- changeset application-service:v72_update_installments_ddl.sql

-- update installments
ALTER TABLE application.application_installments ALTER COLUMN end_due_date TYPE timestamp USING end_due_date::timestamp;
ALTER TABLE application.application_installments ALTER COLUMN start_due_date TYPE timestamp USING start_due_date::timestamp;
ALTER TABLE application.application_installments ALTER COLUMN last_due_date TYPE timestamp USING last_due_date::timestamp;

-- add columns to installment table
alter table application.application_installments add column if not exists exception_message text null;
alter table application.application_installments add column if not exists bill_number varchar(255);
alter table application.application_installments add column if not exists penalty_cost numeric(19, 2);
alter table application.application_installments add column if not exists tax_cost numeric(19, 2);
alter table application.application_installments add column if not exists fee_cost numeric(19, 2);
alter table application.application_installments add column if not exists is_dates_updated boolean default false;

-- drop installment columns
ALTER TABLE application.application_installments DROP COLUMN if exists amount;
ALTER TABLE application.application_installments DROP COLUMN if exists penalty_amount;

-- add installment constrain

delete from application.application_installments where application_id in (
    select ai.application_id from application.application_installments ai
    group by application_id, installment_index
    having count(1) > 1
);

ALTER TABLE application.application_installments ADD CONSTRAINT application_installments_app_index_un UNIQUE (installment_index,application_id);

-- add columns to installment config
alter table application.application_installments_config add column if not exists application_desertion_status_id int8 NULL;
ALTER TABLE application.application_installments_config add column if not exists open_renewal_duration int4 NULL;
update  application.application_installments_config set open_renewal_duration = 12, application_desertion_status_id = 22;

-- annual fees updates
ALTER TABLE application.annual_fees_request ADD if not exists cost_codes varchar(400) NULL;

ALTER TABLE application.annual_fees_request DROP COLUMN if exists service_type_pay;

-- create table lock table
CREATE TABLE if not exists public.shedlock (
    "name" varchar(64) NOT NULL,
    lock_until timestamp(3) NULL,
    locked_at timestamp(3) NULL,
    locked_by varchar(255) NULL,
    CONSTRAINT shedlock_pkey PRIMARY KEY (name)
    );



