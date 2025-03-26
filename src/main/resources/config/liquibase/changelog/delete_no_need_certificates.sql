delete
from application.certificate_types_application_categories
where lk_certificate_type_id = (select id
                                from application.lk_certificate_types
                                where code = 'ALL_APPLICATION_RECORDS')
  and lk_category_id = (select id
                        from application.lk_application_category
                        where saip_code = 'TRADEMARK');
