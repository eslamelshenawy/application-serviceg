update application.lk_support_services set
desc_en='Appeal Request',
name_en='Appeal Request',
code='APPEAL_REQUEST' where id = 11;
update application.lk_document_type set
name='Appeal Statement',
code='APPEAL_STATEMENT',
category='APPEAL' where id = 69;
update application.lk_document_type set
name='Appeal POA',
code='APPEAL_POA',
category='APPEAL' where id = 70;
update application.lk_document_type set
category='APPEAL' where id = 71;
update application.lk_document_type set
name='Appeal Committee Decisions',
code='APPEAL_COMMITTEE_DECISIONS',
category='APPEAL' where id = 72;

