INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en,
 enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id,
 notes_step)
VALUES ((SELECT max(id) + 1 FROM application.lk_notes), 'DB', NULL, NULL, NULL, 0, NULL,
        'يجب كتابة الملخص باللغة العربية وفقا  للمادة (9) الفقرة (6) من اللائحة التنفيذية',
        'The abstract must be written in Arabic in accordance with Article (9), Paragraph (6) of the Executive Regulations.', true, 'يجب كتابة الملخص باللغة العربية وفقا  للمادة (9) الفقرة (6) من اللائحة التنفيذية', 'The abstract must be written in Arabic in accordance with Article (9), Paragraph (6) of the Executive Regulations.', (SELECT x.id FROM application.lk_application_category x
WHERE x.saip_code = 'PLANT_VARIETIES'), (select s.id from application.lk_sections s where s.code='SUMMARY'), NULL, NULL, 'EXAMINAR', NULL, 'FORMAL_EXAMINER');

INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en,
 enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id,
 notes_step)
VALUES ((SELECT max(id) + 1 FROM application.lk_notes), 'DB', NULL, NULL, NULL, 0, NULL,
        'يجب أن يتضمن الملخص اسم الصنف واسماء الأبوين وطريقة استنباطه بشكل مختصر وفقاً للمادة (23) الفقرة (1) من اللائحة التنفيذية',
        'The summary must include the name of the variety, the names of the parents, and the method of derivation in brief, in accordance with Article (23), paragraph (1) of the Executive Regulations.', true,
        'يجب أن يتضمن الملخص اسم الصنف واسماء الأبوين وطريقة استنباطه بشكل مختصر وفقاً للمادة (23) الفقرة (1) من اللائحة التنفيذية',
        'The summary must include the name of the variety, the names of the parents, and the method of derivation in brief, in accordance with Article (23), paragraph (1) of the Executive Regulations.',
        (SELECT x.id FROM application.lk_application_category x WHERE x.saip_code = 'PLANT_VARIETIES'),
        (select s.id from application.lk_sections s where s.code='SUMMARY'), NULL, NULL, 'EXAMINAR', NULL, 'FORMAL_EXAMINER');

INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en,
 enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id,
 notes_step)
VALUES ((SELECT max(id) + 1 FROM application.lk_notes), 'DB', NULL, NULL, NULL, 0, NULL,
        'يجب أن يصاغ الملخص بأسلوب سهل بحيث يعطي فهما واضحاً للصنف وما يميزه عن أقرب الأصناف المشابهة له وفقاً للمادة (23) الفقرة (2) من اللائحة التنفيذية',
        'The summary must be written in an easy style that provides a clear understanding of the item and what distinguishes it from the most similar items, in accordance with Article (23), Paragraph (2) of the Executive Regulations.', true,
        'يجب أن يصاغ الملخص بأسلوب سهل بحيث يعطي فهما واضحاً للصنف وما يميزه عن أقرب الأصناف المشابهة له وفقاً للمادة (23) الفقرة (2) من اللائحة التنفيذية',
        'The summary must be written in an easy style that provides a clear understanding of the item and what distinguishes it from the most similar items, in accordance with Article (23), Paragraph (2) of the Executive Regulations.',
        (SELECT x.id FROM application.lk_application_category x WHERE x.saip_code = 'PLANT_VARIETIES'),
        (select s.id from application.lk_sections s where s.code='SUMMARY'), NULL, NULL, 'EXAMINAR', NULL, 'FORMAL_EXAMINER');

INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en,
 enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id,
 notes_step)
