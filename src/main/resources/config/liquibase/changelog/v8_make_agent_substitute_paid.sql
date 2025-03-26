--liquibase formatted sql
-- changeset application-service:v8_make_agent_substitute_paid.sql
update application.lk_support_service_type set is_free = false where code = 'SUBSTITUTE_AGENT';