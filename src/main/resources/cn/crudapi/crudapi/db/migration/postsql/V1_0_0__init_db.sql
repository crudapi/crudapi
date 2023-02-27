DROP TABLE IF EXISTS "CA_META_SEQUENCE";
CREATE TABLE "CA_META_SEQUENCE" (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "caption" varchar(255) DEFAULT NULL,
  "createdDate" TIMESTAMP(6) DEFAULT NULL,
  "currentTime" bool DEFAULT NULL,
  "cycle" bool DEFAULT NULL,
  "description" varchar(255) DEFAULT NULL,
  "format" varchar(255) DEFAULT NULL,
  "incrementBy" bigint DEFAULT NULL,
  "lastModifiedDate" TIMESTAMP(6) DEFAULT NULL,
  "maxValue" bigint DEFAULT NULL,
  "minValue" bigint DEFAULT NULL,
  "name" varchar(255) DEFAULT NULL,
  "nextValue" bigint DEFAULT NULL,
  "sequenceType" varchar(255) DEFAULT NULL
);

ALTER TABLE "CA_META_SEQUENCE" ADD CONSTRAINT "UQ_CA_META_SEQUENCE_NAME" UNIQUE("name");

INSERT INTO "CA_META_SEQUENCE" ("id", "caption", "createdDate", "currentTime", "cycle", "description", "format", "incrementBy", "lastModifiedDate", "maxValue", "minValue", "name", "nextValue", "sequenceType") VALUES
(1, '角色编码', '2021-02-01 11:17:34.807000', false, NULL, NULL, 'ROLE_%09d', 1, '2021-02-05 17:53:05.432000', 999999999, 1, 'roleCode', 8, 'STRING'),
(2, '资源编码', '2021-02-02 10:06:15.140000', false, NULL, NULL, 'RESOURCE_%09d', 1, '2021-02-02 10:06:15.140000', 999999999, 1, 'resourceCode', 8, 'STRING'),
(3, '会员流水号', '2021-07-25 21:24:48.973000', false, NULL, NULL, '%018d', 1, '2021-07-25 21:28:07.858000', 9999999999, 1, 'membershipCode', 17, 'STRING');

SELECT setval('"CA_META_SEQUENCE_id_seq"', 10000, true);