VALUES ((SELECT max(id) + 1 FROM application.lk_notes), 'DB', NULL, NULL, NULL, 0, NULL,
        'يجب أن يشار إلى رقم الشكل العام للصنف النباتي بالملخص وفقاً للمادة (23) الفقرة (3) من اللائحة التنفيذية .',
        'The general form number of the plant variety must be indicated in the summary in accordance with Article (23), Paragraph (3) of the Executive Regulations.', true,
        'يجب أن يشار إلى رقم الشكل العام للصنف النباتي بالملخص وفقاً للمادة (23) الفقرة (3) من اللائحة التنفيذية .',
        'The general form number of the plant variety must be indicated in the summary in accordance with Article (23), Paragraph (3) of the Executive Regulations.',
        (SELECT x.id FROM application.lk_application_category x WHERE x.saip_code = 'PLANT_VARIETIES'),
        (select s.id from application.lk_sections s where s.code='SUMMARY'), NULL, NULL, 'EXAMINAR', NULL, 'FORMAL_EXAMINER');

INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en,
 enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id,
 notes_step)
VALUES ((SELECT max(id) + 1 FROM application.lk_notes), 'DB', NULL, NULL, NULL, 0, NULL,
        'يجب أن يتطابق اسم الصنف النباتي بالملخص مع نماذج التقديم (عربي/إنجليزي) وفقاً للمادة (23) الفقرة (4) من اللائحة التنفيذية.',
        'The name of the plant variety in the abstract must match the application forms (Arabic/English) in accordance with Article (23), Paragraph (4) of the Executive Regulations.', true,
        'يجب أن يتطابق اسم الصنف النباتي بالملخص مع نماذج التقديم (عربي/إنجليزي) وفقاً للمادة (23) الفقرة (4) من اللائحة التنفيذية.',
        'The name of the plant variety in the abstract must match the application forms (Arabic/English) in accordance with Article (23), Paragraph (4) of the Executive Regulations.',
        (SELECT x.id FROM application.lk_application_category x WHERE x.saip_code = 'PLANT_VARIETIES'),
        (select s.id from application.lk_sections s where s.code='SUMMARY'), NULL, NULL, 'EXAMINAR', NULL, 'FORMAL_EXAMINER');

INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en,
 enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id,
 notes_step)
VALUES ((SELECT max(id) + 1 FROM application.lk_notes), 'DB', NULL, NULL, NULL, 0, NULL,
        'يجب ألا يتجاوز الملخص أكثر من نصف صفحة وفي الحالات القصوى صفحة واحدة (23) الفقرة (5) من اللائحة التنفيذية.',
        'The summary shall not exceed half a page and in extreme cases one page (23) Paragraph (5) of the Executive Regulations.', true,
        'يجب ألا يتجاوز الملخص أكثر من نصف صفحة وفي الحالات القصوى صفحة واحدة (23) الفقرة (5) من اللائحة التنفيذية.',
        'The summary shall not exceed half a page and in extreme cases one page (23) Paragraph (5) of the Executive Regulations.',
        (SELECT x.id FROM application.lk_application_category x WHERE x.saip_code = 'PLANT_VARIETIES'),
        (select s.id from application.lk_sections s where s.code='SUMMARY'), NULL, NULL, 'EXAMINAR', NULL, 'FORMAL_EXAMINER');

INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en,
 enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id,
 notes_step)
VALUES ((SELECT max(id) + 1 FROM application.lk_notes), 'DB', NULL, NULL, NULL, 0, NULL,
        'يجب ان يتطابق اسم الصنف النباتي بالملخص مع نماذج التقديم ( عربي / انجليزي) وفقا للمادة (23) الفقرة (4) من اللائحة التنفيذية .',
        'The name of the plant variety in the abstract must match the application forms (Arabic/English) in accordance with Article (23), Paragraph (4) of the Executive Regulations.', true,
        'يجب ان يتطابق اسم الصنف النباتي بالملخص مع نماذج التقديم ( عربي / انجليزي) وفقا للمادة (23) الفقرة (4) من اللائحة التنفيذية .',
        'The name of the plant variety in the abstract must match the application forms (Arabic/English) in accordance with Article (23), Paragraph (4) of the Executive Regulations.',
        (SELECT x.id FROM application.lk_application_category x WHERE x.saip_code = 'PLANT_VARIETIES'),
        (select s.id from application.lk_sections s where s.code='SUMMARY'), NULL, NULL, 'EXAMINAR', NULL, 'FORMAL_EXAMINER');
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en,
 enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id,
 notes_step)
