DROP TABLE IF EXISTS `data_source`;
CREATE TABLE `data_source` (
  `id` INTEGER CONSTRAINT `pk_id` PRIMARY KEY AUTOINCREMENT NOT NULL,
  `name` VARCHAR(255) CONSTRAINT `uk_name` UNIQUE NOT NULL,
  `caption` VARCHAR(255) CONSTRAINT `uk_caption` UNIQUE NOT NULL,
  `database_type` VARCHAR(255) NOT NULL,
  `driver_class_name` VARCHAR(255) NOT NULL,
  `url` VARCHAR(1000) NOT NULL,
  `username` VARCHAR(255) DEFAULT NULL,
  `password` VARCHAR(255) DEFAULT NULL,
  `display_order` INTEGER NOT NULL DEFAULT 0,
  `create_time` DATETIME NOT NULL DEFAULT (datetime('now', 'localtime')),
  `update_time` DATETIME NOT NULL DEFAULT (datetime('now', 'localtime')),
  `create_by_id` INTEGER DEFAULT NULL,
  `update_by_id` INTEGER DEFAULT NULL,
  `owner_id` INTEGER DEFAULT NULL,
  `status` VARCHAR(255) NOT NULL DEFAULT 'ACTIVE',
  `is_deleted` BOOLEAN NOT NULL DEFAULT false
);

INSERT INTO `data_source` (`name`, `caption`, `database_type`, `driver_class_name`, `url`, `username`, `password`, `display_order`, `status`, `is_deleted`)
VALUES ('database-mysql-crudapi3', 'MYSQL_CRUDAPI3', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://localhost:3306/crudapi3?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true', 'root', 'root@Mysql', 1, 'INACTIVE', false);

INSERT INTO `data_source` (`name`, `caption`, `database_type`, `driver_class_name`, `url`, `username`, `password`, `display_order`, `status`, `is_deleted`)
VALUES ('database-mysql-crudapi4', 'MYSQL_CRUDAPI4', 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://localhost:3306/crudapi4?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true', 'root', 'root@Mysql', 2, 'INACTIVE', false);

INSERT INTO `data_source` (`name`, `caption`, `database_type`, `driver_class_name`, `url`, `username`, `password`, `display_order`, `status`, `is_deleted`)
VALUES ('database-postsql-crudapi3', 'POSTSQL_CRUDAPI3', 'postsql', 'org.postgresql.Driver', 'jdbc:postgresql://localhost:5432/crudapi3', 'postgres', 'postgres', 3, 'ACTIVE', false);

INSERT INTO `data_source` (`name`, `caption`, `database_type`, `driver_class_name`, `url`, `username`, `password`, `display_order`, `status`, `is_deleted`)
VALUES ('database-postsql-crudapi4', 'POSTSQL_CRUDAPI4', 'postsql', 'org.postgresql.Driver', 'jdbc:postgresql://localhost:5432/crudapi4', 'postgres', 'postgres', 4, 'ACTIVE', false);

INSERT INTO `data_source` (`name`, `caption`, `database_type`, `driver_class_name`, `url`, `username`, `password`, `display_order`, `status`, `is_deleted`)
VALUES ('database-mssql-crudapi', 'MSSQL_CRUDAPI', 'mssql', 'com.microsoft.sqlserver.jdbc.SQLServerDriver', 'jdbc:sqlserver://localhost:1433;SelectMethod=cursor;DatabaseName=crudapi', 'sa', 'sa', 5, 'INACTIVE', false);

INSERT INTO `data_source` (`name`, `caption`, `database_type`, `driver_class_name`, `url`, `username`, `password`, `display_order`, `status`, `is_deleted`)
VALUES ('database-oracle-XEPDB1', 'ORACLE_CRUDAPI', 'oracle', 'oracle.jdbc.OracleDriver', 'jdbc:oracle:thin:@//localhost:1521/XEPDB1', 'crudapi', 'crudapi', 6, 'INACTIVE', false);

INSERT INTO `data_source` (`name`, `caption`, `database_type`, `driver_class_name`, `url`, `username`, `password`, `display_order`, `status`, `is_deleted`)
VALUES ('database-sqlite-crudapi', 'SQLITE_CRUDAPI', 'sqlite', 'org.sqlite.JDBC', 'jdbc:sqlite:crudapi2.db', '', '', 7, 'INACTIVE', false);

