## resource naming
[restfulapi](https://restfulapi.net/resource-naming/)

## Config
### API Config
Name |  Value | Remark
--- | --- | ---
apiResourceNaming | LOWER_HYPHEN | 小写连字符
apiParamNaming | LOWER_UNDERSCORE | 小写下划线
objectNaming | LOWER_CAMEL | 小驼峰

### Database Config
Name |  Value | Remark
--- | --- | ---
metadataTablePrefix | ca_m_ | 元数据
businessTablePrefix | ca_b_ | 业务数据
metadataDatabaseNaming | LOWER_UNDERSCORE | 小写下划线
businessDatabaseNaming | LOWER_UNDERSCORE | 小写下划线


## API

### System
Name |  Method | Url
--- | --- | ---
获取数据源列表 | GET | /api/crudapi/system/data-sources
获取默认配置 | GET | /api/crudapi/system/configs/default

