delete
from
	application.certificate_types_application_categories ctac
where
	ctac.lk_category_id = 2
	and ctac.lk_certificate_type_id = (
	select
		lct.id
	from
		application.lk_certificate_types lct
	where
		code = 'REVOKE_VOLUNTARY_TRADEMARK_CERTIFICATE');