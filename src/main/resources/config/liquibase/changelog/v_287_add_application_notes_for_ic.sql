-------------------------------------------------------------------------------------------
--APPLICATION
INSERT INTO application.lk_notes
(id,
created_by_user,
created_date,
is_deleted,
code,
description_ar
,description_en
,enabled
,name_ar
,name_en
,category_id
,section_id
,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes),
           'DB',
           CURRENT_TIMESTAMP,
           0,
           (select max(id)+1 from application.lk_notes),
           'مقدم الطلب فرد وليس شركة',
           'The applicant is an individual, not a company',
           true,
           'مقدم الطلب فرد وليس شركة',
           'The applicant is an individual, not a company',

           (select id from application.lk_application_category where saip_code = 'INTEGRATED_CIRCUITS'),
           (SELECT id FROM application.lk_sections WHERE code = 'APPLICATION'),
           'APPLICANT'
       );

INSERT INTO application.lk_notes
(id,
created_by_user,
created_date,
is_deleted,
code,
description_ar
,description_en
,enabled
,name_ar
,name_en
,category_id
,section_id
,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes),
           'DB',
           CURRENT_TIMESTAMP,
           0,
           (select max(id)+1 from application.lk_notes),
           'يجب ذكر جميع بيانات مقدمي الطلب',
           'All applicants data must be mentioned',
           true,
           'يجب ذكر جميع بيانات مقدمي الطلب',
           'All applicants data must be mentioned',

           (select id from application.lk_application_category where saip_code = 'INTEGRATED_CIRCUITS'),
           (SELECT id FROM application.lk_sections WHERE code = 'APPLICATION'),
           'APPLICANT'
       );

INSERT INTO application.lk_notes
(id,
created_by_user,
created_date,
is_deleted,
code,
description_ar
,description_en
,enabled
,name_ar
,name_en
,category_id
,section_id
,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes),
           'DB',
           CURRENT_TIMESTAMP,
           0,
           (select max(id)+1 from application.lk_notes),
           'الجنسية غير محددة',
           'Nationality not specified',
           true,
           'الجنسية غير محددة',
           'Nationality not specified',

           (select id from application.lk_application_category where saip_code = 'INTEGRATED_CIRCUITS'),
           (SELECT id FROM application.lk_sections WHERE code = 'APPLICATION'),
           'APPLICANT'
       );


INSERT INTO application.lk_notes
(id,
created_by_user,
created_date,
is_deleted,
code,
description_ar
,description_en
,enabled
,name_ar
,name_en
,category_id
,section_id
,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes),
           'DB',
           CURRENT_TIMESTAMP,
           0,
           (select max(id)+1 from application.lk_notes),
           'يجب تحديد نوع مقدم الطلب بشكل صحيح (فرد أو شركة) ',
           'The type of applicants must be correctly identified (individual or company)',
           true,
           'يجب تحديد نوع مقدم الطلب بشكل صحيح (فرد أو شركة)',
           'The type of applicants must be correctly identified (individual or company)',

           (select id from application.lk_application_category where saip_code = 'INTEGRATED_CIRCUITS'),
           (SELECT id FROM application.lk_sections WHERE code = 'APPLICATION'),
           'APPLICANT'
       );


INSERT INTO application.lk_notes
(id,
created_by_user,
created_date,
is_deleted,
code,
description_ar
,description_en
,enabled
,name_ar
,name_en
,category_id
,section_id
,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes),
           'DB',
           CURRENT_TIMESTAMP,
           0,
           (select max(id)+1 from application.lk_notes),
           'يوجد خطأ في بيانات مقدم الطلب',
           'There is an error in the applicantsdata',
           true,
           'يوجد خطأ في بيانات مقدم الطلب',
           'There is an error in the applicantsdata',

           (select id from application.lk_application_category where saip_code = 'INTEGRATED_CIRCUITS'),
           (SELECT id FROM application.lk_sections WHERE code = 'APPLICATION'),
           'APPLICANT'
       );