VALUES ((SELECT max(id) + 1 FROM application.lk_notes), 'DB', NULL, NULL, NULL, 0, NULL,
        'يجب ان يكون اسم مقدم الطلب للافراد مطابقا لما هو موجود في الهوية ، اما المؤسسات والشركات فيجب ان يكون مطابقاً للاسم الرسمي وفقا للمادة (21) الفقرة (4 ) من اللائحة التنفيذية .',
        'The name of the applicant for individuals must match what is on the ID, while for institutions and companies it must match the official name in accordance with Article (21) Paragraph (4) of the Executive Regulations.', true,
        'يجب ان يكون اسم مقدم الطلب للافراد مطابقا لما هو موجود في الهوية ، اما المؤسسات والشركات فيجب ان يكون مطابقاً للاسم الرسمي وفقا للمادة (21) الفقرة (4 ) من اللائحة التنفيذية .',
        'The name of the applicant for individuals must match what is on the ID, while for institutions and companies it must match the official name in accordance with Article (21) Paragraph (4) of the Executive Regulations.',
        (SELECT x.id FROM application.lk_application_category x WHERE x.saip_code = 'PLANT_VARIETIES'),
        (select s.id from application.lk_sections s where s.code='APPLICATION'), NULL, NULL, 'EXAMINAR', NULL, 'FORMAL_EXAMINER');

INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en,
 enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id,
 notes_step)
VALUES ((SELECT max(id) + 1 FROM application.lk_notes), 'DB', NULL, NULL, NULL, 0, NULL,
        'يجب أن يذكر اسم الوكيل، ويجـب أن يكون معتـمداً بموجب وكالة صادرة من جهة معتمدة تقبلها الهيئة إذا كان الموكل داخل المملكة أما إذا كان الموكل خارج المملكة فيلزم إحضار وكالة معتمدة من الجهات المختصة ومصدقة من قبل ممثليات المملكة في الخارج وفقاً للمادة (21 ) الفقرة (6) من اللائحة التنفيذية',
        'The name of the agent must be mentioned, and he must be accredited by virtue of a power of attorney issued by an accredited body accepted by the Authority if the principal is inside the Kingdom. However, if the principal is outside the Kingdom, it is necessary to bring an agency accredited by the competent authorities and certified by the Kingdom’s representations abroad in accordance with Article (21), paragraph (6) of the Executive Regulations.', true,
        'يجب أن يذكر اسم الوكيل، ويجـب أن يكون معتـمداً بموجب وكالة صادرة من جهة معتمدة تقبلها الهيئة إذا كان الموكل داخل المملكة أما إذا كان الموكل خارج المملكة فيلزم إحضار وكالة معتمدة من الجهات المختصة ومصدقة من قبل ممثليات المملكة في الخارج وفقاً للمادة (21 ) الفقرة (6) من اللائحة التنفيذية',
        'The name of the agent must be mentioned, and he must be accredited by virtue of a power of attorney issued by an accredited body accepted by the Authority if the principal is inside the Kingdom. However, if the principal is outside the Kingdom, it is necessary to bring an agency accredited by the competent authorities and certified by the Kingdom’s representations abroad in accordance with Article (21), paragraph (6) of the Executive Regulations.',
        (SELECT x.id FROM application.lk_application_category x WHERE x.saip_code = 'PLANT_VARIETIES'),
        (select s.id from application.lk_sections s where s.code='APPLICATION'), NULL, NULL, 'EXAMINAR', NULL, 'FORMAL_EXAMINER');

INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en,
 enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id,
 notes_step)
