
ALTER TABLE application.application_search_results ALTER COLUMN result_link TYPE text USING result_link::text;
