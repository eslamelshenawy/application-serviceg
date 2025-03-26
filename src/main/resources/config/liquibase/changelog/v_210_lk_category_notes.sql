-- Update note_category_id in lk_notes table
UPDATE application.lk_notes
SET note_category_id = (
    SELECT c.id
    FROM application.lk_note_category c
    WHERE c.code = 'PARTIAL-REQUEST'
)
WHERE note_category_id = (
    SELECT c.id
    FROM application.lk_note_category c
    WHERE c.code = 'PARTIAL-ORDER'
);

-- Delete record from lk_note_category table
DELETE FROM application.lk_note_category
WHERE code = 'PARTIAL-ORDER';