-- --------------------------------------- Start Patent -----------------------------

UPDATE application.lk_application_status SET application_category_id = 1
WHERE code in ('DRAFT', 'WAITING_FOR_APPLICATION_FEE_PAYMENT', 'THE_APPLICATION_IS_AS_IF_IT_NEVER_EXISTED', 'NEW', 'UNDER_FORMAL_PROCESS',
'ACCEPTANCE_OF_THE_REGISTRATION_APPLICATION', 'INVITATION_FOR_OBJECTIVE_CORRECTION', 'RETURNED_TO_THE_CLASSIFICATION_OFFICER',
'PAYMENT_OF_MODIFICATION_FEES_IS_PENDING', 'WAIVED', 'ACCEPTANCE', 'PUBLICATION_FEES_ARE_PENDING', 'PUBLISHED_ELECTRONICALLY', 'AWAITING_PAYMENT_OF_THE_GRIEVANCE_FEE',
'COMPLAINANT_TO_THE_GRIEVANCE_COMMITTEE', 'RETURNED_TO_THE_APPLICANT', 'INVITATION_FOR_FORMAL_CORRECTION', 'UNDER_OBJECTIVE_PROCESS', 'AWAITING_VERIFICATION',
'AWAITING_FOR_UPDATE_XML', 'IN_COMMITTEE', 'ABANDONED', 'APPLICANT_SENDING_UPDATES', 'COMPLETE_REQUIREMENTS',
'UNDER_PROCESS_BY_CLASSIFICATION_OFFICIAL', 'UNDER_REVIEW_BY_AN_OBJECTIVE_AUDITOR', 'REVOKED_FOR_NON_RENEWAL_OF_REGISTRATION',
'ACCEPTED_BY_THE_GRIEVANCES_COMMITTEE', 'REJECTED_BY_THE_GRIEVANCES_COMMITTEE', 'UNDER_STUDY_BY_THE_CLASSIFICATIONS_OFFICIAL',
'THE_APPLICANT_IS_INVITED_TO_ACCEPT_FORMAL_AMENDMENTS', 'CLOSED_DUE_TO_NON_PAYMENT_OF_APPLICATION_FEES', 'UNDER_REVIEW_BY_AN_CHECKER_AUDITOR');

UPDATE application.lk_application_status
SET ips_status_desc_ar_external='نشر قبول تسجيل براءة الإختراع', ips_status_desc_en_external='Publication of acceptance of patent registration'
WHERE code = 'PUBLISHED_ELECTRONICALLY' AND application_category_id = 1;

UPDATE application.lk_application_status
SET ips_status_desc_ar_external='تحت الدراسة', ips_status_desc_en_external='Under Process'
WHERE code='RETURNED_TO_THE_CLASSIFICATION_OFFICER' AND application_category_id = 1;

update application.lk_application_status
set application_category_id=1
where code='FORMAL_REJECTION';

UPDATE application.lk_application_status
SET ips_status_desc_ar_external='مرفوض شكليا', ips_status_desc_en_external='Formal Rejection'
WHERE code='FORMAL_REJECTION' AND application_category_id = 1;

-- --------------------------------------- END -------------------------------------------------------------------------


-- --------------------------------------- Start Industrial ------------------------------------------------------

INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'مرفوض', 'Rejected', 'FORMAL_REJECTION', 'مرفوض شكليا', 'Formal Rejection', 2);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'تحت المراجعة من المدقق الشكلى', 'Under review by an checker auditor', 'UNDER_REVIEW_BY_AN_CHECKER_AUDITOR', 'تحت الدراسة', 'Under Process', 2);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'مغلق لعدم سداد رسوم التقديم', 'Closed due to non-payment of application fees', 'CLOSED_DUE_TO_NON_PAYMENT_OF_APPLICATION_FEES', 'مغلق لعدم سداد رسوم التقديم', 'Closed due to non-payment of application fees', 2);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'معاد لمقدم الطلب لقبول التعديلات الشكلية', 'The applicant is invited to accept the formal amendments', 'THE_APPLICANT_IS_INVITED_TO_ACCEPT_FORMAL_AMENDMENTS', 'معاد لقبول التعديلات', 'Deadline for accepting amendments', 2);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'تحت الدراسة من مسئول التصنيفات ', 'Under study by the classifications official', 'UNDER_STUDY_BY_THE_CLASSIFICATIONS_OFFICIAL', 'تحت الدراسة', 'Under Process', 2);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'مرفوض من قبل لجنة التظلمات', 'Rejected by the Grievances Committee', 'REJECTED_BY_THE_GRIEVANCES_COMMITTEE', 'مرفوض من قبل لجنة التظلمات', 'Rejected by the Grievances Committee', 2);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'مقبول من قبل لجنة التظلمات', 'Accepted by the Grievances Committee', 'ACCEPTED_BY_THE_GRIEVANCES_COMMITTEE', 'مقبول من قبل لجنة التظلمات', 'Accepted by the Grievances Committee', 2);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'شطب لعدم تجديد التسجيل ', 'Cancellation for non-renewal of registration', 'REVOKED_FOR_NON_RENEWAL_OF_REGISTRATION', 'شطب لعدم تجديد الحماية', 'Cancellation for non-renewal of protection', 2);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'تحت المراجعة من المدقق الموضوعي ', 'Under review by an objective auditor', 'UNDER_REVIEW_BY_AN_OBJECTIVE_AUDITOR', 'تحت الدراسة', 'Under Process', 2);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'تحت الدراسة من مسئول التصنيفات ', 'Under Process by the classifications official', 'UNDER_PROCESS_BY_CLASSIFICATION_OFFICIAL', 'تحت الدراسة', 'Under Process', 2);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'استكمال متطلبات', 'Complete the requirements', 'COMPLETE_REQUIREMENTS', 'استكمال متطلبات', 'Complete the requirements', 2);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'ارسال تعديل الى مقدم الطلب', 'Sending updates to applicant', 'APPLICANT_SENDING_UPDATES', 'ارسال تعديل الى مقدم الطلب', 'Sending updates to applicant', 2);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'قيد اللجنة', 'In Committee', 'IN_COMMITTEE', 'مرفوض', 'rejected', 2);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'بإنتظار تعديل XML', 'Awaiting for update XML', 'AWAITING_FOR_UPDATE_XML', 'بإنتظار تعديل XML', 'Awaiting for update XML', 2);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'بانتظار التحقق', 'Awaiting for verifications', 'AWAITING_VERIFICATION', 'بانتظار النشر', 'Waiting for publication', 2);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'تحت الدراسة من الفاحص الموضوعي', 'Under consideration by the objective examiner', 'UNDER_OBJECTIVE_PROCESS', 'تحت الدراسة', 'Under Procedure', 2);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'معاد لمقدم الطلب', 'Return to the applicant', 'INVITATION_FOR_FORMAL_CORRECTION', 'معاد لأستكمال البيانات ', 'Return to complete the data', 2);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'معاد لمقدم الطلب', 'Returned to the applicant', 'RETURNED_TO_THE_APPLICANT', 'معاد لمقدم الطلب', 'Returned to the applicant', 2);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'متظلم امام لجنة التظلمات', 'Complainant before the Grievances Committee', 'COMPLAINANT_TO_THE_GRIEVANCE_COMMITTEE', 'التظلم أمام لجنة التظلمات', 'Grievance before the Grievances Committee', 2);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'بإنتظار  سداد رسوم التظلم', 'Awaiting payment of the grievance fee', 'AWAITING_PAYMENT_OF_THE_GRIEVANCE_FEE', 'بإنتظار  سداد رسوم التظلم', 'Awaiting payment of the grievance fee', 2);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'تم النشر إلكترونياً', 'Published Electronically', 'PUBLISHED_ELECTRONICALLY', 'نشر قبول تسجيل التصميم الصناعي', 'Publication of acceptance of industerial registration', 2);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'بانتظار سداد رسوم النشر', 'Pending payment of publication fees', 'PUBLICATION_FEES_ARE_PENDING', 'سداد رسوم النشر', 'Pay publication fees', 2);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'تم المنح', 'granted', 'ACCEPTANCE', 'تم المنح', 'granted', 2);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'متنازل عنه', 'Waived', 'WAIVED', 'متنازل عنه', 'Waived', 2);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'بانتظار سداد رسوم التعديل', 'Payment of modification fees is pending', 'PAYMENT_OF_MODIFICATION_FEES_IS_PENDING', 'بانتظار سداد رسوم التعديل', 'Payment of modification fees is pending', 2);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'معادة لمسؤول التصنيف', 'Returned to the classification officer', 'RETURNED_TO_THE_CLASSIFICATION_OFFICER', 'تحت الدراسة', 'Under Process', 2);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'معاد لمقدم الطلب', 'Return to the applicant', 'INVITATION_FOR_OBJECTIVE_CORRECTION', 'معاد لأستكمال البيانات ', 'Return to the applicant', 2);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'قبول طلب التسجيل', 'Acceptance of the registration application', 'ACCEPTANCE_OF_THE_REGISTRATION_APPLICATION', 'قبول طلب التسجيل', 'Acceptance of the registration application', 2);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'تحت الدراسة من مسؤول الفحص الشكلي', 'Under study by the formal examination official', 'UNDER_FORMAL_PROCESS', 'تحت الدراسة', 'Under Procedure', 2);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'الطلب كأن لم يكن', 'The application is as if it never existed', 'THE_APPLICATION_IS_AS_IF_IT_NEVER_EXISTED', 'الطلب كأن لم يكن', 'The application is as if it never existed', 2);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'جديد', 'New', 'NEW', 'جديد', 'New', 2);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'بانتظار سداد رسوم تقديم الطلب', 'Waiting for payment of the application fee', 'WAITING_FOR_APPLICATION_FEE_PAYMENT', 'سداد رسوم تقديم الطلب', 'Pay the application submission fee', 2);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'مسودة', 'Draft', 'DRAFT', 'مسودة', 'Draft', 2);

