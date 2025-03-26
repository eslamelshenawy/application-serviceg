--liquibase formatted sql
-- changeset application-service:lk_application_status_adding_closed.sql

INSERT INTO application.lk_application_status (id, ips_status_desc_ar, ips_status_desc_en, is_deleted)
VALUES (32, N'طلب مغلق', 'Closed', 0);