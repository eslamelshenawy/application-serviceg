insert
	into
	application.support_service_type_status
select
	(
	select
		lss.id
	from
		application.lk_support_services lss
	join application.support_service_application_categories ssac on
		lss.id = ssac.support_service_id
	join application.lk_application_category lac on
		ssac.category_id = lac.id
	where
		lss.code = 'EVICTION'
		and lac.saip_code = 'PATENT') lk_support_service_type_id,
	lk_support_service_status_id
from
	unnest(array(
	select
		lssrs.id
	from
		application.lk_support_service_request_status lssrs
	where
		lssrs.code in ('COMPLETED', 'DRAFT'))) lk_support_service_status_id;
------------------------------------------------------------------------------------------------------------------------------------------------------------------------
insert
	into
	application.support_service_type_status
select
	(
	select
		lss.id
	from
		application.lk_support_services lss
	join application.support_service_application_categories ssac on
		lss.id = ssac.support_service_id
	join application.lk_application_category lac on
		ssac.category_id = lac.id
	where
		lss.code = 'EXTENSION'
		and lac.saip_code = 'PATENT') lk_support_service_type_id,
	lk_support_service_status_id
from
	unnest(array(
	select
		lssrs.id
	from
		application.lk_support_service_request_status lssrs
	where
		lssrs.code in ('COMPLETED', 'DRAFT'))) lk_support_service_status_id;
------------------------------------------------------------------------------------------------------------------------------------------------------------------------
insert
	into
	application.support_service_type_status
select
	(
	select
		lss.id
	from
		application.lk_support_services lss
	join application.support_service_application_categories ssac on
		lss.id = ssac.support_service_id
	join application.lk_application_category lac on
		ssac.category_id = lac.id
	where
		lss.code = 'RETRACTION'
		and lac.saip_code = 'PATENT') lk_support_service_type_id,
	lk_support_service_status_id
from
	unnest(array(
	select
		lssrs.id
	from
		application.lk_support_service_request_status lssrs
	where
		lssrs.code in ('COMPLETED', 'DRAFT'))) lk_support_service_status_id;
------------------------------------------------------------------------------------------------------------------------------------------------------------------------
insert
	into
	application.support_service_type_status
select
	(
	select
		lss.id
	from
		application.lk_support_services lss
	join application.support_service_application_categories ssac on
		lss.id = ssac.support_service_id
	join application.lk_application_category lac on
		ssac.category_id = lac.id
	where
		lss.code = 'PETITION_RECOVERY'
		and lac.saip_code = 'PATENT') lk_support_service_type_id,
	lk_support_service_status_id
from
	unnest(array(
	select
		lssrs.id
	from
		application.lk_support_service_request_status lssrs
	where
		lssrs.code in ('DRAFT', 'PETITION_RECOVERY_APPROVAL', 'REOPENED', 'UNDER_PROCEDURE', 'PETITION_RECOVERY_REJECTION'))) lk_support_service_status_id;
------------------------------------------------------------------------------------------------------------------------------------------------------------------------
insert
	into
	application.support_service_type_status
select
	(
	select
		lss.id
	from
		application.lk_support_services lss
	join application.support_service_application_categories ssac on
		lss.id = ssac.support_service_id
	join application.lk_application_category lac on
		ssac.category_id = lac.id
	where
		lss.code = 'INITIAL_MODIFICATION'
		and lac.saip_code = 'PATENT') lk_support_service_type_id,
	lk_support_service_status_id
from
	unnest(array(
	select
		lssrs.id
	from
		application.lk_support_service_request_status lssrs
	where
		lssrs.code in ('DRAFT', 'COMPLETED', 'UNDER_PROCEDURE', 'WAIVED'))) lk_support_service_status_id;
------------------------------------------------------------------------------------------------------------------------------------------------------------------------
insert
	into
	application.support_service_type_status
select
	(
	select
		lss.id
	from
		application.lk_support_services lss
	join application.support_service_application_categories ssac on
		lss.id = ssac.support_service_id
	join application.lk_application_category lac on
		ssac.category_id = lac.id
	where
		lss.code = 'LICENSING_REGISTRATION'
		and lac.saip_code = 'PATENT') lk_support_service_type_id,
	lk_support_service_status_id
from
	unnest(array(
	select
		lssrs.id
	from
		application.lk_support_service_request_status lssrs
	where
		lssrs.code in ('DRAFT', 'LICENSED', 'UNDER_PROCEDURE', 'APPROVED', 'REQUEST_CORRECTION', 'REJECTED'))) lk_support_service_status_id;
------------------------------------------------------------------------------------------------------------------------------------------------------------------------
insert
	into
	application.support_service_type_status
select
	(
	select
		lss.id
	from
		application.lk_support_services lss
	join application.support_service_application_categories ssac on
		lss.id = ssac.support_service_id
	join application.lk_application_category lac on
		ssac.category_id = lac.id
	where
		lss.code = 'LICENSING_MODIFICATION'
		and lac.saip_code = 'PATENT') lk_support_service_type_id,
	lk_support_service_status_id
