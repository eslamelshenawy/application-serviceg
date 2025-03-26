DELETE FROM application.lk_notes
WHERE description_ar='يجب أن يذكر اسم المصطلح العلمي بلغته الأصلية مرادفاً للاسم باللغة العربية عند وروده لأول مرة ويكتفى بالاسم العربي فقط في المرات اللاحقة وذلك طبقاً لأحكام المادة الثانية عشرة الفقرة الأولى من اللائحة التنفيذية'
and category_id=1 and attribute_id = 2 and section_id = 4 and notes_type_enum = 'APPLICANT' and notes_step = 'FORMAL_EXAMINER';


DELETE FROM application.lk_notes
where description_ar = 'يجب تعبئة القالب بالطريقة الصحيحة في (أدخل العنوان باللغة العربية) وذلك طبقاً لأحكام المادة التاسعة الفقرة السادسة من اللائحة التنفيذية للنظام'
and category_id=1 and attribute_id = 3 and section_id = 4 and notes_type_enum = 'APPLICANT' and notes_step = 'FORMAL_EXAMINER';


DELETE FROM application.lk_notes
where description_ar = 'يجب استخدام نفس المصطلح المقابل باللغة العربية عند ورود كلمة لاتينية سبق إطلاق مصطلح مقابل لها باللغة العربية وذلك طبقاً لأحكام المادة الثانية عشرة الفقرة الثالثة من اللائحة التنفيذية'
and category_id=1 and attribute_id = 2 and section_id = 4 and notes_type_enum = 'APPLICANT' and notes_step = 'FORMAL_EXAMINER';








INSERT INTO application.lk_notes (id, created_by_user, created_date, modified_by_user, modified_date, is_deleted, code, description_ar, description_en, enabled, name_ar, name_en, category_id, section_id, step_id, attribute_id, notes_type_enum, note_category_id, notes_step)
VALUES((select max (id+1) from application.lk_notes), NULL, NULL, NULL, NULL, 0, NULL, 'يجب ذكر التسمية كاملة باللغة العربية والإنجليزية عند ورودها لأول مرة في الملخص ويكتفى بعد ذلك بالتسمية المختصرة وذلك طبقاً لأحكام المادة الثانية عشرة الفقرة الثانية من اللائحة التنفيذية', 'يجب ذكر التسمية كاملة باللغة العربية والإنجليزية عند ورودها لأول مرة في الملخص ويكتفى بعد ذلك بالتسمية المختصرة وذلك طبقاً لأحكام المادة الثانية عشرة الفقرة الثانية من اللائحة التنفيذية', true, 'يجب ذكر التسمية كاملة باللغة العربية والإنجليزية عند ورودها لأول مرة في الملخص ويكتفى بعد ذلك بالتسمية المختصرة وذلك طبقاً لأحكام المادة الثانية عشرة الفقرة الثانية من اللائحة التنفيذية', 'يجب ذكر التسمية كاملة باللغة العربية والإنجليزية عند ورودها لأول مرة في الملخص ويكتفى بعد ذلك بالتسمية المختصرة وذلك طبقاً لأحكام المادة الثانية عشرة الفقرة الثانية من اللائحة التنفيذية', 1, 4, NULL, 2, 'APPLICANT', NULL, 'FORMAL_EXAMINER');




UPDATE application.lk_notes
SET  category_id=1, section_id=4, attribute_id=4, notes_type_enum='APPLICANT',  notes_step='FORMAL_EXAMINER'
WHERE description_ar='يجب إرفاق قالب الملخص في الخانة الصحيحة وذلك طبقاً لأحكام المادة التاسعة الفقرة السادسة من اللائحة التنفيذية'
and section_id= 1;



DELETE FROM application.lk_notes
where description_ar = 'يجب أن يشار للشكل العام للاختراع بالملخص وذلك طبقاً لأحكام المادة الثالثة عشرة الفقرة الثانية من اللائحة التنفيذية'
and category_id=1 and attribute_id = 2 and section_id = 4 and notes_type_enum = 'APPLICANT' and notes_step = 'FORMAL_EXAMINER';



UPDATE application.lk_notes
SET category_id=1, section_id=4, step_id=NULL, attribute_id=2, notes_type_enum='APPLICANT',notes_step='FORMAL_EXAMINER'
where description_ar='يجب أن يشار للشكل العام للاختراع بالملخص وذلك طبقاً لأحكام المادة الثالثة عشرة الفقرة الثانية من اللائحة التنفيذية'
and section_id=1;








