--liquibase formatted sql
-- changeset application-service:v53_add_migrate_customers_to_application_customers.sql

INSERT INTO application.application_customers
(id,  is_deleted, customer_code, customer_id, application_customer_type, application_id)
select art.id, 0,art.customer_code, c.id, 'MAIN_OWNER', ai.id  from application.applications_info ai
                                                                   join application.application_relevant_type art on ai.id = art.application_info_id
                                                                   join customer.customer c on c.code = art.customer_code
where art."type" = 'Applicant_MAIN'
  and not exists (select 1 from application.application_customers ac where ac.customer_code  = c.code and c.id = ac.customer_id and ai.id = ac.application_id);


INSERT INTO application.application_customers
(id,  is_deleted, customer_code, customer_id, application_customer_type, application_id)
select aa.id, 0, c.code , c.id, 'AGENT', ai.id  from application.applications_info ai
                                                         join application.application_agents aa  on ai.id = aa.application_id
                                                         join customer.customer c on c.id = aa.customer_id
where aa.status  = 'ACTIVE' and c.code is not null
  and not exists (select 1 from application.application_customers ac where ac.customer_code  = c.code and c.id = ac.customer_id and ai.id = ac.application_id);

INSERT INTO application.application_customers
(id,  is_deleted, customer_code, customer_id, application_customer_type, application_id)
select ai.id, 0, c.code , c.id, 'APPLICANT', ai.id from application.applications_info ai
                                                            join customer.users u on u.id = ai.created_by_user_id
                                                            join customer.customer c on c.id = u.customer_id
where not exists (select 1 from application.application_customers ac where ac.customer_code = c.code and c.id = ac.customer_id and ai.id = ac.application_id);
