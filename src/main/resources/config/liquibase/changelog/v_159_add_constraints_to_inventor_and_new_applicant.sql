ALTER TABLE application.application_relevant_type  ADD duplication_flag int8 NULL DEFAULT 1;


UPDATE application.application_relevant_type art
SET duplication_flag = id
WHERE id IN
(
  SELECT art.id FROM application.application_relevant_type art
  JOIN
  (
    select count(1) ,type,application_info_id , customer_code , application_relevant_id from application.application_relevant_type art
group by application_info_id , customer_code , application_relevant_id , type having count(1)>1
  ) dup
ON art.application_info_id= dup.application_info_id
AND (art.customer_code= dup.customer_code or art.customer_code is null)
AND art.type= dup.type
and (art.application_relevant_id=dup.application_relevant_id or art.application_relevant_id is null)
);





ALTER TABLE application.application_relevant_type
ADD CONSTRAINT unique_app_customer_constraint UNIQUE (application_info_id,type,customer_code,duplication_flag);

ALTER TABLE application.application_relevant_type
ADD CONSTRAINT unique_app_relevant_constraint UNIQUE (application_info_id,type,application_relevant_id,duplication_flag);
