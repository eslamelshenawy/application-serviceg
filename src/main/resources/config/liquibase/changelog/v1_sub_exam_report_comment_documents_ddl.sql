
 create table application.document_comment_attaches (document_comment_id int8 not null, document_id int8 not null);
 create table application.sub_exam_report_documents (sub_exam_report_id int8 not null, document_id int8 not null);
 alter table application.document_comment_attaches add constraint UK3v1vx9b81xdnxlnfltg4oi27n unique (document_comment_id, document_id);
 alter table application.sub_exam_report_documents add constraint UK211iak0l4u24353kmx0l96xuv unique (sub_exam_report_id, document_id);
 alter table application.document_comment_attaches add constraint FKb6u58w4l5eawqvs435mkdu7yh foreign key (document_id) references application.documents;
 alter table application.document_comment_attaches add constraint FK4vgyhlixqgwfunb5mstysa10w foreign key (document_comment_id) references application.application_document_comments;
 alter table application.sub_exam_report_documents add constraint FKof7xse9kwfhsrs2jvnh6cgnxx foreign key (document_id) references application.documents;
 alter table application.sub_exam_report_documents add constraint FK5guped0vu0u8lmijca6j0hxsx foreign key (sub_exam_report_id) references application.substantive_examination_reports;
