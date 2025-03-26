alter table application.agent_substitution_request add column application_support_services_type_id int8;
alter table application.eviction_request add column application_support_services_type_id int8;
alter table application.initial_modification_request add column application_support_services_type_id int8;
alter table application.extension_request add column application_support_services_type_id int8;
alter table application.lk_support_services add column code varchar(255);
alter table application.petition_recovery_request add column application_support_services_type_id int8;
alter table application.retraction_request add column application_support_services_type_id int8;
alter table application.agent_substitution_request add constraint FKea9r7ap7bkrhoqht062u1f11s foreign key (application_support_services_type_id) references application.application_support_services_type;
alter table application.eviction_request add constraint FKcfa2d44fvscff0uq0jn7593gu foreign key (application_support_services_type_id) references application.application_support_services_type;
alter table application.extension_request add constraint FKmfvcj9m29ywyjcnfvc110dm3h foreign key (application_support_services_type_id) references application.application_support_services_type;
alter table application.initial_modification_request add constraint FKfmu6dcvwwlbargxci647pji66 foreign key (application_support_services_type_id) references application.application_support_services_type;
alter table application.petition_recovery_request add constraint FKlfv462ql2194twx0yu3vi8pk foreign key (application_support_services_type_id) references application.application_support_services_type;
alter table application.retraction_request add constraint FKcxqdw6wlb6r7fprju5j5v19p3 foreign key (application_support_services_type_id) references application.application_support_services_type;
ALTER TABLE application.agent_substitution_request DROP CONSTRAINT fkowrlkph2mk9qw10r17cagfie6;
ALTER TABLE application.agent_substitution_request DROP COLUMN application_info_id;
ALTER TABLE application.eviction_request DROP CONSTRAINT fkb15t30rqv907saq1p8ur81wp2;
ALTER TABLE application.eviction_request DROP COLUMN application_info_id;
ALTER TABLE application.extension_request DROP CONSTRAINT fkypim45as6ics8a3h03blko3s;
ALTER TABLE application.extension_request DROP COLUMN application_info_id;
ALTER TABLE application.initial_modification_request DROP CONSTRAINT fkfh8v2adhdwgunibd8xy1sjbw1;
ALTER TABLE application.initial_modification_request DROP COLUMN application_info_id;
ALTER TABLE application.petition_recovery_request DROP CONSTRAINT fk3dn6c9g4ucugcy3dw6bw9o8sj;
ALTER TABLE application.petition_recovery_request DROP COLUMN application_info_id;
ALTER TABLE application.retraction_request DROP CONSTRAINT fk8pg84r8q1or1h0e5kl1ie4bcq;
ALTER TABLE application.retraction_request DROP COLUMN application_info_id;
ALTER TABLE application.application_support_services_type DROP COLUMN support_service_request_id;

-- insert lookup values for support services
INSERT INTO application.lk_support_services (id, name_ar, name_en, desc_ar, desc_en, code, cost, is_deleted)
    VALUES (1, N'Eviction Request', N'طلب التخلي', N'نبذة عن الخدمة', N'description', 'EVICTION', 0 ,0);
INSERT INTO application.lk_support_services (id, name_ar, name_en, desc_ar, desc_en, code, cost, is_deleted)
    VALUES (2, N'Extension Request', N'طلب تمديد مهلة', N'نبذة عن الخدمة', N'description', 'EXTENSION', 0 ,0);
INSERT INTO application.lk_support_services (id, name_ar, name_en, desc_ar, desc_en, code, cost, is_deleted)
    VALUES (3, N'Retraction Request', N'طلب انسحاب', N'نبذة عن الخدمة', N'description', 'RETRACTION', 0 ,0);
INSERT INTO application.lk_support_services (id, name_ar, name_en, desc_ar, desc_en, code, cost, is_deleted)
    VALUES (4, N'Petition Recovery Request', N'طلب التماس للاستعادة', N'نبذة عن الخدمة', N'description', 'PETITION_RECOVERY', 0 ,0);
