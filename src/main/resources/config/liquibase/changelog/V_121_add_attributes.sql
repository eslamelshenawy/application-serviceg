-- Auto-generated SQL script #202311241346
INSERT INTO application.lk_attributes (id,code,name_ar,name_en)
VALUES ((select max(id) + 1 from application.lk_attributes),'PRIORITY','وثائق الاسبقية','Priority');
INSERT INTO application.lk_attributes (id,code,name_ar,name_en)
VALUES ((select max(id) + 1 from application.lk_attributes),'SUMMARY_AR','الملخص بالعربية','Summary ar');
INSERT INTO application.lk_attributes (id,code,name_ar,name_en)
VALUES ((select max(id) + 1 from application.lk_attributes),'SUMMARY_EN','المخلص بالانجليزية','Summary en');
INSERT INTO application.lk_attributes (id,code,name_ar,name_en)
VALUES ((select max(id) + 1 from application.lk_attributes),'DESCRIPTION_AR','الوصف بالعربية','Description ar');
INSERT INTO application.lk_attributes (id,code,name_ar,name_en)
VALUES ((select max(id) + 1 from application.lk_attributes),'DESCRIPTION_EN','الوصف بالانجليزية','Description en');



-- Auto-generated SQL script #202311241351
INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES ((select max(id) + 1 from application.lk_notes),'DB','2023-05-12 10:22:44.334',0,(select max(id) + 1 from application.lk_notes),'يجب إلغاء الترقيم للرسمة أو الصورة.','يجب إلغاء الترقيم للرسمة أو الصورة.',true,'يجب إلغاء الترقيم للرسمة أو الصورة.','يجب إلغاء الترقيم للرسمة أو الصورة.',1,1,1,9,'APPLICANT');
INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES ((select max(id) + 1 from application.lk_notes),'DB','2023-05-12 10:22:44.334',0,(select max(id) + 1 from application.lk_notes),'الطلب المودع مخالف لأحكام المادة الرابعة من نظام براءات الاختراع والتصميمات التخطيطية للدارات المتكاملة والأصناف النباتية والنماذج الصناعية.','الطلب المودع مخالف لأحكام المادة الرابعة من نظام براءات الاختراع والتصميمات التخطيطية للدارات المتكاملة والأصناف النباتية والنماذج الصناعية.',true,'الطلب المودع مخالف لأحكام المادة الرابعة من نظام براءات الاختراع والتصميمات التخطيطية للدارات المتكاملة والأصناف النباتية والنماذج الصناعية.','الطلب المودع مخالف لأحكام المادة الرابعة من نظام براءات الاختراع والتصميمات التخطيطية للدارات المتكاملة والأصناف النباتية والنماذج الصناعية.',1,1,1,9,'APPLICANT');
INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES ((select max(id) + 1 from application.lk_notes),'DB','2023-05-12 10:22:44.334',0,(select max(id) + 1 from application.lk_notes),'جودة الصور والرسومات منخفضة.','جودة الصور والرسومات منخفضة.',true,'جودة الصور والرسومات منخفضة.','جودة الصور والرسومات منخفضة.',1,1,1,10,'APPLICANT');
INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES ((select max(id) + 1 from application.lk_notes),'DB','2023-05-12 10:22:44.334',0,(select max(id) + 1 from application.lk_notes),'يجب ان لا تحتوي الصور والرسومات على ظل.','يجب ان لا تحتوي الصور والرسومات على ظل.',true,'يجب ان لا تحتوي الصور والرسومات على ظل.','يجب ان لا تحتوي الصور والرسومات على ظل.',1,1,1,10,'APPLICANT');
INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES ((select max(id) + 1 from application.lk_notes),'DB','2023-05-12 10:22:44.334',0,(select max(id) + 1 from application.lk_notes),'إذا كان هناك أجزاء في النموذج الصناعي غير مطلوب حمايتها فترسم بخطوط متقطعة.','إذا كان هناك أجزاء في النموذج الصناعي غير مطلوب حمايتها فترسم بخطوط متقطعة.',true,'إذا كان هناك أجزاء في النموذج الصناعي غير مطلوب حمايتها فترسم بخطوط متقطعة.','إذا كان هناك أجزاء في النموذج الصناعي غير مطلوب حمايتها فترسم بخطوط متقطعة.',1,6,1,11,'APPLICANT');
INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES ((select max(id) + 1 from application.lk_notes),'DB','2023-05-12 10:22:44.334',0,(select max(id) + 1 from application.lk_notes),'لا ينطبق على الطلب المقدم تعريف الدارة المتكاملة.','لا ينطبق على الطلب المقدم تعريف الدارة المتكاملة.',true,'لا ينطبق على الطلب المقدم تعريف الدارة المتكاملة.','لا ينطبق على الطلب المقدم تعريف الدارة المتكاملة.',1,6,1,11,'APPLICANT');
INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES ((select max(id) + 1 from application.lk_notes),'DB','2023-05-12 10:22:44.334',0,(select max(id) + 1 from application.lk_notes),'الرسمة أو الصورة غير واضحة','الرسمة أو الصورة غير واضحة',true,'الرسمة أو الصورة غير واضحة','الرسمة أو الصورة غير واضحة',1,1,1,12,'APPLICANT');
INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES ((select max(id) + 1 from application.lk_notes),'DB','2023-05-12 10:22:44.334',0,(select max(id) + 1 from application.lk_notes),'يجب أن تكون المسافة بين الأسطر في الملخص حوالي 1 سم وذلك طبقاً لأحكام المادة الحادية عشرة الفقرة الخامسة من اللائحة التنفيذية','يجب أن تكون المسافة بين الأسطر في الملخص حوالي 1 سم وذلك طبقاً لأحكام المادة الحادية عشرة الفقرة الخامسة من اللائحة التنفيذية',true,'يجب أن تكون المسافة بين الأسطر في الملخص حوالي 1 سم وذلك طبقاً لأحكام المادة الحادية عشرة الفقرة الخامسة من اللائحة التنفيذية','يجب أن تكون المسافة بين الأسطر في الملخص حوالي 1 سم وذلك طبقاً لأحكام المادة الحادية عشرة الفقرة الخامسة من اللائحة التنفيذية',2,1,1,12,'APPLICANT');
INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES ((select max(id) + 1 from application.lk_notes),'DB','2023-05-12 10:22:44.334',0,(select max(id) + 1 from application.lk_notes),'الوصف المقدم عن النموذج الصناعي لا يبين استخدامات التصميم','الوصف لايبين استخدامات التصميم',true,'الوصف المقدم عن النموذج الصناعي لا يبين استخدامات التصميم','الوصف لايبين استخدامات التصميم',1,1,1,13,'APPLICANT');
INSERT INTO application.lk_notes (id,created_by_user,created_date,is_deleted,code,description_ar,description_en,enabled,name_ar,name_en,category_id,section_id,step_id,attribute_id,notes_type_enum)
VALUES ((select max(id) + 1 from application.lk_notes),'DB','2023-05-12 10:22:44.334',0,(select max(id) + 1 from application.lk_notes),'الأسبقية لا تخص الطلب','الأسبقية لا تخص الطلب',true,'الأسبقية لا تخص الطلب','الأسبقية لا تخص الطلب',1,1,1,13,'APPLICANT');