VALUES ((SELECT max(id) + 1 FROM application.lk_notes), 'DB', NULL, NULL, NULL, 0, NULL,
        'يجب إرفاق التوكيل الرسمي',
        'Power of attorney must be attached.', true,
        'يجب إرفاق التوكيل الرسمي',
        'Power of attorney must be attached.',
        (SELECT x.id FROM application.lk_application_category x WHERE x.saip_code = 'PLANT_VARIETIES'),
        (select s.id from application.lk_sections s where s.code='APPLICATION'), NULL, NULL, 'EXAMINAR', NULL, 'FORMAL_EXAMINER');
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en,
 enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id,
 notes_step)
VALUES ((SELECT max(id) + 1 FROM application.lk_notes), 'DB', NULL, NULL, NULL, 0, NULL,
        'يجب ان يكون اسم مستنبط النبات مطابقا لما هو موجود في الهوية وفقا للمادة (21) الفقرة (5) من اللائحة التنفيذية',
        'The name of the plant breeder must match what is on the ID in accordance with Article (21), Paragraph (5) of the Executive Regulations.', true,
        'يجب ان يكون اسم مستنبط النبات مطابقا لما هو موجود في الهوية وفقا للمادة (21) الفقرة (5) من اللائحة التنفيذية',
        'The name of the plant breeder must match what is on the ID in accordance with Article (21), Paragraph (5) of the Executive Regulations.',
        (SELECT x.id FROM application.lk_application_category x WHERE x.saip_code = 'PLANT_VARIETIES'),
        (select s.id from application.lk_sections s where s.code='INVENTORS'), NULL, NULL, 'EXAMINAR', NULL, 'FORMAL_EXAMINER');

INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en,
 enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id,
 notes_step)
VALUES ((SELECT max(id) + 1 FROM application.lk_notes), 'DB', NULL, NULL, NULL, 0, NULL,
        'بيانات مستنبط النبات غير صحيحة حيث يجب ان يكون المستنبط شخص طبيعي وليس اعتباري وفقا للمادة (2) من النظام والمادة (21) الفقرة (5) من اللائحة التنفيذية',
        'The data of the plant breeder is incorrect, as the breeder must be a natural person and not a legal entity, according to Article (2) of the system and Article (21), paragraph (5) of the executive regulations.', true,
        'بيانات مستنبط النبات غير صحيحة حيث يجب ان يكون المستنبط شخص طبيعي وليس اعتباري وفقا للمادة (2) من النظام والمادة (21) الفقرة (5) من اللائحة التنفيذية',
        'The data of the plant breeder is incorrect, as the breeder must be a natural person and not a legal entity, according to Article (2) of the system and Article (21), paragraph (5) of the executive regulations.',
        (SELECT x.id FROM application.lk_application_category x WHERE x.saip_code = 'PLANT_VARIETIES'),
        (select s.id from application.lk_sections s where s.code='INVENTORS'), NULL, NULL, 'EXAMINAR', NULL, 'FORMAL_EXAMINER');

INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en,
 enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id,
 notes_step)
VALUES ((SELECT max(id) + 1 FROM application.lk_notes), 'DB', NULL, NULL, NULL, 0, NULL,
        'يجب أن يكون التنازل رسمي التنازل في حالة ان مقدم الطلب ليس هو مستنبط النبات وفقا للمادة (8) من النظام',
        'The waiver must be official if the applicant is not the plant breeder in accordance with Article (8) of the system.', true,
        'يجب أن يكون التنازل رسمي التنازل في حالة ان مقدم الطلب ليس هو مستنبط النبات وفقا للمادة (8) من النظام',
        'The waiver must be official if the applicant is not the plant breeder in accordance with Article (8) of the system.',
        (SELECT x.id FROM application.lk_application_category x WHERE x.saip_code = 'PLANT_VARIETIES'),
        (select s.id from application.lk_sections s where s.code='INVENTORS'), NULL, NULL, 'EXAMINAR', NULL, 'FORMAL_EXAMINER');


INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en,
 enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id,
 notes_step)
