INSERT INTO application.lk_support_services (id,created_by_user,created_date,modified_by_user,modified_date, is_deleted, cost,desc_ar, desc_en,name_ar,name_en,code)
VALUES((select (max(id) + 1) from application.lk_support_services),null,null,null,null, 0, 0,'شطب طوعي', 'Voluntary Revoke', 'شطب طوعي', 'Voluntary Revoke','VOLUNTARY_REVOKE');

INSERT INTO application.lk_support_services (id,created_by_user,created_date,modified_by_user,modified_date, is_deleted, cost,desc_ar, desc_en,name_ar,name_en,code)
VALUES((select (max(id) + 1) from application.lk_support_services),null,null,null,null, 0, 0,'شطب بموجب حكم قضائي', 'Revoke By Court Order', 'شطب بموجب حكم قضائي', 'Revoke By Court Order','REVOKE_BY_COURT_ORDER');


INSERT INTO application.lk_support_services (id,created_by_user,created_date,modified_by_user,modified_date, is_deleted, cost,desc_ar, desc_en,name_ar,name_en,code)
VALUES((select (max(id) + 1) from application.lk_support_services),null,null,null,null, 0, 0,'قصر المنتجات', 'Revoke Products', 'قصر المنتجات', 'Revoke Products','REVOKE_PRODUCTS');