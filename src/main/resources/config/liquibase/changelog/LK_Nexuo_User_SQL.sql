--liquibase formatted sql
-- changeset application-service:LK_Nexuo_User_SQL.sql


insert into application.lk_nexuo_user(id,is_deleted,"type","name") VALUES(1,0,'IPRS_AUTHENTICATION_AND_AUTHORIZATION','IPRs4AAS');
insert into application.lk_nexuo_user(id,is_deleted,"type","name") VALUES(2,0,'IPRS_PATENT','IPRs4Patent');
insert into application.lk_nexuo_user(id,is_deleted,"type","name") VALUES(3,0,'IPRS_INDUSTRIAL_DESIGN','IPRs4ID');
insert into application.lk_nexuo_user(id,is_deleted,"type","name") VALUES(4,0,'IPRS_PLANT_VARIETIES','IPRs4PV');
insert into application.lk_nexuo_user(id,is_deleted,"type","name") VALUES(5,0,'IPRS_INTEGRATED_CIRCUITS','IPRs4ICL');
insert into application.lk_nexuo_user(id,is_deleted,"type","name") VALUES(6,0,'IPRS_TRADEMARK','IPRs4TM');
insert into application.lk_nexuo_user(id,is_deleted,"type","name") VALUES(7,0,'IPRS_GEOGRAPHICAL_INDICATION','IPRs4GI');
insert into application.lk_nexuo_user(id,is_deleted,"type","name") VALUES(8,0,'IPRS_COPYRIGHT','IPRs4CR');

