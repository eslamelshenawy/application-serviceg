

create table application.licence_request (id int8 not null, created_by_user varchar(255), created_date timestamp, modified_by_user varchar(255), modified_date timestamp, is_deleted int4 not null, customer_id int8, licence_purpose varchar(255), licence_type_enum varchar(255), licence_validity_number int4 not null, application_support_services_type_id int8, canceled_contract_document_id int8, support_document_id int8, poa_document_id int8, updated_contract_document_id int8, primary key (id));
alter table application.licence_request add constraint FK140sltif88op8gfqt4dv1gu7o foreign key (application_support_services_type_id) references application.application_support_services_type;
alter table application.licence_request add constraint FKhrmgpky3csryd6rl13at2t7ft foreign key (canceled_contract_document_id) references application.documents;
alter table application.licence_request add constraint FK96n6sgxuw5k0mntqmoxsq4wsu foreign key (support_document_id) references application.documents;
alter table application.licence_request add constraint FK9lqunptaxkph7q67ydc2s5q5a foreign key (poa_document_id) references application.documents;
alter table application.licence_request add constraint FKtuuraea2yui7n4rqrmgsp2pa foreign key (updated_contract_document_id) references application.documents;
