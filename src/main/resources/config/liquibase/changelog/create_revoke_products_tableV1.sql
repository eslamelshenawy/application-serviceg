INSERT INTO application.lk_document_type (id, name, code, name_ar, description, category, doc_order, is_deleted)
VALUES ((select (max(id) + 1) from application.lk_document_type), 'License waiver document - Revoke Products', 'REVOKE_PRODUCTS', 'وثيقة التنازل عن الترخيص - قصر المنتجات ', ' وثيقة التنازل عن الترخيص - قصر المنتجات ', 'REVOKE_PRODUCTS', NULL,0);



CREATE TABLE application.revoke_products (
	 id int8 NOT NULL,
	"notes" text NULL,
	CONSTRAINT revoke_products_pkey PRIMARY KEY (id)
);

create table application.revoke_products_documents (
revoke_products_id int8 not null,
document_id int8  not null,
FOREIGN KEY (revoke_products_id) REFERENCES application.revoke_products(id),
FOREIGN KEY (document_id)  REFERENCES application.documents(id)
);

CREATE TABLE application.revoke_products_sub_classifications (
    sub_classifications_id int8,
    revoke_products_id int8,
    CONSTRAINT fk_sub_classifications_id FOREIGN KEY (sub_classifications_id) REFERENCES application.sub_classifications(id),
    CONSTRAINT fk_revoke_products_id FOREIGN KEY (revoke_products_id) REFERENCES application.revoke_products(id)
);



