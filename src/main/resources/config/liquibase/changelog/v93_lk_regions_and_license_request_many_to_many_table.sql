CREATE TABLE application.regions_license_request (
            licence_request_id int8 NOT NULL,
            region_id int8 NOT NULL,
            CONSTRAINT licence_request_id_fk FOREIGN KEY (licence_request_id) REFERENCES application.licence_request(id),
            CONSTRAINT region_id_fk FOREIGN KEY (region_id) REFERENCES application.lk_regions(id)
);

