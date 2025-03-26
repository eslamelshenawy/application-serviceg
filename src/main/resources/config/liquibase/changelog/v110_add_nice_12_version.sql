alter table application.lk_classification_versions add column category_id int8;
alter table application.lk_classification_versions add constraint FKko8sogw5iuam9yw5f2v66ss63 foreign key (category_id) references application.lk_application_category;

INSERT INTO application.lk_classification_versions (id,code,name_ar,name_en,category_id) VALUES
    (12,'12','تصنيف نيس (12-2023)','Nice (12-2023)',5);