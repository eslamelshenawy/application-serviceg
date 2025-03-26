update application.lk_document_type set code = name;
update application.lk_document_type set name = 'Ownership Waiver Document'
where name like 'Waiver Document'
and category like 'OWNERSHIP_CHANGE_REQUEST';
delete from application.lk_support_services where id = 13;
delete from application.lk_support_services where id = 16;