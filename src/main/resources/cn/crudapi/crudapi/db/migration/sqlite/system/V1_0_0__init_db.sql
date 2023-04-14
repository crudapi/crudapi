CREATE TABLE `ca_system_data_source` (
  `id` INTEGER CONSTRAINT `pk_id` PRIMARY KEY AUTOINCREMENT NOT NULL,
  `name` VARCHAR(255) CONSTRAINT `uk_name` UNIQUE NOT NULL,
  `caption` VARCHAR(255) CONSTRAINT `uk_caption` UNIQUE NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `display_order` INTEGER NOT NULL DEFAULT 0,
  `create_time` DATETIME NOT NULL DEFAULT (datetime('now', 'localtime')),
  `update_time` DATETIME NOT NULL DEFAULT (datetime('now', 'localtime')),
  `owner_id` INTEGER DEFAULT NULL,
  `create_by_id` INTEGER DEFAULT NULL,
  `update_by_id` INTEGER DEFAULT NULL,
  `status` VARCHAR(255) NOT NULL DEFAULT 'ACTIVE',
  `is_deleted` BOOLEAN NOT NULL DEFAULT false,
  `database_type` VARCHAR(255) NOT NULL,
  `driver_class_name` VARCHAR(255) NOT NULL,
  `url` VARCHAR(1000) NOT NULL,
  `username` VARCHAR(255) DEFAULT NULL,
  `password` VARCHAR(255) DEFAULT NULL,
  `metadata_table_prefix` VARCHAR(255) DEFAULT 'ca_m_',
  `metadata_database_naming` VARCHAR(255) DEFAULT 'LOWER_UNDERSCORE',
  `business_table_prefix` VARCHAR(255) DEFAULT 'ca_b_',
  `business_database_naming` VARCHAR(255) DEFAULT 'LOWER_UNDERSCORE'
);

INSERT INTO `ca_system_data_source` (`name`, `caption`, `display_order`, `status`, `is_deleted`, `database_type`, `driver_class_name`, `url`, `username`, `password`)
VALUES ('database-mysql-crudapi3', 'MYSQL_CRUDAPI3', 1, 'INACTIVE', false, 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://localhost:3306/crudapi3?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true', 'root', 'root@Mysql');

INSERT INTO `ca_system_data_source` (`name`, `caption`, `display_order`, `status`, `is_deleted`, `database_type`, `driver_class_name`, `url`, `username`, `password`)
VALUES ('database-mysql-crudapi4', 'MYSQL_CRUDAPI4', 2, 'INACTIVE', false, 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://localhost:3306/crudapi4?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true', 'root', 'root@Mysql');

INSERT INTO `ca_system_data_source` (`name`, `caption`, `display_order`, `status`, `is_deleted`, `database_type`, `driver_class_name`, `url`, `username`, `password`)
VALUES ('database-postsql-crudapi3', 'POSTSQL_CRUDAPI3', 3, 'ACTIVE', false, 'postsql', 'org.postgresql.Driver', 'jdbc:postgresql://localhost:5432/crudapi3', 'postgres', 'postgres');

INSERT INTO `ca_system_data_source` (`name`, `caption`, `display_order`, `status`, `is_deleted`, `database_type`, `driver_class_name`, `url`, `username`, `password`)
VALUES ('database-postsql-crudapi4', 'POSTSQL_CRUDAPI4', 4, 'ACTIVE', false, 'postsql', 'org.postgresql.Driver', 'jdbc:postgresql://localhost:5432/crudapi4', 'postgres', 'postgres');

INSERT INTO `ca_system_data_source` (`name`, `caption`, `display_order`, `status`, `is_deleted`, `database_type`, `driver_class_name`, `url`, `username`, `password`)
VALUES ('database-mssql-crudapi', 'MSSQL_CRUDAPI',  5, 'INACTIVE', false, 'mssql', 'com.microsoft.sqlserver.jdbc.SQLServerDriver', 'jdbc:sqlserver://localhost:1433;SelectMethod=cursor;DatabaseName=crudapi', 'sa', 'sa');

INSERT INTO `ca_system_data_source` (`name`, `caption`, `display_order`, `status`, `is_deleted`, `database_type`, `driver_class_name`, `url`, `username`, `password`)
VALUES ('database-oracle-XEPDB1', 'ORACLE_CRUDAPI', 6, 'INACTIVE', false, 'oracle', 'oracle.jdbc.OracleDriver', 'jdbc:oracle:thin:@//localhost:1521/XEPDB1', 'crudapi', 'crudapi');

INSERT INTO `ca_system_data_source` (`name`, `caption`, `display_order`, `status`, `is_deleted`, `database_type`, `driver_class_name`, `url`, `username`, `password`)
VALUES ('database-sqlite-crudapi', 'SQLITE_CRUDAPI', 7, 'ACTIVE', false, 'sqlite', 'org.sqlite.JDBC', 'jdbc:sqlite:crudapi.db', '', '');


CREATE TABLE `ca_system_config` (
  `id` INTEGER CONSTRAINT `pk_id` PRIMARY KEY AUTOINCREMENT NOT NULL,
  `name` VARCHAR(255) CONSTRAINT `uk_name` UNIQUE NOT NULL,
  `caption` VARCHAR(255) CONSTRAINT `uk_caption` UNIQUE NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `display_order` INTEGER NOT NULL DEFAULT 0,
  `create_time` DATETIME NOT NULL DEFAULT (datetime('now', 'localtime')),
  `update_time` DATETIME NOT NULL DEFAULT (datetime('now', 'localtime')),
  `owner_id` INTEGER DEFAULT NULL,
  `create_by_id` INTEGER DEFAULT NULL,
  `update_by_id` INTEGER DEFAULT NULL,
  `status` VARCHAR(255) NOT NULL DEFAULT 'ACTIVE',
  `is_deleted` BOOLEAN NOT NULL DEFAULT false,
  `is_default` BOOLEAN NOT NULL DEFAULT false,
  `api_resource_naming` VARCHAR(255) DEFAULT 'LOWER_HYPHEN',
  `api_param_naming` VARCHAR(255) DEFAULT 'LOWER_UNDERSCORE',
  `object_naming` VARCHAR(255) DEFAULT 'LOWER_CAMEL'
);

INSERT INTO `ca_system_config` (`name`, `caption`, `display_order`, `is_default`)
VALUES ('default', 'DEFAULT', 1, true);


