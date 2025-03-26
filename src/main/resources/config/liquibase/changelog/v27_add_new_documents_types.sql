INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES (90, N'Official Appeal Committee Sent Letter ', N'OFFICIAL_APPEAL_COMMITTEE_SENT_LETTER', N'الخطاب الرسمي المرسل للجنة التظلم', N'الخطاب الرسمي المرسل للجنة التظلم ', N'Appeal', NULL,0);
update application.lk_document_type set
name='Appeal Committee Decision',
code='APPEAL_COMMITTEE_DECISION',
name_ar = 'قرار لجنة التظلم' ,
description='قرار لجنة التظلم',
category='APPEAL' where id = 72;
INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES (91, N'Appeal Committee Opinion ', N'APPEAL_COMMITTEE_OPINION', N'رأي لجنة التظلم', N'رأي لجنة التظلم ', N'Appeal', NULL,0);
