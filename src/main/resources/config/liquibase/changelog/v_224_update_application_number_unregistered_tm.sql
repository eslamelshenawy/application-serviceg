UPDATE
	application.applications_info as ai
SET
	application_number = NULL
FROM
	application.lk_application_status as appStat
WHERE
	ai.lk_category_id = 5
	AND appStat.id = ai.application_status_id
	AND appStat.code not in ('THE_TRADEMARK_IS_REGISTERED', 'ACCEPTANCE')
	AND (ai.migration_stage is null or ai.migration_stage = 0);