--liquibase formatted sql
-- changeset application-service:lk_application_priority_status.sql


INSERT INTO application.lk_application_priority_status (id,  is_deleted, ips_status_desc_ar, ips_status_desc_en)
VALUES (1,0, N'مقبولة', N'Accepted'),
       (2,0, N'مرفوضة', N'Rejected'),
       (3,0, N'غير محدد', N'NA'),
       (4,0, N'محذوف', N'Deleted');