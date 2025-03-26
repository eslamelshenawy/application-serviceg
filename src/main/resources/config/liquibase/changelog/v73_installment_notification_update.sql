--liquibase formatted sql
-- changeset application-service:v73_installment_notification_update.sql


-- add columns to notification service

ALTER TABLE application.application_installment_notifications ADD if not exists notification_template_code varchar(50) NULL;
ALTER TABLE application.application_installment_notifications ADD if not exists user_name varchar(50) NULL;


truncate table application.application_installment_config_types;

INSERT INTO application.application_installment_config_types
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, notification_template_type, notification_type, application_installment_config_id)
VALUES(1, NULL, NULL, NULL, NULL, 0, 'PATENT_RENEWAL_DUE_PERIOD_STARTED', 'RENEWAL_DUE_PERIOD_STARTED', 1);

INSERT INTO application.application_installment_config_types
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, notification_template_type, notification_type, application_installment_config_id)
VALUES(2, NULL, NULL, NULL, NULL, 0, 'PATENT_RENEWAL_GRACE_PERIOD_STARTED', 'RENEWAL_GRACE_PERIOD_STARTED', 1);

INSERT INTO application.application_installment_config_types
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, notification_template_type, notification_type, application_installment_config_id)
VALUES(3, NULL, NULL, NULL, NULL, 0, 'PATENT_RENEWAL_APPLICATION_DESERTION', 'RENEWAL_APPLICATION_DESERTION', 1);




INSERT INTO application.application_installment_config_types
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, notification_template_type, notification_type, application_installment_config_id)
VALUES(4, NULL, NULL, NULL, NULL, 0, 'INDUSTRIAL_DESIGN_RENEWAL_DUE_PERIOD_STARTED', 'RENEWAL_DUE_PERIOD_STARTED', 2);

INSERT INTO application.application_installment_config_types
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, notification_template_type, notification_type, application_installment_config_id)
VALUES(5, NULL, NULL, NULL, NULL, 0, 'INDUSTRIAL_DESIGN_RENEWAL_GRACE_PERIOD_STARTED', 'RENEWAL_GRACE_PERIOD_STARTED', 2);

INSERT INTO application.application_installment_config_types
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, notification_template_type, notification_type, application_installment_config_id)
VALUES(6, NULL, NULL, NULL, NULL, 0, 'INDUSTRIAL_DESIGN_RENEWAL_APPLICATION_DESERTION', 'RENEWAL_APPLICATION_DESERTION', 2);





INSERT INTO application.application_installment_config_types
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, notification_template_type, notification_type, application_installment_config_id)
VALUES(7, NULL, NULL, NULL, NULL, 0, 'TRADEMARK_RENEWAL_DUE_PERIOD_STARTED', 'RENEWAL_DUE_PERIOD_STARTED', 3);

INSERT INTO application.application_installment_config_types
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, notification_template_type, notification_type, application_installment_config_id)
VALUES(8, NULL, NULL, NULL, NULL, 0, 'TRADEMARK_RENEWAL_GRACE_PERIOD_STARTED', 'RENEWAL_GRACE_PERIOD_STARTED', 3);

INSERT INTO application.application_installment_config_types
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, notification_template_type, notification_type, application_installment_config_id)
VALUES(9, NULL, NULL, NULL, NULL, 0, 'TRADEMARK_RENEWAL_APPLICATION_DESERTION', 'RENEWAL_APPLICATION_DESERTION', 3);
