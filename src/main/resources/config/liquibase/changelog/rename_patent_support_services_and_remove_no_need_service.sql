-- remove no need services

DELETE
FROM application.support_service_application_categories
WHERE support_service_id = 39
  AND category_id = 1;

DELETE
FROM application.support_service_application_categories
WHERE support_service_id = 38
  AND category_id = 1;

DELETE
FROM application.support_service_application_categories
WHERE support_service_id = 37
  AND category_id = 1;

DELETE
FROM application.support_service_application_categories
WHERE support_service_id = 36
  AND category_id = 1;

DELETE
FROM application.support_service_application_categories
WHERE support_service_id = 30
  AND category_id = 1;

-- update certificate names
UPDATE application.lk_certificate_types
SET name_ar='صوره طبق الاصل',
    name_en='Exact Copy Image'
WHERE code = 'CERTIFIED_REGISTER_COPY';


-- update support services in patent

UPDATE application.lk_support_services
SET name_ar='طلب تسجيل ترخيص تعاقدي/إجباري',
    name_en='Registration of a Contractual/Compulsory License Request'
WHERE code = 'LICENSING_REGISTRATION'
  and id = 31;

UPDATE application.lk_support_services
SET desc_ar='خدمة تتيح لمن انتقلت اليه ملكية البراءة بتقديم طلب لتغيير ملكية البراءة',
    name_ar='طلب تغيير ملكية',
    name_en='Ownership Change Request'
WHERE id = 34
  and code = 'OWNERSHIP_CHANGE';

UPDATE application.lk_support_services
SET desc_ar='سحب براءة الاختراع',
    desc_en='Withdrawing the Patent',
    name_ar='طلب سحب براءة الاختراع',
    name_en='Withdrawing the Patent Request'
WHERE id = 27
  and code = 'RETRACTION';

UPDATE application.lk_support_services
SET desc_ar='سداد المقابل المالي السنوي للطلبات المودعة الكترونيا',
    desc_en='pay the annual financial fee for applications filed electronically',
    name_ar='طلب سداد المقابل المالي السنوي للطلبات المودعة الكترونيا',
    name_en='Request to pay the annual financial fee for applications filed electronically'
WHERE id = 35
  and code = 'ANNUAL_FEES_PAY';

INSERT INTO application.lk_certificate_types
    (id, code, name_ar, name_en)
VALUES (14, 'ISSUE_CERTIFICATE', 'طلب إعادة إصدار وثيقة حماية',
        'Request to reissue a protection document');

UPDATE application.certificate_types_application_categories
SET lk_certificate_type_id=14,
    lk_category_id=1
where lk_certificate_type_id = 1
  and lk_category_id = 1;

UPDATE application.lk_support_services
SET desc_ar='تعديل / إلغاء ترخيص',
    desc_en='Licensing Cancellation/Modification',
    name_ar='تعديل / إلغاء ترخيص',
    name_en='Licensing Cancellation/Modification'
WHERE id = 32
  and code = 'LICENSING_MODIFICATION';






