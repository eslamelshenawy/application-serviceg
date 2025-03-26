update application.lk_support_services set
code = 'REVOKE_LICENSE_REQUEST'
where code = 'REVOKE_LICENCE_REQUEST';

update application.lk_task_eqm_types set
code = 'REVOKE_LICENSE_REQUEST'
where code = 'REVOKE_LICENCE_REQUEST';

update application.lk_document_type set
code='REVOKE_LICENSE_REQUEST',
category='REVOKE_LICENSE_REQUEST'
where code = 'REVOKE_LICENCE_REQUEST' and category = 'REVOKE_LICENCE_REQUEST';