INSERT INTO application.lk_notes
(id,
created_by_user,
created_date,
is_deleted,
code,
description_ar
,description_en
,enabled
,name_ar
,name_en
,category_id
,section_id
,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes),
           'DB',
           CURRENT_TIMESTAMP,
           0,
           (select max(id)+1 from application.lk_notes),
           'يوجد تكرار في اسم مقدم الطلب',
           'There is a repetition in the applicantsname',
           true,
           'يوجد تكرار في اسم مقدم الطلب',
           'There is a repetition in the applicantsname',

           (select id from application.lk_application_category where saip_code = 'INTEGRATED_CIRCUITS'),
           (SELECT id FROM application.lk_sections WHERE code = 'APPLICATION'),
           'APPLICANT'
       );

-------------------------------------------------------------------------------------------
--INVENTORS

INSERT INTO application.lk_notes
(id,
created_by_user,
created_date,
is_deleted,
code,
description_ar
,description_en
,enabled
,name_ar
,name_en
,category_id
,section_id
,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes),
           'DB',
           CURRENT_TIMESTAMP,
           0,
           (select max(id)+1 from application.lk_notes),
           'يجب ارفاق التنازل لجميع المصممين',
           'A waiver document to all designers must be attached',
           true,
           'يجب ارفاق التنازل لجميع المصممين',
           'A waiver document to all designers must be attached',

           (select id from application.lk_application_category where saip_code = 'INTEGRATED_CIRCUITS'),
           (SELECT id FROM application.lk_sections WHERE code = 'INVENTORS'),
           'APPLICANT'
       );

INSERT INTO application.lk_notes
(id,
created_by_user,
created_date,
is_deleted,
code,
description_ar
,description_en
,enabled
,name_ar
,name_en
,category_id
,section_id
,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes),
           'DB',
           CURRENT_TIMESTAMP,
           0,
           (select max(id)+1 from application.lk_notes),
           'التنازل المرفق لا يحتوي على اسم التصميم',
           'The attached waiver does not contain the name of the design',
           true,
           'التنازل المرفق لا يحتوي على اسم التصميم',
           'The attached waiver does not contain the name of the design',

           (select id from application.lk_application_category where saip_code = 'INTEGRATED_CIRCUITS'),
           (SELECT id FROM application.lk_sections WHERE code = 'INVENTORS'),
           'APPLICANT'
       );


INSERT INTO application.lk_notes
(id,
created_by_user,
created_date,
is_deleted,
code,
description_ar
,description_en
,enabled
,name_ar
,name_en
,category_id
,section_id
,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes),
           'DB',
           CURRENT_TIMESTAMP,
           0,
           (select max(id)+1 from application.lk_notes),
           'التنازل المرفق لا يخص الطلب',
           'The attached waiver does not pertain to the application',
           true,
           'التنازل المرفق لا يخص الطلب',
           'The attached waiver does not pertain to the application',

           (select id from application.lk_application_category where saip_code = 'INTEGRATED_CIRCUITS'),
           (SELECT id FROM application.lk_sections WHERE code = 'INVENTORS'),
           'APPLICANT'
       );

INSERT INTO application.lk_notes
(id,
created_by_user,
created_date,
is_deleted,
code,
description_ar
,description_en
,enabled
,name_ar
,name_en
,category_id
,section_id
,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes),
           'DB',
           CURRENT_TIMESTAMP,
           0,
           (select max(id)+1 from application.lk_notes),
           'التنازل المرفق غير مصدق من الجهات ذات الاختصاص',
           'The attached waiver has not been certified by the competent authorities',
           true,
           'التنازل المرفق غير مصدق من الجهات ذات الاختصاص',
           'The attached waiver has not been certified by the competent authorities',

           (select id from application.lk_application_category where saip_code = 'INTEGRATED_CIRCUITS'),
           (SELECT id FROM application.lk_sections WHERE code = 'INVENTORS'),
           'APPLICANT'
       );

INSERT INTO application.lk_notes
(id,
created_by_user,
created_date,
is_deleted,
code,
description_ar
,description_en
,enabled
,name_ar
,name_en
,category_id
,section_id
,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes),
           'DB',
           CURRENT_TIMESTAMP,
           0,
           (select max(id)+1 from application.lk_notes),
           'التنازل المرفق غير واضح',
           'The attached waiver is not clear',
           true,
           'التنازل المرفق غير واضح',
           'The attached waiver is not clear',

           (select id from application.lk_application_category where saip_code = 'INTEGRATED_CIRCUITS'),
           (SELECT id FROM application.lk_sections WHERE code = 'INVENTORS'),
           'APPLICANT'
       );


