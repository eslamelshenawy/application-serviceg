INSERT INTO application.lk_support_service_request_status (id, code, name_ar, name_en, name_ar_external, name_en_external)
VALUES ((select max(id) + 1 from application.lk_support_service_request_status ), 'AGENT_DELETION_APPROVED', 'تم قبول حذف الوكيل',
        'Deletion of agent has been approved', 'تم قبول حذف الوكيل', 'Deletion of agent has been approved');