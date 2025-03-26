INSERT INTO application.lk_support_services (id,created_by_user,created_date,modified_by_user,modified_date, is_deleted, cost,desc_ar, desc_en,name_ar,name_en,code)
VALUES((select (max(id) + 1) from application.lk_support_services),null,null,null,null, 0, 0,'البحث عن وجود طلبات مشابهة', 'Application Search', 'البحث عن وجود طلبات مشابهة', 'Application Search','APPLICATION_SEARCH');


INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES ((select (max(id) + 1) from application.lk_document_type), 'Application Search', 'APPLICATION_SEARCH', 'صورة العلامة التجارية', 'صورة العلامة التجارية ','APPLICATION_SEARCH', NULL,0);

create table application.application_search(
id int8 NOT NULL PRIMARY Key,
title varchar(250),
description TEXT,
notes TEXT ,
classification_id int8,
document_id int8,
FOREIGN KEY (classification_id) REFERENCES application.classifications(id),
FOREIGN KEY (document_id) REFERENCES application.documents(id)
);

create table application.application_search_similars(
application_search_id int8 not null,
application_id int8 not null,
FOREIGN KEY (application_search_id) REFERENCES application.application_search(id),
FOREIGN KEY (application_id) REFERENCES application.applications_info(id)
);


