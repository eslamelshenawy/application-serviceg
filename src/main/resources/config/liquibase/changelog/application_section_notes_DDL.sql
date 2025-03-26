--liquibase formatted sql
-- changeset application-service:application_section_notes_DDL.sql

CREATE TABLE IF NOT EXISTS application.application_section_notes (
                                                       application_notes_id int8 NOT NULL,
                                                       note_id int8 NOT NULL,
                                                       CONSTRAINT ukqak7rvjv9ec083u1semjjd51p UNIQUE (application_notes_id, note_id),
                                                       CONSTRAINT fk1pt8g5c4hky99q1vh1y0va2ms FOREIGN KEY (note_id) REFERENCES application.lk_notes(id),
                                                       CONSTRAINT fkwnvvootvdrojwgbvwl7u4yp8 FOREIGN KEY (application_notes_id) REFERENCES application.application_notes(id)
);