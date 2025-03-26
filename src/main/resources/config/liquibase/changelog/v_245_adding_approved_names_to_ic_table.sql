alter table application.integrated_circuits
    add column if not exists approved_name_ar varchar(255) null,
    add column if not exists approved_name_en varchar(255) null;