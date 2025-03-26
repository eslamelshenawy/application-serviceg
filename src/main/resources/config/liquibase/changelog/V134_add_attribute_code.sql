INSERT INTO application.lk_attributes (id,code,name_ar,name_en)
VALUES ((select max(id) + 1 from application.lk_attributes),'PROTICTION','عناصر الحماية','Protiction Elements');

INSERT INTO application.lk_attributes (id,code,name_ar,name_en)
VALUES ((select max(id) + 1 from application.lk_attributes),'SHAPE','الاشكال','Shapes');
