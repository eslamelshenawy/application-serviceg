update
	application.lk_support_services
set
	is_deleted = 1
where
	id = (
	select
		lss.id
	from
		application.lk_support_services lss
	join application.support_service_application_categories ssac on
		lss.id = ssac.support_service_id
	where
		ssac.category_id = 2
		and code = 'REVOKE_LICENSE_REQUEST');