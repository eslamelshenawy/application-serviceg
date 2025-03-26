INSERT INTO application.lk_document_type (id,created_by_user,created_date,modified_by_user,modified_date,is_deleted,category,description,doc_order,name,name_ar,code) VALUES
    ((select (max(id) + 1) from application.lk_document_type),NULL,NULL,NULL,NULL,0,'APPLICATION','إشعاراسقاط الطلب',NULL,'DroppedRequestReportPublicationB','إشعار اسقاط الطلب','DroppedRequestReportPublicationB');
INSERT INTO application.lk_document_type (id,created_by_user,created_date,modified_by_user,modified_date,is_deleted,category,description,doc_order,name,name_ar,code) VALUES
    ((select (max(id) + 1) from application.lk_document_type),NULL,NULL,NULL,NULL,0,'APPLICATION','إشعاراسقاط الطلب',NULL,'DroppedRequestReportForExaminerBill','إشعار اسقاط الطلب','DroppedRequestReportForExaminerBill');
