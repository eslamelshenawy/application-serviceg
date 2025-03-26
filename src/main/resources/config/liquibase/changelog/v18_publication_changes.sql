DROP TABLE application.publication_issue_application;

CREATE SEQUENCE IF NOT EXISTS id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE application.application_publication
(
    id                  BIGINT  NOT NULL,
    is_deleted          INTEGER NOT NULL,
    created_date        TIMESTAMP WITHOUT TIME ZONE,
    created_by_user     VARCHAR(255),
    modified_date       TIMESTAMP WITHOUT TIME ZONE,
    modified_by_user    VARCHAR(255),
    application_info_id BIGINT,
    publication_type_id INTEGER,
    publication_date    TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_application_publication PRIMARY KEY (id)
);

ALTER TABLE application.application_publication
    ADD CONSTRAINT FK_APPLICATION_PUBLICATION_ON_APPLICATIONINFO FOREIGN KEY (application_info_id) REFERENCES application.applications_info (id);

ALTER TABLE application.application_publication
    ADD CONSTRAINT FK_APPLICATION_PUBLICATION_ON_PUBLICATIONTYPE FOREIGN KEY (publication_type_id) REFERENCES application.lk_publication_type (id);

CREATE SEQUENCE IF NOT EXISTS id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE application.publication_issue_application_publication
(
    id                         BIGINT  NOT NULL,
    is_deleted                 INTEGER NOT NULL,
    created_date               TIMESTAMP WITHOUT TIME ZONE,
    created_by_user            VARCHAR(255),
    modified_date              TIMESTAMP WITHOUT TIME ZONE,
    modified_by_user           VARCHAR(255),
    publication_issue_id       BIGINT,
    application_publication_id BIGINT,
    CONSTRAINT pk_publication_issue_application_publication PRIMARY KEY (id)
);

ALTER TABLE application.publication_issue_application_publication
    ADD CONSTRAINT FK_PUBLICATIONISSUEAPPLICATIONPUBLICATION_ON_PUBLICATIONISSUE FOREIGN KEY (publication_issue_id) REFERENCES application.publication_issue (id);

ALTER TABLE application.publication_issue_application_publication
    ADD CONSTRAINT FK_PUBLICATIONISSUEAPPLICATIONPUBLICA_ON_APPLICATIONPUBLICATION FOREIGN KEY (application_publication_id) REFERENCES application.application_publication (id);