VALUES ((SELECT max(id) + 1 FROM application.lk_notes), 'DB', NULL, NULL, NULL, 0, NULL,
        'يجب ان يطابق اسم الصنف النباتي المذكور في الاستبيان الفني للصنف الاسم الوارد في الوثائق الرسمية وفقا للمادة (20) الفقرة (8) من اللائحة التنفيذية.',
        'The name of the plant variety mentioned in the technical questionnaire for the variety must match the name mentioned in the official documents in accordance with Article (20), Paragraph (8) of the Executive Regulations.', true,
        'يجب ان يطابق اسم الصنف النباتي المذكور في الاستبيان الفني للصنف الاسم الوارد في الوثائق الرسمية وفقا للمادة (20) الفقرة (8) من اللائحة التنفيذية.',
        'The name of the plant variety mentioned in the technical questionnaire for the variety must match the name mentioned in the official documents in accordance with Article (20), Paragraph (8) of the Executive Regulations.',
        (SELECT x.id FROM application.lk_application_category x WHERE x.saip_code = 'PLANT_VARIETIES'),
        (select s.id from application.lk_sections s where s.code='INVENTORS'), NULL, NULL, 'EXAMINAR', NULL, 'FORMAL_EXAMINER');
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en,
 enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id,
 notes_step)
VALUES ((SELECT max(id) + 1 FROM application.lk_notes), 'DB', NULL, NULL, NULL, 0, NULL,
        'يجب الإفصاح الكامل عن اصل الصنف النباتي الجديد وطريقة استنباطة وفقا للمادة  (22) الفقرة (3) من اللائحة التنفيذية.',
        'The origin of the new plant variety and the method of its derivation must be fully disclosed in accordance with Article (22), Paragraph (3) of the Executive Regulations.', true,
        'يجب الإفصاح الكامل عن اصل الصنف النباتي الجديد وطريقة استنباطة وفقا للمادة  (22) الفقرة (3) من اللائحة التنفيذية.',
        'The origin of the new plant variety and the method of its derivation must be fully disclosed in accordance with Article (22), Paragraph (3) of the Executive Regulations.',
        (SELECT x.id FROM application.lk_application_category x WHERE x.saip_code = 'PLANT_VARIETIES'),
        (select s.id from application.lk_sections s where s.code='TECHNICAL_SURVEY'), NULL, NULL, 'EXAMINAR', NULL, 'FORMAL_EXAMINER');


INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en,
 enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id,
 notes_step)
VALUES ((SELECT max(id) + 1 FROM application.lk_notes), 'DB', NULL, NULL, NULL, 0, NULL,
        'يجب تقديم تسمية مقترحة للصنف ويجوز ان تتكون تسمية الصنف النباتي من كلمة أو مجموعة كلمات وأرقام أو مجموعة حروف وأرقام بمعنى أو بدون معنى ، شرط أن تتيح مكونات التسمية إمكانية التعرف على الصنف وفقاً للمادة (21) الفقرة (1) من اللائحة التنفيذية',
        'A proposed name for the variety must be submitted. The name of the plant variety may consist of a word or a group of words and numbers or a group of letters and numbers with or without meaning, provided that the components of the name allow the variety to be identified in accordance with Article (21), paragraph (1) of the Executive Regulations.', true,
        'يجب تقديم تسمية مقترحة للصنف ويجوز ان تتكون تسمية الصنف النباتي من كلمة أو مجموعة كلمات وأرقام أو مجموعة حروف وأرقام بمعنى أو بدون معنى ، شرط أن تتيح مكونات التسمية إمكانية التعرف على الصنف وفقاً للمادة (21) الفقرة (1) من اللائحة التنفيذية',
        'A proposed name for the variety must be submitted. The name of the plant variety may consist of a word or a group of words and numbers or a group of letters and numbers with or without meaning, provided that the components of the name allow the variety to be identified in accordance with Article (21), paragraph (1) of the Executive Regulations.',
        (SELECT x.id FROM application.lk_application_category x WHERE x.saip_code = 'PLANT_VARIETIES'),
        (select s.id from application.lk_sections s where s.code='TECHNICAL_SURVEY'), NULL, NULL, 'EXAMINAR', NULL, 'FORMAL_EXAMINER');


INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en,
 enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id,
 notes_step)
VALUES ((SELECT max(id) + 1 FROM application.lk_notes), 'DB', NULL, NULL, NULL, 0, NULL,
        'يجب تقديم  وصف دقيق للمراحل المتتابعة لعمليات الاختيار والاكثار التي استخدمت في استنباط الصنف وفقا للمادة (22) الفقرة (4) من اللائحة التنفيذية.',
        'An accurate description of the successive stages of the selection and propagation processes used in developing the variety must be provided in accordance with Article (22), Paragraph (4) of the Executive Regulations.', true,
        'يجب تقديم  وصف دقيق للمراحل المتتابعة لعمليات الاختيار والاكثار التي استخدمت في استنباط الصنف وفقا للمادة (22) الفقرة (4) من اللائحة التنفيذية.',
        'An accurate description of the successive stages of the selection and propagation processes used in developing the variety must be provided in accordance with Article (22), Paragraph (4) of the Executive Regulations.',
        (SELECT x.id FROM application.lk_application_category x WHERE x.saip_code = 'PLANT_VARIETIES'),
        (select s.id from application.lk_sections s where s.code='TECHNICAL_SURVEY'), NULL, NULL, 'EXAMINAR', NULL, 'FORMAL_EXAMINER');

INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en,
 enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id,
 notes_step)
VALUES ((SELECT max(id) + 1 FROM application.lk_notes), 'DB', NULL, NULL, NULL, 0, NULL,
        'يجب اثبات التجانس للصنف موضحا فيه درجة التباين في أي من الصفات التي يتصف بها وفقا للمادة (22) الفقرة (5) من اللائحة التنفيذية',
        'The homogeneity of the variety must be proven, stating the degree of variation in any of its characteristics, in accordance with Article (22), Paragraph (5) of the Executive Regulations.', true,
        'يجب اثبات التجانس للصنف موضحا فيه درجة التباين في أي من الصفات التي يتصف بها وفقا للمادة (22) الفقرة (5) من اللائحة التنفيذية',
        'The homogeneity of the variety must be proven, stating the degree of variation in any of its characteristics, in accordance with Article (22), Paragraph (5) of the Executive Regulations.',
        (SELECT x.id FROM application.lk_application_category x WHERE x.saip_code = 'PLANT_VARIETIES'),
        (select s.id from application.lk_sections s where s.code='TECHNICAL_SURVEY'), NULL, NULL, 'EXAMINAR', NULL, 'FORMAL_EXAMINER');

INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en,
 enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id,
 notes_step)
VALUES ((SELECT max(id) + 1 FROM application.lk_notes), 'DB', NULL, NULL, NULL, 0, NULL,
        'يجب بيان عن ثبات الصنف موضحاً فيه عدد دورات الاكثار التي لم تتغير خلالها أي من الصفات المميزة له وفقاً للمادة (22) الفقرة ( 6) من اللائحة التنفيذية.',
        'A statement must be made about the stability of the variety, indicating the number of breeding cycles during which none of its distinguishing characteristics changed, in accordance with Article (22), Paragraph (6) of the Executive Regulations.', true,
        'يجب بيان عن ثبات الصنف موضحاً فيه عدد دورات الاكثار التي لم تتغير خلالها أي من الصفات المميزة له وفقاً للمادة (22) الفقرة ( 6) من اللائحة التنفيذية.',
        'A statement must be made about the stability of the variety, indicating the number of breeding cycles during which none of its distinguishing characteristics changed, in accordance with Article (22), Paragraph (6) of the Executive Regulations.',
        (SELECT x.id FROM application.lk_application_category x WHERE x.saip_code = 'PLANT_VARIETIES'),
        (select s.id from application.lk_sections s where s.code='TECHNICAL_SURVEY'), NULL, NULL, 'EXAMINAR', NULL, 'FORMAL_EXAMINER');
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en,
 enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id,
 notes_step)
