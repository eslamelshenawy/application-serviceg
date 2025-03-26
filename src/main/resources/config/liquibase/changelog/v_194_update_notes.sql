delete FROM application.application_section_notes x where note_id in (
    select id from application.lk_notes x where  attribute_id in (
        SELECT x.id FROM application.lk_attributes x where code in ('PRIORITY' , 'SUMMARY_AR' , 'SUMMARY_EN' , 'DESCRIPTION_AR' , 'DESCRIPTION_EN')
    )
) ;

delete from application.lk_notes x where category_id = 1 and step_id is null and attribute_id is null and notes_step is null ;

INSERT INTO application.lk_attributes (id, code, name_ar, name_en)
SELECT (SELECT COALESCE(MAX(id) + 1, 1) FROM application.lk_attributes), 'Step1', 'Step1', 'Step1'
    WHERE NOT EXISTS (SELECT 1 FROM application.lk_attributes WHERE code = 'Step1')
ON CONFLICT DO nothing;

INSERT INTO application.lk_attributes (id, code, name_ar, name_en)
SELECT (SELECT COALESCE(MAX(id) + 1, 1) FROM application.lk_attributes), 'PRIORITY', 'وثائق الاسبقية', 'Priority'
    WHERE NOT EXISTS (SELECT 1 FROM application.lk_attributes WHERE code = 'PRIORITY')
ON CONFLICT DO nothing;

INSERT INTO application.lk_attributes (id, code, name_ar, name_en)
SELECT (SELECT COALESCE(MAX(id) + 1, 1) FROM application.lk_attributes), 'SUMMARY_AR', 'الملخص بالعربية', 'Summary ar'
    WHERE NOT EXISTS (SELECT 1 FROM application.lk_attributes WHERE code = 'SUMMARY_AR')
ON CONFLICT DO nothing ;

INSERT INTO application.lk_attributes (id, code, name_ar, name_en)
SELECT (SELECT COALESCE(MAX(id) + 1, 1) FROM application.lk_attributes), 'SUMMARY_EN', 'المخلص بالانجليزية', 'Summary en'
    WHERE NOT EXISTS (SELECT 1 FROM application.lk_attributes WHERE code = 'SUMMARY_EN')
ON CONFLICT DO nothing;

