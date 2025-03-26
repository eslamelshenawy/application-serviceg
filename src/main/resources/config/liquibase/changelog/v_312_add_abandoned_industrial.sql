-- Auto-generated SQL script #202408141219
INSERT INTO application.lk_application_status (id,is_deleted,ips_status_desc_ar,ips_status_desc_en,code,ips_status_desc_ar_external,ips_status_desc_en_external,application_category_id)
VALUES ((select max(id)+1 from application.lk_application_status),0,'متخلي عنه','Abandoned','ABANDONED','متخلي عنه','Abandoned',2);
