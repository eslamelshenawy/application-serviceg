-------------------------------------------delete duplicated notes-------------------------------------------
DELETE FROM application.lk_notes
WHERE id  IN(
    select max(id)
    FROM application.lk_notes
    where note_category_id is not null
      and attribute_id is not null
      and section_id is not null
    GROUP BY
        attribute_id,
        section_id ,
        note_category_id ,
        category_id ,
        step_id ,
        description_ar
    having count(id)>1
);