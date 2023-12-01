DROP TABLE IF EXISTS "CA_META_SEQUENCE";
CREATE TABLE "CA_META_SEQUENCE" (
  "id" BIGINT IDENTITY(10000, 1) NOT NULL PRIMARY KEY,
  "caption" NVARCHAR(255) DEFAULT NULL,
  "createdDate" DATETIME DEFAULT NULL,
  "currentTime" BIT DEFAULT NULL,
  "cycle" BIT DEFAULT NULL,
  "description" VARCHAR(255) DEFAULT NULL,
  "format" VARCHAR(255) DEFAULT NULL,
  "incrementBy" BIGINT DEFAULT NULL,
  "lastModifiedDate" DATETIME DEFAULT NULL,
  "maxValue" BIGINT DEFAULT NULL,
  "minValue" BIGINT DEFAULT NULL,
  "name" VARCHAR(255) DEFAULT NULL,
  "nextValue" BIGINT DEFAULT NULL,
  "sequenceType" NVARCHAR(255) DEFAULT NULL
);

ALTER TABLE "CA_META_SEQUENCE" ADD CONSTRAINT "UQ_CA_META_SEQUENCE_NAME" UNIQUE("name");

SET IDENTITY_INSERT "CA_META_SEQUENCE" ON;
INSERT INTO "CA_META_SEQUENCE" ("id", "caption", "createdDate", "currentTime", "cycle", "description", "format", "incrementBy", "lastModifiedDate", "maxValue", "minValue", "name", "nextValue", "sequenceType") VALUES
(1, '角色编码', '2021-02-01 11:17:34', 0, NULL, NULL, 'ROLE_%09d', 1, '2021-02-05 17:53:05', 999999999, 1, 'roleCode', 8, 'STRING'),
(2, '资源编码', '2021-02-02 10:06:15', 0, NULL, NULL, 'RESOURCE_%09d', 1, '2021-02-02 10:06:15', 999999999, 1, 'resourceCode', 8, 'STRING'),
(3, '会员流水号', '2021-07-25 21:24:48', 0, NULL, NULL, '%018d', 1, '2021-07-25 21:28:07', 9999999999, 1, 'membershipCode', 17, 'STRING');
SET IDENTITY_INSERT "CA_META_SEQUENCE" OFF;