-- --------------------------------------- END -------------------------------------------------------



-- --------------------------------------- Start Trademark -------------------------------------------------------------------

UPDATE application.lk_application_status
SET application_category_id=5
WHERE code in ('RETURN_TO_THE_CLASSIFICATIONS_OFFICIAL', 'OBJECTOR', 'CROSSED_OUT_MARK', 'OBJECTIVE_REJECTION',
'THE_APPLICANT_INVITED_TO_ACCEPT_APPLICATION_WITH_CONDITION', 'ACCEPTED_TRADEMARK_ADMINISTRATION', 'REJECTED_BECAUSE_THE_OPPOSITION_ACCEPTED',
'WAITING_TO_PAY_TRADEMARK_REGISTRATION_FEES', 'THE_TRADEMARK_IS_REGISTERED', 'REVOKED_OF_THE_TRADEMARK_OWNER', 'REVOKED_PURSUANT_TO_COURT_RULING');

INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'تحت المراجعة من المدقق الشكلى', 'Under review by an checker auditor', 'UNDER_REVIEW_BY_AN_CHECKER_AUDITOR', 'تحت الدراسة', 'Under Process', 5);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'مغلق لعدم سداد رسوم التقديم', 'Closed due to non-payment of application fees', 'CLOSED_DUE_TO_NON_PAYMENT_OF_APPLICATION_FEES', 'مغلق لعدم سداد رسوم التقديم', 'Closed due to non-payment of application fees', 5);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'معاد لمقدم الطلب لقبول التعديلات الشكلية', 'The applicant is invited to accept the formal amendments', 'THE_APPLICANT_IS_INVITED_TO_ACCEPT_FORMAL_AMENDMENTS', 'معاد لقبول التعديلات', 'Deadline for accepting amendments', 5);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'تحت الدراسة من مسئول التصنيفات ', 'Under study by the classifications official', 'UNDER_STUDY_BY_THE_CLASSIFICATIONS_OFFICIAL', 'تحت الدراسة', 'Under Process', 5);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'مرفوض من قبل لجنة التظلمات', 'Rejected by the Grievances Committee', 'REJECTED_BY_THE_GRIEVANCES_COMMITTEE', 'مرفوض من قبل لجنة التظلمات', 'Rejected by the Grievances Committee', 5);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'مقبول من قبل لجنة التظلمات', 'Accepted by the Grievances Committee', 'ACCEPTED_BY_THE_GRIEVANCES_COMMITTEE', 'مقبول من قبل لجنة التظلمات', 'Accepted by the Grievances Committee', 5);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'شطب لعدم تجديد التسجيل ', 'Cancellation for non-renewal of registration', 'REVOKED_FOR_NON_RENEWAL_OF_REGISTRATION', 'شطب لعدم تجديد الحماية', 'Cancellation for non-renewal of protection', 5);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'تحت المراجعة من المدقق الموضوعي ', 'Under review by an objective auditor', 'UNDER_REVIEW_BY_AN_OBJECTIVE_AUDITOR', 'تحت الدراسة', 'Under Process', 5);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'معاد لمسئول التصنيفات ', 'Return To the classifications official', 'RETURN_TO_THE_CLASSIFICATIONS_OFFICIAL', 'تحت الدراسة', 'Under Process', 5);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'تحت الدراسة من مسئول التصنيفات ', 'Under Process by the classifications official', 'UNDER_PROCESS_BY_CLASSIFICATION_OFFICIAL', 'تحت الدراسة', 'Under Process', 5);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'استكمال متطلبات', 'Complete the requirements', 'COMPLETE_REQUIREMENTS', 'استكمال متطلبات', 'Complete the requirements', 5);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'ارسال تعديل الى مقدم الطلب', 'Sending updates to applicant', 'APPLICANT_SENDING_UPDATES', 'ارسال تعديل الى مقدم الطلب', 'Sending updates to applicant', 5);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'قيد اللجنة', 'In Committee', 'IN_COMMITTEE', 'مرفوض', 'rejected', 5);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'بإنتظار تعديل XML', 'Awaiting for update XML', 'AWAITING_FOR_UPDATE_XML', 'بإنتظار تعديل XML', 'Awaiting for update XML', 5);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'بانتظار التحقق', 'Awaiting for verifications', 'AWAITING_VERIFICATION', 'بانتظار النشر', 'Waiting for publication', 5);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'تحت الدراسة من الفاحص الموضوعي', 'Under consideration by the objective examiner', 'UNDER_OBJECTIVE_PROCESS', 'تحت الدراسة', 'Under Procedure', 5);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'معاد لمقدم الطلب', 'Return to the applicant', 'INVITATION_FOR_FORMAL_CORRECTION', 'معاد لأستكمال البيانات ', 'Return to complete the data', 5);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'معاد لمقدم الطلب', 'Returned to the applicant', 'RETURNED_TO_THE_APPLICANT', 'معاد لمقدم الطلب', 'Returned to the applicant', 5);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'متظلم امام لجنة التظلمات', 'Complainant before the Grievances Committee', 'COMPLAINANT_TO_THE_GRIEVANCE_COMMITTEE', 'التظلم أمام لجنة التظلمات', 'Grievance before the Grievances Committee', 5);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'بإنتظار  سداد رسوم التظلم', 'Awaiting payment of the grievance fee', 'AWAITING_PAYMENT_OF_THE_GRIEVANCE_FEE', 'بإنتظار  سداد رسوم التظلم', 'Awaiting payment of the grievance fee', 5);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'تم النشر إلكترونياً', 'Published Electronically', 'PUBLISHED_ELECTRONICALLY', 'نشر قبول تسجيل العلامة التجارية  ', 'Publication of acceptance of trademark registration', 5);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'بانتظار سداد رسوم النشر', 'Pending payment of publication fees', 'PUBLICATION_FEES_ARE_PENDING', 'سداد رسوم النشر', 'Pay publication fees', 5);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'تم المنح', 'granted', 'ACCEPTANCE', 'تم المنح', 'granted', 5);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'متنازل عنه', 'Waived', 'WAIVED', 'متنازل عنه', 'Waived', 5);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'بانتظار سداد رسوم التعديل', 'Payment of modification fees is pending', 'PAYMENT_OF_MODIFICATION_FEES_IS_PENDING', 'بانتظار سداد رسوم التعديل', 'Payment of modification fees is pending', 5);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'معاد لمقدم الطلب', 'Return to the applicant', 'INVITATION_FOR_OBJECTIVE_CORRECTION', 'معاد لأستكمال البيانات ', 'Return to the applicant', 5);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'قبول طلب التسجيل', 'Acceptance of the registration application', 'ACCEPTANCE_OF_THE_REGISTRATION_APPLICATION', 'قبول طلب التسجيل', 'Acceptance of the registration application', 5);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'تحت الدراسة من مسؤول الفحص الشكلي', 'Under study by the formal examination official', 'UNDER_FORMAL_PROCESS', 'تحت الدراسة', 'Under Procedure', 5);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'الطلب كأن لم يكن', 'The application is as if it never existed', 'THE_APPLICATION_IS_AS_IF_IT_NEVER_EXISTED', 'الطلب كأن لم يكن', 'The application is as if it never existed', 5);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'جديد', 'New', 'NEW', 'جديد', 'New', 5);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'بانتظار سداد رسوم تقديم الطلب', 'Waiting for payment of the application fee', 'WAITING_FOR_APPLICATION_FEE_PAYMENT', 'سداد رسوم تقديم الطلب', 'Pay the application submission fee', 5);
INSERT INTO application.lk_application_status
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, ips_status_desc_ar, ips_status_desc_en, code, ips_status_desc_ar_external, ips_status_desc_en_external, application_category_id)
VALUES((select max(id)+1 from application.lk_application_status), NULL, NULL, NULL, NULL, 0, 'مسودة', 'Draft', 'DRAFT', 'مسودة', 'Draft', 5);

