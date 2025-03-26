
INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en, enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id, notes_step)
VALUES (
           (SELECT COALESCE(MAX(id), 0) + 1 FROM application.lk_notes),
           NULL,
           NULL,
           NULL,
           NULL,
           0,
           NULL,
           'الملاحظة الأولي',
           'الملاحظة الأولي',
           true,
           'الملاحظة الأولي',
           'الملاحظة الأولي',
           (SELECT id FROM application.lk_application_category WHERE saip_code = 'INDUSTRIAL_DESIGN'),
           (SELECT id FROM application.lk_sections WHERE code = 'REQUESTS'),
           NULL,
           (SELECT id FROM application.lk_attributes WHERE code = 'PROTECTION_ELEMENTS'),
           'APPLICANT',
           NULL,
           'SUBSTANTIVE_EXAMINER'
       );



INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en, enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id, notes_step)
VALUES (
           (SELECT COALESCE(MAX(id), 0) + 1 FROM application.lk_notes),
           NULL,
           NULL,
           NULL,
           NULL,
           0,
           NULL,
           'الملاحظة الثانيه',
           'الملاحظة الثانيه',
           true,
           'الملاحظة الثانيه',
           'الملاحظة الثانيه',
           (SELECT id FROM application.lk_application_category WHERE saip_code = 'INDUSTRIAL_DESIGN'),
           (SELECT id FROM application.lk_sections WHERE code = 'REQUESTS'),
           NULL,
           (SELECT id FROM application.lk_attributes WHERE code = 'PROTECTION_ELEMENTS'),
           'APPLICANT',
           NULL,
           'SUBSTANTIVE_EXAMINER'
       );



INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en, enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id, notes_step)
VALUES (
           (SELECT COALESCE(MAX(id), 0) + 1 FROM application.lk_notes),
           NULL,
           NULL,
           NULL,
           NULL,
           0,
           NULL,
           'الملاحظة الثالثه',
           'الملاحظة الثالثه',
           true,
           'الملاحظة الثالثه',
           'الملاحظة الثالثه',
           (SELECT id FROM application.lk_application_category WHERE saip_code = 'INDUSTRIAL_DESIGN'),
           (SELECT id FROM application.lk_sections WHERE code = 'REQUESTS'),
           NULL,
           (SELECT id FROM application.lk_attributes WHERE code = 'PROTECTION_ELEMENTS'),
           'APPLICANT',
           NULL,
           'SUBSTANTIVE_EXAMINER'
       );



INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en, enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id, notes_step)
VALUES (
           (SELECT COALESCE(MAX(id), 0) + 1 FROM application.lk_notes),
           NULL,
           NULL,
           NULL,
           NULL,
           0,
           NULL,
           'الملاحظة الرابعه',
           'الملاحظة الرابعه',
           true,
           'الملاحظة الرابعه',
           'الملاحظة الرابعه',
           (SELECT id FROM application.lk_application_category WHERE saip_code = 'INDUSTRIAL_DESIGN'),
           (SELECT id FROM application.lk_sections WHERE code = 'REQUESTS'),
           NULL,
           (SELECT id FROM application.lk_attributes WHERE code = 'PROTECTION_ELEMENTS'),
           'APPLICANT',
           NULL,
           'SUBSTANTIVE_EXAMINER'
       );



insert
into
    application.lk_sections (id,
                             code,
                             name_ar,
                             name_en)
select
    (
        select
            MAX(id) + 1
        from
            application.lk_sections),
    'REJECTION_REASONS',
    'أسباب الرفض',
    'Rejection Reasons'
    where
	not exists (
	select
		1
	from
		application.lk_sections
	where
		code = 'REJECTION_REASONS'
);




INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en, enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id, notes_step)
VALUES (
           (SELECT COALESCE(MAX(id), 0) + 1 FROM application.lk_notes),
           NULL,
           NULL,
           NULL,
           NULL,
           0,
           NULL,
           'الملاحظة الأولي',
           'الملاحظة الأولي',
           true,
           'الملاحظة الأولي',
           'الملاحظة الأولي',
           (SELECT id FROM application.lk_application_category WHERE saip_code = 'INDUSTRIAL_DESIGN'),
           (SELECT id FROM application.lk_sections WHERE code = 'REJECTION_REASONS'),
           NULL,
           (SELECT id FROM application.lk_attributes WHERE code = 'PROTECTION_ELEMENTS'),
           'APPLICANT',
           NULL,
           'SUBSTANTIVE_EXAMINER'
       );



INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en, enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id, notes_step)
VALUES (
           (SELECT COALESCE(MAX(id), 0) + 1 FROM application.lk_notes),
           NULL,
           NULL,
           NULL,
           NULL,
           0,
           NULL,
           'الملاحظة الثانيه',
           'الملاحظة الثانيه',
           true,
           'الملاحظة الثانيه',
           'الملاحظة الثانيه',
           (SELECT id FROM application.lk_application_category WHERE saip_code = 'INDUSTRIAL_DESIGN'),
           (SELECT id FROM application.lk_sections WHERE code = 'REJECTION_REASONS'),
           NULL,
           (SELECT id FROM application.lk_attributes WHERE code = 'PROTECTION_ELEMENTS'),
           'APPLICANT',
           NULL,
           'SUBSTANTIVE_EXAMINER'
       );



INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en, enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id, notes_step)
VALUES (
           (SELECT COALESCE(MAX(id), 0) + 1 FROM application.lk_notes),
           NULL,
           NULL,
           NULL,
           NULL,
           0,
           NULL,
           'الملاحظة الثالثه',
           'الملاحظة الثالثه',
           true,
           'الملاحظة الثالثه',
           'الملاحظة الثالثه',
           (SELECT id FROM application.lk_application_category WHERE saip_code = 'INDUSTRIAL_DESIGN'),
           (SELECT id FROM application.lk_sections WHERE code = 'REJECTION_REASONS'),
           NULL,
           (SELECT id FROM application.lk_attributes WHERE code = 'PROTECTION_ELEMENTS'),
           'APPLICANT',
           NULL,
           'SUBSTANTIVE_EXAMINER'
       );



INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en, enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id, notes_step)
VALUES (
           (SELECT COALESCE(MAX(id), 0) + 1 FROM application.lk_notes),
           NULL,
           NULL,
           NULL,
           NULL,
           0,
           NULL,
           'الملاحظة الرابعه',
           'الملاحظة الرابعه',
           true,
           'الملاحظة الرابعه',
           'الملاحظة الرابعه',
           (SELECT id FROM application.lk_application_category WHERE saip_code = 'INDUSTRIAL_DESIGN'),
           (SELECT id FROM application.lk_sections WHERE code = 'REJECTION_REASONS'),
           NULL,
           (SELECT id FROM application.lk_attributes WHERE code = 'PROTECTION_ELEMENTS'),
           'APPLICANT',
           NULL,
           'SUBSTANTIVE_EXAMINER'
       );


