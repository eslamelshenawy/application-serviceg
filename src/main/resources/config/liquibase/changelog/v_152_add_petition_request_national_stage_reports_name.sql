INSERT INTO application.lk_document_type
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES((select (max(id) + 1) from application.lk_document_type), NULL, NULL, NULL, NULL, 0, 'CONDITIONAL_REJECTION_PETITION_REQUEST_NATIONAL_STAGE', 'اشعار رفض مرتقب طلب التماس الدخول للمرحلة الوطنية', NULL, 'ConditionalRejectionPetitionRequestNationalStage', 'اشعار رفض مرتقب طلب التماس الدخول للمرحلة الوطنية', 'ConditionalRejectionPetitionRequestNationalStage');

INSERT INTO application.lk_document_type
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES((select (max(id) + 1) from application.lk_document_type), NULL, NULL, NULL, NULL, 0, 'ACCEPTANCE_PETITION_REQUEST_NATIONAL_STAGE', 'اشعار قبول طلب التماس الدخول للمرحلة الوطنية', NULL, 'AcceptancePetitionRequestNationalStage', 'اشعار قبول طلب التماس الدخول للمرحلة الوطنية', 'AcceptancePetitionRequestNationalStage');


INSERT INTO application.lk_document_type
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, category, description, doc_order, "name", name_ar, code)
VALUES((select (max(id) + 1) from application.lk_document_type), NULL, NULL, NULL, NULL, 0, 'REJECTION_PETITION_REQUEST_NATIONAL_STAGE', 'اشعار رفض  طلب التماس الدخول للمرحلة الوطنية', NULL, 'RejectionPetitionRequestNationalStage', 'اشعار رفض طلب التماس الدخول للمرحلة الوطنية', 'RejectionPetitionRequestNationalStage');


ALTER TABLE application.application_checking_reports
ALTER COLUMN application_id drop Not NULL;

