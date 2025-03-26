delete from application.lk_support_service_request_status where code = 'PENDING_RENEWAL_FEES';

INSERT INTO application.lk_support_service_request_status
(id, code, name_ar, name_en, name_ar_external, name_en_external)
VALUES(
          (select max(id + 1) from application.lk_support_service_request_status)
      , 'PENDING_RENEWAL_FEES', 'بانتظار سداد رسوم التجديد', 'Pending Fees for renewal', 'سداد رسوم التجديد', 'Pay Renewal');