INSERT INTO application.lk_support_services (id, name_ar, name_en, desc_ar, desc_en, code, cost, is_deleted)
    VALUES (5, N'Initial Modification Request', N'طلب التعديل الاولي', N'نبذة عن الخدمة', N'description', 'INITIAL_MODIFICATION', 0 ,0);
INSERT INTO application.lk_support_services (id, name_ar, name_en, desc_ar, desc_en, code, cost, is_deleted)
    VALUES (6, N'Agent Substitution Request', N'طلب تغير الوكيل', N'نبذة عن الخدمة', N'description', 'AGENT_SUBSTITUTION', 0 ,0);

-- insert lookup values for support service types options
INSERT INTO application.lk_support_service_type (id, is_deleted, desc_ar, desc_en, type)
VALUES(1, 0, 'تعديل العنوان', 'Address modification', 'INITIAL_MODIFICATION');
INSERT INTO application.lk_support_service_type (id, is_deleted, desc_ar, desc_en, type)
VALUES(2, 0, 'تعديل المواصفات', 'Modify specifications', 'INITIAL_MODIFICATION');
INSERT INTO application.lk_support_service_type (id, is_deleted, desc_ar, desc_en, type)
VALUES(3, 0, 'سحب الطلب (إذا كان الطلب لا يزال معلقًا)', 'Withdraw the application (if the application is still pending)', 'RETRACTION');
INSERT INTO application.lk_support_service_type (id, is_deleted, desc_ar, desc_en, type)
VALUES(4, 0, 'سحب الطلب (ما لم يصدر الإجراء النهائي لمكتب الفاحصين)', 'Withdraw the application (unless final action has been issued by the examiners'' office)', 'RETRACTION');
INSERT INTO application.lk_support_service_type (id, is_deleted, desc_ar, desc_en, type)
VALUES(5, 0, 'سحب دعوى الأولوية', 'Withdraw the priority claim', 'RETRACTION');
INSERT INTO application.lk_support_service_type (id, is_deleted, desc_ar, desc_en, type)
VALUES(6, 0, 'التماس الانسحاب من النشر', 'Petition to withdraw from publication', 'RETRACTION');
INSERT INTO application.lk_support_service_type (id, is_deleted, desc_ar, desc_en, type)
VALUES(7, 0, 'تمديد المهلة لتقديم المستندات الداعمة بعد دفع رسوم الإيداع (شهرين)', 'Extending the deadline for submitting supporting documents after paying the filing fees (two months).', 'EXTENSION');
INSERT INTO application.lk_support_service_type (id, is_deleted, desc_ar, desc_en, type)
VALUES(8, 0, 'تمديد المهلة لتقديم الرد الخاص بالفحص الشكلي والفحص الموضوعي (شهرين)', 'Extension of the deadline for submitting a response to the formal examination and the objective examination (two months)', 'EXTENSION');
INSERT INTO application.lk_support_service_type (id, is_deleted, desc_ar, desc_en, type)
VALUES(9, 0, 'تمديد المهلة لرسوم الفحص أو لرسوم النشر والمنح. (شهرين)', 'Extension of the deadline for examination fees or for publication and grant fees. (two months).', 'EXTENSION');
INSERT INTO application.lk_support_service_type (id, is_deleted, desc_ar, desc_en, type)
VALUES(10, 0, 'انقضى الطلب بسبب عدم دفع رسوم فحص البراءات ورسوم النشر / المنحة', 'The application lapsed due to non-payment of patent examination and publication/grant fees', 'PETITION_RECOVERY');
INSERT INTO application.lk_support_service_type (id, is_deleted, desc_ar, desc_en, type)
VALUES(11, 0, 'تم التخلي عن طلبات براءات الاختراع بسبب عدم الاستجابة لقرار الرفض الصادر عن فاحصي SAIP', 'Patent applications were abandoned due to failure to respond to the refusal decision of SAIP examiners', 'PETITION_RECOVERY');
