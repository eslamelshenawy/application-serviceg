update application.lk_applicant_category
set applicant_category_name_en = 'Natural Person With Nationality'
where saip_code = 'NATURAL_PERSON_WITH_NATIONALITY';

update application.lk_applicant_category
set applicant_category_name_en = 'Corporation With Nationality'
where saip_code = 'CORPORATION_WITH_NATIONALITY';

update application.lk_applicant_category
set applicant_category_name_en = 'Natural Person With Foreign Nationality'
where saip_code = 'NATURAL_PERSON_WITH_FOREIGN_NATIONALITY';

update application.lk_applicant_category
set applicant_category_name_en = 'Foreign Corporation'
where saip_code = 'FOREIGN_CORPORATION';