-- --------------------------------------- END -------------------------------------------------------------------


--  ------------------------------------------ Migration Scripts -------------------------------------------------------------------
update application.applications_info set application_status_id = (select id from application.lk_application_status
where code = 'THE_APPLICANT_INVITED_TO_ACCEPT_APPLICATION_WITH_CONDITION' and application_category_id = 5) where application_status_id =
(select id from application.lk_application_status where code = 'ACCEPTANCE_SUBJECT_MODIFICATION');


update application.applications_info set application_status_id = (select id from application.lk_application_status
where code = 'PUBLISHED_ELECTRONICALLY' and application_category_id = 5) where application_status_id =
(select id from application.lk_application_status where code = 'PUBLISHED_ELECTRONICALLY_TR');


update application.applications_info set application_status_id = (select id from application.lk_application_status
where code = 'REVOKED_PURSUANT_TO_COURT_RULING' and application_category_id = 5) where application_status_id =
(select id from application.lk_application_status where code = 'REJECTED_DUE_TO_COURT_RULING');


update application.applications_info set application_status_id = (select id from application.lk_application_status
where code = 'REVOKED_PURSUANT_TO_COURT_RULING' and application_category_id = 5) where application_status_id =
(select id from application.lk_application_status where code = 'REVOKED_BY_COURT_ORDER');

