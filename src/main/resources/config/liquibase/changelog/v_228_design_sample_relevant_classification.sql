CREATE TABLE application.design_sample_relevants (
                                                     design_sample_id int8 NOT NULL,
                                                     application_relevant_type_id int8 NOT NULL
);



CREATE TABLE application.design_sample_sub_classifications (
                                                               design_sample_id int8 NOT NULL,
                                                               sub_classification_id int8 NOT NULL
);


ALTER TABLE application.design_sample_relevants ADD CONSTRAINT design_sample_relevants_fk FOREIGN KEY (design_sample_id) REFERENCES application.design_sample(id);
ALTER TABLE application.design_sample_relevants ADD CONSTRAINT design_sample_relevants_type_fk FOREIGN KEY (application_relevant_type_id) REFERENCES application.application_relevant_type(id);


ALTER TABLE application.design_sample_drawings ADD doc3d bool NULL;

UPDATE application.design_sample_drawings set doc3d = false ;