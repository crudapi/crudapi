-- drop sequence "CA_META_SEQUENCE_ID";
-- DROP TABLE "CA_META_SEQUENCE";

CREATE TABLE "CA_META_SEQUENCE" (
  "id" INT NOT NULL PRIMARY KEY,
  "caption" VARCHAR(255) DEFAULT NULL,
  "createdDate" DATE DEFAULT NULL,
  "currentTime" NUMBER(1) DEFAULT NULL,
  "cycle" NUMBER(1) DEFAULT NULL,
  "description" VARCHAR(255) DEFAULT NULL,
  "format" VARCHAR(255) DEFAULT NULL,
  "incrementBy" INT DEFAULT NULL,
  "lastModifiedDate" DATE DEFAULT NULL,
  "maxValue" INT DEFAULT NULL,
  "minValue" INT DEFAULT NULL,
  "name" VARCHAR(255) DEFAULT NULL,
  "nextValue" INT DEFAULT NULL,
  "sequenceType" VARCHAR(255) DEFAULT NULL
);

ALTER TABLE "CA_META_SEQUENCE" ADD CONSTRAINT "UQ_CA_META_SEQUENCE_NAME" UNIQUE("name");

INSERT INTO "CA_META_SEQUENCE" ("id", "caption", "createdDate", "currentTime", "cycle", "description", "format", "incrementBy", "lastModifiedDate", "maxValue", "minValue", "name", "nextValue", "sequenceType") VALUES
(1, '角色编码', to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), 0, NULL, NULL, 'ROLE_%09d', 1, to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), 999999999, 1, 'roleCode', 8, 'STRING');

INSERT INTO "CA_META_SEQUENCE" ("id", "caption", "createdDate", "currentTime", "cycle", "description", "format", "incrementBy", "lastModifiedDate", "maxValue", "minValue", "name", "nextValue", "sequenceType") VALUES
(2, '资源编码', to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), 0, NULL, NULL, 'RESOURCE_%09d', 1, to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), 999999999, 1, 'resourceCode', 8, 'STRING');

INSERT INTO "CA_META_SEQUENCE" ("id", "caption", "createdDate", "currentTime", "cycle", "description", "format", "incrementBy", "lastModifiedDate", "maxValue", "minValue", "name", "nextValue", "sequenceType") VALUES
(3, '会员流水号', to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), 0, NULL, NULL, '%018d', 1, to_date('2022-03-09 17:06:29','yyyy-mm-dd HH24:MI:SS'), 9999999999, 1, 'membershipCode', 17, 'STRING');

create sequence "CA_META_SEQUENCE_ID" minvalue 1 nomaxvalue increment by 1 start with 10000 nocache;

create or replace trigger "CA_META_SEQUENCE_tg_insert" before insert on "CA_META_SEQUENCE" for each row
begin
  select "CA_META_SEQUENCE_id".nextval into:new."id" from dual;
end;
/
