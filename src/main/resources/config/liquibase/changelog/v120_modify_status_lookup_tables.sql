ALTER TABLE application.lk_certificate_status
    ADD COLUMN name_ar_external varchar(255) NULL,
    ADD COLUMN name_en_external varchar(255) NULL;
--------------------------------------------------------------------------------
update application.lk_certificate_status
set name_ar_external = 'مكتمل',
    name_en_external= 'completed'
where id = 1;
update application.lk_certificate_status
set name_ar_external = 'فشل',
    name_en_external= 'failed'
where id = 2;
update application.lk_certificate_status
set name_ar_external = 'سداد رسوم التقديم',
    name_en_external= 'Pay the application fees'
where id = 3;

--------------------------------------------------------------------------------
ALTER TABLE application.lk_support_service_request_status
    ADD COLUMN name_ar_external varchar(255) NULL,
    ADD COLUMN name_en_external varchar(255) NULL;
--------------------------------------------------------------------------------------
UPDATE application.lk_support_service_request_status
SET  name_ar_external=' سداد رسوم التقديم', name_en_external='Pay the application fees'
WHERE id=1;
UPDATE application.lk_support_service_request_status
SET  name_ar_external='تحت الدراسة', name_en_external='Under Procedure'
WHERE id=2;
UPDATE application.lk_support_service_request_status
SET  name_ar_external='معاد لمقدم الطلب', name_en_external='Return to the applicant'
WHERE id=3;
UPDATE application.lk_support_service_request_status
SET  name_ar_external=' سداد رسوم النشر', name_en_external='Pay publication fees'
WHERE id=4;
UPDATE application.lk_support_service_request_status
SET name_ar_external='مرفوض', name_en_external='Rejected'
WHERE id=5;
UPDATE application.lk_support_service_request_status
SET  name_ar_external='مكتمل', name_en_external='Completed'
WHERE id=6;
UPDATE application.lk_support_service_request_status
SET  name_ar_external='إنتظار الاعتراض', name_en_external='oppostion wating'
WHERE id=7;
UPDATE application.lk_support_service_request_status
SET  name_ar_external='ترخيص مشطوب', name_en_external='License Revoked'
WHERE id=8;
UPDATE application.lk_support_service_request_status
SET  name_ar_external='قيد الاعتراض', name_en_external='under opposition'
WHERE id=9;
UPDATE application.lk_support_service_request_status
SET  name_ar_external='انسحاب ', name_en_external=' withdrawal'
WHERE id=10;
UPDATE application.lk_support_service_request_status
SET name_ar_external='معلق ', name_en_external=' pending'
WHERE id=11;

--------------------------------------------------------------------------------------------------------

ALTER TABLE application.lk_application_status
    ADD COLUMN ips_status_desc_ar_external varchar(255) NULL,
    ADD COLUMN ips_status_desc_en_external varchar(255) NULL;
----------------------------------------------------------------------------------------------------------

update  application.lk_application_status set
ips_status_desc_ar_external = 'مسودة',
ips_status_desc_en_external= 'Draft'
where id = 1;

update  application.lk_application_status set
ips_status_desc_ar_external = 'انتظار دفع رسوم التقديم',
ips_status_desc_en_external= 'Waiting for application fee payment'
where id = 2;

update  application.lk_application_status set
ips_status_desc_ar_external = 'جديد',
ips_status_desc_en_external= 'New'
where id =3 ;

update  application.lk_application_status set
ips_status_desc_ar_external = 'الطلب كأن لم يكن',
ips_status_desc_en_external= 'The application is as if it never existed'
where id =4 ;

update  application.lk_application_status set
ips_status_desc_ar_external = 'تحت الدراسة',
ips_status_desc_en_external= 'Under Procedure'
where id = 5;

update  application.lk_application_status set
ips_status_desc_ar_external = 'قبول طلب التسجيل',
ips_status_desc_en_external= 'Acceptance of the registration application'
where id = 6;

update  application.lk_application_status set
ips_status_desc_ar_external = 'إعادة ارسال',
ips_status_desc_en_external= 'Sendback'
where id =7 ;

update  application.lk_application_status set
ips_status_desc_ar_external = 'مرفوض',
ips_status_desc_en_external= 'Rejected'
where id = 8;

