update application.lk_support_services
set desc_en = 'Trademark Application Search' ,
name_en = 'Trademark Application Search',
code = 'TRADEMARK_APPLICATION_SEARCH' where code = 'APPLICATION_SEARCH';

update application.lk_document_type
set category = 'TRADEMARK_APPLICATION_SEARCH',
description = 'البحث عن علامة تجارية',
name = 'Trademark Application Search',
name_ar = 'البحث عن علامة تجارية',
code = 'Trademark Application Search' where code = 'APPLICATION_SEARCH';


update application.lk_task_eqm_types set
code = 'REVIEW_REQUEST_DOCUMENTS_ASE',
name_en = 'Review Request Documents Ase'
where code = 'APPLICATION_SEARCH';

