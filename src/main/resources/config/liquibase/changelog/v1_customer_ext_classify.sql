create table application.customer_ext_classify (id int8 not null,
                                                created_by_user varchar(255),
                                                created_date timestamp,
                                                modified_by_user varchar(255),
                                                modified_date timestamp,
                                                is_deleted int4 not null,
                                                customer_id int8,
                                                duration date,
                                                notes varchar(3000),
                                                primary key (id));

create table application.customer_ext_classify_comments (id int8 not null,
                                                         created_by_user varchar(255),
                                                         created_date timestamp,
                                                         modified_by_user varchar(255),
                                                         modified_date timestamp,
                                                         is_deleted int4 not null,
                                                         comment text,
                                                         comment_date timestamp,
                                                         commenter_name varchar(255),
                                                         commenter_type varchar(255),
                                                         cust_ext_classify_id int8,
                                                         cust_ext_classify_parent_comment_id int8,
                                                         primary key (id));

alter table application.customer_ext_classify_comments add constraint FK397v67vb5g2it0sjohpbq7fyd
    foreign key (cust_ext_classify_id) references application.customer_ext_classify;

alter table application.customer_ext_classify_comments add constraint FK54uyephfbxbrp2ns3aily17vf
    foreign key (cust_ext_classify_parent_comment_id)  references application.customer_ext_classify_comments;

alter table application.customer_ext_classify add column application_id int8;

alter table application.customer_ext_classify add constraint FK1s3mn2us3xw81q3598u1s2x3l
    foreign key (application_id) references application.applications_info;
