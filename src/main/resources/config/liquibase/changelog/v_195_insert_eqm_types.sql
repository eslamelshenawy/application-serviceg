INSERT INTO application.lk_task_eqm_types
(id, code, name_ar, name_en)
VALUES
    ((select (max(id) + 1) from application.lk_task_eqm_types), 'COPYRIGHT', 'حق المؤلف', 'Copyright');

INSERT INTO application.lk_task_eqm_types
(id, code, name_ar, name_en)
VALUES
    ((select (max(id) + 1) from application.lk_task_eqm_types), 'GEOGRAPHICAL_INDICATION', 'المؤشرات الجغرافية', 'Geographical Indication');

INSERT INTO application.lk_task_eqm_types
(id, code, name_ar, name_en)
VALUES
    ((select (max(id) + 1) from application.lk_task_eqm_types), 'PUBLICATION', 'صحيفة الملكية الفكرية', 'Publication');

INSERT INTO application.lk_task_eqm_types
(id, code, name_ar, name_en)
VALUES
    ((select (max(id) + 1) from application.lk_task_eqm_types), 'CASE_AND_COMPLAIN', 'القضية والشكوى', 'Case and Complain');

INSERT INTO application.lk_task_eqm_types
(id, code, name_ar, name_en)
VALUES
    ((select (max(id) + 1) from application.lk_task_eqm_types), 'INTEGRATED_CIRCUITS', 'الدارات المتكاملة', 'Integrated Circuits');

INSERT INTO application.lk_task_eqm_types
(id, code, name_ar, name_en)
VALUES
    ((select (max(id) + 1) from application.lk_task_eqm_types), 'PLANT_VARIETIES', 'الأصناف النباتية', 'Plant Varieties');

INSERT INTO application.lk_task_eqm_types
(id, code, name_ar, name_en)
VALUES
    ((select (max(id) + 1) from application.lk_task_eqm_types), 'LICENSE', 'ترخيص مزاولة أنشطة الملكية الفكرية', 'License');

INSERT INTO application.lk_task_eqm_types
(id, code, name_ar, name_en)
VALUES
    ((select (max(id) + 1) from application.lk_task_eqm_types), 'ENPHATH', 'الانفاذ', 'Enphath');