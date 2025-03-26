CREATE TABLE application.lk_pv_traits_groups(
    id int8 NOT NULL PRIMARY KEY,
    created_by_user varchar(255) NULL,
    created_date timestamp NULL,
    modified_by_user varchar(255) NULL,
    modified_date timestamp NULL,
    is_deleted int4 NOT NULL,
    code varchar(255) NULL,
    name_ar varchar(255) NULL,
    name_en varchar(255) NULL,
    is_active BOOLEAN,
    distinguished BOOLEAN,
    lk_vegetarian_type_id int8,
    FOREIGN KEY (lk_vegetarian_type_id) REFERENCES application.lk_vegetarian_types(id)
);
