
-- this will be removed after run on production
UPDATE application.applications_info
SET created_by_customer_id = (
    SELECT u.customer_id
    FROM customer.users u
    WHERE u.id = created_by_user_id
) where created_by_customer_id is null;

UPDATE application.applications_info ai
SET created_by_customer_type = (
    SELECT ac.application_customer_type
    FROM application.application_customers ac
    WHERE ac.customer_id = ai.created_by_customer_id AND ac.application_id = ai.id
    LIMIT 1
) where created_by_customer_type is null or created_by_customer_type = '';
