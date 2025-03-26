-- Auto-generated SQL script #202407091357
INSERT INTO application.lk_document_type (id,is_deleted,category,description,"name",name_ar,code)
VALUES ((select max(id) + 1 from application.lk_document_type),0,'INDUSTRIAL_DESIGN','وثائق التصاميم الصناعية','Notification of inspection report','اشعار تقرير الفحص','INDUSTRIAL_DESIGN_CHECKER_REPORT');
INSERT INTO application.lk_document_type (id,is_deleted,category,description,"name",name_ar,code)
VALUES ((select max(id) + 1 from application.lk_document_type),0,'INDUSTRIAL_DESIGN','وثائق التصاميم الصناعية','Second Notification of inspection report','اشعار تقرير الفحص الثاني','INDUSTRIAL_DESIGN_SECOND_CHECKER_REPORT');
INSERT INTO application.lk_document_type (id,is_deleted,category,description,"name",name_ar,code)
VALUES ((select max(id) + 1 from application.lk_document_type),0,'INDUSTRIAL_DESIGN','وثائق التصاميم الصناعية','Notice of application rejection','اشعار رفض الطلب','INDUSTRIAL_DESIGN_OBJECTION');
INSERT INTO application.lk_document_type (id,is_deleted,category,description,"name",name_ar,code)
VALUES ((select max(id) + 1 from application.lk_document_type),0,'INDUSTRIAL_DESIGN','وثائق التصاميم الصناعية','Notice of financial compensation for grants','اشعار بالمقابل المالى للمنح','INDUSTRIAL_DESIGN_FEE_GRANT');
