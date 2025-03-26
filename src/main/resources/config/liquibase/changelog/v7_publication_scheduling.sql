CREATE TABLE application.lk_day_of_week
(
    id      INTEGER NOT NULL,
    code    VARCHAR(255),
    name_ar VARCHAR(255),
    name_en VARCHAR(255),
    CONSTRAINT pk_lk_day_of_week PRIMARY KEY (id)
);

INSERT INTO application.lk_day_of_week (id, code, name_ar, name_en)
VALUES (1, 'SUNDAY', 'الأحد', 'Sunday'),
       (2, 'MONDAY', 'الاثنين', 'Monday'),
       (3, 'TUESDAY', 'الثلاثاء', 'Tuesday'),
       (4, 'WEDNESDAY', 'الأربعاء', 'Wednesday'),
       (5, 'THURSDAY', 'الخميس', 'Thursday'),
       (6, 'FRIDAY', 'الجمعة', 'Friday'),
       (7, 'SATURDAY', 'السبت', 'Saturday');


CREATE TABLE application.publication_scheduling_config
(
    id                      BIGINT  NOT NULL,
    is_deleted              INTEGER NOT NULL,
    created_date            TIMESTAMP WITHOUT TIME ZONE,
    created_by_user         VARCHAR(255),
    modified_date           TIMESTAMP WITHOUT TIME ZONE,
    modified_by_user        VARCHAR(255),
    publication_frequency   VARCHAR(255),
    application_category_id BIGINT,
    CONSTRAINT pk_publication_scheduling_config PRIMARY KEY (id)
);

ALTER TABLE application.publication_scheduling_config
    ADD CONSTRAINT FK_PUBLICATION_SCHEDULING_CONFIG_ON_APPLICATIONCATEGORY FOREIGN KEY (application_category_id) REFERENCES application.lk_application_category (id);

CREATE TABLE application.publication_time
(
    id                               BIGINT  NOT NULL,
    is_deleted                       INTEGER NOT NULL,
    created_date                     TIMESTAMP WITHOUT TIME ZONE,
    created_by_user                  VARCHAR(255),
    modified_date                    TIMESTAMP WITHOUT TIME ZONE,
    modified_by_user                 VARCHAR(255),
    time                             TIMESTAMP WITHOUT TIME ZONE,
    day_of_week_id                   INTEGER,
    day_of_month                     INTEGER,
    publication_scheduling_config_id BIGINT,
    CONSTRAINT pk_publication_time PRIMARY KEY (id)
);

ALTER TABLE application.publication_time
    ADD CONSTRAINT FK_PUBLICATION_TIME_ON_DAYOFWEEK FOREIGN KEY (day_of_week_id) REFERENCES application.lk_day_of_week (id);

ALTER TABLE application.publication_time
    ADD CONSTRAINT FK_PUBLICATION_TIME_ON_PUBLICATIONSCHEDULINGCONFIG FOREIGN KEY (publication_scheduling_config_id) REFERENCES application.publication_scheduling_config (id);

CREATE TABLE application.publication_issue
(
    id                         BIGINT  NOT NULL,
    is_deleted                 INTEGER NOT NULL,
    created_date               TIMESTAMP WITHOUT TIME ZONE,
    created_by_user            VARCHAR(255),
    modified_date              TIMESTAMP WITHOUT TIME ZONE,
    modified_by_user           VARCHAR(255),
    issue_number               BIGINT,
    issuing_date               TIMESTAMP WITHOUT TIME ZONE,
    name_en                    VARCHAR(255),
    name_ar                    VARCHAR(255),
    lk_application_category_id BIGINT,
    CONSTRAINT pk_publication_issue PRIMARY KEY (id)
);

ALTER TABLE application.publication_issue
    ADD CONSTRAINT FK_PUBLICATION_ISSUE_ON_LKAPPLICATIONCATEGORY FOREIGN KEY (lk_application_category_id) REFERENCES application.lk_application_category (id);

CREATE SEQUENCE IF NOT EXISTS id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE application.publication_issue_application
(
    id                   BIGINT  NOT NULL,
    is_deleted           INTEGER NOT NULL,
    created_date         TIMESTAMP WITHOUT TIME ZONE,
    created_by_user      VARCHAR(255),
    modified_date        TIMESTAMP WITHOUT TIME ZONE,
    modified_by_user     VARCHAR(255),
    publication_issue_id BIGINT,
    application_info_id  BIGINT,
    CONSTRAINT pk_publication_issue_application PRIMARY KEY (id)
);

ALTER TABLE application.publication_issue_application
    ADD CONSTRAINT FK_PUBLICATION_ISSUE_APPLICATION_ON_APPLICATIONINFO FOREIGN KEY (application_info_id) REFERENCES application.applications_info (id);

ALTER TABLE application.publication_issue_application
    ADD CONSTRAINT FK_PUBLICATION_ISSUE_APPLICATION_ON_PUBLICATIONISSUE FOREIGN KEY (publication_issue_id) REFERENCES application.publication_issue (id);