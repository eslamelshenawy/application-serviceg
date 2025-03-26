
-- create application agent and it's document table
create table application.application_agent_documents (application_agent_id int8 not null, document_id int8 not null);
create table application.application_agents (id int8 not null, created_by_user varchar(255), created_date timestamp, modified_by_user varchar(255), modified_date timestamp, is_deleted int4 not null, customer_id int8, expiration_date date, status varchar(255), application_id int8, primary key (id));
alter table application.application_agent_documents add constraint uniqu_application_agent_id_document_id unique (application_agent_id, document_id);
alter table application.application_agents add constraint uniqu_application_id_agent_id unique (application_id, customer_id);
alter table application.application_agent_documents add constraint fk_application_agent_documents_documents foreign key (document_id) references application.documents;
alter table application.application_agent_documents add constraint fk_application_agent_documents_application_agents foreign key (application_agent_id) references application.application_agents;
alter table application.application_agents add constraint fk_application_agents_applications_info foreign key (application_id) references application.applications_info;