update application.application_status_change_log set previous_status_id = (select id from application.lk_application_status
where code = 'THE_APPLICANT_INVITED_TO_ACCEPT_APPLICATION_WITH_CONDITION' and application_category_id = 5)
where previous_status_id = (select id from application.lk_application_status where code = 'ACCEPTANCE_SUBJECT_MODIFICATION');


update application.application_status_change_log set previous_status_id = (select id from application.lk_application_status
where code = 'REVOKED_PURSUANT_TO_COURT_RULING' and application_category_id = 5)
where previous_status_id = (select id from application.lk_application_status where code = 'REVOKED_BY_COURT_ORDER');

update application.application_status_change_log  set previous_status_id = (select id from application.lk_application_status
where code = 'REVOKED_PURSUANT_TO_COURT_RULING' and application_category_id = 5)
where previous_status_id = (select id from application.lk_application_status where code = 'REJECTED_DUE_TO_COURT_RULING');

update application.application_status_change_log set previous_status_id = (select id from application.lk_application_status
where code = 'PUBLISHED_ELECTRONICALLY' and application_category_id = 5)
where previous_status_id = (select id from application.lk_application_status where code = 'PUBLISHED_ELECTRONICALLY_TR');


update application.application_status_change_log set new_status_id = (select id from application.lk_application_status
where code = 'THE_APPLICANT_INVITED_TO_ACCEPT_APPLICATION_WITH_CONDITION' and application_category_id = 5)
where new_status_id = (select id from application.lk_application_status where code = 'ACCEPTANCE_SUBJECT_MODIFICATION');


update application.application_status_change_log set new_status_id = (select id from application.lk_application_status
where code = 'REVOKED_PURSUANT_TO_COURT_RULING' and application_category_id = 5)
where new_status_id = (select id from application.lk_application_status where code = 'REVOKED_BY_COURT_ORDER');

update application.application_status_change_log  set new_status_id = (select id from application.lk_application_status
where code = 'REVOKED_PURSUANT_TO_COURT_RULING' and application_category_id = 5)
where new_status_id = (select id from application.lk_application_status where code = 'REJECTED_DUE_TO_COURT_RULING');

update application.application_status_change_log set new_status_id = (select id from application.lk_application_status
where code = 'PUBLISHED_ELECTRONICALLY' and application_category_id = 5)
where new_status_id = (select id from application.lk_application_status where code = 'PUBLISHED_ELECTRONICALLY_TR');



---------------- Delete not needed status ------------------------------------------------------------------------------------------------
delete FROM application.lk_application_status
where code in('ACCEPTANCE_SUBJECT_MODIFICATION','PUBLISHED_ELECTRONICALLY_TR','REJECTED_DUE_TO_COURT_RULING', 'REVOKED_BY_COURT_ORDER');