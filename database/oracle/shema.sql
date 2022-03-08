create user crudapi identified by crudapi;
create tablespace tbs_crudapi datafile '/opt/oracle/oradata/XE/tbs_crudapi.dbf' size 2048M;
alter user crudapi default tablespace tbs_crudapi;
grant create any table to crudapi; 
grant create session,unlimited tablespace to crudapi;
