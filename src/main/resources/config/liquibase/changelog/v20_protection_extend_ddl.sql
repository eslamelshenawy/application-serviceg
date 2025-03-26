

create table application.protection_extend_request (id int8 not null, created_by_user varchar(255), created_date timestamp, modified_by_user varchar(255), modified_date timestamp, is_deleted int4 not null, claim_count int4 not null, claim_number int4 not null, protection_extend_type varchar(255), application_support_services_type_id int8, poa_document_id int8, support_document_id int8, waive_document_id int8, primary key (id));
alter table application.protection_extend_request add constraint FKftk719jo15npy3eqpfwj43j5e foreign key (application_support_services_type_id) references application.application_support_services_type;
alter table application.protection_extend_request add constraint FKm7xbeiwqlttsxcvboshrb6v3d foreign key (poa_document_id) references application.documents;
alter table application.protection_extend_request add constraint FKmtc95ekp84po5w99sscog977j foreign key (support_document_id) references application.documents;
alter table application.protection_extend_request add constraint FK1rll4g5d9go5jcqd2lkicet32 foreign key (waive_document_id) references application.documents;




