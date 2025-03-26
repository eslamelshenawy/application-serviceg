create table application.lk_plant_details(
id int4 PRIMARY KEY,
name_ar VARCHAR(250),
name_en VARCHAR(250),
code VARCHAR(250),
main_code VARCHAR(250)
);

INSERT INTO application.lk_plant_details (id, name_ar, name_en, code, main_code)
VALUES
(1, 'تهجين غير معروف الاباء', 'Unknown Parents Hybridization', 'UNKNOWN_PARENTS_HYBRIDIZATION', 'HYBRIDIZATION_TYPE'),
(2, 'تهجين متحكم به جزئيا', 'Partially controlled hybridization', 'PARTIALLY_CONTROLLED_HYBRIDIZATION', 'HYBRIDIZATION_TYPE'),
(3, 'تهجين متحكم به كليا', 'Completely controlled hybridization', 'COMPLETELY_CONTROLLED_HYBRIDIZATION', 'HYBRIDIZATION_TYPE'),
(4, 'خضري', 'Vegetative', 'VEGETATIVE', 'REPRODUCTION_METHOD'),
(5, 'فسائل', 'Offset', 'OFFSET', 'REPRODUCTION_METHOD'),
(6, 'بذور', 'Seeds', 'SEEDS', 'REPRODUCTION_METHOD'),
(7, 'اخري', 'Other', 'OTHER', 'REPRODUCTION_METHOD'),
(8, 'مجمد', 'Frozen', 'FROZEN', 'VEGETARIAN_TYPE_USE'),
(9, 'معلبات', 'Canned Food', 'CANNED_FOOD', 'VEGETARIAN_TYPE_USE'),
(10, 'استهلاك طازج', 'Fresh Consumption', 'FRESH_CONSUMPTION', 'VEGETARIAN_TYPE_USE'),
(11, 'بذور مجففة للاستهلاك', 'Dried Seeds For Consumption', 'DRIED_SEEDS_FOR_CONSUMPTION', 'VEGETARIAN_TYPE_USE'),
(12, 'بروتين جاف', 'Dry Protein', 'DRY_PROTEIN', 'VEGETARIAN_TYPE_USE'),
(13, 'علف', 'Feed', 'FEED', 'VEGETARIAN_TYPE_USE'),
(14, 'اخري', 'Other', 'OTHER', 'VEGETARIAN_TYPE_USE'),
(15, 'مقاوم', 'Resistant', 'RESISTANT', 'ILLNESS_RESULT'),
(16, 'لم يختبر', 'Not Tested', 'NOT_TESTED', 'ILLNESS_RESULT'),
(17, 'قابل', 'Sickable', 'SICKABLE', 'ILLNESS_RESULT');
