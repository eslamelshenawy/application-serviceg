--liquibase formatted sql
-- changeset application-service:v2_lk_application_status.sql

ALTER TABLE application.applications_info ALTER COLUMN application_status_id DROP NOT NULL;

UPDATE application.applications_info SET application_status_id = null WHERE application_status_id IS not null;

DELETE FROM application.lk_application_status WHERE true;

ALTER table application.lk_application_status
       ADD COLUMN IF NOT EXISTS code varchar(255) NULL;

INSERT INTO application.lk_application_status (id, ips_status_desc_ar, ips_status_desc_en, code, is_deleted)
VALUES (1, N'مسودة', N'Draft','DRAFT', 0),
       (2, N'انتظار دفع رسوم التقديم', N'Waiting for application fee payment', 'WAITING_FOR_APPLICATION_FEE_PAYMENT', 0),
       (3, N'جديد', N'New', 'NEW', 0),
       (4, N'الطلب كأن لم يكن', N'The application is as if it never existed', 'THE_APPLICATION_IS_AS_IF_IT_NEVER_EXISTED', 0),
       (5, N'تحت الاجراء ', N'Under process', 'UNDER_PROCESS', 0),
       (6, N'قبول طلب التسجيل ', N'Acceptance of the registration application', 'ACCEPTANCE_OF_THE_REGISTRATION_APPLICATION', 0),
       (7, N'إعادة ارسال', N'Sendback', 'SENDBACK', 0),
       (8, N'رفض', N'Refusal', 'REFUSAL', 0),
       (9, N'دعوة للتصحيح الموضوعي', N'Invitation for objective correction', 'INVITATION_FOR_OBJECTIVE_CORRECTION',0),
       (10, N'معادة لمسؤول التصنيف', N'Returned to the classification officer', 'RETURNED_TO_THE_CLASSIFICATION_OFFICER',0),
       (11, N'بانتظار سداد رسوم التعديل ', N'Payment of modification fees is pending', 'PAYMENT_OF_MODIFICATION_FEES_IS_PENDING',0),
       (12, N'متنازل عنه', N'Waived', 'WAIVED',0),
       (13, N'قبول', N'Acceptance', 'ACCEPTANCE',0),
       (14, N'بانتظار سداد رسوم النشر', N'Publication fees are pending', 'PUBLICATION_FEES_ARE_PENDING',0),
       (15, N'تم النشر إلكترونياً', N'Published electronically', 'PUBLISHED_ELECTRONICALLY',0),
       (16, N'بإنتظار  سداد رسوم التظلم', N'Awaiting payment of the grievance fee', 'AWAITING_PAYMENT_OF_THE_GRIEVANCE_FEE',0),
       (17, N'متظلم للجنة التظلمات', N'Complainant to the Grievance Committee', 'COMPLAINANT_TO_THE_GRIEVANCE_COMMITTEE',0),
       (18, N'معاد لمقدم الطلب', N'Returned to the applicant', 'RETURNED_TO_THE_APPLICANT',0),
       (19, N'معترض', N'Objector', 'OBJECTOR',0),
       (20, N'قبول الاعتراض', N'Accept the objection', 'ACCEPT_THE_OBJECTION',0),
       (21, N'رفض الاعتراض ', N'Reject the objection', 'REJECT_THE_OBJECTION',0),
       (22, N'علامة مشطوبة', N'Crossed out mark', 'CROSSED_OUT_MARK',0),
       (23, N'دعوة للتصحيح الشكلي', N'Invitation for formal correction', 'INVITATION_FOR_FORMAL_CORRECTION',0);

UPDATE application.applications_info SET application_status_id = 3 WHERE application_status_id IS null;

ALTER TABLE application.applications_info ALTER COLUMN application_status_id SET NOT NULL;