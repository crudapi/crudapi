DROP TABLE IF EXISTS `DATA_SOURCE`;
CREATE TABLE `DATA_SOURCE` (
  `ID` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  `NAME` VARCHAR(255) CONSTRAINT UQ_NAME UNIQUE NOT NULL,
  `CAPTION` VARCHAR(255) CONSTRAINT UQ_CAPTION UNIQUE NOT NULL,
  `DATABASE_TYPE` VARCHAR(255) NOT NULL,
  `DRIVER_CLASS_NAME` VARCHAR(255) NOT NULL,
  `URL` VARCHAR(1000) NOT NULL,
  `USERNAME` VARCHAR(255) DEFAULT NULL,
  `PASSWORD` VARCHAR(255) DEFAULT NULL,
  `DISPLAY_ORDER` INTEGER DEFAULT 0,
  `CREATED_DATE` DATETIME DEFAULT NULL,
  `LAST_MODIFIED_DATE` DATETIME DEFAULT NULL,
  `CREATE_BY_ID` INTEGER DEFAULT NULL,
  `UPDATE_BY_ID` INTEGER DEFAULT NULL,
  `OWNER_ID` INTEGER DEFAULT NULL,
  `STATUS` VARCHAR(255) DEFAULT 'ACTIVE',
  `DELETED` BOOLEAN DEFAULT false
);

INSERT INTO `DATA_SOURCE` (`NAME`, `CAPTION`, `DATABASE_TYPE`, `DRIVER_CLASS_NAME`, `URL`, `USERNAME`, `PASSWORD`, `DISPLAY_ORDER`, `STATUS`, `DELETED`)
VALUES ('database-mysql-crudapi3', 'MYSQL_CRUDAPI3', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://localhost:3306/crudapi3?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true', 'root', 'root@Mysql', 1, 'INACTIVE', false);

INSERT INTO `DATA_SOURCE` (`NAME`, `CAPTION`, `DATABASE_TYPE`, `DRIVER_CLASS_NAME`, `URL`, `USERNAME`, `PASSWORD`, `DISPLAY_ORDER`, `STATUS`, `DELETED`)
VALUES ('database-mysql-crudapi4', 'MYSQL_CRUDAPI4', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://localhost:3306/crudapi4?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true', 'root', 'root@Mysql', 2, 'INACTIVE', false);

INSERT INTO `DATA_SOURCE` (`NAME`, `CAPTION`, `DATABASE_TYPE`, `DRIVER_CLASS_NAME`, `URL`, `USERNAME`, `PASSWORD`, `DISPLAY_ORDER`, `STATUS`, `DELETED`)
VALUES ('database-postsql-crudapi3', 'POSTSQL_CRUDAPI3', 'postsql', 'org.postgresql.Driver', 'jdbc:postgresql://localhost:5432/crudapi3', 'postgres', 'postgres', 3, 'ACTIVE', false);

INSERT INTO `DATA_SOURCE` (`NAME`, `CAPTION`, `DATABASE_TYPE`, `DRIVER_CLASS_NAME`, `URL`, `USERNAME`, `PASSWORD`, `DISPLAY_ORDER`, `STATUS`, `DELETED`)
VALUES ('database-postsql-crudapi4', 'POSTSQL_CRUDAPI4', 'postsql', 'org.postgresql.Driver', 'jdbc:postgresql://localhost:5432/crudapi4', 'postgres', 'postgres', 4, 'ACTIVE', false);

INSERT INTO `DATA_SOURCE` (`NAME`, `CAPTION`, `DATABASE_TYPE`, `DRIVER_CLASS_NAME`, `URL`, `USERNAME`, `PASSWORD`, `DISPLAY_ORDER`, `STATUS`, `DELETED`)
VALUES ('database-mssql-crudapi', 'MSSQL_CRUDAPI', 'mssql', 'com.microsoft.sqlserver.jdbc.SQLServerDriver', 'jdbc:sqlserver://localhost:1433;SelectMethod=cursor;DatabaseName=crudapi', 'sa', 'sa', 5, 'INACTIVE', false);

INSERT INTO `DATA_SOURCE` (`NAME`, `CAPTION`, `DATABASE_TYPE`, `DRIVER_CLASS_NAME`, `URL`, `USERNAME`, `PASSWORD`, `DISPLAY_ORDER`, `STATUS`, `DELETED`)
VALUES ('database-oracle-XEPDB1', 'ORACLE_CRUDAPI', 'oracle', 'oracle.jdbc.OracleDriver', 'jdbc:oracle:thin:@//localhost:1521/XEPDB1', 'crudapi', 'crudapi', 6, 'INACTIVE', false);

INSERT INTO `DATA_SOURCE` (`NAME`, `CAPTION`, `DATABASE_TYPE`, `DRIVER_CLASS_NAME`, `URL`, `USERNAME`, `PASSWORD`, `DISPLAY_ORDER`, `STATUS`, `DELETED`)
VALUES ('database-sqlite-crudapi', 'SQLITE_CRUDAPI', 'sqlite', 'org.sqlite.JDBC', 'jdbc:sqlite:crudapi2.db', '', '', 7, 'INACTIVE', false);

