INSERT INTO application.lk_support_service_request_status (id, code, name_ar, name_en)
VALUES ((SELECT MAX(id) + 1 FROM application.lk_support_service_request_status), 'PENDING_IMG_FEES_MOD_REQ', 'بانتظار سداد رسوم طلب تعديل الصورة',
        'Pending Fees for Image Modification Request');

INSERT INTO application.lk_support_service_request_status (id, code, name_ar, name_en)
VALUES ((SELECT MAX(id) + 1 FROM application.lk_support_service_request_status), 'UNDER_REVIEW_VISUAL', 'تحت الدراسة من مسؤول الفحص الشكلي',
        'Under Review by Visual Inspection Responsible');

INSERT INTO application.lk_support_service_request_status (id, code, name_ar, name_en)
VALUES ((SELECT MAX(id) + 1 FROM application.lk_support_service_request_status), 'UNDER_REVIEW_SUBJECTIVE', 'تحت الدراسة من الفاحص الموضوعي',
        'Under Review by Subjective Inspector');

INSERT INTO application.lk_support_service_request_status (id, code, name_ar, name_en)
VALUES ((SELECT MAX(id) + 1 FROM application.lk_support_service_request_status), 'UNDER_REVIEW_AUDITOR', 'تحت الدراسة من المدقق الموضوعي',
        'Under Review by Subjective Auditor');

INSERT INTO application.lk_support_service_request_status (id, code, name_ar, name_en)
VALUES ((SELECT MAX(id) + 1 FROM application.lk_support_service_request_status), 'ACCEPTED_WITH_MOD', 'قبول الطلب مع إمكانية التعديل',
        'Request Accepted with Modification Possibility');

INSERT INTO application.lk_support_service_request_status (id, code, name_ar, name_en)
VALUES ((SELECT MAX(id) + 1 FROM application.lk_support_service_request_status), 'ACCEPTED_MOD_REQUEST', 'قبول طلب تعديل صورة العلامة',
        'Accepted Image Modification Request');

INSERT INTO application.lk_support_service_request_status (id, code, name_ar, name_en)
VALUES ((SELECT MAX(id) + 1 FROM application.lk_support_service_request_status), 'PENDING_IMG_FEES_MOD_PUB', 'بانتظار سداد رسوم نشر تعديل الصورة',
        'Pending Fees for Image Modification Publication');

INSERT INTO application.lk_support_service_request_status (id, code, name_ar, name_en)
VALUES ((SELECT MAX(id) + 1 FROM application.lk_support_service_request_status), 'PUBLISHED_ELECTRONIC', 'تم النشر الإلكتروني',
        'Electronic Publication Completed');

INSERT INTO application.lk_support_service_request_status (id, code, name_ar, name_en)
VALUES ((SELECT MAX(id) + 1 FROM application.lk_support_service_request_status), 'TRADEMARK_IMAGE_MODIFIED', 'تم تعديل صورة العلامة',
        'Trademark Image Modified');

INSERT INTO application.lk_support_service_request_status (id, code, name_ar, name_en)
VALUES ((SELECT MAX(id) + 1 FROM application.lk_support_service_request_status), 'REJECTED_BY_MANAGEMENT', 'مرفوض من إدارة العلامات التجارية',
        'Rejected by Trademark Management');

INSERT INTO application.lk_support_service_request_status (id, code, name_ar, name_en)
VALUES ((SELECT MAX(id) + 1 FROM application.lk_support_service_request_status), 'WAIVED', 'متنازل عنه', 'Waived');

INSERT INTO application.lk_support_service_request_status (id, code, name_ar, name_en)
VALUES ((SELECT MAX(id) + 1 FROM application.lk_support_service_request_status), 'PENDING_FEES_COMPLAINT',
        'بانتظار سداد رسوم التظلم', 'Pending Fees for Complaint');

INSERT INTO application.lk_support_service_request_status (id, code, name_ar, name_en)
VALUES ((SELECT MAX(id) + 1 FROM application.lk_support_service_request_status), 'COMPLAINANT_TO_COMMITTEE', 'متظلم لدى لجنة التظلمات',
        'Complainant to Complaints Committee');

INSERT INTO application.lk_support_service_request_status (id, code, name_ar, name_en)
VALUES ((SELECT MAX(id) + 1 FROM application.lk_support_service_request_status), 'ACCEPTED_BY_COMMITTEE', 'مقبول من لجنة التظلمات',
        'Accepted by Complaints Committee');
