UPDATE application.lk_note_category
SET name_ar = 'الرد على مبررات مقدم الطلب'
WHERE code = 'PRECEDENCE'
  AND EXISTS (
        SELECT 1
        FROM information_schema.tables
        WHERE table_schema = 'application'
          AND table_name = 'lk_note_category'
    );

UPDATE application.lk_note_category
SET name_ar = 'التعديلات على الطلب'
WHERE code = 'RESULT'
  AND EXISTS (
        SELECT 1
        FROM information_schema.tables
        WHERE table_schema = 'application'
          AND table_name = 'lk_note_category'
    );

