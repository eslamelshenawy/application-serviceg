INSERT INTO application.lk_document_type (id,created_by_user,created_date,modified_by_user,modified_date,is_deleted,category,description,doc_order,name,name_ar,code) VALUES
    ((select (max(id) + 1) from application.lk_document_type),NULL,NULL,NULL,NULL,0,'APPLICATION','إشعار رفض الطلب',NULL,'DroppedRequestReport_For_Timing_Checker','إشعار رفض الطلب','DroppedRequestReport_For_Timing_Checker');
