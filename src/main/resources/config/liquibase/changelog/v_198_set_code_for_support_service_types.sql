UPDATE application.lk_support_service_type
set code = 'WITHDRAW_APPLICATION_IF_PENDING'
WHERE desc_en = 'Withdraw the application (if the application is still pending)';


UPDATE application.lk_support_service_type
SET code = 'WITHDRAW_APPLICATION_UNLESS_FINAL_ACTION_ISSUED_BY_EXAMINERS_OFFICE'
WHERE desc_en = 'Withdraw the application (unless final action has been issued by the examiners'' office)';