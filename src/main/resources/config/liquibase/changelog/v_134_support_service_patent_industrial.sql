
INSERT INTO application.lk_support_services (id,created_by_user,created_date,modified_by_user,modified_date,is_deleted,"cost",desc_ar,desc_en,name_ar,name_en,code) VALUES
            (25,NULL,NULL,NULL,NULL,0,0,'طلب التخلي','Eviction Request','طلب التخلي','Eviction Request','EVICTION'),
            (26,NULL,NULL,NULL,NULL,0,0,'طلب تمديد مهلة','Extension Request','طلب تمديد مهلة','Extension Request','EXTENSION'),
            (27,NULL,NULL,NULL,NULL,0,0,'طلب انسحاب','Retraction Request','طلب انسحاب','Retraction Request','RETRACTION'),
            (28,NULL,NULL,NULL,NULL,0,0,'طلب التماس للاستعادة','Petition Recovery Request','طلب التماس للاستعادة','Petition Recovery Request','PETITION_RECOVERY'),
            (29,NULL,NULL,NULL,NULL,0,0,'طلب التعديل الاولي','Initial Modification Request','طلب التعديل الاولي','Initial Modification Request','INITIAL_MODIFICATION'),
            (30,NULL,NULL,NULL,NULL,0,0,'طلب تغير الوكيل','Agent Substitution Request','طلب تغير الوكيل','Agent Substitution Request','AGENT_SUBSTITUTION'),
            (31,NULL,NULL,NULL,NULL,0,0,'خدمة تتيح لمالك البراءة ترخيص أي شخص طبيعي أو معنوي باستعمال البراءة عن كل أو بعض السلع أو الخدمات المسجلة عنها البراءة','Licensing Registration','تسجيل ترخيص تعاقدي','Licensing Registration','LICENSING_REGISTRATION'),
            (32,NULL,NULL,NULL,NULL,0,0,'تعديل ترخيص','Licensing Modification','تعديل ترخيص','Licensing Modification','LICENSING_MODIFICATION');
INSERT INTO application.lk_support_services (id,created_by_user,created_date,modified_by_user,modified_date,is_deleted,"cost",desc_ar,desc_en,name_ar,name_en,code) VALUES
            (33,NULL,NULL,NULL,NULL,0,0,'طلب تظلم','Appeal Request','طلب تظلم','Appeal Request','APPEAL_REQUEST'),
            (34,NULL,NULL,NULL,NULL,0,0,'خدمة تتيح لمن انتقلت اليه ملكية البراءة بتقديم طلب لنقل ملكية البراءة','Ownership Change','نقل الملكية','Ownership Change','OWNERSHIP_CHANGE'),
            (35,NULL,NULL,NULL,NULL,0,0,'دفع الرسوم السنوية','Annual Fees Pay','دفع الرسوم السنوية','Annual Fees Pay','ANNUAL_FEES_PAY'),
            (36,NULL,NULL,NULL,NULL,0,0,'خدمة تتيح شطب البراءة المسجلة بناءً على رغبة مالك البراءة','Voluntary Revoke','الشطب الطوعي','Voluntary Revoke','VOLUNTARY_REVOKE'),
            (37,NULL,NULL,NULL,NULL,0,0,'خدمة تتيح شطب البراءة المسجلة بناءً على حكم قضائي صادر من المحكمة المختصة','Revoke By Court Order','الشطب بأمر المحكمة','Revoke By Court Order','REVOKE_BY_COURT_ORDER'),
            (38,NULL,NULL,NULL,NULL,0,0,'خدمة تمكن مالك البراءة او المستفيد من الترخيص بشطب قيد عقد الترخيص باستعمال البراءة','Revoke Licence Request','شطب الترخيص التعاقدي','Revoke Licence Request','REVOKE_LICENSE_REQUEST');
INSERT INTO application.lk_support_services (id,created_by_user,created_date,modified_by_user,modified_date,is_deleted,"cost",desc_ar,desc_en,name_ar,name_en,code) VALUES
            (39,NULL,NULL,NULL,NULL,0,0,'طلب الاعتراض على شطب ترخيص','Opposition Revoke Licence Request','طلب الاعتراض على شطب ترخيص','Opposition Revoke Licence Request','OPPOSITION_REVOKE_LICENCE_REQUEST');

delete from application.support_service_application_categories where category_id = 1;

INSERT INTO application.support_service_application_categories (support_service_id,category_id) VALUES
            (25,1),
            (26,1),
            (27,1),
            (28,1),
            (29,1),
            (30,1),
            (31,1),
            (32,1),
            (33,1),
            (34,1);
INSERT INTO application.support_service_application_categories (support_service_id,category_id) VALUES
            (35,1),
            (36,1),
            (37,1),
            (38,1),
            (39,1);

