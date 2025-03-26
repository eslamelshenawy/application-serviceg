INSERT INTO application.lk_attributes (id,code,name_ar,name_en)
VALUES ((select max(id) + 1 from application.lk_attributes),'PROTICTION_AR','عناصر الحماية بالعربية','Protiction ar');
INSERT INTO application.lk_attributes (id,code,name_ar,name_en)
VALUES ((select max(id) + 1 from application.lk_attributes),'PROTICTION_EN','عناصر الحماية بالانجليزية','Protiction en');

