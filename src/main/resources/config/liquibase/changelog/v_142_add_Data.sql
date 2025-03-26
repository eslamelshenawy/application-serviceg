INSERT INTO application.lk_sections (id,code,name_ar,name_en)
VALUES ((select max(id) + 1 from application.lk_sections),'REPORT','التقارير','Report');

INSERT INTO application.lk_attributes (id,code,name_ar,name_en)
VALUES ((select max(id) + 1 from application.lk_attributes),'RESULT','نتيجة الفحص','The test result');
INSERT INTO application.lk_attributes (id,code,name_ar,name_en)
VALUES ((select max(id) + 1 from application.lk_attributes),'GRANT','مواضيع قابلة للمنح','Grantable topics');
