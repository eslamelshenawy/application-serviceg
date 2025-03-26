ALTER TABLE application.customer_ext_classify ADD duration_days int4 NULL;
alter table application.customer_ext_classify add constraint UK2j5b5kty3oe754bpqgj7fhpju unique (customer_ext_classify_type, application_id);
