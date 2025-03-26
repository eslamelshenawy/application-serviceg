update application.lk_support_services lks set is_deleted = 1 where lks.code = 'VOLUNTARY_REVOKE'
                                                                and lks.desc_ar = 'خدمة تتيح شطب التصميم المسجلة بناءً على رغبة مالك التصميم';

update application.lk_support_services lks set is_deleted = 1 where lks.code = 'REVOKE_BY_COURT_ORDER'
                                                                and lks.desc_ar = 'خدمة تتيح شطب التصميم المسجلة بناءً على حكم قضائي صادر من المحكمة المختصة';