from
	unnest(array(
	select
		lssrs.id
	from
		application.lk_support_service_request_status lssrs
	where
		lssrs.code in ('DRAFT', 'UNDER_PROCEDURE', 'REQUEST_CORRECTION', 'APPROVED', 'REJECTED', 'LICENSED'))) lk_support_service_status_id;
------------------------------------------------------------------------------------------------------------------------------------------------------------------------
insert
	into
	application.support_service_type_status
select
	(
	select
		lss.id
	from
		application.lk_support_services lss
	join application.support_service_application_categories ssac on
		lss.id = ssac.support_service_id
	join application.lk_application_category lac on
		ssac.category_id = lac.id
	where
		lss.code = 'APPEAL_REQUEST'
		and lac.saip_code = 'PATENT') lk_support_service_type_id,
	lk_support_service_status_id
from
	unnest(array(
	select
		lssrs.id
	from
		application.lk_support_service_request_status lssrs
	where
		lssrs.code in ('UNDER_PROCEDURE', 'REJECTED', 'ACCEPTED_BY_COMMITTEE', 'DRAFT', 'REQUEST_CORRECTION', 'APPROVED'))) lk_support_service_status_id;
------------------------------------------------------------------------------------------------------------------------------------------------------------------------
insert
	into
	application.support_service_type_status
select
	(
	select
		lss.id
	from
		application.lk_support_services lss
	join application.support_service_application_categories ssac on
		lss.id = ssac.support_service_id
	join application.lk_application_category lac on
		ssac.category_id = lac.id
	where
		lss.code = 'OWNERSHIP_CHANGE'
		and lac.saip_code = 'PATENT') lk_support_service_type_id,
	lk_support_service_status_id
from
	unnest(array(
	select
		lssrs.id
	from
		application.lk_support_service_request_status lssrs
	where
		lssrs.code in ('DRAFT', 'COMPLETED', 'APPROVED', 'UNDER_PROCEDURE', 'REQUEST_CORRECTION', 'REJECTED', 'WAIVED'))) lk_support_service_status_id;
------------------------------------------------------------------------------------------------------------------------------------------------------------------------
insert
	into
	application.support_service_type_status
select
	(
	select
		lss.id
	from
		application.lk_support_services lss
	join application.support_service_application_categories ssac on
		lss.id = ssac.support_service_id
	join application.lk_application_category lac on
		ssac.category_id = lac.id
	where
		lss.code = 'ANNUAL_FEES_PAY'
		and lac.saip_code = 'PATENT') lk_support_service_type_id,
	lk_support_service_status_id
from
	unnest(array(
	select
		lssrs.id
	from
		application.lk_support_service_request_status lssrs
	where
		lssrs.code in ('DRAFT', 'UNDER_PROCEDURE'))) lk_support_service_status_id;
------------------------------------------------------------------------------------------------------------------------------------------------------------------------
insert
	into
	application.support_service_type_status
select
	(
	select
		lss.id
	from
		application.lk_support_services lss
	join application.support_service_application_categories ssac on
		lss.id = ssac.support_service_id
	join application.lk_application_category lac on
		ssac.category_id = lac.id
	where
		lss.code = 'PETITION_REQUEST_NATIONAL_STAGE'
		and lac.saip_code = 'PATENT') lk_support_service_type_id,
	lk_support_service_status_id
from
	unnest(array(
	select
		lssrs.id
	from
		application.lk_support_service_request_status lssrs
	where
		lssrs.code in ('DRAFT', 'UNDER_PROCEDURE', 'CONDITIONAL_REJECTION', 'REJECTED', 'COMPLETED'))) lk_support_service_status_id;
------------------------------------------------------------------------------------------------------------------------------------------------------------------------
insert
	into
	application.support_service_type_status
select
	(
	select
		lss.id
	from
		application.lk_support_services lss
	join application.support_service_application_categories ssac on
		lss.id = ssac.support_service_id
	join application.lk_application_category lac on
		ssac.category_id = lac.id
	where
		lss.code = 'PATENT_PRIORITY_REQUEST'
		and lac.saip_code = 'PATENT') lk_support_service_type_id,
	lk_support_service_status_id
from
	unnest(array(
	select
		lssrs.id
	from
		application.lk_support_service_request_status lssrs
	where
		lssrs.code in ('DRAFT', 'UNDER_PROCEDURE', 'REJECTED', 'CONDITIONAL_REJECTION', 'ACCEPTED', 'COMPLETED'))) lk_support_service_status_id;
------------------------------------------------------------------------------------------------------------------------------------------------------------------------
insert
	into
	application.support_service_type_status
select
	(
	select
		lss.id
	from
		application.lk_support_services lss
	join application.support_service_application_categories ssac on
		lss.id = ssac.support_service_id
	join application.lk_application_category lac on
		ssac.category_id = lac.id
	where
		lss.code = 'PATENT_PRIORITY_MODIFY'
		and lac.saip_code = 'PATENT') lk_support_service_type_id,
	lk_support_service_status_id
from
	unnest(array(
	select
		lssrs.id
	from
		application.lk_support_service_request_status lssrs
	where
		lssrs.code in ('CONDITIONAL_REJECTION', 'COMPLETED', 'UNDER_PROCEDURE', 'DRAFT', 'REJECTED', 'WAIVED'))) lk_support_service_status_id;