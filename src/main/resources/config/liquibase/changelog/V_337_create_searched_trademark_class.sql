
CREATE TABLE application.searched_trademark (
                                                id int8 NOT NULL,
                                                created_by_user VARCHAR(255),
                                                created_date TIMESTAMP,
                                                modified_by_user VARCHAR(255),
                                                modified_date TIMESTAMP,
                                                is_deleted INT NOT NULL DEFAULT 0,
                                                application_info_id BIGINT NOT NULL,
                                                request_number VARCHAR(255) NOT NULL UNIQUE,
                                                name_ar VARCHAR(255),
                                                name_en VARCHAR(255),
                                                mark_image VARCHAR(500),
                                                country_code VARCHAR(10),
                                                publication_date VARCHAR(50),
                                                registration_date VARCHAR(50),
                                                filing_date VARCHAR(50),
                                                mark_status VARCHAR(255),
                                                trademark_type VARCHAR(255),
                                                description TEXT,
                                                owner VARCHAR(255),
                                                representative VARCHAR(255),
                                                nice_classification TEXT -- Store as comma-separated values
);