INSERT INTO application.lk_support_services (id,created_by_user,created_date,modified_by_user,modified_date,is_deleted,"cost",desc_ar,desc_en,name_ar,name_en,code) VALUES
            (40,NULL,NULL,NULL,NULL,0,0,'طلب التخلي','Eviction Request','طلب التخلي','Eviction Request','EVICTION'),
            (41,NULL,NULL,NULL,NULL,0,0,'طلب تمديد مهلة','Extension Request','طلب تمديد مهلة','Extension Request','EXTENSION'),
            (42,NULL,NULL,NULL,NULL,0,0,'طلب انسحاب','Retraction Request','طلب انسحاب','Retraction Request','RETRACTION'),
            (43,NULL,NULL,NULL,NULL,0,0,'طلب التماس للاستعادة','Petition Recovery Request','طلب التماس للاستعادة','Petition Recovery Request','PETITION_RECOVERY'),
            (44,NULL,NULL,NULL,NULL,0,0,'طلب التعديل الاولي','Initial Modification Request','طلب التعديل الاولي','Initial Modification Request','INITIAL_MODIFICATION'),
            (45,NULL,NULL,NULL,NULL,0,0,'طلب تغير الوكيل','Agent Substitution Request','طلب تغير الوكيل','Agent Substitution Request','AGENT_SUBSTITUTION'),
            (46,NULL,NULL,NULL,NULL,0,0,'خدمة تتيح لمالك التصميم ترخيص أي شخص طبيعي أو معنوي باستعمال التصميم عن كل أو بعض السلع أو الخدمات المسجلة عنها التصميم','Licensing Registration','تسجيل ترخيص تعاقدي','Licensing Registration','LICENSING_REGISTRATION'),
            (47,NULL,NULL,NULL,NULL,0,0,'تعديل ترخيص','Licensing Modification','تعديل ترخيص','Licensing Modification','LICENSING_MODIFICATION');
INSERT INTO application.lk_support_services (id,created_by_user,created_date,modified_by_user,modified_date,is_deleted,"cost",desc_ar,desc_en,name_ar,name_en,code) VALUES
            (48,NULL,NULL,NULL,NULL,0,0,'طلب تظلم','Appeal Request','طلب تظلم','Appeal Request','APPEAL_REQUEST'),
            (49,NULL,NULL,NULL,NULL,0,0,'خدمة تتيح لمن انتقلت اليه ملكية التصميم بتقديم طلب لنقل ملكية التصميم','Ownership Change','نقل الملكية','Ownership Change','OWNERSHIP_CHANGE'),
            (50,NULL,NULL,NULL,NULL,0,0,'دفع الرسوم السنوية','Annual Fees Pay','دفع الرسوم السنوية','Annual Fees Pay','ANNUAL_FEES_PAY'),
            (51,NULL,NULL,NULL,NULL,0,0,'خدمة تتيح شطب التصميم المسجلة بناءً على رغبة مالك التصميم','Voluntary Revoke','الشطب الطوعي','Voluntary Revoke','VOLUNTARY_REVOKE'),
            (52,NULL,NULL,NULL,NULL,0,0,'خدمة تتيح شطب التصميم المسجلة بناءً على حكم قضائي صادر من المحكمة المختصة','Revoke By Court Order','الشطب بأمر المحكمة','Revoke By Court Order','REVOKE_BY_COURT_ORDER'),
            (53,NULL,NULL,NULL,NULL,0,0,'خدمة تمكن مالك التصميم او المستفيد من الترخيص بشطب قيد عقد الترخيص باستعمال التصميم','Revoke Licence Request','شطب الترخيص التعاقدي','Revoke Licence Request','REVOKE_LICENSE_REQUEST');
INSERT INTO application.lk_support_services (id,created_by_user,created_date,modified_by_user,modified_date,is_deleted,"cost",desc_ar,desc_en,name_ar,name_en,code) VALUES
            (54,NULL,NULL,NULL,NULL,0,0,'طلب الاعتراض على شطب ترخيص','Opposition Revoke Licence Request','طلب الاعتراض على شطب ترخيص','Opposition Revoke Licence Request','OPPOSITION_REVOKE_LICENCE_REQUEST');

delete from application.support_service_application_categories where category_id = 2;

INSERT INTO application.support_service_application_categories (support_service_id,category_id) VALUES
            (40,2),
            (41,2),
            (42,2),
            (43,2),
            (44,2),
            (45,2),
            (46,2),
            (47,2),
            (48,2),
            (49,2);
INSERT INTO application.support_service_application_categories (support_service_id,category_id) VALUES
            (50,2),
            (51,2),
            (52,2),
            (53,2),
            (54,2);