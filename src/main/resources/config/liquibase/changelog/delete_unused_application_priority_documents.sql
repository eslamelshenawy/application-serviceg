update application.documents d set is_deleted = 1
    from (
select d.id from application.documents d
join application.lk_document_type ldt on d.document_type_id = ldt.id
left join application.application_priority pr on pr.priority_document_id = d.id
where
ldt.code = 'Prioirty Document'
and pr.id is null) q
where q.id = d.id ;