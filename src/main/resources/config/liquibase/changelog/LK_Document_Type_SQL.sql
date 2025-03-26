--liquibase formatted sql
-- changeset application-service:LK_Document_Type_SQL.sql


INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (1, N'Legal Document', N'وثائق قانونية اخرى', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (2, N'Abstract/Summary Arabic', N'ملخص-عربي', N'APPLICATION', NULL, 0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (3, N'Abstract/Summary English', N'ملخص:انجليزي', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (4, N'Description/Specification Arabic', N'الوصف الكامل-عربي', N'APPLICATION', NULL, 0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (5, N'Description/Specification English', N'الوصف الكامل-انجليزي', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (6, N'Claims Arabic', N'عناصر الحماية-عربي', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (7, N'Claims English', N'عناصر الحماية-انجليزي', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (8, N'Shape/Drawing Arabic', N'الاشكال/الرسومات', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (9, N'Shape/Drawing English', N'الاشكال/الرسومات', N'APPLICATION', NULL, 0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (10, N'Prioirty Document', N'وثائق الاسبقية', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (11, N'Authorization Document', N'وثائق التوكيل ', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (12, N'Waiver Document', N'وثائق التنازل', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (13, N'Reply Examination Report', N'Reply Examination Report', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (14, N'Presented Document', N'Presented Document', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (15, N'Technical Questionnaire Arabic', N'Technical Questionnaire Arabic', N'Application', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (16, N'Technical Questionnaire English', N'Technical Questionnaire English', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (17, N'Files For Update', N'Files For Update', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (18, N'Obection Reply Without Update', N'Obection Reply Without Update', N'Application', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (19, N'Substansive Report', N'Substansive Report', N'Application', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (20, N'Medical Documents', N'Medical Document', N'Medical Documents', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (21, N'Grant Reply Document', N'Grant Reply Document', N'Grant Docuemnts', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (22, N'Research Report Document', N'Research Report Docuement', N'Research Report Document', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (23, N'Merged Document', N'المواصفة', N'Merged Document', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (24, N'Request Form/Manual Form', N'Manual Form - used to keep migrated data', N'Application', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (25, N'Prioirty Document Translation', N'Prioirty Document Translation', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (26, N'Certificate Cover Page', N'Certificate Cover Page', N'Certificate', NULL, 0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (27, N'Applicant Document', N'Applicant Document', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (28, N'Withdraw Application Document', N'Withdraw Application Document', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (29, N'Cancel Agent Document', N'Cancel Agent Document', N'Application', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (30, N'Contract Registration Document', N'Contract Registration Document', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (31, N'Cancel Certificate Document', N'Cancel Certificate Document', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (32, N'Re Issue Certificate Document', N'Re Issue Certificate Document', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (35, N'Update Reason Document', N'Update Reason Document', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (36, N'Substantive Reject Document', N'Substantive Reject Report Document', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (37, N'Sub Report Form Reply Without Update Reason Doc', N'Sub Report Form Reply Without Update Reason Doc', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (38, N'PCT Document', N'نسخة من الطلب الدولي ', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (39, N'Search Report Identical Doc File', N'Search Report Identical Doc File', N'Search Report Identical Doc File', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (40, N'Search Report Office Document', N'Search Report Office Document', N'Search Report Office Document', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (41, N'Search Report Other Documetns', N'Search Report Other Documetns', N'Search Report Other Documetns', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (42, N'Search Report Result Document', N'Search Report Result Document', N'Search Report Result Document', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (43, N'Patent Certificate First Page', N'PatentCertificate First Page', N'PatentCertificateFirstPage', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (44, N'PCTPublicationFirstPage', N'صفحة النشر ', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (45, N'POA', N'تفويض', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (49, N'chronology', N'التسلسل الزمني', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (50, N'The official copy of the constitution', N'النسخة رسمية من الدستور', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (51, N'Collaborative research', N'البحث التعاوني', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (52, N'Latest patentable claims', N'اخر نسخة من عناصر الحماية الممنوحة', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (53, N'Closest prior art documents related to cited references', N'أقرب وثائق الحماية التي تم الاستشهاد بها في الطلب الممنوح', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (54, N'Comparative table of corresponding claim between KR application and KSA application', N'جدول مقارنة بين عناصر الحماية الممنوحة في المكتب الموقع معه اتفاقية PPH و المودعة في الهيئة السعودية للملكية الفكرية', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (55, N'Trademark Translation', N'ترجمة العلامة', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (56, N'Trademark Image/voice', N'صورة العلامة/ملف صوتي', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (57, N'Supporting documents related to the exhibition', N'المستندات الداعمة المتعلقة بالمعرض', N'APPLICATION', NULL,0);
INSERT INTO application.lk_document_type (id, name, description, category, doc_order, is_deleted) VALUES (58, N'Trademark Additional Documents', N'المستندات اضافية', N'APPLICATION', NULL,0);


