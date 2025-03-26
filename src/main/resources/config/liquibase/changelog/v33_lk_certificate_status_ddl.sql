

CREATE TABLE application.lk_certificate_status (
                                                   id int4 NOT NULL,
                                                   code varchar(255) NULL,
                                                   name_ar varchar(255) NULL,
                                                   name_en varchar(255) NULL,
                                                   CONSTRAINT lk_certificate_status_pkey PRIMARY KEY (id)
);


INSERT INTO application.lk_certificate_status
(id, code, name_ar, name_en)
VALUES(1, 'COMPLETED', 'مكتمل', 'completed');

INSERT INTO application.lk_certificate_status
(id, code, name_ar, name_en)
VALUES(2, 'FAILED', 'فشل', 'failed');


INSERT INTO application.lk_certificate_status
(id, code, name_ar, name_en)
VALUES(3, 'PENDING', 'بالانتظار', 'pending');

alter table application.certificates_request add column certificate_status_id int4 NULL;
alter table application.certificates_request add constraint fk_certificates_request_on_lk_certificate_status foreign key (certificate_status_id) references application.lk_certificate_status;