VALUES ((SELECT max(id) + 1 FROM application.lk_notes), 'DB', NULL, NULL, NULL, 0, NULL,
        'يجب بيان عن تميز الصنف موضحاً فيه تميزه عن غيرة من الأصناف المنحدرة من النوع نفسه ، وفي حالة وجود تقارب أو تشابه بين الصنف والاصناف الأخرى ينبغي تحديد هذه الأصناف مع وصف دقيق لأوجه الاختلاف بينها وفقاً للمادة (22) الفقرة (7) من اللائحة التنفيذية',
        'A statement must be made about the distinctiveness of the variety, explaining its distinction from other varieties of the same species. In the event of a closeness or similarity between the variety and other varieties, these varieties must be identified with an accurate description of the differences between them in accordance with Article (22), paragraph (7) of the Executive Regulations.', true,
        'يجب بيان عن تميز الصنف موضحاً فيه تميزه عن غيرة من الأصناف المنحدرة من النوع نفسه ، وفي حالة وجود تقارب أو تشابه بين الصنف والاصناف الأخرى ينبغي تحديد هذه الأصناف مع وصف دقيق لأوجه الاختلاف بينها وفقاً للمادة (22) الفقرة (7) من اللائحة التنفيذية',
        'A statement must be made about the distinctiveness of the variety, explaining its distinction from other varieties of the same species. In the event of a closeness or similarity between the variety and other varieties, these varieties must be identified with an accurate description of the differences between them in accordance with Article (22), paragraph (7) of the Executive Regulations.',
        (SELECT x.id FROM application.lk_application_category x WHERE x.saip_code = 'PLANT_VARIETIES'),
        (select s.id from application.lk_sections s where s.code='PROOF_EXCELLENCE'), NULL, NULL, 'EXAMINAR', NULL, 'FORMAL_EXAMINER');
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en,
 enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id,
 notes_step)
VALUES ((SELECT max(id) + 1 FROM application.lk_notes), 'DB', NULL, NULL, NULL, 0, NULL,
        'يجب ارفاق مستند الأسبقية وفقاً للمادة (21) الفقرة ( 8) من اللائحة التنفيذية.',
        'The priority document must be attached in accordance with Article (21), Paragraph (8) of the Executive Regulations.', true,
        'يجب ارفاق مستند الأسبقية وفقاً للمادة (21) الفقرة ( 8) من اللائحة التنفيذية.',
        'The priority document must be attached in accordance with Article (21), Paragraph (8) of the Executive Regulations.',
        (SELECT x.id FROM application.lk_application_category x WHERE x.saip_code = 'PLANT_VARIETIES'),
        (select s.id from application.lk_sections s where s.code='PRECEDENCE'), NULL, NULL, 'EXAMINAR', NULL, 'FORMAL_EXAMINER');
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------
INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en,
 enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id,
 notes_step)
VALUES ((SELECT max(id) + 1 FROM application.lk_notes), 'DB', NULL, NULL, NULL, 0, NULL,
        'يجب ان تكون الصور الفوتوغرافيه أو الرسومات التوضيحية واضحة وذات جودة عالية ويمكن استنساخها بوضوح ، وغير مظللة ولا تحتوي على خلفيه وفقا للمادة (24) الفقرة (1) من اللائحة التنفيذية',
        'Photographs or illustrations must be clear, of high quality, clearly reproducible, unshaded and without a background in accordance with Article (24), Paragraph (1) of the Executive Regulations.', true,
        'يجب ان تكون الصور الفوتوغرافيه أو الرسومات التوضيحية واضحة وذات جودة عالية ويمكن استنساخها بوضوح ، وغير مظللة ولا تحتوي على خلفيه وفقا للمادة (24) الفقرة (1) من اللائحة التنفيذية',
        'Photographs or illustrations must be clear, of high quality, clearly reproducible, unshaded and without a background in accordance with Article (24), Paragraph (1) of the Executive Regulations.',
        (SELECT x.id FROM application.lk_application_category x WHERE x.saip_code = 'PLANT_VARIETIES'),
        (select s.id from application.lk_sections s where s.code='REPORT'), NULL, NULL, 'EXAMINAR', NULL, 'FORMAL_EXAMINER');

INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en,
 enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id,
 notes_step)
VALUES ((SELECT max(id) + 1 FROM application.lk_notes), 'DB', NULL, NULL, NULL, 0, NULL,
        'يجب أن ترقم الصور الفوتوغرافية أو الرسومات التوضيحية بالتسلسل وفقا للمادة (24) الفقرة (2) من اللائحة التنفيذية',
        'Photographs or illustrations must be numbered sequentially in accordance with Article (24) Paragraph (2) of the Executive Regulations.', true,
        'يجب أن ترقم الصور الفوتوغرافية أو الرسومات التوضيحية بالتسلسل وفقا للمادة (24) الفقرة (2) من اللائحة التنفيذية',
        'Photographs or illustrations must be numbered sequentially in accordance with Article (24) Paragraph (2) of the Executive Regulations.',
        (SELECT x.id FROM application.lk_application_category x WHERE x.saip_code = 'PLANT_VARIETIES'),
        (select s.id from application.lk_sections s where s.code='REPORT'), NULL, NULL, 'EXAMINAR', NULL, 'FORMAL_EXAMINER');

INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en,
 enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id,
 notes_step)
VALUES ((SELECT max(id) + 1 FROM application.lk_notes), 'DB', NULL, NULL, NULL, 0, NULL,
        'يجب استعمال الأرقام أو الحروف لتمييز مكونات الرسومات التوضيحية  وفقاً للمادة (24 ) الفقرة (3) من اللائحة التنفيذية',
        'Numbers or letters must be used to distinguish the components of the illustrations in accordance with Article (24), Paragraph (3) of the Executive Regulations.', true,
        'يجب استعمال الأرقام أو الحروف لتمييز مكونات الرسومات التوضيحية  وفقاً للمادة (24 ) الفقرة (3) من اللائحة التنفيذية',
        'Numbers or letters must be used to distinguish the components of the illustrations in accordance with Article (24), Paragraph (3) of the Executive Regulations.',
        (SELECT x.id FROM application.lk_application_category x WHERE x.saip_code = 'PLANT_VARIETIES'),
        (select s.id from application.lk_sections s where s.code='REPORT'), NULL, NULL, 'EXAMINAR', NULL, 'FORMAL_EXAMINER');

INSERT INTO application.lk_notes
(id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en,
 enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id,
 notes_step)
VALUES ((SELECT max(id) + 1 FROM application.lk_notes), 'DB', NULL, NULL, NULL, 0, NULL,
        'ييجب أن يكون الشكل الرئيسي المرفق في صفحة مستقلة وفقاً للمادة (24) الفقرة ( 4 ) من اللائحة التنفيذية',
        'The attached main form must be on a separate page in accordance with Article (24) Paragraph (4) of the Executive Regulations.', true,
        'يجب أن يكون الشكل الرئيسي المرفق في صفحة مستقلة وفقاً للمادة (24) الفقرة ( 4 ) من اللائحة التنفيذية',
        'The attached main form must be on a separate page in accordance with Article (24) Paragraph (4) of the Executive Regulations.',
        (SELECT x.id FROM application.lk_application_category x WHERE x.saip_code = 'PLANT_VARIETIES'),
        (select s.id from application.lk_sections s where s.code='REPORT'), NULL, NULL, 'EXAMINAR', NULL, 'FORMAL_EXAMINER');