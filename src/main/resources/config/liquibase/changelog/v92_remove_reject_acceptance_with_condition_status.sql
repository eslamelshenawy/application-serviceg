update application.applications_info set application_status_id =
(select id FROM application.lk_application_status where code = 'OBJECTIVE_REJECTION')
where application_status_id =
(select id FROM application.lk_application_status where code = 'REJECT_ACCEPTANCE_WITH_CONDITION');

DELETE FROM application.lk_application_status
where code = 'REJECT_ACCEPTANCE_WITH_CONDITION';

