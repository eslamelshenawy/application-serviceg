
WITH Duplicates AS (
    SELECT
        ctid,
        row_number() OVER (
            PARTITION BY art.application_id
            ORDER BY art.id asc
        ) AS row_num
    FROM application.trademark_details art
)
DELETE FROM application.trademark_details art
WHERE ctid IN (
    SELECT ctid FROM Duplicates WHERE row_num > 1
);

ALTER TABLE application.trademark_details ADD CONSTRAINT trademark_details_application_id_unique UNIQUE (application_id);