INSERT INTO application.lk_attributes (id, code, name_ar, name_en)
SELECT (SELECT COALESCE(MAX(id) + 1, 1) FROM application.lk_attributes), 'DESCRIPTION_AR', 'الوصف بالعربية', 'Description ar'
    WHERE NOT EXISTS (SELECT 1 FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR')
ON CONFLICT DO nothing;

INSERT INTO application.lk_attributes (id, code, name_ar, name_en)
SELECT (SELECT COALESCE(MAX(id) + 1, 1) FROM application.lk_attributes), 'DESCRIPTION_EN', 'الوصف بالانجليزية', 'Description en'
    WHERE NOT EXISTS (SELECT 1 FROM application.lk_attributes WHERE code = 'DESCRIPTION_EN')
ON CONFLICT DO nothing;



-- Auto-generated SQL script #202311241351
INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES ((select max(id) + 1 from application.lk_notes),'DB','2023-05-12 10:22:44.334',0,(select max(id) + 1 from application.lk_notes),'يجب إلغاء الترقيم للرسمة أو الصورة.','يجب إلغاء الترقيم للرسمة أو الصورة.',true,'يجب إلغاء الترقيم للرسمة أو الصورة.','يجب إلغاء الترقيم للرسمة أو الصورة.',
        (SELECT x.id FROM application.lk_application_category x WHERE saip_code = 'PATENT'),
        (SELECT x.id FROM application.lk_sections x WHERE code = 'APPLICATION'),
        (SELECT x.id FROM application.lk_steps x WHERE code = 'Step1'),
        (SELECT x.id FROM application.lk_attributes x WHERE code = 'PRIORITY'),
        'APPLICANT');
INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,
                                  category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES ((select max(id) + 1 from application.lk_notes),'DB','2023-05-12 10:22:44.334',0,(select max(id) + 1 from application.lk_notes),'الطلب المودع مخالف لأحكام المادة الرابعة من نظام براءات الاختراع والتصميمات التخطيطية للدارات المتكاملة والأصناف النباتية والنماذج الصناعية.','الطلب المودع مخالف لأحكام المادة الرابعة من نظام براءات الاختراع والتصميمات التخطيطية للدارات المتكاملة والأصناف النباتية والنماذج الصناعية.',true,'الطلب المودع مخالف لأحكام المادة الرابعة من نظام براءات الاختراع والتصميمات التخطيطية للدارات المتكاملة والأصناف النباتية والنماذج الصناعية.','الطلب المودع مخالف لأحكام المادة الرابعة من نظام براءات الاختراع والتصميمات التخطيطية للدارات المتكاملة والأصناف النباتية والنماذج الصناعية.',
        (SELECT x.id FROM application.lk_application_category x WHERE saip_code = 'PATENT'),
        (SELECT x.id FROM application.lk_sections x WHERE code = 'APPLICATION'),
        (SELECT x.id FROM application.lk_steps x WHERE code = 'Step1'),
        (SELECT x.id FROM application.lk_attributes x WHERE code = 'PRIORITY'),
        'APPLICANT');
INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES ((select max(id) + 1 from application.lk_notes),'DB','2023-05-12 10:22:44.334',0,(select max(id) + 1 from application.lk_notes),'جودة الصور والرسومات منخفضة.','جودة الصور والرسومات منخفضة.',true,'جودة الصور والرسومات منخفضة.','جودة الصور والرسومات منخفضة.',
        (SELECT x.id FROM application.lk_application_category x WHERE saip_code = 'PATENT'),
        (SELECT x.id FROM application.lk_sections x WHERE code = 'APPLICATION'),
        (SELECT x.id FROM application.lk_steps x WHERE code = 'Step1'),
        (SELECT x.id FROM application.lk_attributes x WHERE code = 'SUMMARY_AR'),
        'APPLICANT');
INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES ((select max(id) + 1 from application.lk_notes),'DB','2023-05-12 10:22:44.334',0,(select max(id) + 1 from application.lk_notes),'يجب ان لا تحتوي الصور والرسومات على ظل.','يجب ان لا تحتوي الصور والرسومات على ظل.',true,'يجب ان لا تحتوي الصور والرسومات على ظل.','يجب ان لا تحتوي الصور والرسومات على ظل.',
        (SELECT x.id FROM application.lk_application_category x WHERE saip_code = 'PATENT'),
        (SELECT x.id FROM application.lk_sections x WHERE code = 'APPLICATION'),
        (SELECT x.id FROM application.lk_steps x WHERE code = 'Step1'),
        (SELECT x.id FROM application.lk_attributes x WHERE code = 'SUMMARY_AR'),
        'APPLICANT');
INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES ((select max(id) + 1 from application.lk_notes),'DB','2023-05-12 10:22:44.334',0,(select max(id) + 1 from application.lk_notes),'إذا كان هناك أجزاء في النموذج الصناعي غير مطلوب حمايتها فترسم بخطوط متقطعة.','إذا كان هناك أجزاء في النموذج الصناعي غير مطلوب حمايتها فترسم بخطوط متقطعة.',true,'
إذا كان هناك أجزاء في النموذج الصناعي غير مطلوب حمايتها فترسم بخطوط متقطعة.','إذا كان هناك أجزاء في النموذج الصناعي غير مطلوب حمايتها فترسم بخطوط متقطعة.'
           ,(SELECT x.id FROM application.lk_application_category x WHERE saip_code = 'PATENT'),
        (SELECT x.id FROM application.lk_sections x WHERE code = 'APPLICATION'),
        (SELECT x.id FROM application.lk_steps x WHERE code = 'Step1'),
        (SELECT x.id FROM application.lk_attributes x WHERE code = 'SUMMARY_EN'),
        'APPLICANT');
INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES ((select max(id) + 1 from application.lk_notes),'DB','2023-05-12 10:22:44.334',0,(select max(id) + 1 from application.lk_notes),'لا ينطبق على الطلب المقدم تعريف الدارة المتكاملة.','لا ينطبق على الطلب المقدم تعريف الدارة المتكاملة.',true,'
لا ينطبق على الطلب المقدم تعريف الدارة المتكاملة.','لا ينطبق على الطلب المقدم تعريف الدارة المتكاملة.'
           ,(SELECT x.id FROM application.lk_application_category x WHERE saip_code = 'PATENT'),
        (SELECT x.id FROM application.lk_sections x WHERE code = 'APPLICATION'),
        (SELECT x.id FROM application.lk_steps x WHERE code = 'Step1'),
        (SELECT x.id FROM application.lk_attributes x WHERE code = 'SUMMARY_EN'),
        'APPLICANT');
INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES ((select max(id) + 1 from application.lk_notes),'DB','2023-05-12 10:22:44.334',0,(select max(id) + 1 from application.lk_notes),'الرسمة أو الصورة غير واضحة','الرسمة أو الصورة غير واضحة',true,'الرسمة أو الصورة غير واضحة','الرسمة أو الصورة غير واضحة',
        (SELECT x.id FROM application.lk_application_category x WHERE saip_code = 'PATENT'),
        (SELECT x.id FROM application.lk_sections x WHERE code = 'APPLICATION'),
        (SELECT x.id FROM application.lk_steps x WHERE code = 'Step1'),
        (SELECT x.id FROM application.lk_attributes x WHERE code = 'DESCRIPTION_AR'),
        'APPLICANT');
INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES ((select max(id) + 1 from application.lk_notes),'DB','2023-05-12 10:22:44.334',0,(select max(id) + 1 from application.lk_notes),'يجب أن تكون المسافة بين الأسطر في الملخص حوالي 1 سم وذلك طبقاً لأحكام المادة الحادية عشرة الفقرة الخامسة من اللائحة التنفيذية','يجب أن تكون المسافة بين الأسطر في الملخص حوالي 1 سم وذلك طبقاً لأحكام المادة الحادية عشرة الفقرة الخامسة من اللائحة التنفيذية',true,'يجب أن تكون المسافة بين الأسطر في الملخص حوالي 1 سم وذلك طبقاً لأحكام المادة الحادية عشرة الفقرة الخامسة من اللائحة التنفيذية','يجب أن تكون المسافة بين الأسطر في الملخص حوالي 1 سم وذلك طبقاً لأحكام المادة الحادية عشرة الفقرة الخامسة من اللائحة التنفيذية',
        (SELECT x.id FROM application.lk_application_category x WHERE saip_code = 'PATENT'),
        (SELECT x.id FROM application.lk_sections x WHERE code = 'APPLICATION'),
        (SELECT x.id FROM application.lk_steps x WHERE code = 'Step1'),
        (SELECT x.id FROM application.lk_attributes x WHERE code = 'DESCRIPTION_AR'),
        'APPLICANT');
INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES ((select max(id) + 1 from application.lk_notes),'DB','2023-05-12 10:22:44.334',0,(select max(id) + 1 from application.lk_notes),'الوصف المقدم عن النموذج الصناعي لا يبين استخدامات التصميم','الوصف لايبين استخدامات التصميم',true,'الوصف المقدم عن النموذج الصناعي لا يبين استخدامات التصميم','الوصف لايبين استخدامات التصميم',
        (SELECT x.id FROM application.lk_application_category x WHERE saip_code = 'PATENT'),
        (SELECT x.id FROM application.lk_sections x WHERE code = 'APPLICATION'),
        (SELECT x.id FROM application.lk_steps x WHERE code = 'Step1'),
        (SELECT x.id FROM application.lk_attributes x WHERE code = 'DESCRIPTION_EN'),
        'APPLICANT');
INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES ((select max(id) + 1 from application.lk_notes),'DB','2023-05-12 10:22:44.334',0,(select max(id) + 1 from application.lk_notes),'الأسبقية لا تخص الطلب','الأسبقية لا تخص الطلب',true,'
الأسبقية لا تخص الطلب','الأسبقية لا تخص الطلب'
           ,(SELECT x.id FROM application.lk_application_category x WHERE saip_code = 'PATENT'),
        (SELECT x.id FROM application.lk_sections x WHERE code = 'APPLICATION'),
        (SELECT x.id FROM application.lk_steps x WHERE code = 'Step1'),
        (SELECT x.id FROM application.lk_attributes x WHERE code = 'DESCRIPTION_EN'),'APPLICANT');




INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'مقدم الطلب لم يطالب بالأسبقية عند تقديم الطلب الحالي لذلك فإن تاريخ الإيداع الفعلي لــ عنصر/ عناصر  الحماية]**[  هو تاريخ الإيداع للطلب الحالي لدى الهيئة السعودية للملكية الفكرية والذي يوافق ‏**‏/**‏/**** م.
','مقدم الطلب لم يطالب بالأسبقية عند تقديم الطلب الحالي لذلك فإن تاريخ الإيداع الفعلي لــ عنصر/ عناصر  الحماية]**[  هو تاريخ الإيداع للطلب الحالي لدى الهيئة السعودية للملكية الفكرية والذي يوافق ‏**‏/**‏/**** م.
',true,'مقدم الطلب لم يطالب بالأسبقية عند تقديم الطلب الحالي لذلك فإن تاريخ الإيداع الفعلي لــ عنصر/ عناصر  الحماية]**[  هو تاريخ الإيداع للطلب الحالي لدى الهيئة السعودية للملكية الفكرية والذي يوافق ‏**‏/**‏/**** م.
','مقدم الطلب لم يطالب بالأسبقية عند تقديم الطلب الحالي لذلك فإن تاريخ الإيداع الفعلي لــ عنصر/ عناصر  الحماية]**[  هو تاريخ الإيداع للطلب الحالي لدى الهيئة السعودية للملكية الفكرية والذي يوافق ‏**‏/**‏/**** م.
',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'PRIORITY'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='NOT-CLAIM-Priority')
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'*المطالبة بأسبقية واحدة
تعد الأسبقية المقررة للطلب السابق طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. مقبولة وفقاً للمادة العاشرة الفقرتين (أ) و (ب) من نظام براءات الاختراع والتصميمات التخطيطية للدارات المتكاملة والأصناف النباتية والنماذج الصناعية. لذلك، فإن تاريخ الإيداع الفعلي لعناصر الحماية الحالية هو تاريخ إيداع الطلب السابق.
','*المطالبة بأسبقية واحدة
تعد الأسبقية المقررة للطلب السابق طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. مقبولة وفقاً للمادة العاشرة الفقرتين (أ) و (ب) من نظام براءات الاختراع والتصميمات التخطيطية للدارات المتكاملة والأصناف النباتية والنماذج الصناعية. لذلك، فإن تاريخ الإيداع الفعلي لعناصر الحماية الحالية هو تاريخ إيداع الطلب السابق.
',true,'*المطالبة بأسبقية واحدة
تعد الأسبقية المقررة للطلب السابق طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. مقبولة وفقاً للمادة العاشرة الفقرتين (أ) و (ب) من نظام براءات الاختراع والتصميمات التخطيطية للدارات المتكاملة والأصناف النباتية والنماذج الصناعية. لذلك، فإن تاريخ الإيداع الفعلي لعناصر الحماية الحالية هو تاريخ إيداع الطلب السابق.
','*المطالبة بأسبقية واحدة
تعد الأسبقية المقررة للطلب السابق طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. مقبولة وفقاً للمادة العاشرة الفقرتين (أ) و (ب) من نظام براءات الاختراع والتصميمات التخطيطية للدارات المتكاملة والأصناف النباتية والنماذج الصناعية. لذلك، فإن تاريخ الإيداع الفعلي لعناصر الحماية الحالية هو تاريخ إيداع الطلب السابق.
',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'PRIORITY'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='ACCEPTABLE')
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'•المطالبة بأسبقيات متعددة
تعد الأسبقيات المتعددة المقررة للطلبات السابقة طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. وطلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م مقبولة وفقاً للمادة العاشرة الفقرتين (أ) و (ب) من نظام براءات الاختراع والتصميمات التخطيطية للدارات المتكاملة والأصناف النباتية والنماذج الصناعية. لذلك، فإن تاريخ الإيداع الفعلي لعناصر الحماية الحالية هو تاريخ إيداع الطلب السابق.
','•المطالبة بأسبقيات متعددة
تعد الأسبقيات المتعددة المقررة للطلبات السابقة طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. وطلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م مقبولة وفقاً للمادة العاشرة الفقرتين (أ) و (ب) من نظام براءات الاختراع والتصميمات التخطيطية للدارات المتكاملة والأصناف النباتية والنماذج الصناعية. لذلك، فإن تاريخ الإيداع الفعلي لعناصر الحماية الحالية هو تاريخ إيداع الطلب السابق.
',true,'•المطالبة بأسبقيات متعددة
تعد الأسبقيات المتعددة المقررة للطلبات السابقة طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. وطلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م مقبولة وفقاً للمادة العاشرة الفقرتين (أ) و (ب) من نظام براءات الاختراع والتصميمات التخطيطية للدارات المتكاملة والأصناف النباتية والنماذج الصناعية. لذلك، فإن تاريخ الإيداع الفعلي لعناصر الحماية الحالية هو تاريخ إيداع الطلب السابق.
','•المطالبة بأسبقيات متعددة
تعد الأسبقيات المتعددة المقررة للطلبات السابقة طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. وطلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م مقبولة وفقاً للمادة العاشرة الفقرتين (أ) و (ب) من نظام براءات الاختراع والتصميمات التخطيطية للدارات المتكاملة والأصناف النباتية والنماذج الصناعية. لذلك، فإن تاريخ الإيداع الفعلي لعناصر الحماية الحالية هو تاريخ إيداع الطلب السابق.
',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'PRIORITY'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='ACCEPTABLE')
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'*المطالبة بأسبقية واحدة
تعد الأسبقية المقررة للطلب السابق طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. مقبولة فقط لعناصر الحماية ]**[ حيث لا يوجد ما يدعم عنصر/عناصر الحماية ]**[ في الطلب السابق، لذلك فإن تاريخ الإيداع الفعلي لعناصر الحماية ] كتابة رقم العنصر الغير مدعوم في طلب الأسبقية[  هو تاريخ إيداع الطلب لدى الهيئة السعودية للملكية الفكرية والذي يوافق ‏**‏/**‏/**** م.
','*المطالبة بأسبقية واحدة
تعد الأسبقية المقررة للطلب السابق طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. مقبولة فقط لعناصر الحماية ]**[ حيث لا يوجد ما يدعم عنصر/عناصر الحماية ]**[ في الطلب السابق، لذلك فإن تاريخ الإيداع الفعلي لعناصر الحماية ] كتابة رقم العنصر الغير مدعوم في طلب الأسبقية[  هو تاريخ إيداع الطلب لدى الهيئة السعودية للملكية الفكرية والذي يوافق ‏**‏/**‏/**** م.
',true,'*المطالبة بأسبقية واحدة
تعد الأسبقية المقررة للطلب السابق طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. مقبولة فقط لعناصر الحماية ]**[ حيث لا يوجد ما يدعم عنصر/عناصر الحماية ]**[ في الطلب السابق، لذلك فإن تاريخ الإيداع الفعلي لعناصر الحماية ] كتابة رقم العنصر الغير مدعوم في طلب الأسبقية[  هو تاريخ إيداع الطلب لدى الهيئة السعودية للملكية الفكرية والذي يوافق ‏**‏/**‏/**** م.
','*المطالبة بأسبقية واحدة
تعد الأسبقية المقررة للطلب السابق طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. مقبولة فقط لعناصر الحماية ]**[ حيث لا يوجد ما يدعم عنصر/عناصر الحماية ]**[ في الطلب السابق، لذلك فإن تاريخ الإيداع الفعلي لعناصر الحماية ] كتابة رقم العنصر الغير مدعوم في طلب الأسبقية[  هو تاريخ إيداع الطلب لدى الهيئة السعودية للملكية الفكرية والذي يوافق ‏**‏/**‏/**** م.
',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'PRIORITY'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='PARTIALLY-ACCEPTED')
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'المطالبة بأسبقيات متعددة*
تعد الأسبقيات المتعددة المقررة الطلبات السابقة طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. و طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. مقبولة فقط لعناصر الحماية ]**] حيث لا يوجد ما يدعم عنصر/عناصر الحماية ] كتابة رقم العنصر[ في الطلبات السابقة، لذلك فإن تاريخ الإيداع الفعلي لعناصر الحماية ] كتابة رقم العنصر الغير مدعوم في طلب الأسبقية[  هو تاريخ إيداع الطلب لدى الهيئة السعودية للملكية الفكرية والذي يوافق ‏**‏/**‏/**** م.','المطالبة بأسبقيات متعددة*
تعد الأسبقيات المتعددة المقررة الطلبات السابقة طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. و طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. مقبولة فقط لعناصر الحماية ]**] حيث لا يوجد ما يدعم عنصر/عناصر الحماية ] كتابة رقم العنصر[ في الطلبات السابقة، لذلك فإن تاريخ الإيداع الفعلي لعناصر الحماية ] كتابة رقم العنصر الغير مدعوم في طلب الأسبقية[  هو تاريخ إيداع الطلب لدى الهيئة السعودية للملكية الفكرية والذي يوافق ‏**‏/**‏/**** م.',true,'المطالبة بأسبقيات متعددة*
تعد الأسبقيات المتعددة المقررة الطلبات السابقة طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. و طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. مقبولة فقط لعناصر الحماية ]**] حيث لا يوجد ما يدعم عنصر/عناصر الحماية ] كتابة رقم العنصر[ في الطلبات السابقة، لذلك فإن تاريخ الإيداع الفعلي لعناصر الحماية ] كتابة رقم العنصر الغير مدعوم في طلب الأسبقية[  هو تاريخ إيداع الطلب لدى الهيئة السعودية للملكية الفكرية والذي يوافق ‏**‏/**‏/**** م.','المطالبة بأسبقيات متعددة*
تعد الأسبقيات المتعددة المقررة الطلبات السابقة طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. و طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. مقبولة فقط لعناصر الحماية ]**] حيث لا يوجد ما يدعم عنصر/عناصر الحماية ] كتابة رقم العنصر[ في الطلبات السابقة، لذلك فإن تاريخ الإيداع الفعلي لعناصر الحماية ] كتابة رقم العنصر الغير مدعوم في طلب الأسبقية[  هو تاريخ إيداع الطلب لدى الهيئة السعودية للملكية الفكرية والذي يوافق ‏**‏/**‏/**** م.',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'PRIORITY'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='PARTIALLY-ACCEPTED')
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'•الأسبقية غير مقبولة كونها لا تدعم عنصر/عناصر الحماية
تعد الأسبقية المقررة للطلب السابق طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. غير مقبولة حيث لا يوجد ما يدعم عنصر/عناصر الحماية ]***[ في الطلب السابق/الطلبات لسابقة, لذلك فإن تاريخ الإيداع الفعلي لعناصر الحماية ] كتابة رقم العنصر الغير مدعوم في طلب الأسبقية[  هو تاريخ إيداع الطلب لدى الهيئة السعودية للملكية السعودية والذي يوافق ‏**‏/**‏/**** م.
','•الأسبقية غير مقبولة كونها لا تدعم عنصر/عناصر الحماية
تعد الأسبقية المقررة للطلب السابق طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. غير مقبولة حيث لا يوجد ما يدعم عنصر/عناصر الحماية ]***[ في الطلب السابق/الطلبات لسابقة, لذلك فإن تاريخ الإيداع الفعلي لعناصر الحماية ] كتابة رقم العنصر الغير مدعوم في طلب الأسبقية[  هو تاريخ إيداع الطلب لدى الهيئة السعودية للملكية السعودية والذي يوافق ‏**‏/**‏/**** م.
',true,'•الأسبقية غير مقبولة كونها لا تدعم عنصر/عناصر الحماية
تعد الأسبقية المقررة للطلب السابق طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. غير مقبولة حيث لا يوجد ما يدعم عنصر/عناصر الحماية ]***[ في الطلب السابق/الطلبات لسابقة, لذلك فإن تاريخ الإيداع الفعلي لعناصر الحماية ] كتابة رقم العنصر الغير مدعوم في طلب الأسبقية[  هو تاريخ إيداع الطلب لدى الهيئة السعودية للملكية السعودية والذي يوافق ‏**‏/**‏/**** م.
','•الأسبقية غير مقبولة كونها لا تدعم عنصر/عناصر الحماية
تعد الأسبقية المقررة للطلب السابق طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. غير مقبولة حيث لا يوجد ما يدعم عنصر/عناصر الحماية ]***[ في الطلب السابق/الطلبات لسابقة, لذلك فإن تاريخ الإيداع الفعلي لعناصر الحماية ] كتابة رقم العنصر الغير مدعوم في طلب الأسبقية[  هو تاريخ إيداع الطلب لدى الهيئة السعودية للملكية السعودية والذي يوافق ‏**‏/**‏/**** م.
',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'PRIORITY'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='UNACCEPTABLE')
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'لأسبقية غير مقبولة بسبب عدم إرفاق ترجمة الأسبقية خلال المهلة النظامية
تعد الأسبقية المقررة للطلب السابق طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. غير مقبولة بسبب عدم إرفاق ترجمة الأسبقية خلال المهلة النظامية، لذلك فإن تاريخ الإيداع الفعلي لعناصر الحماية [**[ هو تاريخ إيداع الطلب لدى الهيئة السعودية للملكية الفكرية والذي يوافق ‏**‏/**‏/**** م.','لأسبقية غير مقبولة بسبب عدم إرفاق ترجمة الأسبقية خلال المهلة النظامية
تعد الأسبقية المقررة للطلب السابق طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. غير مقبولة بسبب عدم إرفاق ترجمة الأسبقية خلال المهلة النظامية، لذلك فإن تاريخ الإيداع الفعلي لعناصر الحماية [**[ هو تاريخ إيداع الطلب لدى الهيئة السعودية للملكية الفكرية والذي يوافق ‏**‏/**‏/**** م.',true,'لأسبقية غير مقبولة بسبب عدم إرفاق ترجمة الأسبقية خلال المهلة النظامية
تعد الأسبقية المقررة للطلب السابق طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. غير مقبولة بسبب عدم إرفاق ترجمة الأسبقية خلال المهلة النظامية، لذلك فإن تاريخ الإيداع الفعلي لعناصر الحماية [**[ هو تاريخ إيداع الطلب لدى الهيئة السعودية للملكية الفكرية والذي يوافق ‏**‏/**‏/**** م.','لأسبقية غير مقبولة بسبب عدم إرفاق ترجمة الأسبقية خلال المهلة النظامية
تعد الأسبقية المقررة للطلب السابق طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. غير مقبولة بسبب عدم إرفاق ترجمة الأسبقية خلال المهلة النظامية، لذلك فإن تاريخ الإيداع الفعلي لعناصر الحماية [**[ هو تاريخ إيداع الطلب لدى الهيئة السعودية للملكية الفكرية والذي يوافق ‏**‏/**‏/**** م.',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'PRIORITY'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='UNACCEPTABLE')
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'
•الأسبقية غير مقبولة بسبب تجاوز المدة النظامية للإيداع
-تعد الأسبقية المقررة للطلب السابق طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. غير مقبولة بسبب تجاوز المدة النظامية للإيداع. لذلك فإن تاريخ الإيداع الفعلي لعناصر الحماية [**[ هو تاريخ إيداع الطلب لدى الهيئة السعودية للملكية الفكرية والذي يوافق ‏**‏/**‏/**** م.
-تعد الأسبقية المقررة للطلب السابق طلب براءة الاختراع (اسم الدولة) رقم (PCT/***/***) وتاريخ ‏ ‏ **‏/**‏/**** م. غير مقبولة حيث أن مقدم الطلب لم يمتثل بالمهلة المحددة للدخول في المرحلة الوطنية".  وعليه تعتبر الأسبقية (اسم الدولة) رقم (***) بتاريخ 26/05/ 2014 م. غير مقبولة وبالتالي فإن تاريخ الإيداع الفعلي للطلب الحالي ‏ **‏/**‏/**** هـ الموافق ‏ **‏/**‏/**** م. يعد تاريخ الإيداع المعتمد في فحص الطلب.
-تم تقديم الطلب الحالي كطلب مودع حسب معاهدة التعاون بشأن البراءات PCT، حيث تم إيداع الطلب كمرحلة وطنية في المملكة العربية السعودية بتاريخ 22‏/04‏/1437 هـ الموافق ‏ **‏/**‏/**** م. وبعد الرجوع إلى الأسبقية اتضح أنه تم إيداعها بالرقم (***) والرقم (***) وتاريخ ‏ **‏/**‏/**** م. وتم تقديم طلب دولي بالرقم (***) وتاريخ ‏ **‏/**‏/**** م. وعليه، فقد تجاوز ايداع طلب المرحلة الوطنية رقم (***) المدة النظامية (30
شهراً)، وبناءً على ما سبق فإنه لا يمكن اعتبار الطلب الحالي مودع حسب معاهدة التعاون بشأن البراءات PCT، ويعتبر تاريخ الإيداع الحالي ‏ **‏/**‏/**** هـ الموافق 01 ‏ **‏/**‏/**** م. هو تاريخ تقديم الطلب الحالي.
تعد المطالبة بالأسبقية المقررة للطلب السابق طلب براءة الاختراع (الدولية) رقم (PCT/***/***) وتاريخ ‏26‏/11‏/2013م.  غير مقبولة بسبب أن الطلب الحالي محل الفحص وصل للمرحلة الوطنية عبر معاهدة التعاون بشأن البراءات، وذلك بسبب أنه تم تجاوز المهلة النظامية (12 شهر)، وبالتالي فإن تاريخ الإيداع الفعلي لعناصر الحماية للطب الدولي في المرحلة الوطنية هو تاريخ إيداع الطلب الدولي لدى الهيئة السعودية للملكية الفكرية كعضو دولي لتسجيل الطلبات والذي يوافق ‏ **‏/**‏/**** م
','
•الأسبقية غير مقبولة بسبب تجاوز المدة النظامية للإيداع
-تعد الأسبقية المقررة للطلب السابق طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. غير مقبولة بسبب تجاوز المدة النظامية للإيداع. لذلك فإن تاريخ الإيداع الفعلي لعناصر الحماية [**[ هو تاريخ إيداع الطلب لدى الهيئة السعودية للملكية الفكرية والذي يوافق ‏**‏/**‏/**** م.
-تعد الأسبقية المقررة للطلب السابق طلب براءة الاختراع (اسم الدولة) رقم (PCT/***/***) وتاريخ ‏ ‏ **‏/**‏/**** م. غير مقبولة حيث أن مقدم الطلب لم يمتثل بالمهلة المحددة للدخول في المرحلة الوطنية".  وعليه تعتبر الأسبقية (اسم الدولة) رقم (***) بتاريخ 26/05/ 2014 م. غير مقبولة وبالتالي فإن تاريخ الإيداع الفعلي للطلب الحالي ‏ **‏/**‏/**** هـ الموافق ‏ **‏/**‏/**** م. يعد تاريخ الإيداع المعتمد في فحص الطلب.
-تم تقديم الطلب الحالي كطلب مودع حسب معاهدة التعاون بشأن البراءات PCT، حيث تم إيداع الطلب كمرحلة وطنية في المملكة العربية السعودية بتاريخ 22‏/04‏/1437 هـ الموافق ‏ **‏/**‏/**** م. وبعد الرجوع إلى الأسبقية اتضح أنه تم إيداعها بالرقم (***) والرقم (***) وتاريخ ‏ **‏/**‏/**** م. وتم تقديم طلب دولي بالرقم (***) وتاريخ ‏ **‏/**‏/**** م. وعليه، فقد تجاوز ايداع طلب المرحلة الوطنية رقم (***) المدة النظامية (30
شهراً)، وبناءً على ما سبق فإنه لا يمكن اعتبار الطلب الحالي مودع حسب معاهدة التعاون بشأن البراءات PCT، ويعتبر تاريخ الإيداع الحالي ‏ **‏/**‏/**** هـ الموافق 01 ‏ **‏/**‏/**** م. هو تاريخ تقديم الطلب الحالي.
تعد المطالبة بالأسبقية المقررة للطلب السابق طلب براءة الاختراع (الدولية) رقم (PCT/***/***) وتاريخ ‏26‏/11‏/2013م.  غير مقبولة بسبب أن الطلب الحالي محل الفحص وصل للمرحلة الوطنية عبر معاهدة التعاون بشأن البراءات، وذلك بسبب أنه تم تجاوز المهلة النظامية (12 شهر)، وبالتالي فإن تاريخ الإيداع الفعلي لعناصر الحماية للطب الدولي في المرحلة الوطنية هو تاريخ إيداع الطلب الدولي لدى الهيئة السعودية للملكية الفكرية كعضو دولي لتسجيل الطلبات والذي يوافق ‏ **‏/**‏/**** م
',true,'
•الأسبقية غير مقبولة بسبب تجاوز المدة النظامية للإيداع
-تعد الأسبقية المقررة للطلب السابق طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. غير مقبولة بسبب تجاوز المدة النظامية للإيداع. لذلك فإن تاريخ الإيداع الفعلي لعناصر الحماية [**[ هو تاريخ إيداع الطلب لدى الهيئة السعودية للملكية الفكرية والذي يوافق ‏**‏/**‏/**** م.
-تعد الأسبقية المقررة للطلب السابق طلب براءة الاختراع (اسم الدولة) رقم (PCT/***/***) وتاريخ ‏ ‏ **‏/**‏/**** م. غير مقبولة حيث أن مقدم الطلب لم يمتثل بالمهلة المحددة للدخول في المرحلة الوطنية".  وعليه تعتبر الأسبقية (اسم الدولة) رقم (***) بتاريخ 26/05/ 2014 م. غير مقبولة وبالتالي فإن تاريخ الإيداع الفعلي للطلب الحالي ‏ **‏/**‏/**** هـ الموافق ‏ **‏/**‏/**** م. يعد تاريخ الإيداع المعتمد في فحص الطلب.
-تم تقديم الطلب الحالي كطلب مودع حسب معاهدة التعاون بشأن البراءات PCT، حيث تم إيداع الطلب كمرحلة وطنية في المملكة العربية السعودية بتاريخ 22‏/04‏/1437 هـ الموافق ‏ **‏/**‏/**** م. وبعد الرجوع إلى الأسبقية اتضح أنه تم إيداعها بالرقم (***) والرقم (***) وتاريخ ‏ **‏/**‏/**** م. وتم تقديم طلب دولي بالرقم (***) وتاريخ ‏ **‏/**‏/**** م. وعليه، فقد تجاوز ايداع طلب المرحلة الوطنية رقم (***) المدة النظامية (30
شهراً)، وبناءً على ما سبق فإنه لا يمكن اعتبار الطلب الحالي مودع حسب معاهدة التعاون بشأن البراءات PCT، ويعتبر تاريخ الإيداع الحالي ‏ **‏/**‏/**** هـ الموافق 01 ‏ **‏/**‏/**** م. هو تاريخ تقديم الطلب الحالي.
تعد المطالبة بالأسبقية المقررة للطلب السابق طلب براءة الاختراع (الدولية) رقم (PCT/***/***) وتاريخ ‏26‏/11‏/2013م.  غير مقبولة بسبب أن الطلب الحالي محل الفحص وصل للمرحلة الوطنية عبر معاهدة التعاون بشأن البراءات، وذلك بسبب أنه تم تجاوز المهلة النظامية (12 شهر)، وبالتالي فإن تاريخ الإيداع الفعلي لعناصر الحماية للطب الدولي في المرحلة الوطنية هو تاريخ إيداع الطلب الدولي لدى الهيئة السعودية للملكية الفكرية كعضو دولي لتسجيل الطلبات والذي يوافق ‏ **‏/**‏/**** م
','
•الأسبقية غير مقبولة بسبب تجاوز المدة النظامية للإيداع
-تعد الأسبقية المقررة للطلب السابق طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. غير مقبولة بسبب تجاوز المدة النظامية للإيداع. لذلك فإن تاريخ الإيداع الفعلي لعناصر الحماية [**[ هو تاريخ إيداع الطلب لدى الهيئة السعودية للملكية الفكرية والذي يوافق ‏**‏/**‏/**** م.
-تعد الأسبقية المقررة للطلب السابق طلب براءة الاختراع (اسم الدولة) رقم (PCT/***/***) وتاريخ ‏ ‏ **‏/**‏/**** م. غير مقبولة حيث أن مقدم الطلب لم يمتثل بالمهلة المحددة للدخول في المرحلة الوطنية".  وعليه تعتبر الأسبقية (اسم الدولة) رقم (***) بتاريخ 26/05/ 2014 م. غير مقبولة وبالتالي فإن تاريخ الإيداع الفعلي للطلب الحالي ‏ **‏/**‏/**** هـ الموافق ‏ **‏/**‏/**** م. يعد تاريخ الإيداع المعتمد في فحص الطلب.
-تم تقديم الطلب الحالي كطلب مودع حسب معاهدة التعاون بشأن البراءات PCT، حيث تم إيداع الطلب كمرحلة وطنية في المملكة العربية السعودية بتاريخ 22‏/04‏/1437 هـ الموافق ‏ **‏/**‏/**** م. وبعد الرجوع إلى الأسبقية اتضح أنه تم إيداعها بالرقم (***) والرقم (***) وتاريخ ‏ **‏/**‏/**** م. وتم تقديم طلب دولي بالرقم (***) وتاريخ ‏ **‏/**‏/**** م. وعليه، فقد تجاوز ايداع طلب المرحلة الوطنية رقم (***) المدة النظامية (30
شهراً)، وبناءً على ما سبق فإنه لا يمكن اعتبار الطلب الحالي مودع حسب معاهدة التعاون بشأن البراءات PCT، ويعتبر تاريخ الإيداع الحالي ‏ **‏/**‏/**** هـ الموافق 01 ‏ **‏/**‏/**** م. هو تاريخ تقديم الطلب الحالي.
تعد المطالبة بالأسبقية المقررة للطلب السابق طلب براءة الاختراع (الدولية) رقم (PCT/***/***) وتاريخ ‏26‏/11‏/2013م.  غير مقبولة بسبب أن الطلب الحالي محل الفحص وصل للمرحلة الوطنية عبر معاهدة التعاون بشأن البراءات، وذلك بسبب أنه تم تجاوز المهلة النظامية (12 شهر)، وبالتالي فإن تاريخ الإيداع الفعلي لعناصر الحماية للطب الدولي في المرحلة الوطنية هو تاريخ إيداع الطلب الدولي لدى الهيئة السعودية للملكية الفكرية كعضو دولي لتسجيل الطلبات والذي يوافق ‏ **‏/**‏/**** م
',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'PRIORITY'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='UNACCEPTABLE')
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'1--	تعد الأسبقية المقررة للطلب السابق طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. غير مقبولة بسبب تجاوز المدة النظامية للإيداع. لذلك فإن تاريخ الإيداع الفعلي لعناصر الحماية [**[ هو تاريخ إيداع الطلب لدى الهيئة السعودية للملكية الفكرية والذي يوافق ‏**‏/**‏/**** م. ','1--	تعد الأسبقية المقررة للطلب السابق طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. غير مقبولة بسبب تجاوز المدة النظامية للإيداع. لذلك فإن تاريخ الإيداع الفعلي لعناصر الحماية [**[ هو تاريخ إيداع الطلب لدى الهيئة السعودية للملكية الفكرية والذي يوافق ‏**‏/**‏/**** م. ',true,'1--	تعد الأسبقية المقررة للطلب السابق طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. غير مقبولة بسبب تجاوز المدة النظامية للإيداع. لذلك فإن تاريخ الإيداع الفعلي لعناصر الحماية [**[ هو تاريخ إيداع الطلب لدى الهيئة السعودية للملكية الفكرية والذي يوافق ‏**‏/**‏/**** م. ','1--	تعد الأسبقية المقررة للطلب السابق طلب براءة الاختراع (اسم الدولة) رقم (كتابة رقم الطلب) وتاريخ ‏**‏/**‏/**** م. غير مقبولة بسبب تجاوز المدة النظامية للإيداع. لذلك فإن تاريخ الإيداع الفعلي لعناصر الحماية [**[ هو تاريخ إيداع الطلب لدى الهيئة السعودية للملكية الفكرية والذي يوافق ‏**‏/**‏/**** م. ',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'PRIORITY'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='UNACCEPTABLE')
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'2-تعد الأسبقية المقررة للطلب السابق طلب براءة الاختراع (اسم الدولة) رقم (PCT/***/***) وتاريخ ‏ ‏ **‏/**‏/**** م. غير مقبولة حيث أن مقدم الطلب لم يمتثل بالمهلة المحددة للدخول في المرحلة الوطنية".  وعليه تعتبر الأسبقية (اسم الدولة) رقم (***) بتاريخ 26/05/ 2014 م. غير مقبولة وبالتالي فإن تاريخ الإيداع الفعلي للطلب الحالي ‏ **‏/**‏/**** هـ الموافق ‏ **‏/**‏/**** م. يعد تاريخ الإيداع المعتمد في فحص الطلب.','2-تعد الأسبقية المقررة للطلب السابق طلب براءة الاختراع (اسم الدولة) رقم (PCT/***/***) وتاريخ ‏ ‏ **‏/**‏/**** م. غير مقبولة حيث أن مقدم الطلب لم يمتثل بالمهلة المحددة للدخول في المرحلة الوطنية".  وعليه تعتبر الأسبقية (اسم الدولة) رقم (***) بتاريخ 26/05/ 2014 م. غير مقبولة وبالتالي فإن تاريخ الإيداع الفعلي للطلب الحالي ‏ **‏/**‏/**** هـ الموافق ‏ **‏/**‏/**** م. يعد تاريخ الإيداع المعتمد في فحص الطلب.',true,'2-تعد الأسبقية المقررة للطلب السابق طلب براءة الاختراع (اسم الدولة) رقم (PCT/***/***) وتاريخ ‏ ‏ **‏/**‏/**** م. غير مقبولة حيث أن مقدم الطلب لم يمتثل بالمهلة المحددة للدخول في المرحلة الوطنية".  وعليه تعتبر الأسبقية (اسم الدولة) رقم (***) بتاريخ 26/05/ 2014 م. غير مقبولة وبالتالي فإن تاريخ الإيداع الفعلي للطلب الحالي ‏ **‏/**‏/**** هـ الموافق ‏ **‏/**‏/**** م. يعد تاريخ الإيداع المعتمد في فحص الطلب.','2-تعد الأسبقية المقررة للطلب السابق طلب براءة الاختراع (اسم الدولة) رقم (PCT/***/***) وتاريخ ‏ ‏ **‏/**‏/**** م. غير مقبولة حيث أن مقدم الطلب لم يمتثل بالمهلة المحددة للدخول في المرحلة الوطنية".  وعليه تعتبر الأسبقية (اسم الدولة) رقم (***) بتاريخ 26/05/ 2014 م. غير مقبولة وبالتالي فإن تاريخ الإيداع الفعلي للطلب الحالي ‏ **‏/**‏/**** هـ الموافق ‏ **‏/**‏/**** م. يعد تاريخ الإيداع المعتمد في فحص الطلب.',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'PRIORITY'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='UNACCEPTABLE')
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'3-تم تقديم الطلب الحالي كطلب مودع حسب معاهدة التعاون بشأن البراءات PCT، حيث تم إيداع الطلب كمرحلة وطنية في المملكة العربية السعودية بتاريخ 22‏/04‏/1437 هـ الموافق ‏ **‏/**‏/**** م. وبعد الرجوع إلى الأسبقية اتضح أنه تم إيداعها بالرقم (***) والرقم (***) وتاريخ ‏ **‏/**‏/**** م. وتم تقديم طلب دولي بالرقم (***) وتاريخ ‏ **‏/**‏/**** م. وعليه، فقد تجاوز ايداع طلب المرحلة الوطنية رقم (***) المدة النظامية (30
شهراً)، وبناءً على ما سبق فإنه لا يمكن اعتبار الطلب الحالي مودع حسب معاهدة التعاون بشأن البراءات PCT، ويعتبر تاريخ الإيداع الحالي ‏ **‏/**‏/**** هـ الموافق 01 ‏ **‏/**‏/**** م. هو تاريخ تقديم الطلب الحالي. ','3-تم تقديم الطلب الحالي كطلب مودع حسب معاهدة التعاون بشأن البراءات PCT، حيث تم إيداع الطلب كمرحلة وطنية في المملكة العربية السعودية بتاريخ 22‏/04‏/1437 هـ الموافق ‏ **‏/**‏/**** م. وبعد الرجوع إلى الأسبقية اتضح أنه تم إيداعها بالرقم (***) والرقم (***) وتاريخ ‏ **‏/**‏/**** م. وتم تقديم طلب دولي بالرقم (***) وتاريخ ‏ **‏/**‏/**** م. وعليه، فقد تجاوز ايداع طلب المرحلة الوطنية رقم (***) المدة النظامية (30
شهراً)، وبناءً على ما سبق فإنه لا يمكن اعتبار الطلب الحالي مودع حسب معاهدة التعاون بشأن البراءات PCT، ويعتبر تاريخ الإيداع الحالي ‏ **‏/**‏/**** هـ الموافق 01 ‏ **‏/**‏/**** م. هو تاريخ تقديم الطلب الحالي. ',true,'3-تم تقديم الطلب الحالي كطلب مودع حسب معاهدة التعاون بشأن البراءات PCT، حيث تم إيداع الطلب كمرحلة وطنية في المملكة العربية السعودية بتاريخ 22‏/04‏/1437 هـ الموافق ‏ **‏/**‏/**** م. وبعد الرجوع إلى الأسبقية اتضح أنه تم إيداعها بالرقم (***) والرقم (***) وتاريخ ‏ **‏/**‏/**** م. وتم تقديم طلب دولي بالرقم (***) وتاريخ ‏ **‏/**‏/**** م. وعليه، فقد تجاوز ايداع طلب المرحلة الوطنية رقم (***) المدة النظامية (30
شهراً)، وبناءً على ما سبق فإنه لا يمكن اعتبار الطلب الحالي مودع حسب معاهدة التعاون بشأن البراءات PCT، ويعتبر تاريخ الإيداع الحالي ‏ **‏/**‏/**** هـ الموافق 01 ‏ **‏/**‏/**** م. هو تاريخ تقديم الطلب الحالي. ','3-تم تقديم الطلب الحالي كطلب مودع حسب معاهدة التعاون بشأن البراءات PCT، حيث تم إيداع الطلب كمرحلة وطنية في المملكة العربية السعودية بتاريخ 22‏/04‏/1437 هـ الموافق ‏ **‏/**‏/**** م. وبعد الرجوع إلى الأسبقية اتضح أنه تم إيداعها بالرقم (***) والرقم (***) وتاريخ ‏ **‏/**‏/**** م. وتم تقديم طلب دولي بالرقم (***) وتاريخ ‏ **‏/**‏/**** م. وعليه، فقد تجاوز ايداع طلب المرحلة الوطنية رقم (***) المدة النظامية (30
شهراً)، وبناءً على ما سبق فإنه لا يمكن اعتبار الطلب الحالي مودع حسب معاهدة التعاون بشأن البراءات PCT، ويعتبر تاريخ الإيداع الحالي ‏ **‏/**‏/**** هـ الموافق 01 ‏ **‏/**‏/**** م. هو تاريخ تقديم الطلب الحالي. ',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'PRIORITY'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='UNACCEPTABLE')
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'4-تعد المطالبة بالأسبقية المقررة للطلب السابق طلب براءة الاختراع (الدولية) رقم (PCT/***/***) وتاريخ ‏26‏/11‏/2013م.  غير مقبولة بسبب أن الطلب الحالي محل الفحص وصل للمرحلة الوطنية عبر معاهدة التعاون بشأن البراءات، وذلك بسبب أنه تم تجاوز المهلة النظامية (12 شهر)، وبالتالي فإن تاريخ الإيداع الفعلي لعناصر الحماية للطب الدولي في المرحلة الوطنية هو تاريخ إيداع الطلب الدولي لدى الهيئة السعودية للملكية الفكرية كعضو دولي لتسجيل الطلبات والذي يوافق ‏ **‏/**‏/**** م
-	تم تقديم الطلب الحالي كطلب مودع حسب معاهدة التعاون بشأن البراءات PCT، حيث تم إيداع الطلب كمرحلة وطنية في المملكة العربية السعودية بتاريخ 22‏/04‏/1437 هـ الموافق ‏ **‏/**‏/**** م. وبعد الرجوع إلى الأسبقية اتضح أنه تم إيداعها بالرقم (***) والرقم (***) وتاريخ ‏ **‏/**‏/**** م. وتم تقديم طلب دولي بالرقم (***) وتاريخ ‏ **‏/**‏/**** م. وعليه، فقد تجاوز ايداع طلب المرحلة الوطنية رقم (***) المدة النظامية (30
شهراً)، وبناءً على ما سبق فإنه لا يمكن اعتبار الطلب الحالي مودع حسب معاهدة التعاون بشأن البراءات PCT، ويعتبر تاريخ الإيداع الحالي ‏ **‏/**‏/**** هـ الموافق 01 ‏ **‏/**‏/**** م. هو تاريخ تقديم الطلب الحالي. ','4-تعد المطالبة بالأسبقية المقررة للطلب السابق طلب براءة الاختراع (الدولية) رقم (PCT/***/***) وتاريخ ‏26‏/11‏/2013م.  غير مقبولة بسبب أن الطلب الحالي محل الفحص وصل للمرحلة الوطنية عبر معاهدة التعاون بشأن البراءات، وذلك بسبب أنه تم تجاوز المهلة النظامية (12 شهر)، وبالتالي فإن تاريخ الإيداع الفعلي لعناصر الحماية للطب الدولي في المرحلة الوطنية هو تاريخ إيداع الطلب الدولي لدى الهيئة السعودية للملكية الفكرية كعضو دولي لتسجيل الطلبات والذي يوافق ‏ **‏/**‏/**** م
-	تم تقديم الطلب الحالي كطلب مودع حسب معاهدة التعاون بشأن البراءات PCT، حيث تم إيداع الطلب كمرحلة وطنية في المملكة العربية السعودية بتاريخ 22‏/04‏/1437 هـ الموافق ‏ **‏/**‏/**** م. وبعد الرجوع إلى الأسبقية اتضح أنه تم إيداعها بالرقم (***) والرقم (***) وتاريخ ‏ **‏/**‏/**** م. وتم تقديم طلب دولي بالرقم (***) وتاريخ ‏ **‏/**‏/**** م. وعليه، فقد تجاوز ايداع طلب المرحلة الوطنية رقم (***) المدة النظامية (30
شهراً)، وبناءً على ما سبق فإنه لا يمكن اعتبار الطلب الحالي مودع حسب معاهدة التعاون بشأن البراءات PCT، ويعتبر تاريخ الإيداع الحالي ‏ **‏/**‏/**** هـ الموافق 01 ‏ **‏/**‏/**** م. هو تاريخ تقديم الطلب الحالي. ',true,'4-تعد المطالبة بالأسبقية المقررة للطلب السابق طلب براءة الاختراع (الدولية) رقم (PCT/***/***) وتاريخ ‏26‏/11‏/2013م.  غير مقبولة بسبب أن الطلب الحالي محل الفحص وصل للمرحلة الوطنية عبر معاهدة التعاون بشأن البراءات، وذلك بسبب أنه تم تجاوز المهلة النظامية (12 شهر)، وبالتالي فإن تاريخ الإيداع الفعلي لعناصر الحماية للطب الدولي في المرحلة الوطنية هو تاريخ إيداع الطلب الدولي لدى الهيئة السعودية للملكية الفكرية كعضو دولي لتسجيل الطلبات والذي يوافق ‏ **‏/**‏/**** م
-	تم تقديم الطلب الحالي كطلب مودع حسب معاهدة التعاون بشأن البراءات PCT، حيث تم إيداع الطلب كمرحلة وطنية في المملكة العربية السعودية بتاريخ 22‏/04‏/1437 هـ الموافق ‏ **‏/**‏/**** م. وبعد الرجوع إلى الأسبقية اتضح أنه تم إيداعها بالرقم (***) والرقم (***) وتاريخ ‏ **‏/**‏/**** م. وتم تقديم طلب دولي بالرقم (***) وتاريخ ‏ **‏/**‏/**** م. وعليه، فقد تجاوز ايداع طلب المرحلة الوطنية رقم (***) المدة النظامية (30
شهراً)، وبناءً على ما سبق فإنه لا يمكن اعتبار الطلب الحالي مودع حسب معاهدة التعاون بشأن البراءات PCT، ويعتبر تاريخ الإيداع الحالي ‏ **‏/**‏/**** هـ الموافق 01 ‏ **‏/**‏/**** م. هو تاريخ تقديم الطلب الحالي. ','4-تعد المطالبة بالأسبقية المقررة للطلب السابق طلب براءة الاختراع (الدولية) رقم (PCT/***/***) وتاريخ ‏26‏/11‏/2013م.  غير مقبولة بسبب أن الطلب الحالي محل الفحص وصل للمرحلة الوطنية عبر معاهدة التعاون بشأن البراءات، وذلك بسبب أنه تم تجاوز المهلة النظامية (12 شهر)، وبالتالي فإن تاريخ الإيداع الفعلي لعناصر الحماية للطب الدولي في المرحلة الوطنية هو تاريخ إيداع الطلب الدولي لدى الهيئة السعودية للملكية الفكرية كعضو دولي لتسجيل الطلبات والذي يوافق ‏ **‏/**‏/**** م
-	تم تقديم الطلب الحالي كطلب مودع حسب معاهدة التعاون بشأن البراءات PCT، حيث تم إيداع الطلب كمرحلة وطنية في المملكة العربية السعودية بتاريخ 22‏/04‏/1437 هـ الموافق ‏ **‏/**‏/**** م. وبعد الرجوع إلى الأسبقية اتضح أنه تم إيداعها بالرقم (***) والرقم (***) وتاريخ ‏ **‏/**‏/**** م. وتم تقديم طلب دولي بالرقم (***) وتاريخ ‏ **‏/**‏/**** م. وعليه، فقد تجاوز ايداع طلب المرحلة الوطنية رقم (***) المدة النظامية (30
شهراً)، وبناءً على ما سبق فإنه لا يمكن اعتبار الطلب الحالي مودع حسب معاهدة التعاون بشأن البراءات PCT، ويعتبر تاريخ الإيداع الحالي ‏ **‏/**‏/**** هـ الموافق 01 ‏ **‏/**‏/**** م. هو تاريخ تقديم الطلب الحالي. ',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'PRIORITY'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='UNACCEPTABLE')
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'5-تعد المطالبة بالأسبقية المقررة للطلب السابق طلب براءة الاختراع (الدولية) رقم (PCT/***/***) وتاريخ ‏26‏/11‏/2013م.  غير مقبولة بسبب أن الطلب الحالي محل الفحص وصل للمرحلة الوطنية عبر معاهدة التعاون بشأن البراءات، وذلك بسبب أنه تم تجاوز المهلة النظامية (12 شهر)، وبالتالي فإن تاريخ الإيداع الفعلي لعناصر الحماية للطب الدولي في المرحلة الوطنية هو تاريخ إيداع الطلب الدولي لدى الهيئة السعودية للملكية الفكرية كعضو دولي لتسجيل الطلبات والذي يوافق ‏ **‏/**‏/**** م...','5-تعد المطالبة بالأسبقية المقررة للطلب السابق طلب براءة الاختراع (الدولية) رقم (PCT/***/***) وتاريخ ‏26‏/11‏/2013م.  غير مقبولة بسبب أن الطلب الحالي محل الفحص وصل للمرحلة الوطنية عبر معاهدة التعاون بشأن البراءات، وذلك بسبب أنه تم تجاوز المهلة النظامية (12 شهر)، وبالتالي فإن تاريخ الإيداع الفعلي لعناصر الحماية للطب الدولي في المرحلة الوطنية هو تاريخ إيداع الطلب الدولي لدى الهيئة السعودية للملكية الفكرية كعضو دولي لتسجيل الطلبات والذي يوافق ‏ **‏/**‏/**** م...',true,'5-تعد المطالبة بالأسبقية المقررة للطلب السابق طلب براءة الاختراع (الدولية) رقم (PCT/***/***) وتاريخ ‏26‏/11‏/2013م.  غير مقبولة بسبب أن الطلب الحالي محل الفحص وصل للمرحلة الوطنية عبر معاهدة التعاون بشأن البراءات، وذلك بسبب أنه تم تجاوز المهلة النظامية (12 شهر)، وبالتالي فإن تاريخ الإيداع الفعلي لعناصر الحماية للطب الدولي في المرحلة الوطنية هو تاريخ إيداع الطلب الدولي لدى الهيئة السعودية للملكية الفكرية كعضو دولي لتسجيل الطلبات والذي يوافق ‏ **‏/**‏/**** م...','5-تعد المطالبة بالأسبقية المقررة للطلب السابق طلب براءة الاختراع (الدولية) رقم (PCT/***/***) وتاريخ ‏26‏/11‏/2013م.  غير مقبولة بسبب أن الطلب الحالي محل الفحص وصل للمرحلة الوطنية عبر معاهدة التعاون بشأن البراءات، وذلك بسبب أنه تم تجاوز المهلة النظامية (12 شهر)، وبالتالي فإن تاريخ الإيداع الفعلي لعناصر الحماية للطب الدولي في المرحلة الوطنية هو تاريخ إيداع الطلب الدولي لدى الهيئة السعودية للملكية الفكرية كعضو دولي لتسجيل الطلبات والذي يوافق ‏ **‏/**‏/**** م...',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'PRIORITY'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='UNACCEPTABLE')
       );




INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'أ‌-	العناصر طبق الأصل في الطلب الأصلي
بناء على المادة (46) من نظام براءات الاختراع والتي تنص على أنه " ولمقدم الطلب قبل البت في منحه براءة اختراع تجزئة طلبه إلى أكثر من طلب، بشرط ألا يتجاوز ما كشف عنه في الطلب الأصلي، ويعد تاريخ إيداع الطلب الأصلي، أو تاريخ الأسبقية تاريخ إيداع لهذه الطلبات" لذا فإنه لا يمكن قبول الطلب الحالي كطلب جزئي من الطلب رقم (**********)، وذلك بسبب أن عناصر الحماية للطلب الحالي هي نسخة طبق الأصل من عناصر الحماية للطلب الأصلي، وبالتالي فإن تاريخ الإيداع الفعلي للطلب الحالي ‏ **‏/**‏/**** هـ الموافق ‏**‏/**‏/**** م. يعتبر تاريخ الإيداع المعتمد في فحص الطلب.
','أ‌-	العناصر طبق الأصل في الطلب الأصلي
بناء على المادة (46) من نظام براءات الاختراع والتي تنص على أنه " ولمقدم الطلب قبل البت في منحه براءة اختراع تجزئة طلبه إلى أكثر من طلب، بشرط ألا يتجاوز ما كشف عنه في الطلب الأصلي، ويعد تاريخ إيداع الطلب الأصلي، أو تاريخ الأسبقية تاريخ إيداع لهذه الطلبات" لذا فإنه لا يمكن قبول الطلب الحالي كطلب جزئي من الطلب رقم (**********)، وذلك بسبب أن عناصر الحماية للطلب الحالي هي نسخة طبق الأصل من عناصر الحماية للطلب الأصلي، وبالتالي فإن تاريخ الإيداع الفعلي للطلب الحالي ‏ **‏/**‏/**** هـ الموافق ‏**‏/**‏/**** م. يعتبر تاريخ الإيداع المعتمد في فحص الطلب.
',true,'أ‌-	العناصر طبق الأصل في الطلب الأصلي
بناء على المادة (46) من نظام براءات الاختراع والتي تنص على أنه " ولمقدم الطلب قبل البت في منحه براءة اختراع تجزئة طلبه إلى أكثر من طلب، بشرط ألا يتجاوز ما كشف عنه في الطلب الأصلي، ويعد تاريخ إيداع الطلب الأصلي، أو تاريخ الأسبقية تاريخ إيداع لهذه الطلبات" لذا فإنه لا يمكن قبول الطلب الحالي كطلب جزئي من الطلب رقم (**********)، وذلك بسبب أن عناصر الحماية للطلب الحالي هي نسخة طبق الأصل من عناصر الحماية للطلب الأصلي، وبالتالي فإن تاريخ الإيداع الفعلي للطلب الحالي ‏ **‏/**‏/**** هـ الموافق ‏**‏/**‏/**** م. يعتبر تاريخ الإيداع المعتمد في فحص الطلب.
','أ‌-	العناصر طبق الأصل في الطلب الأصلي
بناء على المادة (46) من نظام براءات الاختراع والتي تنص على أنه " ولمقدم الطلب قبل البت في منحه براءة اختراع تجزئة طلبه إلى أكثر من طلب، بشرط ألا يتجاوز ما كشف عنه في الطلب الأصلي، ويعد تاريخ إيداع الطلب الأصلي، أو تاريخ الأسبقية تاريخ إيداع لهذه الطلبات" لذا فإنه لا يمكن قبول الطلب الحالي كطلب جزئي من الطلب رقم (**********)، وذلك بسبب أن عناصر الحماية للطلب الحالي هي نسخة طبق الأصل من عناصر الحماية للطلب الأصلي، وبالتالي فإن تاريخ الإيداع الفعلي للطلب الحالي ‏ **‏/**‏/**** هـ الموافق ‏**‏/**‏/**** م. يعتبر تاريخ الإيداع المعتمد في فحص الطلب.
',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'REPORT'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'GENERAL_NOTES'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='PARTIAL-ORDER')
       );


INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'
ب‌-	الطلب الأصلي يشكل مفهوماً ابتكارياً واحداً ولم تتم تجزئته
بناء على المادة (46) من نظام براءات الاختراع والتي تنص على أنه "يجب أن يتعلق الطلب باختراع واحد، أو مجموعة من الأجزاء المرتبطة على نحو يجعلها مفهوماً ابتكارياً واحداً، ولمقدم الطلب قبل البت في منحه براءة اختراع تجزئة طلبه إلى أكثر من طلب، بشرط ألا يتجاوز ما كشف عنه في الطلب الأصلي، ويعد تاريخ إيداع الطلب الأصلي، أو تاريخ الأسبقية تاريخ إيداع لهذه الطلبات". فإنه لا يمكن قبول الطلب الحالي كطلب جزئي من الطلب رقم (******)، وذلك بسبب أن الطلب الأصلي يشكل مفهوماً ابتكارياً واحداً لا يمكن تجزئته الى أكثر من طلب،
وبالتالي فإن تاريخ الإيداع الفعلي للطلب الحالي ‏ **‏/**‏/**** هـ الموافق ‏**‏/**‏/**** م. يعتبر تاريخ الإيداع المعتمد في فحص الطلب.','
ب‌-	الطلب الأصلي يشكل مفهوماً ابتكارياً واحداً ولم تتم تجزئته
بناء على المادة (46) من نظام براءات الاختراع والتي تنص على أنه "يجب أن يتعلق الطلب باختراع واحد، أو مجموعة من الأجزاء المرتبطة على نحو يجعلها مفهوماً ابتكارياً واحداً، ولمقدم الطلب قبل البت في منحه براءة اختراع تجزئة طلبه إلى أكثر من طلب، بشرط ألا يتجاوز ما كشف عنه في الطلب الأصلي، ويعد تاريخ إيداع الطلب الأصلي، أو تاريخ الأسبقية تاريخ إيداع لهذه الطلبات". فإنه لا يمكن قبول الطلب الحالي كطلب جزئي من الطلب رقم (******)، وذلك بسبب أن الطلب الأصلي يشكل مفهوماً ابتكارياً واحداً لا يمكن تجزئته الى أكثر من طلب،
وبالتالي فإن تاريخ الإيداع الفعلي للطلب الحالي ‏ **‏/**‏/**** هـ الموافق ‏**‏/**‏/**** م. يعتبر تاريخ الإيداع المعتمد في فحص الطلب.',true,'
ب‌-	الطلب الأصلي يشكل مفهوماً ابتكارياً واحداً ولم تتم تجزئته
بناء على المادة (46) من نظام براءات الاختراع والتي تنص على أنه "يجب أن يتعلق الطلب باختراع واحد، أو مجموعة من الأجزاء المرتبطة على نحو يجعلها مفهوماً ابتكارياً واحداً، ولمقدم الطلب قبل البت في منحه براءة اختراع تجزئة طلبه إلى أكثر من طلب، بشرط ألا يتجاوز ما كشف عنه في الطلب الأصلي، ويعد تاريخ إيداع الطلب الأصلي، أو تاريخ الأسبقية تاريخ إيداع لهذه الطلبات". فإنه لا يمكن قبول الطلب الحالي كطلب جزئي من الطلب رقم (******)، وذلك بسبب أن الطلب الأصلي يشكل مفهوماً ابتكارياً واحداً لا يمكن تجزئته الى أكثر من طلب،
وبالتالي فإن تاريخ الإيداع الفعلي للطلب الحالي ‏ **‏/**‏/**** هـ الموافق ‏**‏/**‏/**** م. يعتبر تاريخ الإيداع المعتمد في فحص الطلب.','
ب‌-	الطلب الأصلي يشكل مفهوماً ابتكارياً واحداً ولم تتم تجزئته
بناء على المادة (46) من نظام براءات الاختراع والتي تنص على أنه "يجب أن يتعلق الطلب باختراع واحد، أو مجموعة من الأجزاء المرتبطة على نحو يجعلها مفهوماً ابتكارياً واحداً، ولمقدم الطلب قبل البت في منحه براءة اختراع تجزئة طلبه إلى أكثر من طلب، بشرط ألا يتجاوز ما كشف عنه في الطلب الأصلي، ويعد تاريخ إيداع الطلب الأصلي، أو تاريخ الأسبقية تاريخ إيداع لهذه الطلبات". فإنه لا يمكن قبول الطلب الحالي كطلب جزئي من الطلب رقم (******)، وذلك بسبب أن الطلب الأصلي يشكل مفهوماً ابتكارياً واحداً لا يمكن تجزئته الى أكثر من طلب،
وبالتالي فإن تاريخ الإيداع الفعلي للطلب الحالي ‏ **‏/**‏/**** هـ الموافق ‏**‏/**‏/**** م. يعتبر تاريخ الإيداع المعتمد في فحص الطلب.',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'REPORT'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'GENERAL_NOTES'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='PARTIAL-ORDER')
       );


INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'
ج- إيداع الطلب الجزئي تم بعد البت في الطلب
بناء على المادة (46) من نظام براءات الاختراع والتي تنص على أنه " ولمقدم الطلب قبل البت في منحه براءة اختراع تجزئة طلبه إلى أكثر من طلب، بشرط ألا يتجاوز ما كشف عنه في الطلب الأصلي، ويعد تاريخ إيداع الطلب الأصلي، أو تاريخ الأسبقية تاريخ إيداع لهذه الطلبات". فإنه لا يمكن قبول الطلب الحالي كطلب جزئي من الطلب رقم (******)، وذلك بسبب أن إيداع الطلب الحالي تم في تاريخ ‏ **‏/**‏/**** هـ الموافق ‏**‏/**‏/**** م. وذلك بعد البت في الطلب الأصلي الذي أرسل له تقرير بالمنح في تاريخ ‏ **‏/**‏/**** هـ الموافق ‏**‏/**‏/**** م. وبالتالي فإن تاريخ الإيداع الفعلي للطلب الحالي يعد تاريخ الإيداع المعتمد في فحص الطلب.','
ج- إيداع الطلب الجزئي تم بعد البت في الطلب
بناء على المادة (46) من نظام براءات الاختراع والتي تنص على أنه " ولمقدم الطلب قبل البت في منحه براءة اختراع تجزئة طلبه إلى أكثر من طلب، بشرط ألا يتجاوز ما كشف عنه في الطلب الأصلي، ويعد تاريخ إيداع الطلب الأصلي، أو تاريخ الأسبقية تاريخ إيداع لهذه الطلبات". فإنه لا يمكن قبول الطلب الحالي كطلب جزئي من الطلب رقم (******)، وذلك بسبب أن إيداع الطلب الحالي تم في تاريخ ‏ **‏/**‏/**** هـ الموافق ‏**‏/**‏/**** م. وذلك بعد البت في الطلب الأصلي الذي أرسل له تقرير بالمنح في تاريخ ‏ **‏/**‏/**** هـ الموافق ‏**‏/**‏/**** م. وبالتالي فإن تاريخ الإيداع الفعلي للطلب الحالي يعد تاريخ الإيداع المعتمد في فحص الطلب.',true,'
ج- إيداع الطلب الجزئي تم بعد البت في الطلب
بناء على المادة (46) من نظام براءات الاختراع والتي تنص على أنه " ولمقدم الطلب قبل البت في منحه براءة اختراع تجزئة طلبه إلى أكثر من طلب، بشرط ألا يتجاوز ما كشف عنه في الطلب الأصلي، ويعد تاريخ إيداع الطلب الأصلي، أو تاريخ الأسبقية تاريخ إيداع لهذه الطلبات". فإنه لا يمكن قبول الطلب الحالي كطلب جزئي من الطلب رقم (******)، وذلك بسبب أن إيداع الطلب الحالي تم في تاريخ ‏ **‏/**‏/**** هـ الموافق ‏**‏/**‏/**** م. وذلك بعد البت في الطلب الأصلي الذي أرسل له تقرير بالمنح في تاريخ ‏ **‏/**‏/**** هـ الموافق ‏**‏/**‏/**** م. وبالتالي فإن تاريخ الإيداع الفعلي للطلب الحالي يعد تاريخ الإيداع المعتمد في فحص الطلب.','
ج- إيداع الطلب الجزئي تم بعد البت في الطلب
بناء على المادة (46) من نظام براءات الاختراع والتي تنص على أنه " ولمقدم الطلب قبل البت في منحه براءة اختراع تجزئة طلبه إلى أكثر من طلب، بشرط ألا يتجاوز ما كشف عنه في الطلب الأصلي، ويعد تاريخ إيداع الطلب الأصلي، أو تاريخ الأسبقية تاريخ إيداع لهذه الطلبات". فإنه لا يمكن قبول الطلب الحالي كطلب جزئي من الطلب رقم (******)، وذلك بسبب أن إيداع الطلب الحالي تم في تاريخ ‏ **‏/**‏/**** هـ الموافق ‏**‏/**‏/**** م. وذلك بعد البت في الطلب الأصلي الذي أرسل له تقرير بالمنح في تاريخ ‏ **‏/**‏/**** هـ الموافق ‏**‏/**‏/**** م. وبالتالي فإن تاريخ الإيداع الفعلي للطلب الحالي يعد تاريخ الإيداع المعتمد في فحص الطلب.',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'REPORT'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'GENERAL_NOTES'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='PARTIAL-ORDER')
       );


INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'أ‌-	عناصر الحماية للطلب الحالي متطابقة مع براءة ممنوحة
عنصر/ عناصر  الحماية (**)، غير مقبول/ة بناء على المادة (5) الفقرة (د) من نظام براءات الاختراع والتي تنص على التالي: "إذا توصل بشكل مستقل أكثر من شخص إلى موضوع الحماية نفسه، فإن وثيقة الحماية تكون لمن سبق في إيداع طلبه" كونه يطالب بنفس عنصر الحماية (**) لبراءة الاختراع السعودية الممنوحة برقم (*****). ولتجاوز هذا الرفض، يجب إلغاء أو تعديل عناصر الحماية الحالية والمتعلقة بنفس عناصر حماية البراءة السابقة.
','أ‌-	عناصر الحماية للطلب الحالي متطابقة مع براءة ممنوحة
عنصر/ عناصر  الحماية (**)، غير مقبول/ة بناء على المادة (5) الفقرة (د) من نظام براءات الاختراع والتي تنص على التالي: "إذا توصل بشكل مستقل أكثر من شخص إلى موضوع الحماية نفسه، فإن وثيقة الحماية تكون لمن سبق في إيداع طلبه" كونه يطالب بنفس عنصر الحماية (**) لبراءة الاختراع السعودية الممنوحة برقم (*****). ولتجاوز هذا الرفض، يجب إلغاء أو تعديل عناصر الحماية الحالية والمتعلقة بنفس عناصر حماية البراءة السابقة.
',true,'أ‌-	عناصر الحماية للطلب الحالي متطابقة مع براءة ممنوحة
عنصر/ عناصر  الحماية (**)، غير مقبول/ة بناء على المادة (5) الفقرة (د) من نظام براءات الاختراع والتي تنص على التالي: "إذا توصل بشكل مستقل أكثر من شخص إلى موضوع الحماية نفسه، فإن وثيقة الحماية تكون لمن سبق في إيداع طلبه" كونه يطالب بنفس عنصر الحماية (**) لبراءة الاختراع السعودية الممنوحة برقم (*****). ولتجاوز هذا الرفض، يجب إلغاء أو تعديل عناصر الحماية الحالية والمتعلقة بنفس عناصر حماية البراءة السابقة.
','أ‌-	عناصر الحماية للطلب الحالي متطابقة مع براءة ممنوحة
عنصر/ عناصر  الحماية (**)، غير مقبول/ة بناء على المادة (5) الفقرة (د) من نظام براءات الاختراع والتي تنص على التالي: "إذا توصل بشكل مستقل أكثر من شخص إلى موضوع الحماية نفسه، فإن وثيقة الحماية تكون لمن سبق في إيداع طلبه" كونه يطالب بنفس عنصر الحماية (**) لبراءة الاختراع السعودية الممنوحة برقم (*****). ولتجاوز هذا الرفض، يجب إلغاء أو تعديل عناصر الحماية الحالية والمتعلقة بنفس عناصر حماية البراءة السابقة.
',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'REPORT'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'GENERAL_NOTES'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='DUPLICATE-PATENTING')
       );


INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'ب‌-	عناصر الحماية للطلب الحالي متطابقة مع طلب تحت الفحص
عنصر/ عناصر  الحماية (**)، غير مقبول/ة مؤقتا بناء على المادة (5) الفقرة (د) من نظام براءات الاختراع والتي تنص على التالي: "إذا توصل بشكل مستقل أكثر من شخص إلى موضوع الحماية نفسه؛ فإن وثيقة الحماية تكون لمن سبق في إيداع طلبه" كونه يطالب بنفس عنصر/ عناصر  الحماية (***) لطلب براءة الاختراع السعودية السابق رقم (*****). ولتجاوز هذا الرفض، يجب إلغاء أو تعديل عناصر الحماية الحالية.
','ب‌-	عناصر الحماية للطلب الحالي متطابقة مع طلب تحت الفحص
عنصر/ عناصر  الحماية (**)، غير مقبول/ة مؤقتا بناء على المادة (5) الفقرة (د) من نظام براءات الاختراع والتي تنص على التالي: "إذا توصل بشكل مستقل أكثر من شخص إلى موضوع الحماية نفسه؛ فإن وثيقة الحماية تكون لمن سبق في إيداع طلبه" كونه يطالب بنفس عنصر/ عناصر  الحماية (***) لطلب براءة الاختراع السعودية السابق رقم (*****). ولتجاوز هذا الرفض، يجب إلغاء أو تعديل عناصر الحماية الحالية.
',true,'ب‌-	عناصر الحماية للطلب الحالي متطابقة مع طلب تحت الفحص
عنصر/ عناصر  الحماية (**)، غير مقبول/ة مؤقتا بناء على المادة (5) الفقرة (د) من نظام براءات الاختراع والتي تنص على التالي: "إذا توصل بشكل مستقل أكثر من شخص إلى موضوع الحماية نفسه؛ فإن وثيقة الحماية تكون لمن سبق في إيداع طلبه" كونه يطالب بنفس عنصر/ عناصر  الحماية (***) لطلب براءة الاختراع السعودية السابق رقم (*****). ولتجاوز هذا الرفض، يجب إلغاء أو تعديل عناصر الحماية الحالية.
','ب‌-	عناصر الحماية للطلب الحالي متطابقة مع طلب تحت الفحص
عنصر/ عناصر  الحماية (**)، غير مقبول/ة مؤقتا بناء على المادة (5) الفقرة (د) من نظام براءات الاختراع والتي تنص على التالي: "إذا توصل بشكل مستقل أكثر من شخص إلى موضوع الحماية نفسه؛ فإن وثيقة الحماية تكون لمن سبق في إيداع طلبه" كونه يطالب بنفس عنصر/ عناصر  الحماية (***) لطلب براءة الاختراع السعودية السابق رقم (*****). ولتجاوز هذا الرفض، يجب إلغاء أو تعديل عناصر الحماية الحالية.
',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'REPORT'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'GENERAL_NOTES'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='DUPLICATE-PATENTING')
       );


INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'ج- عناصر الحماية للطلب الحالي لا تتطابق مع طلب تحت الفحص:
عنصر/ عناصر  الحماية (***)،  غير مقبول/ة على أساس ازدواجية تسجيل براءة اختراع واضحة مقارنة بعناصر الحماية (***) لطلب براءة الاختراع السعودية المنشور/براءة الاختراع السعودية المنشورة رقم (*****). على الرغم أن عناصر الحماية المتنافسة ليست متطابقة، فإنها ليست مميزة بشكل منفصل عن بعضها البعض لأن قيود عناصر الحماية للطلب السعودي المنشور/البراءة المنشورة تطالب بنفس الاختراع.
مثال ذلك الفرق بين عناصر الحماية (***) للطلب الحالي وعناصر الحماية (***) لطلب براءة الاختراع السعودية المنشور/براءة الاختراع السعودية المنشورة هو إضافة (وحدة الذاكرة الاحتياطية). لذلك، فمن الواضح لرجل المهنة العادي في مجال التقنية والذي لديه تفاصيل الطلب الحالي وطلب براءة الاختراع السعودية المنشور/براءة الاختراع السعودية المنشورة أن يضيف (وحدة ذاكرة احتياطية لغرض توفير قدرات احتياطية).','ج- عناصر الحماية للطلب الحالي لا تتطابق مع طلب تحت الفحص:
عنصر/ عناصر  الحماية (***)،  غير مقبول/ة على أساس ازدواجية تسجيل براءة اختراع واضحة مقارنة بعناصر الحماية (***) لطلب براءة الاختراع السعودية المنشور/براءة الاختراع السعودية المنشورة رقم (*****). على الرغم أن عناصر الحماية المتنافسة ليست متطابقة، فإنها ليست مميزة بشكل منفصل عن بعضها البعض لأن قيود عناصر الحماية للطلب السعودي المنشور/البراءة المنشورة تطالب بنفس الاختراع.
مثال ذلك الفرق بين عناصر الحماية (***) للطلب الحالي وعناصر الحماية (***) لطلب براءة الاختراع السعودية المنشور/براءة الاختراع السعودية المنشورة هو إضافة (وحدة الذاكرة الاحتياطية). لذلك، فمن الواضح لرجل المهنة العادي في مجال التقنية والذي لديه تفاصيل الطلب الحالي وطلب براءة الاختراع السعودية المنشور/براءة الاختراع السعودية المنشورة أن يضيف (وحدة ذاكرة احتياطية لغرض توفير قدرات احتياطية).',true,'ج- عناصر الحماية للطلب الحالي لا تتطابق مع طلب تحت الفحص:
عنصر/ عناصر  الحماية (***)،  غير مقبول/ة على أساس ازدواجية تسجيل براءة اختراع واضحة مقارنة بعناصر الحماية (***) لطلب براءة الاختراع السعودية المنشور/براءة الاختراع السعودية المنشورة رقم (*****). على الرغم أن عناصر الحماية المتنافسة ليست متطابقة، فإنها ليست مميزة بشكل منفصل عن بعضها البعض لأن قيود عناصر الحماية للطلب السعودي المنشور/البراءة المنشورة تطالب بنفس الاختراع.
مثال ذلك الفرق بين عناصر الحماية (***) للطلب الحالي وعناصر الحماية (***) لطلب براءة الاختراع السعودية المنشور/براءة الاختراع السعودية المنشورة هو إضافة (وحدة الذاكرة الاحتياطية). لذلك، فمن الواضح لرجل المهنة العادي في مجال التقنية والذي لديه تفاصيل الطلب الحالي وطلب براءة الاختراع السعودية المنشور/براءة الاختراع السعودية المنشورة أن يضيف (وحدة ذاكرة احتياطية لغرض توفير قدرات احتياطية).','ج- عناصر الحماية للطلب الحالي لا تتطابق مع طلب تحت الفحص:
عنصر/ عناصر  الحماية (***)،  غير مقبول/ة على أساس ازدواجية تسجيل براءة اختراع واضحة مقارنة بعناصر الحماية (***) لطلب براءة الاختراع السعودية المنشور/براءة الاختراع السعودية المنشورة رقم (*****). على الرغم أن عناصر الحماية المتنافسة ليست متطابقة، فإنها ليست مميزة بشكل منفصل عن بعضها البعض لأن قيود عناصر الحماية للطلب السعودي المنشور/البراءة المنشورة تطالب بنفس الاختراع.
مثال ذلك الفرق بين عناصر الحماية (***) للطلب الحالي وعناصر الحماية (***) لطلب براءة الاختراع السعودية المنشور/براءة الاختراع السعودية المنشورة هو إضافة (وحدة الذاكرة الاحتياطية). لذلك، فمن الواضح لرجل المهنة العادي في مجال التقنية والذي لديه تفاصيل الطلب الحالي وطلب براءة الاختراع السعودية المنشور/براءة الاختراع السعودية المنشورة أن يضيف (وحدة ذاكرة احتياطية لغرض توفير قدرات احتياطية).',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'REPORT'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'GENERAL_NOTES'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='DUPLICATE-PATENTING')
       );





INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'•عبارة غير مفهومة أو مشتملة على أخطاء مطبعية/ إملائية/نحوية/لغوية
- لم يتم التمكن من التعرف على المقصود من بعض الجمل الواردة في المواصفة [أنظر على سبيل المثال: الوصف الكامل الصفحة  رقم *** السطر رقم ***وغيرها في كامل المواصفة]، وهذا لا يتوافق مع المادة (8) الفقرة (1) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص بجزء منها على التالي "يجب أن تقدم طلبات منح وثائق الحماية وفق النماذج المعدة لذلك الكترونيا أو بأي طريقة أخرى تقبلها الهيئة، وأن تكون باللغة العربية" لذا يجب تقديم صياغة جيدة ومفهومة للطلب خلال المدة المحددة نظاماً.
المثال ','•عبارة غير مفهومة أو مشتملة على أخطاء مطبعية/ إملائية/نحوية/لغوية
- لم يتم التمكن من التعرف على المقصود من بعض الجمل الواردة في المواصفة [أنظر على سبيل المثال: الوصف الكامل الصفحة  رقم *** السطر رقم ***وغيرها في كامل المواصفة]، وهذا لا يتوافق مع المادة (8) الفقرة (1) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص بجزء منها على التالي "يجب أن تقدم طلبات منح وثائق الحماية وفق النماذج المعدة لذلك الكترونيا أو بأي طريقة أخرى تقبلها الهيئة، وأن تكون باللغة العربية" لذا يجب تقديم صياغة جيدة ومفهومة للطلب خلال المدة المحددة نظاماً.
المثال ',true,'•عبارة غير مفهومة أو مشتملة على أخطاء مطبعية/ إملائية/نحوية/لغوية
- لم يتم التمكن من التعرف على المقصود من بعض الجمل الواردة في المواصفة [أنظر على سبيل المثال: الوصف الكامل الصفحة  رقم *** السطر رقم ***وغيرها في كامل المواصفة]، وهذا لا يتوافق مع المادة (8) الفقرة (1) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص بجزء منها على التالي "يجب أن تقدم طلبات منح وثائق الحماية وفق النماذج المعدة لذلك الكترونيا أو بأي طريقة أخرى تقبلها الهيئة، وأن تكون باللغة العربية" لذا يجب تقديم صياغة جيدة ومفهومة للطلب خلال المدة المحددة نظاماً.
المثال ','•عبارة غير مفهومة أو مشتملة على أخطاء مطبعية/ إملائية/نحوية/لغوية
- لم يتم التمكن من التعرف على المقصود من بعض الجمل الواردة في المواصفة [أنظر على سبيل المثال: الوصف الكامل الصفحة  رقم *** السطر رقم ***وغيرها في كامل المواصفة]، وهذا لا يتوافق مع المادة (8) الفقرة (1) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص بجزء منها على التالي "يجب أن تقدم طلبات منح وثائق الحماية وفق النماذج المعدة لذلك الكترونيا أو بأي طريقة أخرى تقبلها الهيئة، وأن تكون باللغة العربية" لذا يجب تقديم صياغة جيدة ومفهومة للطلب خلال المدة المحددة نظاماً.
المثال ',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'APPLICATION'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='DRAFT')
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'•التسميات المختصرة
-التسميات المختصرة باللغة الانجليزية [كمثال: (DF) و (TPR) الواردة في الصفحة رقم (**) السطر (**)، وكذلك (ORP) الواردة في الصفحة رقم (**) السطر (**)، وغيرها في كامل المواصفة]، وهذا لا يتوافق مع ما نصت عليه المادة (12) الفقرة (2) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على التالي: " في حالة التسميات المختصرة باللغة الأجنبية فينبغي ذكر التسمية كاملة بالعربية والإنجليزية عند ورودها لأول مرة في النص ويكتفى بعد ذلك بالتسمية المختصرة" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.','•التسميات المختصرة
-التسميات المختصرة باللغة الانجليزية [كمثال: (DF) و (TPR) الواردة في الصفحة رقم (**) السطر (**)، وكذلك (ORP) الواردة في الصفحة رقم (**) السطر (**)، وغيرها في كامل المواصفة]، وهذا لا يتوافق مع ما نصت عليه المادة (12) الفقرة (2) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على التالي: " في حالة التسميات المختصرة باللغة الأجنبية فينبغي ذكر التسمية كاملة بالعربية والإنجليزية عند ورودها لأول مرة في النص ويكتفى بعد ذلك بالتسمية المختصرة" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.',true,'•التسميات المختصرة
-التسميات المختصرة باللغة الانجليزية [كمثال: (DF) و (TPR) الواردة في الصفحة رقم (**) السطر (**)، وكذلك (ORP) الواردة في الصفحة رقم (**) السطر (**)، وغيرها في كامل المواصفة]، وهذا لا يتوافق مع ما نصت عليه المادة (12) الفقرة (2) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على التالي: " في حالة التسميات المختصرة باللغة الأجنبية فينبغي ذكر التسمية كاملة بالعربية والإنجليزية عند ورودها لأول مرة في النص ويكتفى بعد ذلك بالتسمية المختصرة" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.','•التسميات المختصرة
-التسميات المختصرة باللغة الانجليزية [كمثال: (DF) و (TPR) الواردة في الصفحة رقم (**) السطر (**)، وكذلك (ORP) الواردة في الصفحة رقم (**) السطر (**)، وغيرها في كامل المواصفة]، وهذا لا يتوافق مع ما نصت عليه المادة (12) الفقرة (2) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على التالي: " في حالة التسميات المختصرة باللغة الأجنبية فينبغي ذكر التسمية كاملة بالعربية والإنجليزية عند ورودها لأول مرة في النص ويكتفى بعد ذلك بالتسمية المختصرة" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'APPLICATION'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='DRAFT')
       );


INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'•	الرموز/الوحدات غير مطابقة لــ IUPAP
-	الرموز/الوحدات/التسميات/الثوابت الأساسية الفيزيائية ] وحدة (سنتي  بواز) الواردة في الصفحة رقم (10) الأسطر (1-4)، وكذلك وحدة (بوصة) الواردة في الصفحة رقم (10) السطر (14)، وغيرها  في كامل المواصفة]، حيث تم استخدام وحدات قياس ليست دولية  (كتابة الوحدة غير دولية) وهذا لا يتوافق مع المادة (12) الفقرة (4) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "تعتمد الرموز, والوحدات, والتسميات, والثوابت الأساسية الفيزيائية التي أقرها الاتحاد الدولي للفيزياء البحتة والتطبيقية IUPAP- اللجنة SUNAMCO المنشورة في وثيقة الاتحاد رقم 25"، ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.
-	"الوصف التفصيلي" تضمن مقاييس بالنظام الأمريكي. ] كمثال وحدة (فهرنهايت) الواردة في الصفحة رقم (17) السطر رقم (22)]، ولتجاوز هذا الاعتراض يجب على مقدم الطلب مراجعة جميع وحدات القياس المذكورة في الطلب والتقيد بما ذكر في المادة (11) الفقرة (4) من اللائحة التنفيذية للنظام والتي تنص على أن " تكون المقاييس بالنظام المتري ودرجات الحرارة بالنظام المئوي. ويجوز ذكر الوحدات الأخرى لاحقة بين قوسين". ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.','•	الرموز/الوحدات غير مطابقة لــ IUPAP
-	الرموز/الوحدات/التسميات/الثوابت الأساسية الفيزيائية ] وحدة (سنتي  بواز) الواردة في الصفحة رقم (10) الأسطر (1-4)، وكذلك وحدة (بوصة) الواردة في الصفحة رقم (10) السطر (14)، وغيرها  في كامل المواصفة]، حيث تم استخدام وحدات قياس ليست دولية  (كتابة الوحدة غير دولية) وهذا لا يتوافق مع المادة (12) الفقرة (4) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "تعتمد الرموز, والوحدات, والتسميات, والثوابت الأساسية الفيزيائية التي أقرها الاتحاد الدولي للفيزياء البحتة والتطبيقية IUPAP- اللجنة SUNAMCO المنشورة في وثيقة الاتحاد رقم 25"، ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.
-	"الوصف التفصيلي" تضمن مقاييس بالنظام الأمريكي. ] كمثال وحدة (فهرنهايت) الواردة في الصفحة رقم (17) السطر رقم (22)]، ولتجاوز هذا الاعتراض يجب على مقدم الطلب مراجعة جميع وحدات القياس المذكورة في الطلب والتقيد بما ذكر في المادة (11) الفقرة (4) من اللائحة التنفيذية للنظام والتي تنص على أن " تكون المقاييس بالنظام المتري ودرجات الحرارة بالنظام المئوي. ويجوز ذكر الوحدات الأخرى لاحقة بين قوسين". ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.',true,'•	الرموز/الوحدات غير مطابقة لــ IUPAP
-	الرموز/الوحدات/التسميات/الثوابت الأساسية الفيزيائية ] وحدة (سنتي  بواز) الواردة في الصفحة رقم (10) الأسطر (1-4)، وكذلك وحدة (بوصة) الواردة في الصفحة رقم (10) السطر (14)، وغيرها  في كامل المواصفة]، حيث تم استخدام وحدات قياس ليست دولية  (كتابة الوحدة غير دولية) وهذا لا يتوافق مع المادة (12) الفقرة (4) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "تعتمد الرموز, والوحدات, والتسميات, والثوابت الأساسية الفيزيائية التي أقرها الاتحاد الدولي للفيزياء البحتة والتطبيقية IUPAP- اللجنة SUNAMCO المنشورة في وثيقة الاتحاد رقم 25"، ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.
-	"الوصف التفصيلي" تضمن مقاييس بالنظام الأمريكي. ] كمثال وحدة (فهرنهايت) الواردة في الصفحة رقم (17) السطر رقم (22)]، ولتجاوز هذا الاعتراض يجب على مقدم الطلب مراجعة جميع وحدات القياس المذكورة في الطلب والتقيد بما ذكر في المادة (11) الفقرة (4) من اللائحة التنفيذية للنظام والتي تنص على أن " تكون المقاييس بالنظام المتري ودرجات الحرارة بالنظام المئوي. ويجوز ذكر الوحدات الأخرى لاحقة بين قوسين". ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.','•	الرموز/الوحدات غير مطابقة لــ IUPAP
-	الرموز/الوحدات/التسميات/الثوابت الأساسية الفيزيائية ] وحدة (سنتي  بواز) الواردة في الصفحة رقم (10) الأسطر (1-4)، وكذلك وحدة (بوصة) الواردة في الصفحة رقم (10) السطر (14)، وغيرها  في كامل المواصفة]، حيث تم استخدام وحدات قياس ليست دولية  (كتابة الوحدة غير دولية) وهذا لا يتوافق مع المادة (12) الفقرة (4) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "تعتمد الرموز, والوحدات, والتسميات, والثوابت الأساسية الفيزيائية التي أقرها الاتحاد الدولي للفيزياء البحتة والتطبيقية IUPAP- اللجنة SUNAMCO المنشورة في وثيقة الاتحاد رقم 25"، ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.
-	"الوصف التفصيلي" تضمن مقاييس بالنظام الأمريكي. ] كمثال وحدة (فهرنهايت) الواردة في الصفحة رقم (17) السطر رقم (22)]، ولتجاوز هذا الاعتراض يجب على مقدم الطلب مراجعة جميع وحدات القياس المذكورة في الطلب والتقيد بما ذكر في المادة (11) الفقرة (4) من اللائحة التنفيذية للنظام والتي تنص على أن " تكون المقاييس بالنظام المتري ودرجات الحرارة بالنظام المئوي. ويجوز ذكر الوحدات الأخرى لاحقة بين قوسين". ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'APPLICATION'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='DRAFT')
       );


INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'•تسمية المركبات ليست حسب نظام IUPAC
-الأشكال البنائية /الصيغ الكيميائية/رموز العناصر/المركبات والأسماء الكيميائية] كمثال أنظر: الصفحة رقم *** السطر/الأسطر رقم ***[، تتعارض مع المادة (12) الفقرة (5) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "تستخدم الحروف اللاتينية حسب نظام IUPAC لكتابة الأشكال البنائية والصيغ الكيميائية ورموز العناصر والمركبات والأسماء الكيميائية أما في حالة ورود الاسم الكيميائي في العنوان فيكتب باللغة العربية بالإضافة إلى اللاتينية" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.','•تسمية المركبات ليست حسب نظام IUPAC
-الأشكال البنائية /الصيغ الكيميائية/رموز العناصر/المركبات والأسماء الكيميائية] كمثال أنظر: الصفحة رقم *** السطر/الأسطر رقم ***[، تتعارض مع المادة (12) الفقرة (5) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "تستخدم الحروف اللاتينية حسب نظام IUPAC لكتابة الأشكال البنائية والصيغ الكيميائية ورموز العناصر والمركبات والأسماء الكيميائية أما في حالة ورود الاسم الكيميائي في العنوان فيكتب باللغة العربية بالإضافة إلى اللاتينية" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.',true,'•تسمية المركبات ليست حسب نظام IUPAC
-الأشكال البنائية /الصيغ الكيميائية/رموز العناصر/المركبات والأسماء الكيميائية] كمثال أنظر: الصفحة رقم *** السطر/الأسطر رقم ***[، تتعارض مع المادة (12) الفقرة (5) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "تستخدم الحروف اللاتينية حسب نظام IUPAC لكتابة الأشكال البنائية والصيغ الكيميائية ورموز العناصر والمركبات والأسماء الكيميائية أما في حالة ورود الاسم الكيميائي في العنوان فيكتب باللغة العربية بالإضافة إلى اللاتينية" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.','•تسمية المركبات ليست حسب نظام IUPAC
-الأشكال البنائية /الصيغ الكيميائية/رموز العناصر/المركبات والأسماء الكيميائية] كمثال أنظر: الصفحة رقم *** السطر/الأسطر رقم ***[، تتعارض مع المادة (12) الفقرة (5) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "تستخدم الحروف اللاتينية حسب نظام IUPAC لكتابة الأشكال البنائية والصيغ الكيميائية ورموز العناصر والمركبات والأسماء الكيميائية أما في حالة ورود الاسم الكيميائي في العنوان فيكتب باللغة العربية بالإضافة إلى اللاتينية" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'APPLICATION'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='DRAFT')
       );


INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'•لم تذكر المراجع بلغتها الأصلية
-لم تذكر المراجع/البحوث/المقالات/الكتب العلمية بلغتها الأصلية [كمثال أنظر: الصفحة رقم *** السطر/الأسطر رقم ***]، وهذا لا يتوافق مع المادة (12) الفقرة (6) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "تذكر المراجع والبحوث والمقالات والكتب العلمية بلغتها الأصلية" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.','•لم تذكر المراجع بلغتها الأصلية
-لم تذكر المراجع/البحوث/المقالات/الكتب العلمية بلغتها الأصلية [كمثال أنظر: الصفحة رقم *** السطر/الأسطر رقم ***]، وهذا لا يتوافق مع المادة (12) الفقرة (6) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "تذكر المراجع والبحوث والمقالات والكتب العلمية بلغتها الأصلية" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.',true,'•لم تذكر المراجع بلغتها الأصلية
-لم تذكر المراجع/البحوث/المقالات/الكتب العلمية بلغتها الأصلية [كمثال أنظر: الصفحة رقم *** السطر/الأسطر رقم ***]، وهذا لا يتوافق مع المادة (12) الفقرة (6) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "تذكر المراجع والبحوث والمقالات والكتب العلمية بلغتها الأصلية" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.','•لم تذكر المراجع بلغتها الأصلية
-لم تذكر المراجع/البحوث/المقالات/الكتب العلمية بلغتها الأصلية [كمثال أنظر: الصفحة رقم *** السطر/الأسطر رقم ***]، وهذا لا يتوافق مع المادة (12) الفقرة (6) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "تذكر المراجع والبحوث والمقالات والكتب العلمية بلغتها الأصلية" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'APPLICATION'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='DRAFT')
       );


INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'•لم تذكر المصطلحات العلمية بلغتها الأصلية
-لم تذكر المصطلحات العلمية بلغتها الأصلية] كمثال أنظر: الصفحة رقم *** السطر/الأسطر رقم ***]، وهذا لا يتوافق مع المادة 12 الفقرة 1 من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "يجب ان يذكر اسم المصطلح العلمي بلغته الأصلية مرادفاً للاسم باللغة العربية لأول مره ويكتفى بالاسم العربي فقط في المرات اللاحقة فيما عدا عناصر الحماية فيتم تكرار ذكر المصطلح باللغتين" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.','•لم تذكر المصطلحات العلمية بلغتها الأصلية
-لم تذكر المصطلحات العلمية بلغتها الأصلية] كمثال أنظر: الصفحة رقم *** السطر/الأسطر رقم ***]، وهذا لا يتوافق مع المادة 12 الفقرة 1 من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "يجب ان يذكر اسم المصطلح العلمي بلغته الأصلية مرادفاً للاسم باللغة العربية لأول مره ويكتفى بالاسم العربي فقط في المرات اللاحقة فيما عدا عناصر الحماية فيتم تكرار ذكر المصطلح باللغتين" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.',true,'•لم تذكر المصطلحات العلمية بلغتها الأصلية
-لم تذكر المصطلحات العلمية بلغتها الأصلية] كمثال أنظر: الصفحة رقم *** السطر/الأسطر رقم ***]، وهذا لا يتوافق مع المادة 12 الفقرة 1 من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "يجب ان يذكر اسم المصطلح العلمي بلغته الأصلية مرادفاً للاسم باللغة العربية لأول مره ويكتفى بالاسم العربي فقط في المرات اللاحقة فيما عدا عناصر الحماية فيتم تكرار ذكر المصطلح باللغتين" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.','•لم تذكر المصطلحات العلمية بلغتها الأصلية
-لم تذكر المصطلحات العلمية بلغتها الأصلية] كمثال أنظر: الصفحة رقم *** السطر/الأسطر رقم ***]، وهذا لا يتوافق مع المادة 12 الفقرة 1 من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "يجب ان يذكر اسم المصطلح العلمي بلغته الأصلية مرادفاً للاسم باللغة العربية لأول مره ويكتفى بالاسم العربي فقط في المرات اللاحقة فيما عدا عناصر الحماية فيتم تكرار ذكر المصطلح باللغتين" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'APPLICATION'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='DRAFT')
       );


INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'•	وجود الأقواس في المصطلحات العلمية
-	إذا أشير إلى مصطلحات علمية في المواصفة بعد ورودها لأول مرة فلابد من حذف الأقواس ] كمثال أنظر: الصفحة رقم *** السطر/الأسطر رقم *** وغيرها في كامل المواصفة]، وفقا للمادة (9) الفقرة (6) والتي تنص على " يجب على مقدم الطلب أن يستوفي كل ما تطلبه الهيئة مما له علاقة بالطلب"، لذا يجب من مقدم الطلب تعديل ما يلزم.','•	وجود الأقواس في المصطلحات العلمية
-	إذا أشير إلى مصطلحات علمية في المواصفة بعد ورودها لأول مرة فلابد من حذف الأقواس ] كمثال أنظر: الصفحة رقم *** السطر/الأسطر رقم *** وغيرها في كامل المواصفة]، وفقا للمادة (9) الفقرة (6) والتي تنص على " يجب على مقدم الطلب أن يستوفي كل ما تطلبه الهيئة مما له علاقة بالطلب"، لذا يجب من مقدم الطلب تعديل ما يلزم.',true,'•	وجود الأقواس في المصطلحات العلمية
-	إذا أشير إلى مصطلحات علمية في المواصفة بعد ورودها لأول مرة فلابد من حذف الأقواس ] كمثال أنظر: الصفحة رقم *** السطر/الأسطر رقم *** وغيرها في كامل المواصفة]، وفقا للمادة (9) الفقرة (6) والتي تنص على " يجب على مقدم الطلب أن يستوفي كل ما تطلبه الهيئة مما له علاقة بالطلب"، لذا يجب من مقدم الطلب تعديل ما يلزم.','•	وجود الأقواس في المصطلحات العلمية
-	إذا أشير إلى مصطلحات علمية في المواصفة بعد ورودها لأول مرة فلابد من حذف الأقواس ] كمثال أنظر: الصفحة رقم *** السطر/الأسطر رقم *** وغيرها في كامل المواصفة]، وفقا للمادة (9) الفقرة (6) والتي تنص على " يجب على مقدم الطلب أن يستوفي كل ما تطلبه الهيئة مما له علاقة بالطلب"، لذا يجب من مقدم الطلب تعديل ما يلزم.',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'APPLICATION'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='DRAFT')
       );


INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'•	لم يتم استخدام " المصطلح اللاتيني في المركبات الكيميائية/مسميات الأمراض "
-	توجد ملاحظات على صياغة الطلب بشكل عام، حيث لم يتم استخدام " المصطلح اللاتيني في المصطلحات العلمية " والتي تتعارض مع المادة (12) الفقرة (1) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "يجب أن يذكر اسم المصطلح العلمي بلغته الاصلية مرادفاً للاسم باللغة العربية عند وروده لأول مرة ويكتفى بالاسم العربي فقط في المرات اللاحقة فيما عدا عناصر الحماية فيتم تكرار ذكر المصطلح باللغتين" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.
المثال ','•	لم يتم استخدام " المصطلح اللاتيني في المركبات الكيميائية/مسميات الأمراض "
-	توجد ملاحظات على صياغة الطلب بشكل عام، حيث لم يتم استخدام " المصطلح اللاتيني في المصطلحات العلمية " والتي تتعارض مع المادة (12) الفقرة (1) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "يجب أن يذكر اسم المصطلح العلمي بلغته الاصلية مرادفاً للاسم باللغة العربية عند وروده لأول مرة ويكتفى بالاسم العربي فقط في المرات اللاحقة فيما عدا عناصر الحماية فيتم تكرار ذكر المصطلح باللغتين" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.
المثال ',true,'•	لم يتم استخدام " المصطلح اللاتيني في المركبات الكيميائية/مسميات الأمراض "
-	توجد ملاحظات على صياغة الطلب بشكل عام، حيث لم يتم استخدام " المصطلح اللاتيني في المصطلحات العلمية " والتي تتعارض مع المادة (12) الفقرة (1) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "يجب أن يذكر اسم المصطلح العلمي بلغته الاصلية مرادفاً للاسم باللغة العربية عند وروده لأول مرة ويكتفى بالاسم العربي فقط في المرات اللاحقة فيما عدا عناصر الحماية فيتم تكرار ذكر المصطلح باللغتين" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.
المثال ','•	لم يتم استخدام " المصطلح اللاتيني في المركبات الكيميائية/مسميات الأمراض "
-	توجد ملاحظات على صياغة الطلب بشكل عام، حيث لم يتم استخدام " المصطلح اللاتيني في المصطلحات العلمية " والتي تتعارض مع المادة (12) الفقرة (1) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "يجب أن يذكر اسم المصطلح العلمي بلغته الاصلية مرادفاً للاسم باللغة العربية عند وروده لأول مرة ويكتفى بالاسم العربي فقط في المرات اللاحقة فيما عدا عناصر الحماية فيتم تكرار ذكر المصطلح باللغتين" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.
المثال ',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'APPLICATION'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='DRAFT')
       );


INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'•	استبدال المصطلحات الحالية بمصطلحات علمية أكثر دقة
تضمنت المواصفة مصطلح "معدّل صدمي" [على سبيل المثال أنظر: الوصف الكامل الصفحة  رقم ** السطر رقم  ** وغيرها]، لذا يفضل استبداله بمصطلح "معدّل تأثير" في كامل المواصفة، وفقا للمادة (9) الفقرة (6) والتي تنص على " يجب على مقدم الطلب أن يستوفي كل ما تطلبه الهيئة مما له علاقة بالطلب"، لذا يجب من مقدم الطلب تعديل ما يلزم.','•	استبدال المصطلحات الحالية بمصطلحات علمية أكثر دقة
تضمنت المواصفة مصطلح "معدّل صدمي" [على سبيل المثال أنظر: الوصف الكامل الصفحة  رقم ** السطر رقم  ** وغيرها]، لذا يفضل استبداله بمصطلح "معدّل تأثير" في كامل المواصفة، وفقا للمادة (9) الفقرة (6) والتي تنص على " يجب على مقدم الطلب أن يستوفي كل ما تطلبه الهيئة مما له علاقة بالطلب"، لذا يجب من مقدم الطلب تعديل ما يلزم.',true,'•	استبدال المصطلحات الحالية بمصطلحات علمية أكثر دقة
تضمنت المواصفة مصطلح "معدّل صدمي" [على سبيل المثال أنظر: الوصف الكامل الصفحة  رقم ** السطر رقم  ** وغيرها]، لذا يفضل استبداله بمصطلح "معدّل تأثير" في كامل المواصفة، وفقا للمادة (9) الفقرة (6) والتي تنص على " يجب على مقدم الطلب أن يستوفي كل ما تطلبه الهيئة مما له علاقة بالطلب"، لذا يجب من مقدم الطلب تعديل ما يلزم.','•	استبدال المصطلحات الحالية بمصطلحات علمية أكثر دقة
تضمنت المواصفة مصطلح "معدّل صدمي" [على سبيل المثال أنظر: الوصف الكامل الصفحة  رقم ** السطر رقم  ** وغيرها]، لذا يفضل استبداله بمصطلح "معدّل تأثير" في كامل المواصفة، وفقا للمادة (9) الفقرة (6) والتي تنص على " يجب على مقدم الطلب أن يستوفي كل ما تطلبه الهيئة مما له علاقة بالطلب"، لذا يجب من مقدم الطلب تعديل ما يلزم.',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'APPLICATION'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='DRAFT')
       );


INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'
تغيير تعداد الخطوات
-	يجب تغيير " تعداد الخطوات المذكورة بالصيغة ( i، ii) " الواردة في كامل المواصفة [ كمثال أنظر: الملخص الأسطر *** وكذلك عنصر/عناصر الحماية رقم ** السطر/الأسطر ** ]، وذلك وفقا للمادة (9) الفقرة (6) والتي تنص على " يجب على مقدم الطلب أن يستوفي كل ما تطلبه الهيئة مما له علاقة بالطلب"، لذا يجب من مقدم الطلب تعديل ذلك واستبدال ذلك بصياغة أخرى.','
تغيير تعداد الخطوات
-	يجب تغيير " تعداد الخطوات المذكورة بالصيغة ( i، ii) " الواردة في كامل المواصفة [ كمثال أنظر: الملخص الأسطر *** وكذلك عنصر/عناصر الحماية رقم ** السطر/الأسطر ** ]، وذلك وفقا للمادة (9) الفقرة (6) والتي تنص على " يجب على مقدم الطلب أن يستوفي كل ما تطلبه الهيئة مما له علاقة بالطلب"، لذا يجب من مقدم الطلب تعديل ذلك واستبدال ذلك بصياغة أخرى.',true,'
تغيير تعداد الخطوات
-	يجب تغيير " تعداد الخطوات المذكورة بالصيغة ( i، ii) " الواردة في كامل المواصفة [ كمثال أنظر: الملخص الأسطر *** وكذلك عنصر/عناصر الحماية رقم ** السطر/الأسطر ** ]، وذلك وفقا للمادة (9) الفقرة (6) والتي تنص على " يجب على مقدم الطلب أن يستوفي كل ما تطلبه الهيئة مما له علاقة بالطلب"، لذا يجب من مقدم الطلب تعديل ذلك واستبدال ذلك بصياغة أخرى.','
تغيير تعداد الخطوات
-	يجب تغيير " تعداد الخطوات المذكورة بالصيغة ( i، ii) " الواردة في كامل المواصفة [ كمثال أنظر: الملخص الأسطر *** وكذلك عنصر/عناصر الحماية رقم ** السطر/الأسطر ** ]، وذلك وفقا للمادة (9) الفقرة (6) والتي تنص على " يجب على مقدم الطلب أن يستوفي كل ما تطلبه الهيئة مما له علاقة بالطلب"، لذا يجب من مقدم الطلب تعديل ذلك واستبدال ذلك بصياغة أخرى.',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'APPLICATION'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='DRAFT')
       );







INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'•عبارة غير مفهومة أو مشتملة على أخطاء مطبعية/ إملائية/نحوية/لغوية
- لم يتم التمكن من التعرف على المقصود من بعض الجمل الواردة في المواصفة [أنظر على سبيل المثال: الوصف الكامل الصفحة  رقم *** السطر رقم ***وغيرها في كامل المواصفة]، وهذا لا يتوافق مع المادة (8) الفقرة (1) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص بجزء منها على التالي "يجب أن تقدم طلبات منح وثائق الحماية وفق النماذج المعدة لذلك الكترونيا أو بأي طريقة أخرى تقبلها الهيئة، وأن تكون باللغة العربية" لذا يجب تقديم صياغة جيدة ومفهومة للطلب خلال المدة المحددة نظاماً.
المثال ','•عبارة غير مفهومة أو مشتملة على أخطاء مطبعية/ إملائية/نحوية/لغوية
- لم يتم التمكن من التعرف على المقصود من بعض الجمل الواردة في المواصفة [أنظر على سبيل المثال: الوصف الكامل الصفحة  رقم *** السطر رقم ***وغيرها في كامل المواصفة]، وهذا لا يتوافق مع المادة (8) الفقرة (1) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص بجزء منها على التالي "يجب أن تقدم طلبات منح وثائق الحماية وفق النماذج المعدة لذلك الكترونيا أو بأي طريقة أخرى تقبلها الهيئة، وأن تكون باللغة العربية" لذا يجب تقديم صياغة جيدة ومفهومة للطلب خلال المدة المحددة نظاماً.
المثال ',true,'•عبارة غير مفهومة أو مشتملة على أخطاء مطبعية/ إملائية/نحوية/لغوية
- لم يتم التمكن من التعرف على المقصود من بعض الجمل الواردة في المواصفة [أنظر على سبيل المثال: الوصف الكامل الصفحة  رقم *** السطر رقم ***وغيرها في كامل المواصفة]، وهذا لا يتوافق مع المادة (8) الفقرة (1) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص بجزء منها على التالي "يجب أن تقدم طلبات منح وثائق الحماية وفق النماذج المعدة لذلك الكترونيا أو بأي طريقة أخرى تقبلها الهيئة، وأن تكون باللغة العربية" لذا يجب تقديم صياغة جيدة ومفهومة للطلب خلال المدة المحددة نظاماً.
المثال ','•عبارة غير مفهومة أو مشتملة على أخطاء مطبعية/ إملائية/نحوية/لغوية
- لم يتم التمكن من التعرف على المقصود من بعض الجمل الواردة في المواصفة [أنظر على سبيل المثال: الوصف الكامل الصفحة  رقم *** السطر رقم ***وغيرها في كامل المواصفة]، وهذا لا يتوافق مع المادة (8) الفقرة (1) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص بجزء منها على التالي "يجب أن تقدم طلبات منح وثائق الحماية وفق النماذج المعدة لذلك الكترونيا أو بأي طريقة أخرى تقبلها الهيئة، وأن تكون باللغة العربية" لذا يجب تقديم صياغة جيدة ومفهومة للطلب خلال المدة المحددة نظاماً.
المثال ',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'APPLICATION'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_EN'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='DRAFT')
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'•التسميات المختصرة
-التسميات المختصرة باللغة الانجليزية [كمثال: (DF) و (TPR) الواردة في الصفحة رقم (**) السطر (**)، وكذلك (ORP) الواردة في الصفحة رقم (**) السطر (**)، وغيرها في كامل المواصفة]، وهذا لا يتوافق مع ما نصت عليه المادة (12) الفقرة (2) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على التالي: " في حالة التسميات المختصرة باللغة الأجنبية فينبغي ذكر التسمية كاملة بالعربية والإنجليزية عند ورودها لأول مرة في النص ويكتفى بعد ذلك بالتسمية المختصرة" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.','•التسميات المختصرة
-التسميات المختصرة باللغة الانجليزية [كمثال: (DF) و (TPR) الواردة في الصفحة رقم (**) السطر (**)، وكذلك (ORP) الواردة في الصفحة رقم (**) السطر (**)، وغيرها في كامل المواصفة]، وهذا لا يتوافق مع ما نصت عليه المادة (12) الفقرة (2) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على التالي: " في حالة التسميات المختصرة باللغة الأجنبية فينبغي ذكر التسمية كاملة بالعربية والإنجليزية عند ورودها لأول مرة في النص ويكتفى بعد ذلك بالتسمية المختصرة" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.',true,'•التسميات المختصرة
-التسميات المختصرة باللغة الانجليزية [كمثال: (DF) و (TPR) الواردة في الصفحة رقم (**) السطر (**)، وكذلك (ORP) الواردة في الصفحة رقم (**) السطر (**)، وغيرها في كامل المواصفة]، وهذا لا يتوافق مع ما نصت عليه المادة (12) الفقرة (2) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على التالي: " في حالة التسميات المختصرة باللغة الأجنبية فينبغي ذكر التسمية كاملة بالعربية والإنجليزية عند ورودها لأول مرة في النص ويكتفى بعد ذلك بالتسمية المختصرة" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.','•التسميات المختصرة
-التسميات المختصرة باللغة الانجليزية [كمثال: (DF) و (TPR) الواردة في الصفحة رقم (**) السطر (**)، وكذلك (ORP) الواردة في الصفحة رقم (**) السطر (**)، وغيرها في كامل المواصفة]، وهذا لا يتوافق مع ما نصت عليه المادة (12) الفقرة (2) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على التالي: " في حالة التسميات المختصرة باللغة الأجنبية فينبغي ذكر التسمية كاملة بالعربية والإنجليزية عند ورودها لأول مرة في النص ويكتفى بعد ذلك بالتسمية المختصرة" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'APPLICATION'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_EN'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='DRAFT')
       );


INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'•	الرموز/الوحدات غير مطابقة لــ IUPAP
-	الرموز/الوحدات/التسميات/الثوابت الأساسية الفيزيائية ] وحدة (سنتي  بواز) الواردة في الصفحة رقم (10) الأسطر (1-4)، وكذلك وحدة (بوصة) الواردة في الصفحة رقم (10) السطر (14)، وغيرها  في كامل المواصفة]، حيث تم استخدام وحدات قياس ليست دولية  (كتابة الوحدة غير دولية) وهذا لا يتوافق مع المادة (12) الفقرة (4) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "تعتمد الرموز, والوحدات, والتسميات, والثوابت الأساسية الفيزيائية التي أقرها الاتحاد الدولي للفيزياء البحتة والتطبيقية IUPAP- اللجنة SUNAMCO المنشورة في وثيقة الاتحاد رقم 25"، ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.
-	"الوصف التفصيلي" تضمن مقاييس بالنظام الأمريكي. ] كمثال وحدة (فهرنهايت) الواردة في الصفحة رقم (17) السطر رقم (22)]، ولتجاوز هذا الاعتراض يجب على مقدم الطلب مراجعة جميع وحدات القياس المذكورة في الطلب والتقيد بما ذكر في المادة (11) الفقرة (4) من اللائحة التنفيذية للنظام والتي تنص على أن " تكون المقاييس بالنظام المتري ودرجات الحرارة بالنظام المئوي. ويجوز ذكر الوحدات الأخرى لاحقة بين قوسين". ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.','•	الرموز/الوحدات غير مطابقة لــ IUPAP
-	الرموز/الوحدات/التسميات/الثوابت الأساسية الفيزيائية ] وحدة (سنتي  بواز) الواردة في الصفحة رقم (10) الأسطر (1-4)، وكذلك وحدة (بوصة) الواردة في الصفحة رقم (10) السطر (14)، وغيرها  في كامل المواصفة]، حيث تم استخدام وحدات قياس ليست دولية  (كتابة الوحدة غير دولية) وهذا لا يتوافق مع المادة (12) الفقرة (4) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "تعتمد الرموز, والوحدات, والتسميات, والثوابت الأساسية الفيزيائية التي أقرها الاتحاد الدولي للفيزياء البحتة والتطبيقية IUPAP- اللجنة SUNAMCO المنشورة في وثيقة الاتحاد رقم 25"، ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.
-	"الوصف التفصيلي" تضمن مقاييس بالنظام الأمريكي. ] كمثال وحدة (فهرنهايت) الواردة في الصفحة رقم (17) السطر رقم (22)]، ولتجاوز هذا الاعتراض يجب على مقدم الطلب مراجعة جميع وحدات القياس المذكورة في الطلب والتقيد بما ذكر في المادة (11) الفقرة (4) من اللائحة التنفيذية للنظام والتي تنص على أن " تكون المقاييس بالنظام المتري ودرجات الحرارة بالنظام المئوي. ويجوز ذكر الوحدات الأخرى لاحقة بين قوسين". ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.',true,'•	الرموز/الوحدات غير مطابقة لــ IUPAP
-	الرموز/الوحدات/التسميات/الثوابت الأساسية الفيزيائية ] وحدة (سنتي  بواز) الواردة في الصفحة رقم (10) الأسطر (1-4)، وكذلك وحدة (بوصة) الواردة في الصفحة رقم (10) السطر (14)، وغيرها  في كامل المواصفة]، حيث تم استخدام وحدات قياس ليست دولية  (كتابة الوحدة غير دولية) وهذا لا يتوافق مع المادة (12) الفقرة (4) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "تعتمد الرموز, والوحدات, والتسميات, والثوابت الأساسية الفيزيائية التي أقرها الاتحاد الدولي للفيزياء البحتة والتطبيقية IUPAP- اللجنة SUNAMCO المنشورة في وثيقة الاتحاد رقم 25"، ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.
-	"الوصف التفصيلي" تضمن مقاييس بالنظام الأمريكي. ] كمثال وحدة (فهرنهايت) الواردة في الصفحة رقم (17) السطر رقم (22)]، ولتجاوز هذا الاعتراض يجب على مقدم الطلب مراجعة جميع وحدات القياس المذكورة في الطلب والتقيد بما ذكر في المادة (11) الفقرة (4) من اللائحة التنفيذية للنظام والتي تنص على أن " تكون المقاييس بالنظام المتري ودرجات الحرارة بالنظام المئوي. ويجوز ذكر الوحدات الأخرى لاحقة بين قوسين". ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.','•	الرموز/الوحدات غير مطابقة لــ IUPAP
-	الرموز/الوحدات/التسميات/الثوابت الأساسية الفيزيائية ] وحدة (سنتي  بواز) الواردة في الصفحة رقم (10) الأسطر (1-4)، وكذلك وحدة (بوصة) الواردة في الصفحة رقم (10) السطر (14)، وغيرها  في كامل المواصفة]، حيث تم استخدام وحدات قياس ليست دولية  (كتابة الوحدة غير دولية) وهذا لا يتوافق مع المادة (12) الفقرة (4) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "تعتمد الرموز, والوحدات, والتسميات, والثوابت الأساسية الفيزيائية التي أقرها الاتحاد الدولي للفيزياء البحتة والتطبيقية IUPAP- اللجنة SUNAMCO المنشورة في وثيقة الاتحاد رقم 25"، ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.
-	"الوصف التفصيلي" تضمن مقاييس بالنظام الأمريكي. ] كمثال وحدة (فهرنهايت) الواردة في الصفحة رقم (17) السطر رقم (22)]، ولتجاوز هذا الاعتراض يجب على مقدم الطلب مراجعة جميع وحدات القياس المذكورة في الطلب والتقيد بما ذكر في المادة (11) الفقرة (4) من اللائحة التنفيذية للنظام والتي تنص على أن " تكون المقاييس بالنظام المتري ودرجات الحرارة بالنظام المئوي. ويجوز ذكر الوحدات الأخرى لاحقة بين قوسين". ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'APPLICATION'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_EN'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='DRAFT')
       );


INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'•تسمية المركبات ليست حسب نظام IUPAC
-الأشكال البنائية /الصيغ الكيميائية/رموز العناصر/المركبات والأسماء الكيميائية] كمثال أنظر: الصفحة رقم *** السطر/الأسطر رقم ***[، تتعارض مع المادة (12) الفقرة (5) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "تستخدم الحروف اللاتينية حسب نظام IUPAC لكتابة الأشكال البنائية والصيغ الكيميائية ورموز العناصر والمركبات والأسماء الكيميائية أما في حالة ورود الاسم الكيميائي في العنوان فيكتب باللغة العربية بالإضافة إلى اللاتينية" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.','•تسمية المركبات ليست حسب نظام IUPAC
-الأشكال البنائية /الصيغ الكيميائية/رموز العناصر/المركبات والأسماء الكيميائية] كمثال أنظر: الصفحة رقم *** السطر/الأسطر رقم ***[، تتعارض مع المادة (12) الفقرة (5) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "تستخدم الحروف اللاتينية حسب نظام IUPAC لكتابة الأشكال البنائية والصيغ الكيميائية ورموز العناصر والمركبات والأسماء الكيميائية أما في حالة ورود الاسم الكيميائي في العنوان فيكتب باللغة العربية بالإضافة إلى اللاتينية" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.',true,'•تسمية المركبات ليست حسب نظام IUPAC
-الأشكال البنائية /الصيغ الكيميائية/رموز العناصر/المركبات والأسماء الكيميائية] كمثال أنظر: الصفحة رقم *** السطر/الأسطر رقم ***[، تتعارض مع المادة (12) الفقرة (5) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "تستخدم الحروف اللاتينية حسب نظام IUPAC لكتابة الأشكال البنائية والصيغ الكيميائية ورموز العناصر والمركبات والأسماء الكيميائية أما في حالة ورود الاسم الكيميائي في العنوان فيكتب باللغة العربية بالإضافة إلى اللاتينية" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.','•تسمية المركبات ليست حسب نظام IUPAC
-الأشكال البنائية /الصيغ الكيميائية/رموز العناصر/المركبات والأسماء الكيميائية] كمثال أنظر: الصفحة رقم *** السطر/الأسطر رقم ***[، تتعارض مع المادة (12) الفقرة (5) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "تستخدم الحروف اللاتينية حسب نظام IUPAC لكتابة الأشكال البنائية والصيغ الكيميائية ورموز العناصر والمركبات والأسماء الكيميائية أما في حالة ورود الاسم الكيميائي في العنوان فيكتب باللغة العربية بالإضافة إلى اللاتينية" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'APPLICATION'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_EN'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='DRAFT')
       );


INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'•لم تذكر المراجع بلغتها الأصلية
-لم تذكر المراجع/البحوث/المقالات/الكتب العلمية بلغتها الأصلية [كمثال أنظر: الصفحة رقم *** السطر/الأسطر رقم ***]، وهذا لا يتوافق مع المادة (12) الفقرة (6) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "تذكر المراجع والبحوث والمقالات والكتب العلمية بلغتها الأصلية" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.','•لم تذكر المراجع بلغتها الأصلية
-لم تذكر المراجع/البحوث/المقالات/الكتب العلمية بلغتها الأصلية [كمثال أنظر: الصفحة رقم *** السطر/الأسطر رقم ***]، وهذا لا يتوافق مع المادة (12) الفقرة (6) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "تذكر المراجع والبحوث والمقالات والكتب العلمية بلغتها الأصلية" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.',true,'•لم تذكر المراجع بلغتها الأصلية
-لم تذكر المراجع/البحوث/المقالات/الكتب العلمية بلغتها الأصلية [كمثال أنظر: الصفحة رقم *** السطر/الأسطر رقم ***]، وهذا لا يتوافق مع المادة (12) الفقرة (6) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "تذكر المراجع والبحوث والمقالات والكتب العلمية بلغتها الأصلية" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.','•لم تذكر المراجع بلغتها الأصلية
-لم تذكر المراجع/البحوث/المقالات/الكتب العلمية بلغتها الأصلية [كمثال أنظر: الصفحة رقم *** السطر/الأسطر رقم ***]، وهذا لا يتوافق مع المادة (12) الفقرة (6) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "تذكر المراجع والبحوث والمقالات والكتب العلمية بلغتها الأصلية" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'APPLICATION'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_EN'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='DRAFT')
       );


INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'•لم تذكر المصطلحات العلمية بلغتها الأصلية
-لم تذكر المصطلحات العلمية بلغتها الأصلية] كمثال أنظر: الصفحة رقم *** السطر/الأسطر رقم ***]، وهذا لا يتوافق مع المادة 12 الفقرة 1 من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "يجب ان يذكر اسم المصطلح العلمي بلغته الأصلية مرادفاً للاسم باللغة العربية لأول مره ويكتفى بالاسم العربي فقط في المرات اللاحقة فيما عدا عناصر الحماية فيتم تكرار ذكر المصطلح باللغتين" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.','•لم تذكر المصطلحات العلمية بلغتها الأصلية
-لم تذكر المصطلحات العلمية بلغتها الأصلية] كمثال أنظر: الصفحة رقم *** السطر/الأسطر رقم ***]، وهذا لا يتوافق مع المادة 12 الفقرة 1 من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "يجب ان يذكر اسم المصطلح العلمي بلغته الأصلية مرادفاً للاسم باللغة العربية لأول مره ويكتفى بالاسم العربي فقط في المرات اللاحقة فيما عدا عناصر الحماية فيتم تكرار ذكر المصطلح باللغتين" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.',true,'•لم تذكر المصطلحات العلمية بلغتها الأصلية
-لم تذكر المصطلحات العلمية بلغتها الأصلية] كمثال أنظر: الصفحة رقم *** السطر/الأسطر رقم ***]، وهذا لا يتوافق مع المادة 12 الفقرة 1 من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "يجب ان يذكر اسم المصطلح العلمي بلغته الأصلية مرادفاً للاسم باللغة العربية لأول مره ويكتفى بالاسم العربي فقط في المرات اللاحقة فيما عدا عناصر الحماية فيتم تكرار ذكر المصطلح باللغتين" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.','•لم تذكر المصطلحات العلمية بلغتها الأصلية
-لم تذكر المصطلحات العلمية بلغتها الأصلية] كمثال أنظر: الصفحة رقم *** السطر/الأسطر رقم ***]، وهذا لا يتوافق مع المادة 12 الفقرة 1 من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "يجب ان يذكر اسم المصطلح العلمي بلغته الأصلية مرادفاً للاسم باللغة العربية لأول مره ويكتفى بالاسم العربي فقط في المرات اللاحقة فيما عدا عناصر الحماية فيتم تكرار ذكر المصطلح باللغتين" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'APPLICATION'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_EN'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='DRAFT')
       );


INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'•	وجود الأقواس في المصطلحات العلمية
-	إذا أشير إلى مصطلحات علمية في المواصفة بعد ورودها لأول مرة فلابد من حذف الأقواس ] كمثال أنظر: الصفحة رقم *** السطر/الأسطر رقم *** وغيرها في كامل المواصفة]، وفقا للمادة (9) الفقرة (6) والتي تنص على " يجب على مقدم الطلب أن يستوفي كل ما تطلبه الهيئة مما له علاقة بالطلب"، لذا يجب من مقدم الطلب تعديل ما يلزم.','•	وجود الأقواس في المصطلحات العلمية
-	إذا أشير إلى مصطلحات علمية في المواصفة بعد ورودها لأول مرة فلابد من حذف الأقواس ] كمثال أنظر: الصفحة رقم *** السطر/الأسطر رقم *** وغيرها في كامل المواصفة]، وفقا للمادة (9) الفقرة (6) والتي تنص على " يجب على مقدم الطلب أن يستوفي كل ما تطلبه الهيئة مما له علاقة بالطلب"، لذا يجب من مقدم الطلب تعديل ما يلزم.',true,'•	وجود الأقواس في المصطلحات العلمية
-	إذا أشير إلى مصطلحات علمية في المواصفة بعد ورودها لأول مرة فلابد من حذف الأقواس ] كمثال أنظر: الصفحة رقم *** السطر/الأسطر رقم *** وغيرها في كامل المواصفة]، وفقا للمادة (9) الفقرة (6) والتي تنص على " يجب على مقدم الطلب أن يستوفي كل ما تطلبه الهيئة مما له علاقة بالطلب"، لذا يجب من مقدم الطلب تعديل ما يلزم.','•	وجود الأقواس في المصطلحات العلمية
-	إذا أشير إلى مصطلحات علمية في المواصفة بعد ورودها لأول مرة فلابد من حذف الأقواس ] كمثال أنظر: الصفحة رقم *** السطر/الأسطر رقم *** وغيرها في كامل المواصفة]، وفقا للمادة (9) الفقرة (6) والتي تنص على " يجب على مقدم الطلب أن يستوفي كل ما تطلبه الهيئة مما له علاقة بالطلب"، لذا يجب من مقدم الطلب تعديل ما يلزم.',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'APPLICATION'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_EN'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='DRAFT')
       );


INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'•	لم يتم استخدام " المصطلح اللاتيني في المركبات الكيميائية/مسميات الأمراض "
-	توجد ملاحظات على صياغة الطلب بشكل عام، حيث لم يتم استخدام " المصطلح اللاتيني في المصطلحات العلمية " والتي تتعارض مع المادة (12) الفقرة (1) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "يجب أن يذكر اسم المصطلح العلمي بلغته الاصلية مرادفاً للاسم باللغة العربية عند وروده لأول مرة ويكتفى بالاسم العربي فقط في المرات اللاحقة فيما عدا عناصر الحماية فيتم تكرار ذكر المصطلح باللغتين" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.
المثال ','•	لم يتم استخدام " المصطلح اللاتيني في المركبات الكيميائية/مسميات الأمراض "
-	توجد ملاحظات على صياغة الطلب بشكل عام، حيث لم يتم استخدام " المصطلح اللاتيني في المصطلحات العلمية " والتي تتعارض مع المادة (12) الفقرة (1) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "يجب أن يذكر اسم المصطلح العلمي بلغته الاصلية مرادفاً للاسم باللغة العربية عند وروده لأول مرة ويكتفى بالاسم العربي فقط في المرات اللاحقة فيما عدا عناصر الحماية فيتم تكرار ذكر المصطلح باللغتين" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.
المثال ',true,'•	لم يتم استخدام " المصطلح اللاتيني في المركبات الكيميائية/مسميات الأمراض "
-	توجد ملاحظات على صياغة الطلب بشكل عام، حيث لم يتم استخدام " المصطلح اللاتيني في المصطلحات العلمية " والتي تتعارض مع المادة (12) الفقرة (1) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "يجب أن يذكر اسم المصطلح العلمي بلغته الاصلية مرادفاً للاسم باللغة العربية عند وروده لأول مرة ويكتفى بالاسم العربي فقط في المرات اللاحقة فيما عدا عناصر الحماية فيتم تكرار ذكر المصطلح باللغتين" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.
المثال ','•	لم يتم استخدام " المصطلح اللاتيني في المركبات الكيميائية/مسميات الأمراض "
-	توجد ملاحظات على صياغة الطلب بشكل عام، حيث لم يتم استخدام " المصطلح اللاتيني في المصطلحات العلمية " والتي تتعارض مع المادة (12) الفقرة (1) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على "يجب أن يذكر اسم المصطلح العلمي بلغته الاصلية مرادفاً للاسم باللغة العربية عند وروده لأول مرة ويكتفى بالاسم العربي فقط في المرات اللاحقة فيما عدا عناصر الحماية فيتم تكرار ذكر المصطلح باللغتين" ولتجاوز هذا الاعتراض يجب على مقدم الطلب تعديل ما يلزم.
المثال ',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'APPLICATION'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_EN'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='DRAFT')
       );


INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'•	استبدال المصطلحات الحالية بمصطلحات علمية أكثر دقة
تضمنت المواصفة مصطلح "معدّل صدمي" [على سبيل المثال أنظر: الوصف الكامل الصفحة  رقم ** السطر رقم  ** وغيرها]، لذا يفضل استبداله بمصطلح "معدّل تأثير" في كامل المواصفة، وفقا للمادة (9) الفقرة (6) والتي تنص على " يجب على مقدم الطلب أن يستوفي كل ما تطلبه الهيئة مما له علاقة بالطلب"، لذا يجب من مقدم الطلب تعديل ما يلزم.','•	استبدال المصطلحات الحالية بمصطلحات علمية أكثر دقة
تضمنت المواصفة مصطلح "معدّل صدمي" [على سبيل المثال أنظر: الوصف الكامل الصفحة  رقم ** السطر رقم  ** وغيرها]، لذا يفضل استبداله بمصطلح "معدّل تأثير" في كامل المواصفة، وفقا للمادة (9) الفقرة (6) والتي تنص على " يجب على مقدم الطلب أن يستوفي كل ما تطلبه الهيئة مما له علاقة بالطلب"، لذا يجب من مقدم الطلب تعديل ما يلزم.',true,'•	استبدال المصطلحات الحالية بمصطلحات علمية أكثر دقة
تضمنت المواصفة مصطلح "معدّل صدمي" [على سبيل المثال أنظر: الوصف الكامل الصفحة  رقم ** السطر رقم  ** وغيرها]، لذا يفضل استبداله بمصطلح "معدّل تأثير" في كامل المواصفة، وفقا للمادة (9) الفقرة (6) والتي تنص على " يجب على مقدم الطلب أن يستوفي كل ما تطلبه الهيئة مما له علاقة بالطلب"، لذا يجب من مقدم الطلب تعديل ما يلزم.','•	استبدال المصطلحات الحالية بمصطلحات علمية أكثر دقة
تضمنت المواصفة مصطلح "معدّل صدمي" [على سبيل المثال أنظر: الوصف الكامل الصفحة  رقم ** السطر رقم  ** وغيرها]، لذا يفضل استبداله بمصطلح "معدّل تأثير" في كامل المواصفة، وفقا للمادة (9) الفقرة (6) والتي تنص على " يجب على مقدم الطلب أن يستوفي كل ما تطلبه الهيئة مما له علاقة بالطلب"، لذا يجب من مقدم الطلب تعديل ما يلزم.',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'APPLICATION'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_EN'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='DRAFT')
       );


INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum,note_category_id)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'
تغيير تعداد الخطوات
-	يجب تغيير " تعداد الخطوات المذكورة بالصيغة ( i، ii) " الواردة في كامل المواصفة [ كمثال أنظر: الملخص الأسطر *** وكذلك عنصر/عناصر الحماية رقم ** السطر/الأسطر ** ]، وذلك وفقا للمادة (9) الفقرة (6) والتي تنص على " يجب على مقدم الطلب أن يستوفي كل ما تطلبه الهيئة مما له علاقة بالطلب"، لذا يجب من مقدم الطلب تعديل ذلك واستبدال ذلك بصياغة أخرى.','
تغيير تعداد الخطوات
-	يجب تغيير " تعداد الخطوات المذكورة بالصيغة ( i، ii) " الواردة في كامل المواصفة [ كمثال أنظر: الملخص الأسطر *** وكذلك عنصر/عناصر الحماية رقم ** السطر/الأسطر ** ]، وذلك وفقا للمادة (9) الفقرة (6) والتي تنص على " يجب على مقدم الطلب أن يستوفي كل ما تطلبه الهيئة مما له علاقة بالطلب"، لذا يجب من مقدم الطلب تعديل ذلك واستبدال ذلك بصياغة أخرى.',true,'
تغيير تعداد الخطوات
-	يجب تغيير " تعداد الخطوات المذكورة بالصيغة ( i، ii) " الواردة في كامل المواصفة [ كمثال أنظر: الملخص الأسطر *** وكذلك عنصر/عناصر الحماية رقم ** السطر/الأسطر ** ]، وذلك وفقا للمادة (9) الفقرة (6) والتي تنص على " يجب على مقدم الطلب أن يستوفي كل ما تطلبه الهيئة مما له علاقة بالطلب"، لذا يجب من مقدم الطلب تعديل ذلك واستبدال ذلك بصياغة أخرى.','
تغيير تعداد الخطوات
-	يجب تغيير " تعداد الخطوات المذكورة بالصيغة ( i، ii) " الواردة في كامل المواصفة [ كمثال أنظر: الملخص الأسطر *** وكذلك عنصر/عناصر الحماية رقم ** السطر/الأسطر ** ]، وذلك وفقا للمادة (9) الفقرة (6) والتي تنص على " يجب على مقدم الطلب أن يستوفي كل ما تطلبه الهيئة مما له علاقة بالطلب"، لذا يجب من مقدم الطلب تعديل ذلك واستبدال ذلك بصياغة أخرى.',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'APPLICATION'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_EN'),'APPLICANT',
           (SELECT id FROM application.lk_note_category where code ='DRAFT')
       );



INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	الملخص لم يبدأ باسم الاختراع
الملخص لا يتوافق مع المادة (11) الفقرة (2) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يبدأ الملخص والوصف الكامل باسم الاختراع"". لذلك يجب على مقدم الطلب تعديله."','"•	الملخص لم يبدأ باسم الاختراع
الملخص لا يتوافق مع المادة (11) الفقرة (2) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يبدأ الملخص والوصف الكامل باسم الاختراع"". لذلك يجب على مقدم الطلب تعديله."',true,'"•	الملخص لم يبدأ باسم الاختراع
الملخص لا يتوافق مع المادة (11) الفقرة (2) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يبدأ الملخص والوصف الكامل باسم الاختراع"". لذلك يجب على مقدم الطلب تعديله."','"•	الملخص لم يبدأ باسم الاختراع
الملخص لا يتوافق مع المادة (11) الفقرة (2) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يبدأ الملخص والوصف الكامل باسم الاختراع"". لذلك يجب على مقدم الطلب تعديله."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'SUMMARY_AR'),'APPLICANT'
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	الملخص أكثر من صفحة واحدة
الملخص المرفق في الطلب الحالي يتجاوز أكثر من صفحة واحدة، وهذا لا يتوافق مع المادة (13) الفقرة (1) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يجب ألا يتجاوز الملخص أكثر من نصف صفحة وفي الحالات القصوى صفحة واحدة"". ولتجاوز هذا الاعتراض يجب على مقدم الطلب التعديل."','"•	الملخص أكثر من صفحة واحدة
الملخص المرفق في الطلب الحالي يتجاوز أكثر من صفحة واحدة، وهذا لا يتوافق مع المادة (13) الفقرة (1) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يجب ألا يتجاوز الملخص أكثر من نصف صفحة وفي الحالات القصوى صفحة واحدة"". ولتجاوز هذا الاعتراض يجب على مقدم الطلب التعديل."',true,'"•	الملخص أكثر من صفحة واحدة
الملخص المرفق في الطلب الحالي يتجاوز أكثر من صفحة واحدة، وهذا لا يتوافق مع المادة (13) الفقرة (1) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يجب ألا يتجاوز الملخص أكثر من نصف صفحة وفي الحالات القصوى صفحة واحدة"". ولتجاوز هذا الاعتراض يجب على مقدم الطلب التعديل."','"•	الملخص أكثر من صفحة واحدة
الملخص المرفق في الطلب الحالي يتجاوز أكثر من صفحة واحدة، وهذا لا يتوافق مع المادة (13) الفقرة (1) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يجب ألا يتجاوز الملخص أكثر من نصف صفحة وفي الحالات القصوى صفحة واحدة"". ولتجاوز هذا الاعتراض يجب على مقدم الطلب التعديل."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'SUMMARY_AR'),'APPLICANT'
       );
INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	لم يشر في آخر الملخص إلى الشكل الذي يمثل الاختراع
لم يشير مقدم الطلب في آخر الملخص إلى الشكل الذي يمثل الاختراع بشكل عام، وهذا لا يتوافق مع المادة (13) الفقرة (2) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""إذا كان هناك رسومات توضيحية يشار إلى الشكل الذي يمثل الاختراع بشكل عام، ويوضع رقم هذا الشكل في آخر الملخص"". ولتجاوز هذا الاعتراض يجب على مقدم الطلب وضع رقم الشكل الذي يمثل الاختراع بشكل عام."','"•	لم يشر في آخر الملخص إلى الشكل الذي يمثل الاختراع
لم يشير مقدم الطلب في آخر الملخص إلى الشكل الذي يمثل الاختراع بشكل عام، وهذا لا يتوافق مع المادة (13) الفقرة (2) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""إذا كان هناك رسومات توضيحية يشار إلى الشكل الذي يمثل الاختراع بشكل عام، ويوضع رقم هذا الشكل في آخر الملخص"". ولتجاوز هذا الاعتراض يجب على مقدم الطلب وضع رقم الشكل الذي يمثل الاختراع بشكل عام."',true,'"•	لم يشر في آخر الملخص إلى الشكل الذي يمثل الاختراع
لم يشير مقدم الطلب في آخر الملخص إلى الشكل الذي يمثل الاختراع بشكل عام، وهذا لا يتوافق مع المادة (13) الفقرة (2) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""إذا كان هناك رسومات توضيحية يشار إلى الشكل الذي يمثل الاختراع بشكل عام، ويوضع رقم هذا الشكل في آخر الملخص"". ولتجاوز هذا الاعتراض يجب على مقدم الطلب وضع رقم الشكل الذي يمثل الاختراع بشكل عام."','"•	لم يشر في آخر الملخص إلى الشكل الذي يمثل الاختراع
لم يشير مقدم الطلب في آخر الملخص إلى الشكل الذي يمثل الاختراع بشكل عام، وهذا لا يتوافق مع المادة (13) الفقرة (2) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""إذا كان هناك رسومات توضيحية يشار إلى الشكل الذي يمثل الاختراع بشكل عام، ويوضع رقم هذا الشكل في آخر الملخص"". ولتجاوز هذا الاعتراض يجب على مقدم الطلب وضع رقم الشكل الذي يمثل الاختراع بشكل عام."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'SUMMARY_AR'),'APPLICANT'
       );
INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	في الملخص تم الإشارة إلى أكثر من شكل كونها تمثل الاختراع
-	الملخص لا يتوافق مع المادة (13) الفقرة (2) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""إذا كان هناك رسومات توضيحية يشار إلى الشكل الذي يمثل الاختراع بشكل عام، ويوضع رقم هذا الشكل في آخر الملخص""، كونه يشير إلى أكثر من شكل. ولتجاوز هذا الاعتراض يجب على مقدم الطلب اختيار
شكل واحد فقط ويوضع رقم الشكل الذي يمثل الاختراع بشكل عام في آخر الملخص.
المثال

•	الشكل الموضوع في آخر الملخص لا يمثل الاختراع بشكل عام
أشير في آخر الملخص إلى الشكل (***)، وهذا الشكل يمثل التقنية السابقة وليس الاختراع، حيث أنه بوضعه الحالي لا يتوافق مع المادة (13) الفقرة (2) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""إذا كان هناك رسومات توضيحية يشار إلى الشكل الذي يمثل الاختراع بشكل عام، ويوضع رقم هذا الشكل في آخر الملخص"". ولتجاوز هذا الاعتراض يجب على مقدم الطلب وضع رقم الشكل الذي يمثل الاختراع بشكل عام."','"•	في الملخص تم الإشارة إلى أكثر من شكل كونها تمثل الاختراع
-	الملخص لا يتوافق مع المادة (13) الفقرة (2) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""إذا كان هناك رسومات توضيحية يشار إلى الشكل الذي يمثل الاختراع بشكل عام، ويوضع رقم هذا الشكل في آخر الملخص""، كونه يشير إلى أكثر من شكل. ولتجاوز هذا الاعتراض يجب على مقدم الطلب اختيار
شكل واحد فقط ويوضع رقم الشكل الذي يمثل الاختراع بشكل عام في آخر الملخص.
المثال

•	الشكل الموضوع في آخر الملخص لا يمثل الاختراع بشكل عام
أشير في آخر الملخص إلى الشكل (***)، وهذا الشكل يمثل التقنية السابقة وليس الاختراع، حيث أنه بوضعه الحالي لا يتوافق مع المادة (13) الفقرة (2) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""إذا كان هناك رسومات توضيحية يشار إلى الشكل الذي يمثل الاختراع بشكل عام، ويوضع رقم هذا الشكل في آخر الملخص"". ولتجاوز هذا الاعتراض يجب على مقدم الطلب وضع رقم الشكل الذي يمثل الاختراع بشكل عام."',true,'"•	في الملخص تم الإشارة إلى أكثر من شكل كونها تمثل الاختراع
-	الملخص لا يتوافق مع المادة (13) الفقرة (2) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""إذا كان هناك رسومات توضيحية يشار إلى الشكل الذي يمثل الاختراع بشكل عام، ويوضع رقم هذا الشكل في آخر الملخص""، كونه يشير إلى أكثر من شكل. ولتجاوز هذا الاعتراض يجب على مقدم الطلب اختيار
شكل واحد فقط ويوضع رقم الشكل الذي يمثل الاختراع بشكل عام في آخر الملخص.
المثال

•	الشكل الموضوع في آخر الملخص لا يمثل الاختراع بشكل عام
أشير في آخر الملخص إلى الشكل (***)، وهذا الشكل يمثل التقنية السابقة وليس الاختراع، حيث أنه بوضعه الحالي لا يتوافق مع المادة (13) الفقرة (2) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""إذا كان هناك رسومات توضيحية يشار إلى الشكل الذي يمثل الاختراع بشكل عام، ويوضع رقم هذا الشكل في آخر الملخص"". ولتجاوز هذا الاعتراض يجب على مقدم الطلب وضع رقم الشكل الذي يمثل الاختراع بشكل عام."','"•	في الملخص تم الإشارة إلى أكثر من شكل كونها تمثل الاختراع
-	الملخص لا يتوافق مع المادة (13) الفقرة (2) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""إذا كان هناك رسومات توضيحية يشار إلى الشكل الذي يمثل الاختراع بشكل عام، ويوضع رقم هذا الشكل في آخر الملخص""، كونه يشير إلى أكثر من شكل. ولتجاوز هذا الاعتراض يجب على مقدم الطلب اختيار
شكل واحد فقط ويوضع رقم الشكل الذي يمثل الاختراع بشكل عام في آخر الملخص.
المثال

•	الشكل الموضوع في آخر الملخص لا يمثل الاختراع بشكل عام
أشير في آخر الملخص إلى الشكل (***)، وهذا الشكل يمثل التقنية السابقة وليس الاختراع، حيث أنه بوضعه الحالي لا يتوافق مع المادة (13) الفقرة (2) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""إذا كان هناك رسومات توضيحية يشار إلى الشكل الذي يمثل الاختراع بشكل عام، ويوضع رقم هذا الشكل في آخر الملخص"". ولتجاوز هذا الاعتراض يجب على مقدم الطلب وضع رقم الشكل الذي يمثل الاختراع بشكل عام."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'SUMMARY_AR'),'APPLICANT'
       );
INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"
•	الملخص لا يصف المجال التقني للاختراع/وأهم مكوناته واستعماله الرئيس
الملخص لا يتوافق مع المادة (13) الفقرة (4) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يذكر في الملخص المجال التقني ووصف مختصر لأهم مكونات الاختراع واستعماله الرئيس""، لذلك يجب على مقدم الطلب التعديل."','"
•	الملخص لا يصف المجال التقني للاختراع/وأهم مكوناته واستعماله الرئيس
الملخص لا يتوافق مع المادة (13) الفقرة (4) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يذكر في الملخص المجال التقني ووصف مختصر لأهم مكونات الاختراع واستعماله الرئيس""، لذلك يجب على مقدم الطلب التعديل."',true,'"
•	الملخص لا يصف المجال التقني للاختراع/وأهم مكوناته واستعماله الرئيس
الملخص لا يتوافق مع المادة (13) الفقرة (4) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يذكر في الملخص المجال التقني ووصف مختصر لأهم مكونات الاختراع واستعماله الرئيس""، لذلك يجب على مقدم الطلب التعديل."','"
•	الملخص لا يصف المجال التقني للاختراع/وأهم مكوناته واستعماله الرئيس
الملخص لا يتوافق مع المادة (13) الفقرة (4) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يذكر في الملخص المجال التقني ووصف مختصر لأهم مكونات الاختراع واستعماله الرئيس""، لذلك يجب على مقدم الطلب التعديل."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'SUMMARY_AR'),'APPLICANT'
       );
INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"
•	الملخص يتطرق وصف لحالة التقنية السابقة
إن ما تم ذكره في الملخص (أنظر السطر/الأسطر ***) هو وصف لحالة التقنية السابقة، وهذا لا يتوافق مع المادة (13) الفقرة (4) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يذكر في الملخص المجال التقني ووصف مختصر لأهم مكونات الاختراع واستعماله الرئيس"". لذلك، يجب على مقدم الطلب حذف وصف حالة التقنية السابقة من الملخص."','"
•	الملخص يتطرق وصف لحالة التقنية السابقة
إن ما تم ذكره في الملخص (أنظر السطر/الأسطر ***) هو وصف لحالة التقنية السابقة، وهذا لا يتوافق مع المادة (13) الفقرة (4) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يذكر في الملخص المجال التقني ووصف مختصر لأهم مكونات الاختراع واستعماله الرئيس"". لذلك، يجب على مقدم الطلب حذف وصف حالة التقنية السابقة من الملخص."',true,'"
•	الملخص يتطرق وصف لحالة التقنية السابقة
إن ما تم ذكره في الملخص (أنظر السطر/الأسطر ***) هو وصف لحالة التقنية السابقة، وهذا لا يتوافق مع المادة (13) الفقرة (4) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يذكر في الملخص المجال التقني ووصف مختصر لأهم مكونات الاختراع واستعماله الرئيس"". لذلك، يجب على مقدم الطلب حذف وصف حالة التقنية السابقة من الملخص."','"
•	الملخص يتطرق وصف لحالة التقنية السابقة
إن ما تم ذكره في الملخص (أنظر السطر/الأسطر ***) هو وصف لحالة التقنية السابقة، وهذا لا يتوافق مع المادة (13) الفقرة (4) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يذكر في الملخص المجال التقني ووصف مختصر لأهم مكونات الاختراع واستعماله الرئيس"". لذلك، يجب على مقدم الطلب حذف وصف حالة التقنية السابقة من الملخص."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'SUMMARY_AR'),'APPLICANT'
       );
INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	الملخص يذكر أهمية أو قيمة أو مزايا الاختراع المتوقعة مستقبلاً
الملخص لا يتوافق مع المادة (13) الفقرة (6) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يجب تجنب ذكر أهمية أو قيمة أو مزايا الاختراع المتوقعة مستقبلاً ""، لذلك يجب على مقدم الطلب التعديل."','"•	الملخص يذكر أهمية أو قيمة أو مزايا الاختراع المتوقعة مستقبلاً
الملخص لا يتوافق مع المادة (13) الفقرة (6) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يجب تجنب ذكر أهمية أو قيمة أو مزايا الاختراع المتوقعة مستقبلاً ""، لذلك يجب على مقدم الطلب التعديل."',true,'"•	الملخص يذكر أهمية أو قيمة أو مزايا الاختراع المتوقعة مستقبلاً
الملخص لا يتوافق مع المادة (13) الفقرة (6) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يجب تجنب ذكر أهمية أو قيمة أو مزايا الاختراع المتوقعة مستقبلاً ""، لذلك يجب على مقدم الطلب التعديل."','"•	الملخص يذكر أهمية أو قيمة أو مزايا الاختراع المتوقعة مستقبلاً
الملخص لا يتوافق مع المادة (13) الفقرة (6) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يجب تجنب ذكر أهمية أو قيمة أو مزايا الاختراع المتوقعة مستقبلاً ""، لذلك يجب على مقدم الطلب التعديل."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'SUMMARY_AR'),'APPLICANT'
       );
INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	الملخص لم يصاغ  بأسلوب سهل بحيث يعطي فهمًا واضحًا لحل المشكلة التقنية
الملخص المرفق في الطلب الحالي لا يتوافق مع المادة (13) الفقرة (5) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: "" يصاغ الملخص بأسلوب سهل بحيث يعطي فهما واضحا لحل المشكلة التقنية وبحيث يمكن استخدامه لأغراض الإعلام التقني في مجال البحث العلمي، ويجب ملاحظة أن الملخص لا يستعمل في تفسير نطاق الحماية "". كونه لم يذكر مكونات الاختراع بشكل مترابط، ولتجاوز هذا الاعتراض يجب على مقدم الطلب التعديل."','"•	الملخص لم يصاغ  بأسلوب سهل بحيث يعطي فهمًا واضحًا لحل المشكلة التقنية
الملخص المرفق في الطلب الحالي لا يتوافق مع المادة (13) الفقرة (5) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: "" يصاغ الملخص بأسلوب سهل بحيث يعطي فهما واضحا لحل المشكلة التقنية وبحيث يمكن استخدامه لأغراض الإعلام التقني في مجال البحث العلمي، ويجب ملاحظة أن الملخص لا يستعمل في تفسير نطاق الحماية "". كونه لم يذكر مكونات الاختراع بشكل مترابط، ولتجاوز هذا الاعتراض يجب على مقدم الطلب التعديل."',true,'"•	الملخص لم يصاغ  بأسلوب سهل بحيث يعطي فهمًا واضحًا لحل المشكلة التقنية
الملخص المرفق في الطلب الحالي لا يتوافق مع المادة (13) الفقرة (5) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: "" يصاغ الملخص بأسلوب سهل بحيث يعطي فهما واضحا لحل المشكلة التقنية وبحيث يمكن استخدامه لأغراض الإعلام التقني في مجال البحث العلمي، ويجب ملاحظة أن الملخص لا يستعمل في تفسير نطاق الحماية "". كونه لم يذكر مكونات الاختراع بشكل مترابط، ولتجاوز هذا الاعتراض يجب على مقدم الطلب التعديل."','"•	الملخص لم يصاغ  بأسلوب سهل بحيث يعطي فهمًا واضحًا لحل المشكلة التقنية
الملخص المرفق في الطلب الحالي لا يتوافق مع المادة (13) الفقرة (5) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: "" يصاغ الملخص بأسلوب سهل بحيث يعطي فهما واضحا لحل المشكلة التقنية وبحيث يمكن استخدامه لأغراض الإعلام التقني في مجال البحث العلمي، ويجب ملاحظة أن الملخص لا يستعمل في تفسير نطاق الحماية "". كونه لم يذكر مكونات الاختراع بشكل مترابط، ولتجاوز هذا الاعتراض يجب على مقدم الطلب التعديل."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'SUMMARY_AR'),'APPLICANT'
       );
INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"
•	الملخص لم يذكر الأرقام أو الأحرف المستعملة لتميز المكونات في الشكل المذكور في الملخص
إذا أشير في الملخص إلى مكونات موجودة بالشكل الذي يمثل الاختراع بشكل عام ووجد بالشكل أرقام أو حروف مستعملة لتميز تلك المكونات فإنه يجب ذكر هذه الأرقام أو الحروف بين قوسين داخل النص في الملخص وذلك طبقا للمادة (13) الفقرة (3) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على"" إذا أشير في الملخص إلى مكونات موجودة بالشكل المشار إليه ، ووجد بالشكل أرقام أو حروف مستعملة لتمييز تلك المكونات فإنه يجب ذكر هذه الأرقام أو الحروف بين قوسين داخل النص في الملخص ""."','"
•	الملخص لم يذكر الأرقام أو الأحرف المستعملة لتميز المكونات في الشكل المذكور في الملخص
إذا أشير في الملخص إلى مكونات موجودة بالشكل الذي يمثل الاختراع بشكل عام ووجد بالشكل أرقام أو حروف مستعملة لتميز تلك المكونات فإنه يجب ذكر هذه الأرقام أو الحروف بين قوسين داخل النص في الملخص وذلك طبقا للمادة (13) الفقرة (3) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على"" إذا أشير في الملخص إلى مكونات موجودة بالشكل المشار إليه ، ووجد بالشكل أرقام أو حروف مستعملة لتمييز تلك المكونات فإنه يجب ذكر هذه الأرقام أو الحروف بين قوسين داخل النص في الملخص ""."',true,'"
•	الملخص لم يذكر الأرقام أو الأحرف المستعملة لتميز المكونات في الشكل المذكور في الملخص
إذا أشير في الملخص إلى مكونات موجودة بالشكل الذي يمثل الاختراع بشكل عام ووجد بالشكل أرقام أو حروف مستعملة لتميز تلك المكونات فإنه يجب ذكر هذه الأرقام أو الحروف بين قوسين داخل النص في الملخص وذلك طبقا للمادة (13) الفقرة (3) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على"" إذا أشير في الملخص إلى مكونات موجودة بالشكل المشار إليه ، ووجد بالشكل أرقام أو حروف مستعملة لتمييز تلك المكونات فإنه يجب ذكر هذه الأرقام أو الحروف بين قوسين داخل النص في الملخص ""."','"
•	الملخص لم يذكر الأرقام أو الأحرف المستعملة لتميز المكونات في الشكل المذكور في الملخص
إذا أشير في الملخص إلى مكونات موجودة بالشكل الذي يمثل الاختراع بشكل عام ووجد بالشكل أرقام أو حروف مستعملة لتميز تلك المكونات فإنه يجب ذكر هذه الأرقام أو الحروف بين قوسين داخل النص في الملخص وذلك طبقا للمادة (13) الفقرة (3) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص على"" إذا أشير في الملخص إلى مكونات موجودة بالشكل المشار إليه ، ووجد بالشكل أرقام أو حروف مستعملة لتمييز تلك المكونات فإنه يجب ذكر هذه الأرقام أو الحروف بين قوسين داخل النص في الملخص ""."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'SUMMARY_AR'),'APPLICANT'
       );











INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	الوصف الكامل لم يبدأ باسم الاختراع
-	الوصف الكامل لا يتوافق مع المادة (11) الفقرة (2) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يبدأ الملخص والوصف الكامل باسم الاختراع"". لذلك يجب على مقدم الطلب التعديل."','"•	الوصف الكامل لم يبدأ باسم الاختراع
-	الوصف الكامل لا يتوافق مع المادة (11) الفقرة (2) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يبدأ الملخص والوصف الكامل باسم الاختراع"". لذلك يجب على مقدم الطلب التعديل."',true,'"•	الوصف الكامل لم يبدأ باسم الاختراع
-	الوصف الكامل لا يتوافق مع المادة (11) الفقرة (2) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يبدأ الملخص والوصف الكامل باسم الاختراع"". لذلك يجب على مقدم الطلب التعديل."','"•	الوصف الكامل لم يبدأ باسم الاختراع
-	الوصف الكامل لا يتوافق مع المادة (11) الفقرة (2) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يبدأ الملخص والوصف الكامل باسم الاختراع"". لذلك يجب على مقدم الطلب التعديل."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT'
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	اسم الاختراع بالوصف الكامل لا يتطابق مع المذكور في النظام الالكتروني
-	يجب أن يتطابق اسم الاختراع بالوصف الكامل مع النظام الالكتروني(عربي/إنجليزي)، لذا يجب على مقدم الطلب إضافة اسم الاختراع لقالب الوصف الكامل وتعديل ما يلزم وفقاً لما نصت عليه المادة (9) الفقرة (3) حيث"" يجب أن يطابق اسم الاختراع الشروط المنصوص عليها، وألا يختلف عن الاسم المذكور في مواصفة الاختراع""."','"•	اسم الاختراع بالوصف الكامل لا يتطابق مع المذكور في النظام الالكتروني
-	يجب أن يتطابق اسم الاختراع بالوصف الكامل مع النظام الالكتروني(عربي/إنجليزي)، لذا يجب على مقدم الطلب إضافة اسم الاختراع لقالب الوصف الكامل وتعديل ما يلزم وفقاً لما نصت عليه المادة (9) الفقرة (3) حيث"" يجب أن يطابق اسم الاختراع الشروط المنصوص عليها، وألا يختلف عن الاسم المذكور في مواصفة الاختراع""."',true,'"•	اسم الاختراع بالوصف الكامل لا يتطابق مع المذكور في النظام الالكتروني
-	يجب أن يتطابق اسم الاختراع بالوصف الكامل مع النظام الالكتروني(عربي/إنجليزي)، لذا يجب على مقدم الطلب إضافة اسم الاختراع لقالب الوصف الكامل وتعديل ما يلزم وفقاً لما نصت عليه المادة (9) الفقرة (3) حيث"" يجب أن يطابق اسم الاختراع الشروط المنصوص عليها، وألا يختلف عن الاسم المذكور في مواصفة الاختراع""."','"•	اسم الاختراع بالوصف الكامل لا يتطابق مع المذكور في النظام الالكتروني
-	يجب أن يتطابق اسم الاختراع بالوصف الكامل مع النظام الالكتروني(عربي/إنجليزي)، لذا يجب على مقدم الطلب إضافة اسم الاختراع لقالب الوصف الكامل وتعديل ما يلزم وفقاً لما نصت عليه المادة (9) الفقرة (3) حيث"" يجب أن يطابق اسم الاختراع الشروط المنصوص عليها، وألا يختلف عن الاسم المذكور في مواصفة الاختراع""."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT'
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	جزء/أجزاء من الوصف الكامل غير موجودة
-	الوصف الكامل لا يتوافق مع المادة (14) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يجب أن يشتمل الوصف الكامل على الأجزاء التالية: خلفية الاختراع، الوصف العام للاختراع، شرح مختصر للرسومات، الوصف التفصيلي""، حيث لم يشتمل الوصف الكامل على (.....) ولتجاوز هذا الاعتراض، يجب على مقدم الطلب التعديل مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."','"•	جزء/أجزاء من الوصف الكامل غير موجودة
-	الوصف الكامل لا يتوافق مع المادة (14) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يجب أن يشتمل الوصف الكامل على الأجزاء التالية: خلفية الاختراع، الوصف العام للاختراع، شرح مختصر للرسومات، الوصف التفصيلي""، حيث لم يشتمل الوصف الكامل على (.....) ولتجاوز هذا الاعتراض، يجب على مقدم الطلب التعديل مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."',true,'"•	جزء/أجزاء من الوصف الكامل غير موجودة
-	الوصف الكامل لا يتوافق مع المادة (14) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يجب أن يشتمل الوصف الكامل على الأجزاء التالية: خلفية الاختراع، الوصف العام للاختراع، شرح مختصر للرسومات، الوصف التفصيلي""، حيث لم يشتمل الوصف الكامل على (.....) ولتجاوز هذا الاعتراض، يجب على مقدم الطلب التعديل مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."','"•	جزء/أجزاء من الوصف الكامل غير موجودة
-	الوصف الكامل لا يتوافق مع المادة (14) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يجب أن يشتمل الوصف الكامل على الأجزاء التالية: خلفية الاختراع، الوصف العام للاختراع، شرح مختصر للرسومات، الوصف التفصيلي""، حيث لم يشتمل الوصف الكامل على (.....) ولتجاوز هذا الاعتراض، يجب على مقدم الطلب التعديل مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT'
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	تفاصيل محتوى جزء /أجزاء من الوصف الكامل غير موجودة
-	لم يتضمن قالب الوصف الكامل التطرق لأي تفاصيل لمحتوى أجزائه الأربعة أو بعضها (خلفية الاختراع، الوصف العام للاختراع، شرح مختصر للرسومات، الوصف التفصيلي)، لذا يجب على مقدم الطلب تعديل ذلك وتقديم صياغة جيدة محتوية على شرح كافيا وشاملا لجميع تفاصيل أجزاء الوصف الكامل."','"•	تفاصيل محتوى جزء /أجزاء من الوصف الكامل غير موجودة
-	لم يتضمن قالب الوصف الكامل التطرق لأي تفاصيل لمحتوى أجزائه الأربعة أو بعضها (خلفية الاختراع، الوصف العام للاختراع، شرح مختصر للرسومات، الوصف التفصيلي)، لذا يجب على مقدم الطلب تعديل ذلك وتقديم صياغة جيدة محتوية على شرح كافيا وشاملا لجميع تفاصيل أجزاء الوصف الكامل."',true,'"•	تفاصيل محتوى جزء /أجزاء من الوصف الكامل غير موجودة
-	لم يتضمن قالب الوصف الكامل التطرق لأي تفاصيل لمحتوى أجزائه الأربعة أو بعضها (خلفية الاختراع، الوصف العام للاختراع، شرح مختصر للرسومات، الوصف التفصيلي)، لذا يجب على مقدم الطلب تعديل ذلك وتقديم صياغة جيدة محتوية على شرح كافيا وشاملا لجميع تفاصيل أجزاء الوصف الكامل."','"•	تفاصيل محتوى جزء /أجزاء من الوصف الكامل غير موجودة
-	لم يتضمن قالب الوصف الكامل التطرق لأي تفاصيل لمحتوى أجزائه الأربعة أو بعضها (خلفية الاختراع، الوصف العام للاختراع، شرح مختصر للرسومات، الوصف التفصيلي)، لذا يجب على مقدم الطلب تعديل ذلك وتقديم صياغة جيدة محتوية على شرح كافيا وشاملا لجميع تفاصيل أجزاء الوصف الكامل."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT'
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	الوصف الكامل يتضمن عناوين إضافية
-	""الوصف الكامل"" بوضعه الحالي لا يتوافق مع ما نصت المادة (14) من اللائحة التنفيذية والتي تنص على"" يجب أن يشتمل الوصف الكامل على الأجزاء التالية: ""خلفية الاختراع ، الوصف العام للاختراع، شرح مختصر للرسومات، الوصف التفصيلي"" وتكون الأجزاء السابقة مرتبة بالتسلسل ويتم ذكر العنوان في بداية السطر ويوضع تحته خط، حيث أن الوصف الكامل وبالتحديد ما ورد في  ""خلفية الاختراع"" تضمن عناوين إضافية مثل "" المجال التقني للاختراع، الخلفية التقنية للاختراع""، حيث يجب أن لا يحتوي الوصف الكامل على أي عناوين إضافية وذلك طبقًا لأحكام المادة (14) من اللائحة التنفيذية، ولتجاوز الاعتراض يجب على مقدم الطلب حذف جميع العناوين الجانبية الإضافية."','"•	الوصف الكامل يتضمن عناوين إضافية
-	""الوصف الكامل"" بوضعه الحالي لا يتوافق مع ما نصت المادة (14) من اللائحة التنفيذية والتي تنص على"" يجب أن يشتمل الوصف الكامل على الأجزاء التالية: ""خلفية الاختراع ، الوصف العام للاختراع، شرح مختصر للرسومات، الوصف التفصيلي"" وتكون الأجزاء السابقة مرتبة بالتسلسل ويتم ذكر العنوان في بداية السطر ويوضع تحته خط، حيث أن الوصف الكامل وبالتحديد ما ورد في  ""خلفية الاختراع"" تضمن عناوين إضافية مثل "" المجال التقني للاختراع، الخلفية التقنية للاختراع""، حيث يجب أن لا يحتوي الوصف الكامل على أي عناوين إضافية وذلك طبقًا لأحكام المادة (14) من اللائحة التنفيذية، ولتجاوز الاعتراض يجب على مقدم الطلب حذف جميع العناوين الجانبية الإضافية."',true,'"•	الوصف الكامل يتضمن عناوين إضافية
-	""الوصف الكامل"" بوضعه الحالي لا يتوافق مع ما نصت المادة (14) من اللائحة التنفيذية والتي تنص على"" يجب أن يشتمل الوصف الكامل على الأجزاء التالية: ""خلفية الاختراع ، الوصف العام للاختراع، شرح مختصر للرسومات، الوصف التفصيلي"" وتكون الأجزاء السابقة مرتبة بالتسلسل ويتم ذكر العنوان في بداية السطر ويوضع تحته خط، حيث أن الوصف الكامل وبالتحديد ما ورد في  ""خلفية الاختراع"" تضمن عناوين إضافية مثل "" المجال التقني للاختراع، الخلفية التقنية للاختراع""، حيث يجب أن لا يحتوي الوصف الكامل على أي عناوين إضافية وذلك طبقًا لأحكام المادة (14) من اللائحة التنفيذية، ولتجاوز الاعتراض يجب على مقدم الطلب حذف جميع العناوين الجانبية الإضافية."','"•	الوصف الكامل يتضمن عناوين إضافية
-	""الوصف الكامل"" بوضعه الحالي لا يتوافق مع ما نصت المادة (14) من اللائحة التنفيذية والتي تنص على"" يجب أن يشتمل الوصف الكامل على الأجزاء التالية: ""خلفية الاختراع ، الوصف العام للاختراع، شرح مختصر للرسومات، الوصف التفصيلي"" وتكون الأجزاء السابقة مرتبة بالتسلسل ويتم ذكر العنوان في بداية السطر ويوضع تحته خط، حيث أن الوصف الكامل وبالتحديد ما ورد في  ""خلفية الاختراع"" تضمن عناوين إضافية مثل "" المجال التقني للاختراع، الخلفية التقنية للاختراع""، حيث يجب أن لا يحتوي الوصف الكامل على أي عناوين إضافية وذلك طبقًا لأحكام المادة (14) من اللائحة التنفيذية، ولتجاوز الاعتراض يجب على مقدم الطلب حذف جميع العناوين الجانبية الإضافية."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT'
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	الرسومات التوضيحية ضرورية للفهم الكامل والواضح للاختراع
-	المواصفة تتعارض مع المادة (11) الفقرة (5) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يتحتم إرفاق الرسومات والأشكال التوضيحية إذا كان ذلك يؤدي إلى الفهم الكامل والواضح للاختراع"". كون الأشكال التوضيحية تعد ضرورية للفهم الكامل والواضح للاختراعات مثل الاختراعات الميكانيكية والكهربائية. ولتجاوز هذا الاعتراض، يجب على مقدم الطلب تعديل المواصفة لتتوافق مع ما نصت عليه المادة (11) الفقرة (5) من اللائحة مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."','"•	الرسومات التوضيحية ضرورية للفهم الكامل والواضح للاختراع
-	المواصفة تتعارض مع المادة (11) الفقرة (5) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يتحتم إرفاق الرسومات والأشكال التوضيحية إذا كان ذلك يؤدي إلى الفهم الكامل والواضح للاختراع"". كون الأشكال التوضيحية تعد ضرورية للفهم الكامل والواضح للاختراعات مثل الاختراعات الميكانيكية والكهربائية. ولتجاوز هذا الاعتراض، يجب على مقدم الطلب تعديل المواصفة لتتوافق مع ما نصت عليه المادة (11) الفقرة (5) من اللائحة مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."',true,'"•	الرسومات التوضيحية ضرورية للفهم الكامل والواضح للاختراع
-	المواصفة تتعارض مع المادة (11) الفقرة (5) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يتحتم إرفاق الرسومات والأشكال التوضيحية إذا كان ذلك يؤدي إلى الفهم الكامل والواضح للاختراع"". كون الأشكال التوضيحية تعد ضرورية للفهم الكامل والواضح للاختراعات مثل الاختراعات الميكانيكية والكهربائية. ولتجاوز هذا الاعتراض، يجب على مقدم الطلب تعديل المواصفة لتتوافق مع ما نصت عليه المادة (11) الفقرة (5) من اللائحة مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."','"•	الرسومات التوضيحية ضرورية للفهم الكامل والواضح للاختراع
-	المواصفة تتعارض مع المادة (11) الفقرة (5) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يتحتم إرفاق الرسومات والأشكال التوضيحية إذا كان ذلك يؤدي إلى الفهم الكامل والواضح للاختراع"". كون الأشكال التوضيحية تعد ضرورية للفهم الكامل والواضح للاختراعات مثل الاختراعات الميكانيكية والكهربائية. ولتجاوز هذا الاعتراض، يجب على مقدم الطلب تعديل المواصفة لتتوافق مع ما نصت عليه المادة (11) الفقرة (5) من اللائحة مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT'
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	خلفية الاختراع للطلب الجزئي
-	خلفية الاختراع للطلب الجزئي الحالي غير مقبولة في وضعها الحالي. ولتجاوز هذا الاعتراض يجب على مقدم الطلب أن يذكر في أول خلفية الاختراع العبارة التالية ""إن هذا الطلب عبارة عن طلب جزئي من الطلب رقم (.....) والذي تم إيداعه لدى الهيئة السعودية للملكية الفكرية بتاريخ ‏ **‏/**‏/**** هـ الموافق ‏**‏/**‏/**** م."','"•	خلفية الاختراع للطلب الجزئي
-	خلفية الاختراع للطلب الجزئي الحالي غير مقبولة في وضعها الحالي. ولتجاوز هذا الاعتراض يجب على مقدم الطلب أن يذكر في أول خلفية الاختراع العبارة التالية ""إن هذا الطلب عبارة عن طلب جزئي من الطلب رقم (.....) والذي تم إيداعه لدى الهيئة السعودية للملكية الفكرية بتاريخ ‏ **‏/**‏/**** هـ الموافق ‏**‏/**‏/**** م."',true,'"•	خلفية الاختراع للطلب الجزئي
-	خلفية الاختراع للطلب الجزئي الحالي غير مقبولة في وضعها الحالي. ولتجاوز هذا الاعتراض يجب على مقدم الطلب أن يذكر في أول خلفية الاختراع العبارة التالية ""إن هذا الطلب عبارة عن طلب جزئي من الطلب رقم (.....) والذي تم إيداعه لدى الهيئة السعودية للملكية الفكرية بتاريخ ‏ **‏/**‏/**** هـ الموافق ‏**‏/**‏/**** م."','"•	خلفية الاختراع للطلب الجزئي
-	خلفية الاختراع للطلب الجزئي الحالي غير مقبولة في وضعها الحالي. ولتجاوز هذا الاعتراض يجب على مقدم الطلب أن يذكر في أول خلفية الاختراع العبارة التالية ""إن هذا الطلب عبارة عن طلب جزئي من الطلب رقم (.....) والذي تم إيداعه لدى الهيئة السعودية للملكية الفكرية بتاريخ ‏ **‏/**‏/**** هـ الموافق ‏**‏/**‏/**** م."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT'
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	خلفية الاختراع لم تبين المجال التقني الذي يتناوله الاختراع
-	""خلفية الاختراع"" بوضعها الحالي لا تتوافق مع المادة (14) الفقرة (1) من اللائحة والتي تنص في جزء منها على أن ""خلفية الاختراع يبين فيها المجال التقني الذي يتناوله الاختراع"". ولتجاوز هذا الاعتراض يجب على مقدم الطلب أن يبين المجال التقني الذي يتناوله الاختراع. ويمكن الاستفادة من تعريف التصنيف الدولي لموضوع الاختراع لبيان المجال التقني للاختراع الحالي."','"•	خلفية الاختراع لم تبين المجال التقني الذي يتناوله الاختراع
-	""خلفية الاختراع"" بوضعها الحالي لا تتوافق مع المادة (14) الفقرة (1) من اللائحة والتي تنص في جزء منها على أن ""خلفية الاختراع يبين فيها المجال التقني الذي يتناوله الاختراع"". ولتجاوز هذا الاعتراض يجب على مقدم الطلب أن يبين المجال التقني الذي يتناوله الاختراع. ويمكن الاستفادة من تعريف التصنيف الدولي لموضوع الاختراع لبيان المجال التقني للاختراع الحالي."',true,'"•	خلفية الاختراع لم تبين المجال التقني الذي يتناوله الاختراع
-	""خلفية الاختراع"" بوضعها الحالي لا تتوافق مع المادة (14) الفقرة (1) من اللائحة والتي تنص في جزء منها على أن ""خلفية الاختراع يبين فيها المجال التقني الذي يتناوله الاختراع"". ولتجاوز هذا الاعتراض يجب على مقدم الطلب أن يبين المجال التقني الذي يتناوله الاختراع. ويمكن الاستفادة من تعريف التصنيف الدولي لموضوع الاختراع لبيان المجال التقني للاختراع الحالي."','"•	خلفية الاختراع لم تبين المجال التقني الذي يتناوله الاختراع
-	""خلفية الاختراع"" بوضعها الحالي لا تتوافق مع المادة (14) الفقرة (1) من اللائحة والتي تنص في جزء منها على أن ""خلفية الاختراع يبين فيها المجال التقني الذي يتناوله الاختراع"". ولتجاوز هذا الاعتراض يجب على مقدم الطلب أن يبين المجال التقني الذي يتناوله الاختراع. ويمكن الاستفادة من تعريف التصنيف الدولي لموضوع الاختراع لبيان المجال التقني للاختراع الحالي."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT'
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"
•	خلفية الاختراع لم تصف حالة التقنية السابقة بما في ذلك أي وثائق يكون المخترع على علم بها/لم تذكر مشاكل تتعلق بحالة التقنية السابقة من شان الاختراع التغلب عليها
-	""خلفية الاختراع"" لم تتطرق إلى تجارب حال التقنية السابقة والوثائق ذات العلاقة أو أي مشاكل بحال التقنية السابقة، إذ يجب أخذ الوثائق ذات العلاقة في الاعتبار موضحًا عيوب حلها وذاكراً ميزات حله. وعليه، فإن خلفية الاختراع لم تستوف المتطلبات الواردة في المادة (14) الفقرة (1) من اللائحة التنفيذية للنظام وتنص في جزء منها على التالي "" خلفية الاختراع يبين فيها المجال التقني الذي يتناوله الاختراع ووصف حالة التقنية بما في ذلك أي وثائق يكون المخترع على علم بها، مع ذكر أي مشاكل تتعلق بحالة التقنية السابقة من شأن الاختراع التغلب عليها"". ولتجاوز هذا الاعتراض، يجب على مقدم الطلب التعديل مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."','"
•	خلفية الاختراع لم تصف حالة التقنية السابقة بما في ذلك أي وثائق يكون المخترع على علم بها/لم تذكر مشاكل تتعلق بحالة التقنية السابقة من شان الاختراع التغلب عليها
-	""خلفية الاختراع"" لم تتطرق إلى تجارب حال التقنية السابقة والوثائق ذات العلاقة أو أي مشاكل بحال التقنية السابقة، إذ يجب أخذ الوثائق ذات العلاقة في الاعتبار موضحًا عيوب حلها وذاكراً ميزات حله. وعليه، فإن خلفية الاختراع لم تستوف المتطلبات الواردة في المادة (14) الفقرة (1) من اللائحة التنفيذية للنظام وتنص في جزء منها على التالي "" خلفية الاختراع يبين فيها المجال التقني الذي يتناوله الاختراع ووصف حالة التقنية بما في ذلك أي وثائق يكون المخترع على علم بها، مع ذكر أي مشاكل تتعلق بحالة التقنية السابقة من شأن الاختراع التغلب عليها"". ولتجاوز هذا الاعتراض، يجب على مقدم الطلب التعديل مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."',true,'"
•	خلفية الاختراع لم تصف حالة التقنية السابقة بما في ذلك أي وثائق يكون المخترع على علم بها/لم تذكر مشاكل تتعلق بحالة التقنية السابقة من شان الاختراع التغلب عليها
-	""خلفية الاختراع"" لم تتطرق إلى تجارب حال التقنية السابقة والوثائق ذات العلاقة أو أي مشاكل بحال التقنية السابقة، إذ يجب أخذ الوثائق ذات العلاقة في الاعتبار موضحًا عيوب حلها وذاكراً ميزات حله. وعليه، فإن خلفية الاختراع لم تستوف المتطلبات الواردة في المادة (14) الفقرة (1) من اللائحة التنفيذية للنظام وتنص في جزء منها على التالي "" خلفية الاختراع يبين فيها المجال التقني الذي يتناوله الاختراع ووصف حالة التقنية بما في ذلك أي وثائق يكون المخترع على علم بها، مع ذكر أي مشاكل تتعلق بحالة التقنية السابقة من شأن الاختراع التغلب عليها"". ولتجاوز هذا الاعتراض، يجب على مقدم الطلب التعديل مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."','"
•	خلفية الاختراع لم تصف حالة التقنية السابقة بما في ذلك أي وثائق يكون المخترع على علم بها/لم تذكر مشاكل تتعلق بحالة التقنية السابقة من شان الاختراع التغلب عليها
-	""خلفية الاختراع"" لم تتطرق إلى تجارب حال التقنية السابقة والوثائق ذات العلاقة أو أي مشاكل بحال التقنية السابقة، إذ يجب أخذ الوثائق ذات العلاقة في الاعتبار موضحًا عيوب حلها وذاكراً ميزات حله. وعليه، فإن خلفية الاختراع لم تستوف المتطلبات الواردة في المادة (14) الفقرة (1) من اللائحة التنفيذية للنظام وتنص في جزء منها على التالي "" خلفية الاختراع يبين فيها المجال التقني الذي يتناوله الاختراع ووصف حالة التقنية بما في ذلك أي وثائق يكون المخترع على علم بها، مع ذكر أي مشاكل تتعلق بحالة التقنية السابقة من شأن الاختراع التغلب عليها"". ولتجاوز هذا الاعتراض، يجب على مقدم الطلب التعديل مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT'
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	خلفية الاختراع تضمنت التطرق إلى الوصف العام للاختراع
-	""خلفية الاختراع"" بوضعه الحالي لا تتوافق مع المادة (14) الفقرة (1) من اللائحة والتي تنص على أن "" خلفية الاختراع يبين فيها المجال التقني الذي يتناوله الاختراع ووصف حالة التقنية بما في ذلك أي وثائق يكون المخترع على علم بها، مع ذكر أي مشاكل تتعلق بحالة التقنية السابقة من شأن الاختراع التغلب عليها "". بسبب أن ما ذكره مقدم الطلب] كمثال أنظر الصفحة رقم ** السطر/الأسطر **[، هو وصف عام للاختراع. ولتجاوز هذا الاعتراض، يجب على مقدم الطلب حذف ذلك من خلفية الاختراع ونقله إلى الوصف العام للاختراع. "','"•	خلفية الاختراع تضمنت التطرق إلى الوصف العام للاختراع
-	""خلفية الاختراع"" بوضعه الحالي لا تتوافق مع المادة (14) الفقرة (1) من اللائحة والتي تنص على أن "" خلفية الاختراع يبين فيها المجال التقني الذي يتناوله الاختراع ووصف حالة التقنية بما في ذلك أي وثائق يكون المخترع على علم بها، مع ذكر أي مشاكل تتعلق بحالة التقنية السابقة من شأن الاختراع التغلب عليها "". بسبب أن ما ذكره مقدم الطلب] كمثال أنظر الصفحة رقم ** السطر/الأسطر **[، هو وصف عام للاختراع. ولتجاوز هذا الاعتراض، يجب على مقدم الطلب حذف ذلك من خلفية الاختراع ونقله إلى الوصف العام للاختراع. "',true,'"•	خلفية الاختراع تضمنت التطرق إلى الوصف العام للاختراع
-	""خلفية الاختراع"" بوضعه الحالي لا تتوافق مع المادة (14) الفقرة (1) من اللائحة والتي تنص على أن "" خلفية الاختراع يبين فيها المجال التقني الذي يتناوله الاختراع ووصف حالة التقنية بما في ذلك أي وثائق يكون المخترع على علم بها، مع ذكر أي مشاكل تتعلق بحالة التقنية السابقة من شأن الاختراع التغلب عليها "". بسبب أن ما ذكره مقدم الطلب] كمثال أنظر الصفحة رقم ** السطر/الأسطر **[، هو وصف عام للاختراع. ولتجاوز هذا الاعتراض، يجب على مقدم الطلب حذف ذلك من خلفية الاختراع ونقله إلى الوصف العام للاختراع. "','"•	خلفية الاختراع تضمنت التطرق إلى الوصف العام للاختراع
-	""خلفية الاختراع"" بوضعه الحالي لا تتوافق مع المادة (14) الفقرة (1) من اللائحة والتي تنص على أن "" خلفية الاختراع يبين فيها المجال التقني الذي يتناوله الاختراع ووصف حالة التقنية بما في ذلك أي وثائق يكون المخترع على علم بها، مع ذكر أي مشاكل تتعلق بحالة التقنية السابقة من شأن الاختراع التغلب عليها "". بسبب أن ما ذكره مقدم الطلب] كمثال أنظر الصفحة رقم ** السطر/الأسطر **[، هو وصف عام للاختراع. ولتجاوز هذا الاعتراض، يجب على مقدم الطلب حذف ذلك من خلفية الاختراع ونقله إلى الوصف العام للاختراع. "',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT'
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	خلفية الاختراع لم تتضمن الإشارة إلى أرقام الوثائق التي تم الاستشهاد بها
-	""خلفية الاختراع"" لم تستوف المتطلبات الواردة في المادة (14) الفقرة (1) من اللائحة التنفيذية للنظام وتنص بجزء منها على "" خلفية الاختراع يبين فيها المجال التقني الذي يتناوله الاختراع ووصف حالة التقنية بما في ذلك أي وثائق يكون المخترع على علم بها، مع ذكر أي مشاكل تتعلق بحالة التقنية السابقة من شأن الاختراع التغلب عليه "". كونه لم يذكر أرقام الوثائق التي تم الاستشهاد بها. ولتجاوز هذا الاعتراض، يجب على مقدم الطلب التعديل وذلك بذكر أرقام الوثائق المستشهد بها مقدم الطلب في خلفية الاختراع بدلًا من التسميات ""المرفق الأول.."" و""المرفق الثاني.."" و ""المرفق الثالث..""، مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي"','"•	خلفية الاختراع لم تتضمن الإشارة إلى أرقام الوثائق التي تم الاستشهاد بها
-	""خلفية الاختراع"" لم تستوف المتطلبات الواردة في المادة (14) الفقرة (1) من اللائحة التنفيذية للنظام وتنص بجزء منها على "" خلفية الاختراع يبين فيها المجال التقني الذي يتناوله الاختراع ووصف حالة التقنية بما في ذلك أي وثائق يكون المخترع على علم بها، مع ذكر أي مشاكل تتعلق بحالة التقنية السابقة من شأن الاختراع التغلب عليه "". كونه لم يذكر أرقام الوثائق التي تم الاستشهاد بها. ولتجاوز هذا الاعتراض، يجب على مقدم الطلب التعديل وذلك بذكر أرقام الوثائق المستشهد بها مقدم الطلب في خلفية الاختراع بدلًا من التسميات ""المرفق الأول.."" و""المرفق الثاني.."" و ""المرفق الثالث..""، مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي"',true,'"•	خلفية الاختراع لم تتضمن الإشارة إلى أرقام الوثائق التي تم الاستشهاد بها
-	""خلفية الاختراع"" لم تستوف المتطلبات الواردة في المادة (14) الفقرة (1) من اللائحة التنفيذية للنظام وتنص بجزء منها على "" خلفية الاختراع يبين فيها المجال التقني الذي يتناوله الاختراع ووصف حالة التقنية بما في ذلك أي وثائق يكون المخترع على علم بها، مع ذكر أي مشاكل تتعلق بحالة التقنية السابقة من شأن الاختراع التغلب عليه "". كونه لم يذكر أرقام الوثائق التي تم الاستشهاد بها. ولتجاوز هذا الاعتراض، يجب على مقدم الطلب التعديل وذلك بذكر أرقام الوثائق المستشهد بها مقدم الطلب في خلفية الاختراع بدلًا من التسميات ""المرفق الأول.."" و""المرفق الثاني.."" و ""المرفق الثالث..""، مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي"','"•	خلفية الاختراع لم تتضمن الإشارة إلى أرقام الوثائق التي تم الاستشهاد بها
-	""خلفية الاختراع"" لم تستوف المتطلبات الواردة في المادة (14) الفقرة (1) من اللائحة التنفيذية للنظام وتنص بجزء منها على "" خلفية الاختراع يبين فيها المجال التقني الذي يتناوله الاختراع ووصف حالة التقنية بما في ذلك أي وثائق يكون المخترع على علم بها، مع ذكر أي مشاكل تتعلق بحالة التقنية السابقة من شأن الاختراع التغلب عليه "". كونه لم يذكر أرقام الوثائق التي تم الاستشهاد بها. ولتجاوز هذا الاعتراض، يجب على مقدم الطلب التعديل وذلك بذكر أرقام الوثائق المستشهد بها مقدم الطلب في خلفية الاختراع بدلًا من التسميات ""المرفق الأول.."" و""المرفق الثاني.."" و ""المرفق الثالث..""، مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي"',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT'
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	خلفية الاختراع تضمنت التطرق لذكر الأسبقية
-	""خلفية الاختراع"" تضمنت التطرق لذكر الأسبقية ] كمثال أنظر الصفحة رقم ** السطر/الأسطر **[، لذا يجب على مقدم الطلب حذفها وتقديم صياغة جيدة ذو علاقة بموضوع الطلب."','"•	خلفية الاختراع تضمنت التطرق لذكر الأسبقية
-	""خلفية الاختراع"" تضمنت التطرق لذكر الأسبقية ] كمثال أنظر الصفحة رقم ** السطر/الأسطر **[، لذا يجب على مقدم الطلب حذفها وتقديم صياغة جيدة ذو علاقة بموضوع الطلب."',true,'"•	خلفية الاختراع تضمنت التطرق لذكر الأسبقية
-	""خلفية الاختراع"" تضمنت التطرق لذكر الأسبقية ] كمثال أنظر الصفحة رقم ** السطر/الأسطر **[، لذا يجب على مقدم الطلب حذفها وتقديم صياغة جيدة ذو علاقة بموضوع الطلب."','"•	خلفية الاختراع تضمنت التطرق لذكر الأسبقية
-	""خلفية الاختراع"" تضمنت التطرق لذكر الأسبقية ] كمثال أنظر الصفحة رقم ** السطر/الأسطر **[، لذا يجب على مقدم الطلب حذفها وتقديم صياغة جيدة ذو علاقة بموضوع الطلب."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT'
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	وثائق التقنية السابقة مكتوبة بشكل غير مقبول
-	رموز وثائق التقنية السابقة مكتوبة بشكل متداخل وخاطئ مع رقم الوثيقة ] كمثال أنظر الصفحة رقم ** السطر/الأسطر **[، ولتجاوز هذا الاعتراض يجب على مقدم الطلب مراجعة ذلك وتعديل ما يلزم.
-	بسبب الترجمة الحرفية لبعض الكلمات وبالتحديد لرموز وثائق التقنية السابقة ذات الصلة الواردة في خلفية الاختراع ] كمثال أنظر الصفحة رقم *** السطر/الأسطر ***[، حيث يجب تعديل ذلك خلال المدة المحددة نظاماً."','"•	وثائق التقنية السابقة مكتوبة بشكل غير مقبول
-	رموز وثائق التقنية السابقة مكتوبة بشكل متداخل وخاطئ مع رقم الوثيقة ] كمثال أنظر الصفحة رقم ** السطر/الأسطر **[، ولتجاوز هذا الاعتراض يجب على مقدم الطلب مراجعة ذلك وتعديل ما يلزم.
-	بسبب الترجمة الحرفية لبعض الكلمات وبالتحديد لرموز وثائق التقنية السابقة ذات الصلة الواردة في خلفية الاختراع ] كمثال أنظر الصفحة رقم *** السطر/الأسطر ***[، حيث يجب تعديل ذلك خلال المدة المحددة نظاماً."',true,'"•	وثائق التقنية السابقة مكتوبة بشكل غير مقبول
-	رموز وثائق التقنية السابقة مكتوبة بشكل متداخل وخاطئ مع رقم الوثيقة ] كمثال أنظر الصفحة رقم ** السطر/الأسطر **[، ولتجاوز هذا الاعتراض يجب على مقدم الطلب مراجعة ذلك وتعديل ما يلزم.
-	بسبب الترجمة الحرفية لبعض الكلمات وبالتحديد لرموز وثائق التقنية السابقة ذات الصلة الواردة في خلفية الاختراع ] كمثال أنظر الصفحة رقم *** السطر/الأسطر ***[، حيث يجب تعديل ذلك خلال المدة المحددة نظاماً."','"•	وثائق التقنية السابقة مكتوبة بشكل غير مقبول
-	رموز وثائق التقنية السابقة مكتوبة بشكل متداخل وخاطئ مع رقم الوثيقة ] كمثال أنظر الصفحة رقم ** السطر/الأسطر **[، ولتجاوز هذا الاعتراض يجب على مقدم الطلب مراجعة ذلك وتعديل ما يلزم.
-	بسبب الترجمة الحرفية لبعض الكلمات وبالتحديد لرموز وثائق التقنية السابقة ذات الصلة الواردة في خلفية الاختراع ] كمثال أنظر الصفحة رقم *** السطر/الأسطر ***[، حيث يجب تعديل ذلك خلال المدة المحددة نظاماً."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT'
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	وثائق التقنية السابقة تضمنت الإشارة لأسماء المخترعين
-	يجب حذف جملة "" الممنوحة لـ ""...."" والإكتفاء بالإشارة إلى رقم الوثيقة فقط ] كمثال أنظر الصفحة رقم ** السطر/الأسطر **[، لذا يجب على مقدم الطلب تعديل ذلك وتقديم صياغة جيدة.
-	يجب حذف جملة "" العائدة، العائد"" والإكتفاء بالإشارة إلى رقم الوثيقة فقط ] كمثال أنظر الصفحة رقم ** السطر/الأسطر **[، لذا يجب على مقدم الطلب تعديل ذلك وتقديم صياغة جيدة."','"•	وثائق التقنية السابقة تضمنت الإشارة لأسماء المخترعين
-	يجب حذف جملة "" الممنوحة لـ ""...."" والإكتفاء بالإشارة إلى رقم الوثيقة فقط ] كمثال أنظر الصفحة رقم ** السطر/الأسطر **[، لذا يجب على مقدم الطلب تعديل ذلك وتقديم صياغة جيدة.
-	يجب حذف جملة "" العائدة، العائد"" والإكتفاء بالإشارة إلى رقم الوثيقة فقط ] كمثال أنظر الصفحة رقم ** السطر/الأسطر **[، لذا يجب على مقدم الطلب تعديل ذلك وتقديم صياغة جيدة."',true,'"•	وثائق التقنية السابقة تضمنت الإشارة لأسماء المخترعين
-	يجب حذف جملة "" الممنوحة لـ ""...."" والإكتفاء بالإشارة إلى رقم الوثيقة فقط ] كمثال أنظر الصفحة رقم ** السطر/الأسطر **[، لذا يجب على مقدم الطلب تعديل ذلك وتقديم صياغة جيدة.
-	يجب حذف جملة "" العائدة، العائد"" والإكتفاء بالإشارة إلى رقم الوثيقة فقط ] كمثال أنظر الصفحة رقم ** السطر/الأسطر **[، لذا يجب على مقدم الطلب تعديل ذلك وتقديم صياغة جيدة."','"•	وثائق التقنية السابقة تضمنت الإشارة لأسماء المخترعين
-	يجب حذف جملة "" الممنوحة لـ ""...."" والإكتفاء بالإشارة إلى رقم الوثيقة فقط ] كمثال أنظر الصفحة رقم ** السطر/الأسطر **[، لذا يجب على مقدم الطلب تعديل ذلك وتقديم صياغة جيدة.
-	يجب حذف جملة "" العائدة، العائد"" والإكتفاء بالإشارة إلى رقم الوثيقة فقط ] كمثال أنظر الصفحة رقم ** السطر/الأسطر **[، لذا يجب على مقدم الطلب تعديل ذلك وتقديم صياغة جيدة."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT'
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	الوصف العام للاختراع غير واضح كونه لم يبين كيفية التغلب على المشاكل السابقة بذكر السمات الفنية بشكل مكتمل ومترابط
-	""الوصف العام للاختراع"" بوضعه الحالي لا يتوافق مع متطلبات المادة (14) الفقرة (2) من اللائحة والتي تنص على أن ""الوصف العام للاختراع يبين فيه مزايا الاختراع مقارنة بحالة التقنية السابقة وكيفية التغلب على الصعوبات أو المشاكل السابقة كما يبين فيه الهدف من الاختراع, ويكون جميع ذلك بطريقة واضحة بحيث تتيح لصاحب المعرفة العادية بالمجال التقني فهمه
، وعادة ما يتعلق هذا الجزء بعنصر الحماية الرئيس""، بسبب أن ما ذكره مقدم الطلب لا يوضح لرجل المهنة العادي في التقنية فهم الاختراع بصورة واضحة كونه مجرد سرد لعدد من مكونات الجهاز دون أي ربط وظيفي  فيما بين تلك المكونات للتغلب على الصعوبات أو المشاكل السابقة ، ولتجاوز هذا الاعتراض، يجب على مقدم الطلب تعديل الوصف العام للاختراع ليتوافق مع ما نصت عليه المادة (14) الفقرة (2) من اللائحة مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي.
-	الوصف العام للاختراع"" لا يتوافق مع المادة (14) الفقرة (2) من اللائحة والتي تنص على أن ""الوصف العام للاختراع يبين فيه مزايا الاختراع مقارنة بحالة التقنية السابقة وكيفية التغلب على الصعوبات أو المشاكل السابقة كما يبين فيه الهدف من الاختراع, ويكون جميع ذلك بطريقة واضحة بحيث تتيح لصاحب المعرفة العادية بالمجال التقني فهمه, وعادة ما يتعلق هذا الجزء بعنصر الحماية الرئيس""، بسبب أن ما ذكره مقدم الطلب ] كمثال أنظر الصفحة رقم *** السطر/الأسطر ***[، لا يوضح لرجل المهنة العادي في التقنية فهم الاختراع بصورة واضحة كونه لم يبين كيفية التغلب على المشاكل السابقة بذكر السمات الفنية بشكل مكتمل ومترابط. ولتجاوز هذا الاعتراض، يجب على مقدم الطلب تعديل الوصف العام للاختراع ليتوافق مع ما نصت عليه المادة (14) الفقرة (2) من اللائحة مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."','"•	الوصف العام للاختراع غير واضح كونه لم يبين كيفية التغلب على المشاكل السابقة بذكر السمات الفنية بشكل مكتمل ومترابط
-	""الوصف العام للاختراع"" بوضعه الحالي لا يتوافق مع متطلبات المادة (14) الفقرة (2) من اللائحة والتي تنص على أن ""الوصف العام للاختراع يبين فيه مزايا الاختراع مقارنة بحالة التقنية السابقة وكيفية التغلب على الصعوبات أو المشاكل السابقة كما يبين فيه الهدف من الاختراع, ويكون جميع ذلك بطريقة واضحة بحيث تتيح لصاحب المعرفة العادية بالمجال التقني فهمه
، وعادة ما يتعلق هذا الجزء بعنصر الحماية الرئيس""، بسبب أن ما ذكره مقدم الطلب لا يوضح لرجل المهنة العادي في التقنية فهم الاختراع بصورة واضحة كونه مجرد سرد لعدد من مكونات الجهاز دون أي ربط وظيفي  فيما بين تلك المكونات للتغلب على الصعوبات أو المشاكل السابقة ، ولتجاوز هذا الاعتراض، يجب على مقدم الطلب تعديل الوصف العام للاختراع ليتوافق مع ما نصت عليه المادة (14) الفقرة (2) من اللائحة مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي.
-	الوصف العام للاختراع"" لا يتوافق مع المادة (14) الفقرة (2) من اللائحة والتي تنص على أن ""الوصف العام للاختراع يبين فيه مزايا الاختراع مقارنة بحالة التقنية السابقة وكيفية التغلب على الصعوبات أو المشاكل السابقة كما يبين فيه الهدف من الاختراع, ويكون جميع ذلك بطريقة واضحة بحيث تتيح لصاحب المعرفة العادية بالمجال التقني فهمه, وعادة ما يتعلق هذا الجزء بعنصر الحماية الرئيس""، بسبب أن ما ذكره مقدم الطلب ] كمثال أنظر الصفحة رقم *** السطر/الأسطر ***[، لا يوضح لرجل المهنة العادي في التقنية فهم الاختراع بصورة واضحة كونه لم يبين كيفية التغلب على المشاكل السابقة بذكر السمات الفنية بشكل مكتمل ومترابط. ولتجاوز هذا الاعتراض، يجب على مقدم الطلب تعديل الوصف العام للاختراع ليتوافق مع ما نصت عليه المادة (14) الفقرة (2) من اللائحة مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."',true,'"•	الوصف العام للاختراع غير واضح كونه لم يبين كيفية التغلب على المشاكل السابقة بذكر السمات الفنية بشكل مكتمل ومترابط
-	""الوصف العام للاختراع"" بوضعه الحالي لا يتوافق مع متطلبات المادة (14) الفقرة (2) من اللائحة والتي تنص على أن ""الوصف العام للاختراع يبين فيه مزايا الاختراع مقارنة بحالة التقنية السابقة وكيفية التغلب على الصعوبات أو المشاكل السابقة كما يبين فيه الهدف من الاختراع, ويكون جميع ذلك بطريقة واضحة بحيث تتيح لصاحب المعرفة العادية بالمجال التقني فهمه
، وعادة ما يتعلق هذا الجزء بعنصر الحماية الرئيس""، بسبب أن ما ذكره مقدم الطلب لا يوضح لرجل المهنة العادي في التقنية فهم الاختراع بصورة واضحة كونه مجرد سرد لعدد من مكونات الجهاز دون أي ربط وظيفي  فيما بين تلك المكونات للتغلب على الصعوبات أو المشاكل السابقة ، ولتجاوز هذا الاعتراض، يجب على مقدم الطلب تعديل الوصف العام للاختراع ليتوافق مع ما نصت عليه المادة (14) الفقرة (2) من اللائحة مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي.
-	الوصف العام للاختراع"" لا يتوافق مع المادة (14) الفقرة (2) من اللائحة والتي تنص على أن ""الوصف العام للاختراع يبين فيه مزايا الاختراع مقارنة بحالة التقنية السابقة وكيفية التغلب على الصعوبات أو المشاكل السابقة كما يبين فيه الهدف من الاختراع, ويكون جميع ذلك بطريقة واضحة بحيث تتيح لصاحب المعرفة العادية بالمجال التقني فهمه, وعادة ما يتعلق هذا الجزء بعنصر الحماية الرئيس""، بسبب أن ما ذكره مقدم الطلب ] كمثال أنظر الصفحة رقم *** السطر/الأسطر ***[، لا يوضح لرجل المهنة العادي في التقنية فهم الاختراع بصورة واضحة كونه لم يبين كيفية التغلب على المشاكل السابقة بذكر السمات الفنية بشكل مكتمل ومترابط. ولتجاوز هذا الاعتراض، يجب على مقدم الطلب تعديل الوصف العام للاختراع ليتوافق مع ما نصت عليه المادة (14) الفقرة (2) من اللائحة مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."','"•	الوصف العام للاختراع غير واضح كونه لم يبين كيفية التغلب على المشاكل السابقة بذكر السمات الفنية بشكل مكتمل ومترابط
-	""الوصف العام للاختراع"" بوضعه الحالي لا يتوافق مع متطلبات المادة (14) الفقرة (2) من اللائحة والتي تنص على أن ""الوصف العام للاختراع يبين فيه مزايا الاختراع مقارنة بحالة التقنية السابقة وكيفية التغلب على الصعوبات أو المشاكل السابقة كما يبين فيه الهدف من الاختراع, ويكون جميع ذلك بطريقة واضحة بحيث تتيح لصاحب المعرفة العادية بالمجال التقني فهمه
، وعادة ما يتعلق هذا الجزء بعنصر الحماية الرئيس""، بسبب أن ما ذكره مقدم الطلب لا يوضح لرجل المهنة العادي في التقنية فهم الاختراع بصورة واضحة كونه مجرد سرد لعدد من مكونات الجهاز دون أي ربط وظيفي  فيما بين تلك المكونات للتغلب على الصعوبات أو المشاكل السابقة ، ولتجاوز هذا الاعتراض، يجب على مقدم الطلب تعديل الوصف العام للاختراع ليتوافق مع ما نصت عليه المادة (14) الفقرة (2) من اللائحة مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي.
-	الوصف العام للاختراع"" لا يتوافق مع المادة (14) الفقرة (2) من اللائحة والتي تنص على أن ""الوصف العام للاختراع يبين فيه مزايا الاختراع مقارنة بحالة التقنية السابقة وكيفية التغلب على الصعوبات أو المشاكل السابقة كما يبين فيه الهدف من الاختراع, ويكون جميع ذلك بطريقة واضحة بحيث تتيح لصاحب المعرفة العادية بالمجال التقني فهمه, وعادة ما يتعلق هذا الجزء بعنصر الحماية الرئيس""، بسبب أن ما ذكره مقدم الطلب ] كمثال أنظر الصفحة رقم *** السطر/الأسطر ***[، لا يوضح لرجل المهنة العادي في التقنية فهم الاختراع بصورة واضحة كونه لم يبين كيفية التغلب على المشاكل السابقة بذكر السمات الفنية بشكل مكتمل ومترابط. ولتجاوز هذا الاعتراض، يجب على مقدم الطلب تعديل الوصف العام للاختراع ليتوافق مع ما نصت عليه المادة (14) الفقرة (2) من اللائحة مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT'
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	الوصف العام للاختراع غير واضح كونه يذكر فقط مزايا الاختراع
-	""الوصف العام للاختراع"" لا يتوافق مع المادة (14) الفقرة (2) من اللائحة والتي تنص على أن ""الوصف العام للاختراع يبين فيه مزايا الاختراع مقارنة بحالة التقنية السابقة وكيفية التغلب على الصعوبات أو المشاكل السابقة كما يبين فيه الهدف من الاختراع, ويكون جميع ذلك بطريقة واضحة بحيث تتيح لصاحب المعرفة العادية بالمجال التقني فهمه, وعادة ما يتعلق هذا الجزء بعنصر الحماية الرئيس""،  بسبب أن ما ذكره مقدم الطلب ] كمثال أنظر الصفحة رقم ** السطر/الأسطر **[، لا يوضح لرجل المهنة العادي في التقنية فهم الاختراع بصورة واضحة كونه يذكر فقط مزايا الاختراع، ولم يبين كيفية التغلب على المشاكل السابقة بذكر السمات الفنية بشكل مكتمل ومترابط. ولتجاوز هذا الاعتراض، يجب على مقدم الطلب تعديل الوصف العام للاختراع ليتوافق مع ما نصت عليه المادة (14) الفقرة (2) من اللائحة مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي"','"•	الوصف العام للاختراع غير واضح كونه يذكر فقط مزايا الاختراع
-	""الوصف العام للاختراع"" لا يتوافق مع المادة (14) الفقرة (2) من اللائحة والتي تنص على أن ""الوصف العام للاختراع يبين فيه مزايا الاختراع مقارنة بحالة التقنية السابقة وكيفية التغلب على الصعوبات أو المشاكل السابقة كما يبين فيه الهدف من الاختراع, ويكون جميع ذلك بطريقة واضحة بحيث تتيح لصاحب المعرفة العادية بالمجال التقني فهمه, وعادة ما يتعلق هذا الجزء بعنصر الحماية الرئيس""،  بسبب أن ما ذكره مقدم الطلب ] كمثال أنظر الصفحة رقم ** السطر/الأسطر **[، لا يوضح لرجل المهنة العادي في التقنية فهم الاختراع بصورة واضحة كونه يذكر فقط مزايا الاختراع، ولم يبين كيفية التغلب على المشاكل السابقة بذكر السمات الفنية بشكل مكتمل ومترابط. ولتجاوز هذا الاعتراض، يجب على مقدم الطلب تعديل الوصف العام للاختراع ليتوافق مع ما نصت عليه المادة (14) الفقرة (2) من اللائحة مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي"',true,'"•	الوصف العام للاختراع غير واضح كونه يذكر فقط مزايا الاختراع
-	""الوصف العام للاختراع"" لا يتوافق مع المادة (14) الفقرة (2) من اللائحة والتي تنص على أن ""الوصف العام للاختراع يبين فيه مزايا الاختراع مقارنة بحالة التقنية السابقة وكيفية التغلب على الصعوبات أو المشاكل السابقة كما يبين فيه الهدف من الاختراع, ويكون جميع ذلك بطريقة واضحة بحيث تتيح لصاحب المعرفة العادية بالمجال التقني فهمه, وعادة ما يتعلق هذا الجزء بعنصر الحماية الرئيس""،  بسبب أن ما ذكره مقدم الطلب ] كمثال أنظر الصفحة رقم ** السطر/الأسطر **[، لا يوضح لرجل المهنة العادي في التقنية فهم الاختراع بصورة واضحة كونه يذكر فقط مزايا الاختراع، ولم يبين كيفية التغلب على المشاكل السابقة بذكر السمات الفنية بشكل مكتمل ومترابط. ولتجاوز هذا الاعتراض، يجب على مقدم الطلب تعديل الوصف العام للاختراع ليتوافق مع ما نصت عليه المادة (14) الفقرة (2) من اللائحة مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي"','"•	الوصف العام للاختراع غير واضح كونه يذكر فقط مزايا الاختراع
-	""الوصف العام للاختراع"" لا يتوافق مع المادة (14) الفقرة (2) من اللائحة والتي تنص على أن ""الوصف العام للاختراع يبين فيه مزايا الاختراع مقارنة بحالة التقنية السابقة وكيفية التغلب على الصعوبات أو المشاكل السابقة كما يبين فيه الهدف من الاختراع, ويكون جميع ذلك بطريقة واضحة بحيث تتيح لصاحب المعرفة العادية بالمجال التقني فهمه, وعادة ما يتعلق هذا الجزء بعنصر الحماية الرئيس""،  بسبب أن ما ذكره مقدم الطلب ] كمثال أنظر الصفحة رقم ** السطر/الأسطر **[، لا يوضح لرجل المهنة العادي في التقنية فهم الاختراع بصورة واضحة كونه يذكر فقط مزايا الاختراع، ولم يبين كيفية التغلب على المشاكل السابقة بذكر السمات الفنية بشكل مكتمل ومترابط. ولتجاوز هذا الاعتراض، يجب على مقدم الطلب تعديل الوصف العام للاختراع ليتوافق مع ما نصت عليه المادة (14) الفقرة (2) من اللائحة مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي"',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT'
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	الوصف العام للاختراع تضمن وصف لحالة التقنية السابقة
-	""الوصف العام للاختراع"" بوضعه الحالي لا يتوافق مع المادة (14) الفقرة (2) من اللائحة والتي تنص على أن ""الوصف العام للاختراع يبين فيه مزايا الاختراع مقارنة بحالة التقنية السابقة وكيفية التغلب على الصعوبات أو المشاكل السابقة كما يبين فيه الهدف من الاختراع, ويكون جميع ذلك بطريقة واضحة بحيث تتيح لصاحب المعرفة العادية بالمجال التقني فهمه, وعادة ما يتعلق هذا الجزء بعنصر الحماية الرئيس"". بسبب أن ما ذكره مقدم الطلب هو وصف لحالة التقنية السابقة ] كمثال أنظر الصفحة رقم *** السطر/الأسطر ***[، ولتجاوز هذا الإعتراض، يجب على مقدم الطلب حذف ذلك من الوصف العام للاختراع ونقله إلى خلفية الاختراع."','"•	الوصف العام للاختراع تضمن وصف لحالة التقنية السابقة
-	""الوصف العام للاختراع"" بوضعه الحالي لا يتوافق مع المادة (14) الفقرة (2) من اللائحة والتي تنص على أن ""الوصف العام للاختراع يبين فيه مزايا الاختراع مقارنة بحالة التقنية السابقة وكيفية التغلب على الصعوبات أو المشاكل السابقة كما يبين فيه الهدف من الاختراع, ويكون جميع ذلك بطريقة واضحة بحيث تتيح لصاحب المعرفة العادية بالمجال التقني فهمه, وعادة ما يتعلق هذا الجزء بعنصر الحماية الرئيس"". بسبب أن ما ذكره مقدم الطلب هو وصف لحالة التقنية السابقة ] كمثال أنظر الصفحة رقم *** السطر/الأسطر ***[، ولتجاوز هذا الإعتراض، يجب على مقدم الطلب حذف ذلك من الوصف العام للاختراع ونقله إلى خلفية الاختراع."',true,'"•	الوصف العام للاختراع تضمن وصف لحالة التقنية السابقة
-	""الوصف العام للاختراع"" بوضعه الحالي لا يتوافق مع المادة (14) الفقرة (2) من اللائحة والتي تنص على أن ""الوصف العام للاختراع يبين فيه مزايا الاختراع مقارنة بحالة التقنية السابقة وكيفية التغلب على الصعوبات أو المشاكل السابقة كما يبين فيه الهدف من الاختراع, ويكون جميع ذلك بطريقة واضحة بحيث تتيح لصاحب المعرفة العادية بالمجال التقني فهمه, وعادة ما يتعلق هذا الجزء بعنصر الحماية الرئيس"". بسبب أن ما ذكره مقدم الطلب هو وصف لحالة التقنية السابقة ] كمثال أنظر الصفحة رقم *** السطر/الأسطر ***[، ولتجاوز هذا الإعتراض، يجب على مقدم الطلب حذف ذلك من الوصف العام للاختراع ونقله إلى خلفية الاختراع."','"•	الوصف العام للاختراع تضمن وصف لحالة التقنية السابقة
-	""الوصف العام للاختراع"" بوضعه الحالي لا يتوافق مع المادة (14) الفقرة (2) من اللائحة والتي تنص على أن ""الوصف العام للاختراع يبين فيه مزايا الاختراع مقارنة بحالة التقنية السابقة وكيفية التغلب على الصعوبات أو المشاكل السابقة كما يبين فيه الهدف من الاختراع, ويكون جميع ذلك بطريقة واضحة بحيث تتيح لصاحب المعرفة العادية بالمجال التقني فهمه, وعادة ما يتعلق هذا الجزء بعنصر الحماية الرئيس"". بسبب أن ما ذكره مقدم الطلب هو وصف لحالة التقنية السابقة ] كمثال أنظر الصفحة رقم *** السطر/الأسطر ***[، ولتجاوز هذا الإعتراض، يجب على مقدم الطلب حذف ذلك من الوصف العام للاختراع ونقله إلى خلفية الاختراع."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT'
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	الوصف العام للاختراع تضمن التطرق لحالات التقنية السابقة
-	تم التطرق لذكر وثائق التقنية السابقة في الوصف العام للاختراع بدون التطرق إلى أي مقارنة مع حالات التقنية السابقة وهذا لا يتوافق مع المادة (14) الفقرة (2) من اللائحة والتي تنص على أن ""الوصف العام للاختراع يبين فيه مزايا الاختراع مقارنة بحالة التقنية السابقة وكيفية التغلب على الصعوبات أو المشاكل السابقة كما يبين فيه الهدف من الاختراع، ويكون جميع ذلك بطريقة واضحة بحيث تتيح لصاحب المعرفة العادية بالمجال التقني فهمه"". حيث كان من المفترض ذكر تلك الوثائق في خلفية الاختراع، ولتجاوز هذا الإعتراض، يجب على مقدم الطلب تعديل الوصف العام للاختراع ليتوافق مع ما نصت عليه المادة (14) الفقرة (2) من اللائحة مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."','"•	الوصف العام للاختراع تضمن التطرق لحالات التقنية السابقة
-	تم التطرق لذكر وثائق التقنية السابقة في الوصف العام للاختراع بدون التطرق إلى أي مقارنة مع حالات التقنية السابقة وهذا لا يتوافق مع المادة (14) الفقرة (2) من اللائحة والتي تنص على أن ""الوصف العام للاختراع يبين فيه مزايا الاختراع مقارنة بحالة التقنية السابقة وكيفية التغلب على الصعوبات أو المشاكل السابقة كما يبين فيه الهدف من الاختراع، ويكون جميع ذلك بطريقة واضحة بحيث تتيح لصاحب المعرفة العادية بالمجال التقني فهمه"". حيث كان من المفترض ذكر تلك الوثائق في خلفية الاختراع، ولتجاوز هذا الإعتراض، يجب على مقدم الطلب تعديل الوصف العام للاختراع ليتوافق مع ما نصت عليه المادة (14) الفقرة (2) من اللائحة مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."',true,'"•	الوصف العام للاختراع تضمن التطرق لحالات التقنية السابقة
-	تم التطرق لذكر وثائق التقنية السابقة في الوصف العام للاختراع بدون التطرق إلى أي مقارنة مع حالات التقنية السابقة وهذا لا يتوافق مع المادة (14) الفقرة (2) من اللائحة والتي تنص على أن ""الوصف العام للاختراع يبين فيه مزايا الاختراع مقارنة بحالة التقنية السابقة وكيفية التغلب على الصعوبات أو المشاكل السابقة كما يبين فيه الهدف من الاختراع، ويكون جميع ذلك بطريقة واضحة بحيث تتيح لصاحب المعرفة العادية بالمجال التقني فهمه"". حيث كان من المفترض ذكر تلك الوثائق في خلفية الاختراع، ولتجاوز هذا الإعتراض، يجب على مقدم الطلب تعديل الوصف العام للاختراع ليتوافق مع ما نصت عليه المادة (14) الفقرة (2) من اللائحة مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."','"•	الوصف العام للاختراع تضمن التطرق لحالات التقنية السابقة
-	تم التطرق لذكر وثائق التقنية السابقة في الوصف العام للاختراع بدون التطرق إلى أي مقارنة مع حالات التقنية السابقة وهذا لا يتوافق مع المادة (14) الفقرة (2) من اللائحة والتي تنص على أن ""الوصف العام للاختراع يبين فيه مزايا الاختراع مقارنة بحالة التقنية السابقة وكيفية التغلب على الصعوبات أو المشاكل السابقة كما يبين فيه الهدف من الاختراع، ويكون جميع ذلك بطريقة واضحة بحيث تتيح لصاحب المعرفة العادية بالمجال التقني فهمه"". حيث كان من المفترض ذكر تلك الوثائق في خلفية الاختراع، ولتجاوز هذا الإعتراض، يجب على مقدم الطلب تعديل الوصف العام للاختراع ليتوافق مع ما نصت عليه المادة (14) الفقرة (2) من اللائحة مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT'
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	الوصف العام للاختراع تضمن التطرق إلى أجزاء من الوصف التفصيلي
-	""الوصف العام للاختراع"" بوضعه الحالي تطرق لتفاصيل في تخص الوصف التفصيلي وهذا لا يتوافق مع متطلبات المادة (14) الفقرة (2) من اللائحة والتي تنص على أن ""الوصف العام للاختراع يبين فيه مزايا الاختراع مقارنة بحالة التقنية السابقة وكيفية التغلب على الصعوبات أو المشاكل السابقة كما يبين فيه الهدف من الاختراع، ويكون جميع ذلك بطريقة واضحة بحيث تتيح لصاحب المعرفة العادية بالمجال التقني فهمه"". ولتجاوز هذا الإعتراض، يجب على مقدم الطلب تعديل الوصف العام للاختراع مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."','"•	الوصف العام للاختراع تضمن التطرق إلى أجزاء من الوصف التفصيلي
-	""الوصف العام للاختراع"" بوضعه الحالي تطرق لتفاصيل في تخص الوصف التفصيلي وهذا لا يتوافق مع متطلبات المادة (14) الفقرة (2) من اللائحة والتي تنص على أن ""الوصف العام للاختراع يبين فيه مزايا الاختراع مقارنة بحالة التقنية السابقة وكيفية التغلب على الصعوبات أو المشاكل السابقة كما يبين فيه الهدف من الاختراع، ويكون جميع ذلك بطريقة واضحة بحيث تتيح لصاحب المعرفة العادية بالمجال التقني فهمه"". ولتجاوز هذا الإعتراض، يجب على مقدم الطلب تعديل الوصف العام للاختراع مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."',true,'"•	الوصف العام للاختراع تضمن التطرق إلى أجزاء من الوصف التفصيلي
-	""الوصف العام للاختراع"" بوضعه الحالي تطرق لتفاصيل في تخص الوصف التفصيلي وهذا لا يتوافق مع متطلبات المادة (14) الفقرة (2) من اللائحة والتي تنص على أن ""الوصف العام للاختراع يبين فيه مزايا الاختراع مقارنة بحالة التقنية السابقة وكيفية التغلب على الصعوبات أو المشاكل السابقة كما يبين فيه الهدف من الاختراع، ويكون جميع ذلك بطريقة واضحة بحيث تتيح لصاحب المعرفة العادية بالمجال التقني فهمه"". ولتجاوز هذا الإعتراض، يجب على مقدم الطلب تعديل الوصف العام للاختراع مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."','"•	الوصف العام للاختراع تضمن التطرق إلى أجزاء من الوصف التفصيلي
-	""الوصف العام للاختراع"" بوضعه الحالي تطرق لتفاصيل في تخص الوصف التفصيلي وهذا لا يتوافق مع متطلبات المادة (14) الفقرة (2) من اللائحة والتي تنص على أن ""الوصف العام للاختراع يبين فيه مزايا الاختراع مقارنة بحالة التقنية السابقة وكيفية التغلب على الصعوبات أو المشاكل السابقة كما يبين فيه الهدف من الاختراع، ويكون جميع ذلك بطريقة واضحة بحيث تتيح لصاحب المعرفة العادية بالمجال التقني فهمه"". ولتجاوز هذا الإعتراض، يجب على مقدم الطلب تعديل الوصف العام للاختراع مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT'
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	شرح مختصر للرسومات لم يبين شرح مختصر للأشكال وقطاعاتها أو أجزاء منها
-	""شرح مختصر للرسومات"" لا يتوافق مع المادة (14) الفقرة (3) من اللائحة والتي تنص على أن "" الشرح المختصر للرسومات يبين فيه شرح مختصر للأشكال وقطاعاتها إن وجدت"". بسبب أن ما ذكر من قبل مقدم الطلب في الشرح المختصر للرسومات لا يعد شرحاً مختصراً للأشكال أو قطاعاتها. ولتجاوز هذا الاعتراض، يجب على مقدم الطلب التعديل مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي.
-	""شرح مختصر للرسومات"" لا يتوافق مع المادة (14) الفقرة (3) من اللائحة والتي تنص على أن "" الشرح المختصر للرسومات يبين فيه شرح مختصر للأشكال وقطاعاتها إن وجدت"". بسبب أن ما ذكر من قبل مقدم الطلب في شرح مختصر للرسومات لم يتضمن شرح الأشكال [كمثال الشكل رقم **]، لذلك يجب على مقدم الطلب شرح جميع الأشكال الموجودة في الطلب كما نصت عليه المادة (14) الفقرة (3) من اللائحة التنفيذية للنظام."','"•	شرح مختصر للرسومات لم يبين شرح مختصر للأشكال وقطاعاتها أو أجزاء منها
-	""شرح مختصر للرسومات"" لا يتوافق مع المادة (14) الفقرة (3) من اللائحة والتي تنص على أن "" الشرح المختصر للرسومات يبين فيه شرح مختصر للأشكال وقطاعاتها إن وجدت"". بسبب أن ما ذكر من قبل مقدم الطلب في الشرح المختصر للرسومات لا يعد شرحاً مختصراً للأشكال أو قطاعاتها. ولتجاوز هذا الاعتراض، يجب على مقدم الطلب التعديل مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي.
-	""شرح مختصر للرسومات"" لا يتوافق مع المادة (14) الفقرة (3) من اللائحة والتي تنص على أن "" الشرح المختصر للرسومات يبين فيه شرح مختصر للأشكال وقطاعاتها إن وجدت"". بسبب أن ما ذكر من قبل مقدم الطلب في شرح مختصر للرسومات لم يتضمن شرح الأشكال [كمثال الشكل رقم **]، لذلك يجب على مقدم الطلب شرح جميع الأشكال الموجودة في الطلب كما نصت عليه المادة (14) الفقرة (3) من اللائحة التنفيذية للنظام."',true,'"•	شرح مختصر للرسومات لم يبين شرح مختصر للأشكال وقطاعاتها أو أجزاء منها
-	""شرح مختصر للرسومات"" لا يتوافق مع المادة (14) الفقرة (3) من اللائحة والتي تنص على أن "" الشرح المختصر للرسومات يبين فيه شرح مختصر للأشكال وقطاعاتها إن وجدت"". بسبب أن ما ذكر من قبل مقدم الطلب في الشرح المختصر للرسومات لا يعد شرحاً مختصراً للأشكال أو قطاعاتها. ولتجاوز هذا الاعتراض، يجب على مقدم الطلب التعديل مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي.
-	""شرح مختصر للرسومات"" لا يتوافق مع المادة (14) الفقرة (3) من اللائحة والتي تنص على أن "" الشرح المختصر للرسومات يبين فيه شرح مختصر للأشكال وقطاعاتها إن وجدت"". بسبب أن ما ذكر من قبل مقدم الطلب في شرح مختصر للرسومات لم يتضمن شرح الأشكال [كمثال الشكل رقم **]، لذلك يجب على مقدم الطلب شرح جميع الأشكال الموجودة في الطلب كما نصت عليه المادة (14) الفقرة (3) من اللائحة التنفيذية للنظام."','"•	شرح مختصر للرسومات لم يبين شرح مختصر للأشكال وقطاعاتها أو أجزاء منها
-	""شرح مختصر للرسومات"" لا يتوافق مع المادة (14) الفقرة (3) من اللائحة والتي تنص على أن "" الشرح المختصر للرسومات يبين فيه شرح مختصر للأشكال وقطاعاتها إن وجدت"". بسبب أن ما ذكر من قبل مقدم الطلب في الشرح المختصر للرسومات لا يعد شرحاً مختصراً للأشكال أو قطاعاتها. ولتجاوز هذا الاعتراض، يجب على مقدم الطلب التعديل مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي.
-	""شرح مختصر للرسومات"" لا يتوافق مع المادة (14) الفقرة (3) من اللائحة والتي تنص على أن "" الشرح المختصر للرسومات يبين فيه شرح مختصر للأشكال وقطاعاتها إن وجدت"". بسبب أن ما ذكر من قبل مقدم الطلب في شرح مختصر للرسومات لم يتضمن شرح الأشكال [كمثال الشكل رقم **]، لذلك يجب على مقدم الطلب شرح جميع الأشكال الموجودة في الطلب كما نصت عليه المادة (14) الفقرة (3) من اللائحة التنفيذية للنظام."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT'
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	شرح مختصر للرسومات لا يتطابق مع أجزاء الأشكال المرفقة
-	شرح مختصر للرسومات لا يتطابق مع أجزاء الأشكال المرفقة، وهذا لا يتوافق مع المادة (14) الفقرة (3) من اللائحة والتي تنص على أن "" يجب أن يذكر في شرح مختصر للسومات شرحا مختصرا للأشكال وقطاعاتها إن وجدت"". حيث يلاحظ عندما تطرق مقدم الطلب لوصف أجزاء الشكل المرفق (1) تم الإشارة إلى تلك الأجزاء في الشكل (1) بأحرف مختلفة عما تم ذكره في الشرح ] كمثال أنظر الصفحة رقم ** السطر/الأسطر **[، لذلك ولتجاوز هذا الإعتراض، يجب على مقدم الطلب التعديل وفقا لما نصت عليه المادة (14) الفقرة (3) من اللائحة التنفيذية لنظام براءات الاختراع."','"•	شرح مختصر للرسومات لا يتطابق مع أجزاء الأشكال المرفقة
-	شرح مختصر للرسومات لا يتطابق مع أجزاء الأشكال المرفقة، وهذا لا يتوافق مع المادة (14) الفقرة (3) من اللائحة والتي تنص على أن "" يجب أن يذكر في شرح مختصر للسومات شرحا مختصرا للأشكال وقطاعاتها إن وجدت"". حيث يلاحظ عندما تطرق مقدم الطلب لوصف أجزاء الشكل المرفق (1) تم الإشارة إلى تلك الأجزاء في الشكل (1) بأحرف مختلفة عما تم ذكره في الشرح ] كمثال أنظر الصفحة رقم ** السطر/الأسطر **[، لذلك ولتجاوز هذا الإعتراض، يجب على مقدم الطلب التعديل وفقا لما نصت عليه المادة (14) الفقرة (3) من اللائحة التنفيذية لنظام براءات الاختراع."',true,'"•	شرح مختصر للرسومات لا يتطابق مع أجزاء الأشكال المرفقة
-	شرح مختصر للرسومات لا يتطابق مع أجزاء الأشكال المرفقة، وهذا لا يتوافق مع المادة (14) الفقرة (3) من اللائحة والتي تنص على أن "" يجب أن يذكر في شرح مختصر للسومات شرحا مختصرا للأشكال وقطاعاتها إن وجدت"". حيث يلاحظ عندما تطرق مقدم الطلب لوصف أجزاء الشكل المرفق (1) تم الإشارة إلى تلك الأجزاء في الشكل (1) بأحرف مختلفة عما تم ذكره في الشرح ] كمثال أنظر الصفحة رقم ** السطر/الأسطر **[، لذلك ولتجاوز هذا الإعتراض، يجب على مقدم الطلب التعديل وفقا لما نصت عليه المادة (14) الفقرة (3) من اللائحة التنفيذية لنظام براءات الاختراع."','"•	شرح مختصر للرسومات لا يتطابق مع أجزاء الأشكال المرفقة
-	شرح مختصر للرسومات لا يتطابق مع أجزاء الأشكال المرفقة، وهذا لا يتوافق مع المادة (14) الفقرة (3) من اللائحة والتي تنص على أن "" يجب أن يذكر في شرح مختصر للسومات شرحا مختصرا للأشكال وقطاعاتها إن وجدت"". حيث يلاحظ عندما تطرق مقدم الطلب لوصف أجزاء الشكل المرفق (1) تم الإشارة إلى تلك الأجزاء في الشكل (1) بأحرف مختلفة عما تم ذكره في الشرح ] كمثال أنظر الصفحة رقم ** السطر/الأسطر **[، لذلك ولتجاوز هذا الإعتراض، يجب على مقدم الطلب التعديل وفقا لما نصت عليه المادة (14) الفقرة (3) من اللائحة التنفيذية لنظام براءات الاختراع."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT'
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	شرح مختصر للرسومات تضمن وصف تفصيلي للأشكال
-	""شرح مختصر للرسومات"" لا يتوافق مع المادة (14) الفقرة (3) من اللائحة والتي تنص على أن "" يجب أن يذكر في شرح مختصر للسومات شرحا مختصرا للأشكال وقطاعاتها إن وجدت""، حيث تضمن شرح أجزاء الأشكال(***) شرحًا مفصّلاً، حيث يجب تضمين هذا الشرح المفصّل في الوصف التفصيلي والإكتفاء بالتطرق لشرح مختصر للأشكال وقطاعاتها، ولتجاوز هذا الإعتراض، يجب على مقدم الطلب التعديل وفقا لما نصت عليه المادة (14) الفقرة (3) من اللائحة التنفيذية لنظام براءات الاختراع."','"•	شرح مختصر للرسومات تضمن وصف تفصيلي للأشكال
-	""شرح مختصر للرسومات"" لا يتوافق مع المادة (14) الفقرة (3) من اللائحة والتي تنص على أن "" يجب أن يذكر في شرح مختصر للسومات شرحا مختصرا للأشكال وقطاعاتها إن وجدت""، حيث تضمن شرح أجزاء الأشكال(***) شرحًا مفصّلاً، حيث يجب تضمين هذا الشرح المفصّل في الوصف التفصيلي والإكتفاء بالتطرق لشرح مختصر للأشكال وقطاعاتها، ولتجاوز هذا الإعتراض، يجب على مقدم الطلب التعديل وفقا لما نصت عليه المادة (14) الفقرة (3) من اللائحة التنفيذية لنظام براءات الاختراع."',true,'"•	شرح مختصر للرسومات تضمن وصف تفصيلي للأشكال
-	""شرح مختصر للرسومات"" لا يتوافق مع المادة (14) الفقرة (3) من اللائحة والتي تنص على أن "" يجب أن يذكر في شرح مختصر للسومات شرحا مختصرا للأشكال وقطاعاتها إن وجدت""، حيث تضمن شرح أجزاء الأشكال(***) شرحًا مفصّلاً، حيث يجب تضمين هذا الشرح المفصّل في الوصف التفصيلي والإكتفاء بالتطرق لشرح مختصر للأشكال وقطاعاتها، ولتجاوز هذا الإعتراض، يجب على مقدم الطلب التعديل وفقا لما نصت عليه المادة (14) الفقرة (3) من اللائحة التنفيذية لنظام براءات الاختراع."','"•	شرح مختصر للرسومات تضمن وصف تفصيلي للأشكال
-	""شرح مختصر للرسومات"" لا يتوافق مع المادة (14) الفقرة (3) من اللائحة والتي تنص على أن "" يجب أن يذكر في شرح مختصر للسومات شرحا مختصرا للأشكال وقطاعاتها إن وجدت""، حيث تضمن شرح أجزاء الأشكال(***) شرحًا مفصّلاً، حيث يجب تضمين هذا الشرح المفصّل في الوصف التفصيلي والإكتفاء بالتطرق لشرح مختصر للأشكال وقطاعاتها، ولتجاوز هذا الإعتراض، يجب على مقدم الطلب التعديل وفقا لما نصت عليه المادة (14) الفقرة (3) من اللائحة التنفيذية لنظام براءات الاختراع."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT'
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	الوصف التفصيلي غير واضح كونه لم يتضمن أي أمثلة تبين طريقة عمل الاختراع أو نتائج تجريبية تظهر على الأقل مقدرة رجل المهنة العادي على تنفيذ الاختراع
-	""الوصف التفصيلي"" لم يتطرق لأي أمثلة تبين طريقة عمل الاختراع أو نتائج تجريبية تظهر على الأقل، مقدرة رجل المهنة العادي على تنفيذ الاختراع كونه من المعروف في مجال التقنية بأن الموجه السماوية يتم استخدامها غالبا في
 نطاقات تردد الموجة القصيرة. ومن المعروف عن الموجات القصيرة بأن مداها كبير جدا وكذلك عرض النطاق الترددي للموجات القصيرة ليس كبيرا، وهو بالضبط عكس ما يريد نظام الهاتف المحمول وهو تقسيم المدينة إلى خلايا صغيرة مما يسمح بإعادة استخدام الترددات واسعة النطاق في جميع أنحاء المدينة لكي يمكن الملايين من الناس من استخدام الهواتف المحمولة في وقت واحد. إضافة إلى أن الهوائي والمكونات تكون كبيرة. عليه، فإن الوصف التفصيلي لم يستوف المتطلبات الواردة في المادة (14) الفقرة (4) من اللائحة التنفيذية للنظام والتي تنص على أن ""يجب إن يكون الوصف واضحاً وكافياً لتمكين رجل المهنة العادي من تنفيذ الاختراع وان يتضمن شرحا تفصيليا للاختراع كما يجب أن يبين مقدم الطلب أفضل طريقة يعرفها المخترع لتنفيذ الاختراع"". ولتجاوز هذا الاعتراض، يجب على مقدم الطلب التعديل مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."','"•	الوصف التفصيلي غير واضح كونه لم يتضمن أي أمثلة تبين طريقة عمل الاختراع أو نتائج تجريبية تظهر على الأقل مقدرة رجل المهنة العادي على تنفيذ الاختراع
-	""الوصف التفصيلي"" لم يتطرق لأي أمثلة تبين طريقة عمل الاختراع أو نتائج تجريبية تظهر على الأقل، مقدرة رجل المهنة العادي على تنفيذ الاختراع كونه من المعروف في مجال التقنية بأن الموجه السماوية يتم استخدامها غالبا في
 نطاقات تردد الموجة القصيرة. ومن المعروف عن الموجات القصيرة بأن مداها كبير جدا وكذلك عرض النطاق الترددي للموجات القصيرة ليس كبيرا، وهو بالضبط عكس ما يريد نظام الهاتف المحمول وهو تقسيم المدينة إلى خلايا صغيرة مما يسمح بإعادة استخدام الترددات واسعة النطاق في جميع أنحاء المدينة لكي يمكن الملايين من الناس من استخدام الهواتف المحمولة في وقت واحد. إضافة إلى أن الهوائي والمكونات تكون كبيرة. عليه، فإن الوصف التفصيلي لم يستوف المتطلبات الواردة في المادة (14) الفقرة (4) من اللائحة التنفيذية للنظام والتي تنص على أن ""يجب إن يكون الوصف واضحاً وكافياً لتمكين رجل المهنة العادي من تنفيذ الاختراع وان يتضمن شرحا تفصيليا للاختراع كما يجب أن يبين مقدم الطلب أفضل طريقة يعرفها المخترع لتنفيذ الاختراع"". ولتجاوز هذا الاعتراض، يجب على مقدم الطلب التعديل مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."',true,'"•	الوصف التفصيلي غير واضح كونه لم يتضمن أي أمثلة تبين طريقة عمل الاختراع أو نتائج تجريبية تظهر على الأقل مقدرة رجل المهنة العادي على تنفيذ الاختراع
-	""الوصف التفصيلي"" لم يتطرق لأي أمثلة تبين طريقة عمل الاختراع أو نتائج تجريبية تظهر على الأقل، مقدرة رجل المهنة العادي على تنفيذ الاختراع كونه من المعروف في مجال التقنية بأن الموجه السماوية يتم استخدامها غالبا في
 نطاقات تردد الموجة القصيرة. ومن المعروف عن الموجات القصيرة بأن مداها كبير جدا وكذلك عرض النطاق الترددي للموجات القصيرة ليس كبيرا، وهو بالضبط عكس ما يريد نظام الهاتف المحمول وهو تقسيم المدينة إلى خلايا صغيرة مما يسمح بإعادة استخدام الترددات واسعة النطاق في جميع أنحاء المدينة لكي يمكن الملايين من الناس من استخدام الهواتف المحمولة في وقت واحد. إضافة إلى أن الهوائي والمكونات تكون كبيرة. عليه، فإن الوصف التفصيلي لم يستوف المتطلبات الواردة في المادة (14) الفقرة (4) من اللائحة التنفيذية للنظام والتي تنص على أن ""يجب إن يكون الوصف واضحاً وكافياً لتمكين رجل المهنة العادي من تنفيذ الاختراع وان يتضمن شرحا تفصيليا للاختراع كما يجب أن يبين مقدم الطلب أفضل طريقة يعرفها المخترع لتنفيذ الاختراع"". ولتجاوز هذا الاعتراض، يجب على مقدم الطلب التعديل مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."','"•	الوصف التفصيلي غير واضح كونه لم يتضمن أي أمثلة تبين طريقة عمل الاختراع أو نتائج تجريبية تظهر على الأقل مقدرة رجل المهنة العادي على تنفيذ الاختراع
-	""الوصف التفصيلي"" لم يتطرق لأي أمثلة تبين طريقة عمل الاختراع أو نتائج تجريبية تظهر على الأقل، مقدرة رجل المهنة العادي على تنفيذ الاختراع كونه من المعروف في مجال التقنية بأن الموجه السماوية يتم استخدامها غالبا في
 نطاقات تردد الموجة القصيرة. ومن المعروف عن الموجات القصيرة بأن مداها كبير جدا وكذلك عرض النطاق الترددي للموجات القصيرة ليس كبيرا، وهو بالضبط عكس ما يريد نظام الهاتف المحمول وهو تقسيم المدينة إلى خلايا صغيرة مما يسمح بإعادة استخدام الترددات واسعة النطاق في جميع أنحاء المدينة لكي يمكن الملايين من الناس من استخدام الهواتف المحمولة في وقت واحد. إضافة إلى أن الهوائي والمكونات تكون كبيرة. عليه، فإن الوصف التفصيلي لم يستوف المتطلبات الواردة في المادة (14) الفقرة (4) من اللائحة التنفيذية للنظام والتي تنص على أن ""يجب إن يكون الوصف واضحاً وكافياً لتمكين رجل المهنة العادي من تنفيذ الاختراع وان يتضمن شرحا تفصيليا للاختراع كما يجب أن يبين مقدم الطلب أفضل طريقة يعرفها المخترع لتنفيذ الاختراع"". ولتجاوز هذا الاعتراض، يجب على مقدم الطلب التعديل مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT'
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	الوصف التفصيلي غير واضح كونه لم يبين المكونات الهيكلية للجهاز وآلية الربط بين تلك المكونات بطرق علمية تؤدي في النهاية إلى عمل الجهاز بالشكل الصحيح
-	""الوصف التفصيلي"" لم يستوف المتطلبات الواردة في المادة (14) الفقرة (4) من اللائحة التنفيذية للنظام والتي تنص على أن ""يجب إن يكون الوصف واضحاً وكافياً لتمكين رجل المهنة العادي من تنفيذ الاختراع، وأن يتضمن شرحا تفصيليا للاختراع كما يجب أن يبين مقدم الطلب أفضل طريقة يعرفها المخترع لتنفيذ الاختراع"". حيث أن ما ذكر عبارة شرح فكرة عمل الجهاز المتوقعة نظرياً والتي تعتبر غير واضحة، كونه لم يبين المكونات الهيكلية للجهاز والية الربط بين تلك المكونات بطريقة تؤدي في النهاية إلى عمل الجهاز بالشكل الصحيح ] كمثال: لم يبين كيفية تدوير الكرات المعدنية لتشغيل مراوح التوربين[."','"•	الوصف التفصيلي غير واضح كونه لم يبين المكونات الهيكلية للجهاز وآلية الربط بين تلك المكونات بطرق علمية تؤدي في النهاية إلى عمل الجهاز بالشكل الصحيح
-	""الوصف التفصيلي"" لم يستوف المتطلبات الواردة في المادة (14) الفقرة (4) من اللائحة التنفيذية للنظام والتي تنص على أن ""يجب إن يكون الوصف واضحاً وكافياً لتمكين رجل المهنة العادي من تنفيذ الاختراع، وأن يتضمن شرحا تفصيليا للاختراع كما يجب أن يبين مقدم الطلب أفضل طريقة يعرفها المخترع لتنفيذ الاختراع"". حيث أن ما ذكر عبارة شرح فكرة عمل الجهاز المتوقعة نظرياً والتي تعتبر غير واضحة، كونه لم يبين المكونات الهيكلية للجهاز والية الربط بين تلك المكونات بطريقة تؤدي في النهاية إلى عمل الجهاز بالشكل الصحيح ] كمثال: لم يبين كيفية تدوير الكرات المعدنية لتشغيل مراوح التوربين[."',true,'"•	الوصف التفصيلي غير واضح كونه لم يبين المكونات الهيكلية للجهاز وآلية الربط بين تلك المكونات بطرق علمية تؤدي في النهاية إلى عمل الجهاز بالشكل الصحيح
-	""الوصف التفصيلي"" لم يستوف المتطلبات الواردة في المادة (14) الفقرة (4) من اللائحة التنفيذية للنظام والتي تنص على أن ""يجب إن يكون الوصف واضحاً وكافياً لتمكين رجل المهنة العادي من تنفيذ الاختراع، وأن يتضمن شرحا تفصيليا للاختراع كما يجب أن يبين مقدم الطلب أفضل طريقة يعرفها المخترع لتنفيذ الاختراع"". حيث أن ما ذكر عبارة شرح فكرة عمل الجهاز المتوقعة نظرياً والتي تعتبر غير واضحة، كونه لم يبين المكونات الهيكلية للجهاز والية الربط بين تلك المكونات بطريقة تؤدي في النهاية إلى عمل الجهاز بالشكل الصحيح ] كمثال: لم يبين كيفية تدوير الكرات المعدنية لتشغيل مراوح التوربين[."','"•	الوصف التفصيلي غير واضح كونه لم يبين المكونات الهيكلية للجهاز وآلية الربط بين تلك المكونات بطرق علمية تؤدي في النهاية إلى عمل الجهاز بالشكل الصحيح
-	""الوصف التفصيلي"" لم يستوف المتطلبات الواردة في المادة (14) الفقرة (4) من اللائحة التنفيذية للنظام والتي تنص على أن ""يجب إن يكون الوصف واضحاً وكافياً لتمكين رجل المهنة العادي من تنفيذ الاختراع، وأن يتضمن شرحا تفصيليا للاختراع كما يجب أن يبين مقدم الطلب أفضل طريقة يعرفها المخترع لتنفيذ الاختراع"". حيث أن ما ذكر عبارة شرح فكرة عمل الجهاز المتوقعة نظرياً والتي تعتبر غير واضحة، كونه لم يبين المكونات الهيكلية للجهاز والية الربط بين تلك المكونات بطريقة تؤدي في النهاية إلى عمل الجهاز بالشكل الصحيح ] كمثال: لم يبين كيفية تدوير الكرات المعدنية لتشغيل مراوح التوربين[."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT'
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	شرح الوصف التفصيلي لم يتضمن الإشارة إلى الرسومات التوضيحية بالتفصيل
-	""الوصف التفصيلي"" لا يتوافق مع المادة (14) الفقرة (4) من اللائحة والتي تنص على أن ""الوصف التفصيلي يجب أن يكون الوصف واضحاً وكافياً لتمكين رجل المهنة العادي من تنفيذ الاختراع، وأن يتضمن شرحاً تفصيلياً للاختراع كما يجب أن يبين مقدم الطلب أفضل طريقة يعرفها المخترع لتنفيذ الاختراع عند تاريخ تقديم الطلب أو تاريخ الأسبقية. ويجب أن يتضمن الشرح الإشارة إلى الرسومات التوضيحية المرفقة بالتفصيل"". بسبب أن ما ذكره مقدم الطلب في الوصف التفصيلي، لا يتضمن الإشارة إلى الشكل/الأشكال***، لذلك يجب على مقدم الطلب تعديل الوصف التفصيلي ليتوافق مع ما نصت عليه المادة (14) الفقرة (4) من اللائحة مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."','"•	شرح الوصف التفصيلي لم يتضمن الإشارة إلى الرسومات التوضيحية بالتفصيل
-	""الوصف التفصيلي"" لا يتوافق مع المادة (14) الفقرة (4) من اللائحة والتي تنص على أن ""الوصف التفصيلي يجب أن يكون الوصف واضحاً وكافياً لتمكين رجل المهنة العادي من تنفيذ الاختراع، وأن يتضمن شرحاً تفصيلياً للاختراع كما يجب أن يبين مقدم الطلب أفضل طريقة يعرفها المخترع لتنفيذ الاختراع عند تاريخ تقديم الطلب أو تاريخ الأسبقية. ويجب أن يتضمن الشرح الإشارة إلى الرسومات التوضيحية المرفقة بالتفصيل"". بسبب أن ما ذكره مقدم الطلب في الوصف التفصيلي، لا يتضمن الإشارة إلى الشكل/الأشكال***، لذلك يجب على مقدم الطلب تعديل الوصف التفصيلي ليتوافق مع ما نصت عليه المادة (14) الفقرة (4) من اللائحة مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."',true,'"•	شرح الوصف التفصيلي لم يتضمن الإشارة إلى الرسومات التوضيحية بالتفصيل
-	""الوصف التفصيلي"" لا يتوافق مع المادة (14) الفقرة (4) من اللائحة والتي تنص على أن ""الوصف التفصيلي يجب أن يكون الوصف واضحاً وكافياً لتمكين رجل المهنة العادي من تنفيذ الاختراع، وأن يتضمن شرحاً تفصيلياً للاختراع كما يجب أن يبين مقدم الطلب أفضل طريقة يعرفها المخترع لتنفيذ الاختراع عند تاريخ تقديم الطلب أو تاريخ الأسبقية. ويجب أن يتضمن الشرح الإشارة إلى الرسومات التوضيحية المرفقة بالتفصيل"". بسبب أن ما ذكره مقدم الطلب في الوصف التفصيلي، لا يتضمن الإشارة إلى الشكل/الأشكال***، لذلك يجب على مقدم الطلب تعديل الوصف التفصيلي ليتوافق مع ما نصت عليه المادة (14) الفقرة (4) من اللائحة مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."','"•	شرح الوصف التفصيلي لم يتضمن الإشارة إلى الرسومات التوضيحية بالتفصيل
-	""الوصف التفصيلي"" لا يتوافق مع المادة (14) الفقرة (4) من اللائحة والتي تنص على أن ""الوصف التفصيلي يجب أن يكون الوصف واضحاً وكافياً لتمكين رجل المهنة العادي من تنفيذ الاختراع، وأن يتضمن شرحاً تفصيلياً للاختراع كما يجب أن يبين مقدم الطلب أفضل طريقة يعرفها المخترع لتنفيذ الاختراع عند تاريخ تقديم الطلب أو تاريخ الأسبقية. ويجب أن يتضمن الشرح الإشارة إلى الرسومات التوضيحية المرفقة بالتفصيل"". بسبب أن ما ذكره مقدم الطلب في الوصف التفصيلي، لا يتضمن الإشارة إلى الشكل/الأشكال***، لذلك يجب على مقدم الطلب تعديل الوصف التفصيلي ليتوافق مع ما نصت عليه المادة (14) الفقرة (4) من اللائحة مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT'
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"
•	شرح الوصف التفصيلي لم يتضمن الإشارة إلى الرسومات التوضيحية بالتفصيل بذكر أجزء /مكونات شكل أو الأشكال بشكل مكتمل ومترابط
-	""الوصف التفصيلي"" لا يتوافق مع المادة (14) الفقرة (4) من اللائحة والتي تنص على أن ""الوصف التفصيلي يجب أن يكون الوصف واضحاً وكافياً لتمكين رجل المهنة العادي من تنفيذ الاختراع، وأن يتضمن شرحاً تفصيلياً للاختراع كما يجب أن يبين مقدم الطلب أفضل طريقة يعرفها المخترع لتنفيذ الاختراع عند تاريخ تقديم الطلب أو تاريخ الأسبقية. ويجب أن يتضمن الشرح الإشارة إلى الرسومات التوضيحية المرفقة بالتفصيل"". بسبب أن ما ذكره مقدم الطلب في الوصف التفصيلي، لا يتضمن الإشارة إلى الرسومات التوضيحية المرفقة بالتفصيل بذكر المكونات لكل شكل من الأشكال بشكل مكتمل ومترابط. لذلك يجب على مقدم الطلب تعديل الوصف التفصيلي ليتوافق مع ما نصت عليه المادة (14) الفقرة (4) من اللائحة مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."','"
•	شرح الوصف التفصيلي لم يتضمن الإشارة إلى الرسومات التوضيحية بالتفصيل بذكر أجزء /مكونات شكل أو الأشكال بشكل مكتمل ومترابط
-	""الوصف التفصيلي"" لا يتوافق مع المادة (14) الفقرة (4) من اللائحة والتي تنص على أن ""الوصف التفصيلي يجب أن يكون الوصف واضحاً وكافياً لتمكين رجل المهنة العادي من تنفيذ الاختراع، وأن يتضمن شرحاً تفصيلياً للاختراع كما يجب أن يبين مقدم الطلب أفضل طريقة يعرفها المخترع لتنفيذ الاختراع عند تاريخ تقديم الطلب أو تاريخ الأسبقية. ويجب أن يتضمن الشرح الإشارة إلى الرسومات التوضيحية المرفقة بالتفصيل"". بسبب أن ما ذكره مقدم الطلب في الوصف التفصيلي، لا يتضمن الإشارة إلى الرسومات التوضيحية المرفقة بالتفصيل بذكر المكونات لكل شكل من الأشكال بشكل مكتمل ومترابط. لذلك يجب على مقدم الطلب تعديل الوصف التفصيلي ليتوافق مع ما نصت عليه المادة (14) الفقرة (4) من اللائحة مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."',true,'"
•	شرح الوصف التفصيلي لم يتضمن الإشارة إلى الرسومات التوضيحية بالتفصيل بذكر أجزء /مكونات شكل أو الأشكال بشكل مكتمل ومترابط
-	""الوصف التفصيلي"" لا يتوافق مع المادة (14) الفقرة (4) من اللائحة والتي تنص على أن ""الوصف التفصيلي يجب أن يكون الوصف واضحاً وكافياً لتمكين رجل المهنة العادي من تنفيذ الاختراع، وأن يتضمن شرحاً تفصيلياً للاختراع كما يجب أن يبين مقدم الطلب أفضل طريقة يعرفها المخترع لتنفيذ الاختراع عند تاريخ تقديم الطلب أو تاريخ الأسبقية. ويجب أن يتضمن الشرح الإشارة إلى الرسومات التوضيحية المرفقة بالتفصيل"". بسبب أن ما ذكره مقدم الطلب في الوصف التفصيلي، لا يتضمن الإشارة إلى الرسومات التوضيحية المرفقة بالتفصيل بذكر المكونات لكل شكل من الأشكال بشكل مكتمل ومترابط. لذلك يجب على مقدم الطلب تعديل الوصف التفصيلي ليتوافق مع ما نصت عليه المادة (14) الفقرة (4) من اللائحة مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."','"
•	شرح الوصف التفصيلي لم يتضمن الإشارة إلى الرسومات التوضيحية بالتفصيل بذكر أجزء /مكونات شكل أو الأشكال بشكل مكتمل ومترابط
-	""الوصف التفصيلي"" لا يتوافق مع المادة (14) الفقرة (4) من اللائحة والتي تنص على أن ""الوصف التفصيلي يجب أن يكون الوصف واضحاً وكافياً لتمكين رجل المهنة العادي من تنفيذ الاختراع، وأن يتضمن شرحاً تفصيلياً للاختراع كما يجب أن يبين مقدم الطلب أفضل طريقة يعرفها المخترع لتنفيذ الاختراع عند تاريخ تقديم الطلب أو تاريخ الأسبقية. ويجب أن يتضمن الشرح الإشارة إلى الرسومات التوضيحية المرفقة بالتفصيل"". بسبب أن ما ذكره مقدم الطلب في الوصف التفصيلي، لا يتضمن الإشارة إلى الرسومات التوضيحية المرفقة بالتفصيل بذكر المكونات لكل شكل من الأشكال بشكل مكتمل ومترابط. لذلك يجب على مقدم الطلب تعديل الوصف التفصيلي ليتوافق مع ما نصت عليه المادة (14) الفقرة (4) من اللائحة مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT'
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	الملخص/الوصف الكامل/ تضمنت رسومات توضيحية
-	المواصفة الحالية تتعارض مع المادة (11) الفقرة (3) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يجوز أن يحتوي الملخص والوصف الكامل وعناصر الحماية على أسماء ورموز وصيغ ومعادلات رياضية وكيميائية ومصطلحات علمية وغيرها بالحروف اللاتينية. ولا يجوز أن يحتوي أي منها على رسومات توضيحية، أما الجداول فتوضع ضمن الوصف الكامل للمواصفة إن وجدت"". كون الوصف الكامل تضمن رسومات توضيحية ] كمثال: أنظر الصفحة رقم ** السطر/الأسطر **[، ولتجاوز هذا الاعتراض، يجب على مقدم الطلب تعديل الوصف الكامل ليتوافق مع ما نصت عليه المادة (11) الفقرة (3) من اللائحة مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."','"•	الملخص/الوصف الكامل/ تضمنت رسومات توضيحية
-	المواصفة الحالية تتعارض مع المادة (11) الفقرة (3) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يجوز أن يحتوي الملخص والوصف الكامل وعناصر الحماية على أسماء ورموز وصيغ ومعادلات رياضية وكيميائية ومصطلحات علمية وغيرها بالحروف اللاتينية. ولا يجوز أن يحتوي أي منها على رسومات توضيحية، أما الجداول فتوضع ضمن الوصف الكامل للمواصفة إن وجدت"". كون الوصف الكامل تضمن رسومات توضيحية ] كمثال: أنظر الصفحة رقم ** السطر/الأسطر **[، ولتجاوز هذا الاعتراض، يجب على مقدم الطلب تعديل الوصف الكامل ليتوافق مع ما نصت عليه المادة (11) الفقرة (3) من اللائحة مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."',true,'"•	الملخص/الوصف الكامل/ تضمنت رسومات توضيحية
-	المواصفة الحالية تتعارض مع المادة (11) الفقرة (3) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يجوز أن يحتوي الملخص والوصف الكامل وعناصر الحماية على أسماء ورموز وصيغ ومعادلات رياضية وكيميائية ومصطلحات علمية وغيرها بالحروف اللاتينية. ولا يجوز أن يحتوي أي منها على رسومات توضيحية، أما الجداول فتوضع ضمن الوصف الكامل للمواصفة إن وجدت"". كون الوصف الكامل تضمن رسومات توضيحية ] كمثال: أنظر الصفحة رقم ** السطر/الأسطر **[، ولتجاوز هذا الاعتراض، يجب على مقدم الطلب تعديل الوصف الكامل ليتوافق مع ما نصت عليه المادة (11) الفقرة (3) من اللائحة مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."','"•	الملخص/الوصف الكامل/ تضمنت رسومات توضيحية
-	المواصفة الحالية تتعارض مع المادة (11) الفقرة (3) من اللائحة التنفيذية للنظام والتي تنص على ما يلي: ""يجوز أن يحتوي الملخص والوصف الكامل وعناصر الحماية على أسماء ورموز وصيغ ومعادلات رياضية وكيميائية ومصطلحات علمية وغيرها بالحروف اللاتينية. ولا يجوز أن يحتوي أي منها على رسومات توضيحية، أما الجداول فتوضع ضمن الوصف الكامل للمواصفة إن وجدت"". كون الوصف الكامل تضمن رسومات توضيحية ] كمثال: أنظر الصفحة رقم ** السطر/الأسطر **[، ولتجاوز هذا الاعتراض، يجب على مقدم الطلب تعديل الوصف الكامل ليتوافق مع ما نصت عليه المادة (11) الفقرة (3) من اللائحة مع مراعاة ألا تتجاوز هذه التعديلات ما كشف عنه في الطلب الأصلي."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT'
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	الإشارة لــجزء/ أجزاء من الأشكال بطريقة غير صحيحة
-	""الوصف التفصيلي"" تضمن الإشارة إلى الأجزاء الداخلية للجهاز ولكنها لم تشير إلى الجزء الصحيح ومنها على سبيل المثال:[ المبادل الحراري الخارجي رقم (6) والصحيح أن المبادل الحراري الخارجي رقم (4)] أنظر الصفحة رقم (**) السطر رقم (**)، ولتجاوز هذا الاعتراض، يجب على مقدم الطلب تصحيح ذلك."','"•	الإشارة لــجزء/ أجزاء من الأشكال بطريقة غير صحيحة
-	""الوصف التفصيلي"" تضمن الإشارة إلى الأجزاء الداخلية للجهاز ولكنها لم تشير إلى الجزء الصحيح ومنها على سبيل المثال:[ المبادل الحراري الخارجي رقم (6) والصحيح أن المبادل الحراري الخارجي رقم (4)] أنظر الصفحة رقم (**) السطر رقم (**)، ولتجاوز هذا الاعتراض، يجب على مقدم الطلب تصحيح ذلك."',true,'"•	الإشارة لــجزء/ أجزاء من الأشكال بطريقة غير صحيحة
-	""الوصف التفصيلي"" تضمن الإشارة إلى الأجزاء الداخلية للجهاز ولكنها لم تشير إلى الجزء الصحيح ومنها على سبيل المثال:[ المبادل الحراري الخارجي رقم (6) والصحيح أن المبادل الحراري الخارجي رقم (4)] أنظر الصفحة رقم (**) السطر رقم (**)، ولتجاوز هذا الاعتراض، يجب على مقدم الطلب تصحيح ذلك."','"•	الإشارة لــجزء/ أجزاء من الأشكال بطريقة غير صحيحة
-	""الوصف التفصيلي"" تضمن الإشارة إلى الأجزاء الداخلية للجهاز ولكنها لم تشير إلى الجزء الصحيح ومنها على سبيل المثال:[ المبادل الحراري الخارجي رقم (6) والصحيح أن المبادل الحراري الخارجي رقم (4)] أنظر الصفحة رقم (**) السطر رقم (**)، ولتجاوز هذا الاعتراض، يجب على مقدم الطلب تصحيح ذلك."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT'
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	التطرق لعناصر الحماية في الوصف التفصيلي
-	ورد في ""الوصف الكامل"" تحديدًا في (الوصف التفصيلي) تكرار لنفس عناصر الحماية حيث اشتملت على نفس الخصائص والتسجيدات والسمات الفنية لنطاق الحماية [ أنظر الصفحات من ** إلى **]، وهذا التكرار يُعد غير مقبول، لذا يرجى من مقدم الطلب حذفه والاكتفاء بذكر ذلك في عناصر الحماية، حيث يجب على مقدم الطلب مراجعة ذلك وتقديم صياغة جيدة وفقا لما نصت عليه المادة (14) الفقرة (4) من اللائحة  ""الوصف التفصيلي يجب أن يكون الوصف واضحاً وكافياً لتمكين رجل المهنة العادي من تنفيذ الاختراع، وأن يتضمن شرحاً تفصيلياً للاختراع كما يجب أن يبين مقدم الطلب أفضل طريقة يعرفها المخترع لتنفيذ الاختراع عند تاريخ تقديم الطلب أو تاريخ الأسبقية. ويجب أن يتضمن الشرح الإشارة إلى الرسومات التوضيحية المرفقة بالتفصيل""."','"•	التطرق لعناصر الحماية في الوصف التفصيلي
-	ورد في ""الوصف الكامل"" تحديدًا في (الوصف التفصيلي) تكرار لنفس عناصر الحماية حيث اشتملت على نفس الخصائص والتسجيدات والسمات الفنية لنطاق الحماية [ أنظر الصفحات من ** إلى **]، وهذا التكرار يُعد غير مقبول، لذا يرجى من مقدم الطلب حذفه والاكتفاء بذكر ذلك في عناصر الحماية، حيث يجب على مقدم الطلب مراجعة ذلك وتقديم صياغة جيدة وفقا لما نصت عليه المادة (14) الفقرة (4) من اللائحة  ""الوصف التفصيلي يجب أن يكون الوصف واضحاً وكافياً لتمكين رجل المهنة العادي من تنفيذ الاختراع، وأن يتضمن شرحاً تفصيلياً للاختراع كما يجب أن يبين مقدم الطلب أفضل طريقة يعرفها المخترع لتنفيذ الاختراع عند تاريخ تقديم الطلب أو تاريخ الأسبقية. ويجب أن يتضمن الشرح الإشارة إلى الرسومات التوضيحية المرفقة بالتفصيل""."',true,'"•	التطرق لعناصر الحماية في الوصف التفصيلي
-	ورد في ""الوصف الكامل"" تحديدًا في (الوصف التفصيلي) تكرار لنفس عناصر الحماية حيث اشتملت على نفس الخصائص والتسجيدات والسمات الفنية لنطاق الحماية [ أنظر الصفحات من ** إلى **]، وهذا التكرار يُعد غير مقبول، لذا يرجى من مقدم الطلب حذفه والاكتفاء بذكر ذلك في عناصر الحماية، حيث يجب على مقدم الطلب مراجعة ذلك وتقديم صياغة جيدة وفقا لما نصت عليه المادة (14) الفقرة (4) من اللائحة  ""الوصف التفصيلي يجب أن يكون الوصف واضحاً وكافياً لتمكين رجل المهنة العادي من تنفيذ الاختراع، وأن يتضمن شرحاً تفصيلياً للاختراع كما يجب أن يبين مقدم الطلب أفضل طريقة يعرفها المخترع لتنفيذ الاختراع عند تاريخ تقديم الطلب أو تاريخ الأسبقية. ويجب أن يتضمن الشرح الإشارة إلى الرسومات التوضيحية المرفقة بالتفصيل""."','"•	التطرق لعناصر الحماية في الوصف التفصيلي
-	ورد في ""الوصف الكامل"" تحديدًا في (الوصف التفصيلي) تكرار لنفس عناصر الحماية حيث اشتملت على نفس الخصائص والتسجيدات والسمات الفنية لنطاق الحماية [ أنظر الصفحات من ** إلى **]، وهذا التكرار يُعد غير مقبول، لذا يرجى من مقدم الطلب حذفه والاكتفاء بذكر ذلك في عناصر الحماية، حيث يجب على مقدم الطلب مراجعة ذلك وتقديم صياغة جيدة وفقا لما نصت عليه المادة (14) الفقرة (4) من اللائحة  ""الوصف التفصيلي يجب أن يكون الوصف واضحاً وكافياً لتمكين رجل المهنة العادي من تنفيذ الاختراع، وأن يتضمن شرحاً تفصيلياً للاختراع كما يجب أن يبين مقدم الطلب أفضل طريقة يعرفها المخترع لتنفيذ الاختراع عند تاريخ تقديم الطلب أو تاريخ الأسبقية. ويجب أن يتضمن الشرح الإشارة إلى الرسومات التوضيحية المرفقة بالتفصيل""."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT'
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"
•	لم يتم استعمال الأرقام أو الحروف نفسها في الوصف الكامل والرسومات لتمييز نفس المكونات
-	تضمن الوصف الكامل بالتحديد في ""شرح مختصر للرسومات ""شكل 3A"" ] كمثال أنظر الصفحة رقم ** السطر/الأسطر **[. وعند الرجوع إلى الأشكال نجد بأنه ذكر ""شكل 3أ "" وذلك لا يتوافق مع المادة (16) الفقرة (3) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص بجزء منها على التالي "" ويجب استعمال الأرقام أو الحروف نفسها في الرسومات المختلفة لتمييز نفس المكونات"". لذلك يجب على مقدم الطلب تصحيح ذلك."','"
•	لم يتم استعمال الأرقام أو الحروف نفسها في الوصف الكامل والرسومات لتمييز نفس المكونات
-	تضمن الوصف الكامل بالتحديد في ""شرح مختصر للرسومات ""شكل 3A"" ] كمثال أنظر الصفحة رقم ** السطر/الأسطر **[. وعند الرجوع إلى الأشكال نجد بأنه ذكر ""شكل 3أ "" وذلك لا يتوافق مع المادة (16) الفقرة (3) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص بجزء منها على التالي "" ويجب استعمال الأرقام أو الحروف نفسها في الرسومات المختلفة لتمييز نفس المكونات"". لذلك يجب على مقدم الطلب تصحيح ذلك."',true,'"
•	لم يتم استعمال الأرقام أو الحروف نفسها في الوصف الكامل والرسومات لتمييز نفس المكونات
-	تضمن الوصف الكامل بالتحديد في ""شرح مختصر للرسومات ""شكل 3A"" ] كمثال أنظر الصفحة رقم ** السطر/الأسطر **[. وعند الرجوع إلى الأشكال نجد بأنه ذكر ""شكل 3أ "" وذلك لا يتوافق مع المادة (16) الفقرة (3) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص بجزء منها على التالي "" ويجب استعمال الأرقام أو الحروف نفسها في الرسومات المختلفة لتمييز نفس المكونات"". لذلك يجب على مقدم الطلب تصحيح ذلك."','"
•	لم يتم استعمال الأرقام أو الحروف نفسها في الوصف الكامل والرسومات لتمييز نفس المكونات
-	تضمن الوصف الكامل بالتحديد في ""شرح مختصر للرسومات ""شكل 3A"" ] كمثال أنظر الصفحة رقم ** السطر/الأسطر **[. وعند الرجوع إلى الأشكال نجد بأنه ذكر ""شكل 3أ "" وذلك لا يتوافق مع المادة (16) الفقرة (3) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص بجزء منها على التالي "" ويجب استعمال الأرقام أو الحروف نفسها في الرسومات المختلفة لتمييز نفس المكونات"". لذلك يجب على مقدم الطلب تصحيح ذلك."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT'
       );

INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES (
           (select max(id)+1 from application.lk_notes ),
           'DB','2023-12-13 10:22:44.334',0,(select max(id)+1 from application.lk_notes ),'"•	تضمنت الرسومات أحرف/أرقام لم تذكر في الوصف التفصيلي
-	""الوصف التفصيلي"" تضمن عند الإشارة إلى الرسومات التوضيحية وصف للأحرف/للأرقام التالية ""**"" ]كمثال أنظر: الصفحة ** السطر/الأسطر **[. وعند الرجوع إلى الرسومات يلاحظ عدم ذكرها في الرسومات. وذلك لا يتوافق مع المادة (16) الفقرة (3) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص بجزء منها على التالي "" ويجب استعمال الأرقام أو الحروف نفسها في الرسومات المختلفة لتمييز نفس المكونات"".  لذلك يجب على مقدم الطلب التصحيح."','"•	تضمنت الرسومات أحرف/أرقام لم تذكر في الوصف التفصيلي
-	""الوصف التفصيلي"" تضمن عند الإشارة إلى الرسومات التوضيحية وصف للأحرف/للأرقام التالية ""**"" ]كمثال أنظر: الصفحة ** السطر/الأسطر **[. وعند الرجوع إلى الرسومات يلاحظ عدم ذكرها في الرسومات. وذلك لا يتوافق مع المادة (16) الفقرة (3) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص بجزء منها على التالي "" ويجب استعمال الأرقام أو الحروف نفسها في الرسومات المختلفة لتمييز نفس المكونات"".  لذلك يجب على مقدم الطلب التصحيح."',true,'"•	تضمنت الرسومات أحرف/أرقام لم تذكر في الوصف التفصيلي
-	""الوصف التفصيلي"" تضمن عند الإشارة إلى الرسومات التوضيحية وصف للأحرف/للأرقام التالية ""**"" ]كمثال أنظر: الصفحة ** السطر/الأسطر **[. وعند الرجوع إلى الرسومات يلاحظ عدم ذكرها في الرسومات. وذلك لا يتوافق مع المادة (16) الفقرة (3) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص بجزء منها على التالي "" ويجب استعمال الأرقام أو الحروف نفسها في الرسومات المختلفة لتمييز نفس المكونات"".  لذلك يجب على مقدم الطلب التصحيح."','"•	تضمنت الرسومات أحرف/أرقام لم تذكر في الوصف التفصيلي
-	""الوصف التفصيلي"" تضمن عند الإشارة إلى الرسومات التوضيحية وصف للأحرف/للأرقام التالية ""**"" ]كمثال أنظر: الصفحة ** السطر/الأسطر **[. وعند الرجوع إلى الرسومات يلاحظ عدم ذكرها في الرسومات. وذلك لا يتوافق مع المادة (16) الفقرة (3) من اللائحة التنفيذية لنظام براءات الاختراع والتي تنص بجزء منها على التالي "" ويجب استعمال الأرقام أو الحروف نفسها في الرسومات المختلفة لتمييز نفس المكونات"".  لذلك يجب على مقدم الطلب التصحيح."',
           (select id from application.lk_application_category where saip_code = 'PATENT'),
           (SELECT id FROM application.lk_sections WHERE code = 'FILES'),
           (SELECT id FROM application.lk_steps  WHERE code = 'Step1'),
           (SELECT id FROM application.lk_attributes WHERE code = 'DESCRIPTION_AR'),'APPLICANT'
       );


UPDATE application.lk_notes
SET section_id=(SELECT x.id FROM application.lk_sections x
                WHERE code = 'DATA')
WHERE section_id=(SELECT x.id FROM application.lk_sections x
                  WHERE code = 'FILES')
  AND category_id = (SELECT x.id FROM application.lk_application_category x
                     WHERE saip_code = 'PATENT')
  AND attribute_id = (SELECT x.id FROM application.lk_attributes x
                      WHERE code = 'PRIORITY');