INSERT INTO application.lk_notes
(id,
created_by_user,
created_date,
is_deleted,
code,
description_ar
,description_en
,enabled
,name_ar
,name_en
,category_id
,section_id
,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes),
           'DB',
           CURRENT_TIMESTAMP,
           0,
           (select max(id)+1 from application.lk_notes),
           'يوجد خطأ في اسم المصمم',
           'There is an error in the designersname',
           true,
           'يوجد خطأ في اسم المصمم',
           'There is an error in the designersname',

           (select id from application.lk_application_category where saip_code = 'INTEGRATED_CIRCUITS'),
           (SELECT id FROM application.lk_sections WHERE code = 'INVENTORS'),
           'APPLICANT'
       );


INSERT INTO application.lk_notes
(id,
created_by_user,
created_date,
is_deleted,
code,
description_ar
,description_en
,enabled
,name_ar
,name_en
,category_id
,section_id
,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes),
           'DB',
           CURRENT_TIMESTAMP,
           0,
           (select max(id)+1 from application.lk_notes),
           'يجب أن يكون المصمم شخص طبيعي',
           'The designer must be a natural person',
           true,
           'يجب أن يكون المصمم شخص طبيعي',
           'The designer must be a natural person',

           (select id from application.lk_application_category where saip_code = 'INTEGRATED_CIRCUITS'),
           (SELECT id FROM application.lk_sections WHERE code = 'INVENTORS'),
           'APPLICANT'
       );

INSERT INTO application.lk_notes
(id,
created_by_user,
created_date,
is_deleted,
code,
description_ar
,description_en
,enabled
,name_ar
,name_en
,category_id
,section_id
,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes),
           'DB',
           CURRENT_TIMESTAMP,
           0,
           (select max(id)+1 from application.lk_notes),
           'لا يوجد تطابق بين اسم المصمم بالطلب واسم المصمم بالتنازل',
           'There is no match between the name of the designer on the application and the name of the designer on the waiver document',
           true,
           'لا يوجد تطابق بين اسم المصمم بالطلب واسم المصمم بالتنازل',
           'There is no match between the name of the designer on the application and the name of the designer on the waiver document',

           (select id from application.lk_application_category where saip_code = 'INTEGRATED_CIRCUITS'),
           (SELECT id FROM application.lk_sections WHERE code = 'INVENTORS'),
           'APPLICANT'
       );


INSERT INTO application.lk_notes
(id,
created_by_user,
created_date,
is_deleted,
code,
description_ar
,description_en
,enabled
,name_ar
,name_en
,category_id
,section_id
,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes),
           'DB',
           CURRENT_TIMESTAMP,
           0,
           (select max(id)+1 from application.lk_notes),
           'يوجد شطب على بعض البيانات بوثيقة التنازل',
           'There is a deletion of some data in the waiver document',
           true,
           'يوجد شطب على بعض البيانات بوثيقة التنازل',
           'There is a deletion of some data in the waiver document',

           (select id from application.lk_application_category where saip_code = 'INTEGRATED_CIRCUITS'),
           (SELECT id FROM application.lk_sections WHERE code = 'INVENTORS'),
           'APPLICANT'
       );

INSERT INTO application.lk_notes
(id,
created_by_user,
created_date,
is_deleted,
code,
description_ar
,description_en
,enabled
,name_ar
,name_en
,category_id
,section_id
,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes),
           'DB',
           CURRENT_TIMESTAMP,
           0,
           (select max(id)+1 from application.lk_notes),
           'التنازل المرفق لا يحتوي على جميع أسماء المصممين',
           'The attached waiver does not contain all the names of the designers',
           true,
           'التنازل المرفق لا يحتوي على جميع أسماء المصممين',
           'The attached waiver does not contain all the names of the designers',

           (select id from application.lk_application_category where saip_code = 'INTEGRATED_CIRCUITS'),
           (SELECT id FROM application.lk_sections WHERE code = 'INVENTORS'),
           'APPLICANT'
       );

