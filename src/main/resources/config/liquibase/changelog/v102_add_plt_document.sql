INSERT INTO application.lk_document_type (id,is_deleted,category,description,"name",name_ar,code)
VALUES ((select max(id) + 1 from application.lk_document_type),0,'APPLICATION','وثيقة حد ادنى للمتطلبات','PLT Document','وثيقة حد ادنى للمتطلبات','PLT Document');