update  application.lk_application_status set
ips_status_desc_ar_external = 'معاد لمقدم الطلب',
ips_status_desc_en_external= 'Return to the applicant'
where id = 9;

update  application.lk_application_status set
ips_status_desc_ar_external = 'معادة لمسؤول التصنيف',
ips_status_desc_en_external= 'Returned to the classification officer'
where id = 10;

update  application.lk_application_status set
ips_status_desc_ar_external = 'بانتظار سداد رسوم التعديل',
ips_status_desc_en_external= 'Payment of modification fees is pending'
where id =11 ;

update  application.lk_application_status set
ips_status_desc_ar_external = 'منسحب',
ips_status_desc_en_external= 'Waived'
where id =12 ;

update  application.lk_application_status set
ips_status_desc_ar_external = 'تم المنح',
ips_status_desc_en_external= 'granted'
where id =13 ;

update  application.lk_application_status set
ips_status_desc_ar_external = 'سداد رسوم النشر',
ips_status_desc_en_external= 'Pay publication fees'
where id = 14;

update  application.lk_application_status set
ips_status_desc_ar_external = 'مكتمل',
ips_status_desc_en_external= 'Completed'
where id = 15;

update  application.lk_application_status set
ips_status_desc_ar_external = 'بإنتظار  سداد رسوم التظلم',
ips_status_desc_en_external= 'Awaiting payment of the grievance fee'
where id = 16;

update  application.lk_application_status set
ips_status_desc_ar_external = 'متظلم للجنة التظلمات',
ips_status_desc_en_external= 'Complainant to the Grievance Committee'
where id = 17;

update  application.lk_application_status set
ips_status_desc_ar_external = 'معاد لمقدم الطلب',
ips_status_desc_en_external= 'Returned to the applicant'
where id =18;

update  application.lk_application_status set
ips_status_desc_ar_external = 'معترض',
ips_status_desc_en_external= 'Objector'
where id =19 ;

update  application.lk_application_status set
ips_status_desc_ar_external = 'قبول الاعتراض',
ips_status_desc_en_external= 'Accept the objection'
where id =20 ;


update  application.lk_application_status set
ips_status_desc_ar_external = 'رفض الاعتراض',
ips_status_desc_en_external= 'Reject the objection'
where id = 21;

update  application.lk_application_status set
ips_status_desc_ar_external = 'علامة مشطوبة ',
ips_status_desc_en_external= 'Crossed out mark'
where id =22 ;

update  application.lk_application_status set
ips_status_desc_ar_external = 'معاد لمقدم الطلب',
ips_status_desc_en_external= 'Return to the applicant'
where id = 23;

update  application.lk_application_status set
ips_status_desc_ar_external = 'مرفوض',
ips_status_desc_en_external= 'Rejected'
where id = 24;

update  application.lk_application_status set
ips_status_desc_ar_external = 'تحت الدراسة',
ips_status_desc_en_external= 'Under Procedure'
where id = 25;

update application.lk_application_status
set ips_status_desc_ar_external = 'مكتمل',
    ips_status_desc_en_external= 'Completed'
where id =26;

update application.lk_application_status
set ips_status_desc_ar_external = 'بإنتظار تعديل XML',
    ips_status_desc_en_external= 'Awaiting for update XML'
where id =27;

update application.lk_application_status
set ips_status_desc_ar_external = 'مرفوض',
    ips_status_desc_en_external= 'rejected'
where id =28;

update application.lk_application_status
set ips_status_desc_ar_external = 'قيد اللجنة',
    ips_status_desc_en_external= 'In Committee'
where id =29;

update application.lk_application_status
set ips_status_desc_ar_external = 'متخلي عنه',
    ips_status_desc_en_external= 'Abandoned'
where id =30;
update application.lk_application_status
set ips_status_desc_ar_external = 'مشطوب بامر محكمة',
    ips_status_desc_en_external= 'Revoked By Court Order'
where id =31;
update application.lk_application_status
set ips_status_desc_ar_external = 'ارسال تعديل الى مقدم الطلب',
    ips_status_desc_en_external= 'Sending updates to applicant'
where id =32;
update application.lk_application_status
set ips_status_desc_ar_external = 'استكمال متطلبات',
    ips_status_desc_en_external= 'Complete the requirements'
where id =33;
