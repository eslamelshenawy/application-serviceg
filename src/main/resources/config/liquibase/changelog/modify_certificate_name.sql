update
    application.lk_certificate_types
set name_ar = 'صوره طبق الاصل'
where code = 'EXACT_COPY';

update
    application.certificates_request
set certificate_type_id = (select id
                           from application.lk_certificate_types
                           where code = 'EXACT_COPY')
where certificate_type_id = (select id
                             from application.lk_certificate_types
                             where code = 'CERTIFIED_REGISTER_COPY');

delete
from application.certificate_types_application_categories
where lk_certificate_type_id = (select id
                                from application.lk_certificate_types
                                where code = 'CERTIFIED_REGISTER_COPY');

delete
from application.lk_certificate_types
where id = (select id
            from application.lk_certificate_types
            where code = 'CERTIFIED_REGISTER_COPY');
