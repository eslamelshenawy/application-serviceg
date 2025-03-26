-- Auto-generated SQL script #202407020623
INSERT INTO application.lk_publication_type (id,code,name_ar,name_en,application_category_id)
VALUES ((select max(id) + 1 from application.lk_publication_type),'INDUSTRIAL_DESIGN_RENEWAL','تجديد  تصاميم','Industrial Design Renewal',2);

ALTER TABLE application.application_installments ADD publish_status varchar NULL;


