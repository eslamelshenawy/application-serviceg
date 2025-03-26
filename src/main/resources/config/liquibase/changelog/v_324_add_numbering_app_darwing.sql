------------------- add new column application_drawing--------------------
ALTER TABLE application.application_drawing ADD COLUMN numbering VARCHAR(500);
--------------------------------------------------------------------------
UPDATE application.application_drawing
SET numbering = CAST(NULLIF(regexp_replace(title, '[^0-9]', '', 'g'), '') AS VARCHAR(500));

-- Update the title column by removing all numeric characters
UPDATE application.application_drawing
SET title = regexp_replace(title, '[0-9]', '', 'g')
WHERE title ~ '[0-9]';  -- Only update rows where title contains numbers

