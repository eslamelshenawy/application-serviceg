update application.lk_notes
set notes_step = 'SUBSTANTIVE_EXAMINER'
where category_id = 1 and note_category_id is not null;