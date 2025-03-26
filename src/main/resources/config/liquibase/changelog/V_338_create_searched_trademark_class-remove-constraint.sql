-- Drop the unique constraint on request_number column in searched_trademark table
ALTER TABLE application.searched_trademark DROP CONSTRAINT searched_trademark_request_number_key;
