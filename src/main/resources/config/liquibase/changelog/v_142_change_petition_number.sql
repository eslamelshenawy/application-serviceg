ALTER TABLE application.pct ALTER COLUMN petition_number TYPE varchar(255) USING petition_number::varchar(255);