INSERT INTO application.lk_notes
(id,
created_by_user,
created_date,
is_deleted,
code,
description_ar
,description_en
,enabled
,name_ar
,name_en
,category_id
,section_id
,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes),
           'DB',
           CURRENT_TIMESTAMP,
           0,
           (select max(id)+1 from application.lk_notes),
           'يوجد اختلاف بين اسم التصميم المتنازل عنه واسم التصميم بالطلب',
           'There is a difference between the name of the waived design and the name of the design in the application',
           true,
           'يوجد اختلاف بين اسم التصميم المتنازل عنه واسم التصميم بالطلب',
           'There is a difference between the name of the waived design and the name of the design in the application',

           (select id from application.lk_application_category where saip_code = 'INTEGRATED_CIRCUITS'),
           (SELECT id FROM application.lk_sections WHERE code = 'INVENTORS'),
           'APPLICANT'
       );


-------------------------------------------------------------------------------------------
--FILES

INSERT INTO application.lk_notes
(id,
created_by_user,
created_date,
is_deleted,
code,
description_ar
,description_en
,enabled
,name_ar
,name_en
,category_id
,section_id
,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes),
           'DB',
           CURRENT_TIMESTAMP,
           0,
           (select max(id)+1 from application.lk_notes),
           'لم يتم ترجمة الوثائق المقدمة',
           'The documents submitted have not been translated',
           true,
           'لم يتم ترجمة الوثائق المقدمة',
           'The documents submitted have not been translated',

           (select id from application.lk_application_category where saip_code = 'INTEGRATED_CIRCUITS'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           'APPLICANT'
       );

INSERT INTO application.lk_notes
(id,
created_by_user,
created_date,
is_deleted,
code,
description_ar
,description_en
,enabled
,name_ar
,name_en
,category_id
,section_id
,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes),
           'DB',
           CURRENT_TIMESTAMP,
           0,
           (select max(id)+1 from application.lk_notes),
           'لم يتم ارفاق المستندات القانونية الإضافية للطلب',
           'Additional legal documents are not attached to the application',
           true,
           'لم يتم ارفاق المستندات القانونية الإضافية للطلب',
           'Additional legal documents are not attached to the application',

           (select id from application.lk_application_category where saip_code = 'INTEGRATED_CIRCUITS'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           'APPLICANT'
       );

-------------------------------------------------------------------------------------------
--PRECEDENCE

INSERT INTO application.lk_notes
(id,
created_by_user,
created_date,
is_deleted,
code,
description_ar
,description_en
,enabled
,name_ar
,name_en
,category_id
,section_id
,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes),
           'DB',
           CURRENT_TIMESTAMP,
           0,
           (select max(id)+1 from application.lk_notes),
           'لم يتم ترجمة الوثائق المقدمة',
           'The documents submitted have not been translated',
           true,
           'لم يتم ترجمة الوثائق المقدمة',
           'The documents submitted have not been translated',

           (select id from application.lk_application_category where saip_code = 'INTEGRATED_CIRCUITS'),
           (SELECT id FROM application.lk_sections WHERE code = 'PRECEDENCE'),
           'APPLICANT'
       );

INSERT INTO application.lk_notes
(id,
created_by_user,
created_date,
is_deleted,
code,
description_ar
,description_en
,enabled
,name_ar
,name_en
,category_id
,section_id
,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes),
           'DB',
           CURRENT_TIMESTAMP,
           0,
           (select max(id)+1 from application.lk_notes),
           'الأسبقية لا تخص الطلب',
           'The precedence is not related to the application',
           true,
           'الأسبقية لا تخص الطلب',
           'The precedence is not related to the application',

           (select id from application.lk_application_category where saip_code = 'INTEGRATED_CIRCUITS'),
           (SELECT id FROM application.lk_sections WHERE code = 'PRECEDENCE'),
           'APPLICANT'
       );

INSERT INTO application.lk_notes
(id,
created_by_user,
created_date,
is_deleted,
code,
description_ar
,description_en
,enabled
,name_ar
,name_en
,category_id
,section_id
,notes_type_enum
)
VALUES (
           (select max(id)+1 from application.lk_notes),
           'DB',
           CURRENT_TIMESTAMP,
           0,
           (select max(id)+1 from application.lk_notes),
           'لم يتم إرفاق مستند الأسبقية',
           'No precedence document is attached',
           true,
           'لم يتم إرفاق مستند الأسبقية',
           'No precedence document is attached',

           (select id from application.lk_application_category where saip_code = 'INTEGRATED_CIRCUITS'),
           (SELECT id FROM application.lk_sections WHERE code = 'PRECEDENCE'),
           'APPLICANT'
       );



