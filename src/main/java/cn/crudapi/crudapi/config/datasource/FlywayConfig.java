package cn.crudapi.crudapi.config.datasource;

import java.sql.DatabaseMetaData;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.flywaydb.core.api.migration.JavaMigration;
import org.flywaydb.core.internal.plugin.PluginRegister;
import org.flywaydb.database.sqlserver.SQLServerConfigurationExtension;
import org.flywaydb.core.api.Location;
import org.flywaydb.core.api.callback.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties(FlywayProperties.class)
@ConfigurationProperties(prefix = "spring.flyway.hikari")
public class FlywayConfig {
	private static final Logger log = LoggerFactory.getLogger(FlywayConfig.class);
	
	private List<Map<String, FlywayProperties>> dataSources;
    
 	@Autowired
    private DynamicDataSourceProvider dynamicDataSourceProvider;

    @Bean
    public void migrate() {
    	log.info("[Flyway migrate]begin migrate multi datasource...");
    	Map<String, FlywayProperties> flywayPropertiesMap = getFlywayPropertiesMap();
    	
        Map<Object, Object> dataSources = dynamicDataSourceProvider.getTargetDataSourcesMap();
        Map<String, String> dataSourceNameDataBaseTypesMap = dynamicDataSourceProvider.getTargetDataSourceNameDataBaseTypesMap();
        
        
        if (dataSources != null) {
        	dataSources.forEach((k, v) -> {
            	String datasourceName = k.toString();
            	String databaseType = dataSourceNameDataBaseTypesMap.get(datasourceName);
            	log.info("[Flyway migrate]datasource = {}, databaseType = {}", datasourceName, databaseType);
            	
            	
            	FlywayProperties properties = flywayPropertiesMap.get(databaseType);
            	if (properties != null) {
            		log.info("[Flyway migrate]datasource " + datasourceName + " execute flyway migrate!");
            		
            		DataSource dataSource = (DataSource)v;
                    
                    FluentConfiguration fluentConfiguration = Flyway.configure().dataSource(dataSource);
                    configureProperties(fluentConfiguration, properties); 
                    
                    Location[] locations = fluentConfiguration.getLocations();
                    for (Location location: locations) {
                    	 log.info("[Flyway migrate]datasource " + datasourceName + " locations = " + location.toString());
                    }
                   
                    Flyway flyway = fluentConfiguration.load();
                    flyway.migrate();
            	} else {
            		log.info("[Flyway migrate]datasource " + datasourceName + " skip flyway migrate!");
            	}
            });
        } else {
        	log.info("[Flyway migrate]migrate multi datasources is null, skip!");
        }
        
        
        log.info("[Flyway migrate]migrate multi datasource is done!");
    }

	public List<Map<String, FlywayProperties>> getDataSources() {
		return dataSources;
	}

	public void setDataSources(List<Map<String, FlywayProperties>> dataSources) {
		this.dataSources = dataSources;
	}
	
 	private Map<String, FlywayProperties> getFlywayPropertiesMap() {
     	Map<String, FlywayProperties> flywayPropertiesMap = new HashMap<>();
     	if (dataSources != null) {
             dataSources.forEach(map -> {
                 Set<String> keys = map.keySet();
                 keys.forEach(key -> {
                	 FlywayProperties properties = map.get(key);
                	 flywayPropertiesMap.put(key, properties);
                 });
             });
     	}
         
        return flywayPropertiesMap;
    }
 	
 	@SuppressWarnings("deprecation")
	private void configureIgnoredMigrations(FluentConfiguration configuration, FlywayProperties properties,
			PropertyMapper map) {
		map.from(properties.isIgnoreMissingMigrations()).to(configuration::ignoreMissingMigrations);
		map.from(properties.isIgnoreIgnoredMigrations()).to(configuration::ignoreIgnoredMigrations);
		map.from(properties.isIgnorePendingMigrations()).to(configuration::ignorePendingMigrations);
		map.from(properties.isIgnoreFutureMigrations()).to(configuration::ignoreFutureMigrations);
	}

 	
 	private void configureFailOnMissingLocations(FluentConfiguration configuration,
			boolean failOnMissingLocations) {
		try {
			configuration.failOnMissingLocations(failOnMissingLocations);
		}
		catch (NoSuchMethodError ex) {
			// Flyway < 7.9
		}
	}

	private void configureCreateSchemas(FluentConfiguration configuration, boolean createSchemas) {
		try {
			configuration.createSchemas(createSchemas);
		}
		catch (NoSuchMethodError ex) {
			// Flyway < 6.5
		}
	}

	private void configureSqlServerKerberosLoginFile(String sqlServerKerberosLoginFile) {
		SQLServerConfigurationExtension sqlServerConfigurationExtension = PluginRegister
				.getPlugin(SQLServerConfigurationExtension.class);
		sqlServerConfigurationExtension.setKerberosLoginFile(sqlServerKerberosLoginFile);
	}

	private void configureValidateMigrationNaming(FluentConfiguration configuration,
			boolean validateMigrationNaming) {
		try {
			configuration.validateMigrationNaming(validateMigrationNaming);
		}
		catch (NoSuchMethodError ex) {
			// Flyway < 6.2
		}
	}

	private void configureCallbacks(FluentConfiguration configuration, List<Callback> callbacks) {
		if (!callbacks.isEmpty()) {
			configuration.callbacks(callbacks.toArray(new Callback[0]));
		}
	}

	private void configureFlywayCallbacks(FluentConfiguration flyway, List<Callback> callbacks) {
		if (!callbacks.isEmpty()) {
			flyway.callbacks(callbacks.toArray(new Callback[0]));
		}
	}

	private void configureJavaMigrations(FluentConfiguration flyway, List<JavaMigration> migrations) {
		if (!migrations.isEmpty()) {
			try {
				flyway.javaMigrations(migrations.toArray(new JavaMigration[0]));
			}
			catch (NoSuchMethodError ex) {
				// Flyway 5.x
			}
		}
	}
 	
 	private void configureProperties(FluentConfiguration configuration, FlywayProperties properties) {
 		PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
		String[] locations = new LocationResolver(configuration.getDataSource())
				.resolveLocations(properties.getLocations()).toArray(new String[0]);
		configureFailOnMissingLocations(configuration, properties.isFailOnMissingLocations());
		map.from(locations).to(configuration::locations);
		map.from(properties.getEncoding()).to(configuration::encoding);
		map.from(properties.getConnectRetries()).to(configuration::connectRetries);
		// No method reference for compatibility with Flyway < 7.15
		map.from(properties.getConnectRetriesInterval())
				.to((interval) -> configuration.connectRetriesInterval((int) interval.getSeconds()));
		// No method reference for compatibility with Flyway 6.x
		map.from(properties.getLockRetryCount())
				.to((lockRetryCount) -> configuration.lockRetryCount(lockRetryCount));
		// No method reference for compatibility with Flyway 5.x
		map.from(properties.getDefaultSchema()).to((schema) -> configuration.defaultSchema(schema));
		map.from(properties.getSchemas()).as(StringUtils::toStringArray).to(configuration::schemas);
		configureCreateSchemas(configuration, properties.isCreateSchemas());
		map.from(properties.getTable()).to(configuration::table);
		// No method reference for compatibility with Flyway 5.x
		map.from(properties.getTablespace()).to((tablespace) -> configuration.tablespace(tablespace));
		map.from(properties.getBaselineDescription()).to(configuration::baselineDescription);
		map.from(properties.getBaselineVersion()).to(configuration::baselineVersion);
		map.from(properties.getInstalledBy()).to(configuration::installedBy);
		map.from(properties.getPlaceholders()).to(configuration::placeholders);
		map.from(properties.getPlaceholderPrefix()).to(configuration::placeholderPrefix);
		map.from(properties.getPlaceholderSuffix()).to(configuration::placeholderSuffix);
		// No method reference for compatibility with Flyway version < 8.0
		map.from(properties.getPlaceholderSeparator())
				.to((placeHolderSeparator) -> configuration.placeholderSeparator(placeHolderSeparator));
		map.from(properties.isPlaceholderReplacement()).to(configuration::placeholderReplacement);
		map.from(properties.getSqlMigrationPrefix()).to(configuration::sqlMigrationPrefix);
		map.from(properties.getSqlMigrationSuffixes()).as(StringUtils::toStringArray)
				.to(configuration::sqlMigrationSuffixes);
		map.from(properties.getSqlMigrationSeparator()).to(configuration::sqlMigrationSeparator);
		map.from(properties.getRepeatableSqlMigrationPrefix()).to(configuration::repeatableSqlMigrationPrefix);
		map.from(properties.getTarget()).to(configuration::target);
		map.from(properties.isBaselineOnMigrate()).to(configuration::baselineOnMigrate);
		map.from(properties.isCleanDisabled()).to(configuration::cleanDisabled);
		map.from(properties.isCleanOnValidationError()).to(configuration::cleanOnValidationError);
		map.from(properties.isGroup()).to(configuration::group);
		configureIgnoredMigrations(configuration, properties, map);
		map.from(properties.isMixed()).to(configuration::mixed);
		map.from(properties.isOutOfOrder()).to(configuration::outOfOrder);
		map.from(properties.isSkipDefaultCallbacks()).to(configuration::skipDefaultCallbacks);
		map.from(properties.isSkipDefaultResolvers()).to(configuration::skipDefaultResolvers);
		configureValidateMigrationNaming(configuration, properties.isValidateMigrationNaming());
		map.from(properties.isValidateOnMigrate()).to(configuration::validateOnMigrate);
		map.from(properties.getInitSqls()).whenNot(CollectionUtils::isEmpty)
				.as((initSqls) -> StringUtils.collectionToDelimitedString(initSqls, "\n"))
				.to(configuration::initSql);
		map.from(properties.getScriptPlaceholderPrefix())
				.to((prefix) -> configuration.scriptPlaceholderPrefix(prefix));
		map.from(properties.getScriptPlaceholderSuffix())
				.to((suffix) -> configuration.scriptPlaceholderSuffix(suffix));
		// Pro properties
		map.from(properties.getBatch()).to(configuration::batch);
		map.from(properties.getDryRunOutput()).to(configuration::dryRunOutput);
		map.from(properties.getErrorOverrides()).to(configuration::errorOverrides);
		map.from(properties.getLicenseKey()).to(configuration::licenseKey);
		map.from(properties.getOracleSqlplus()).to(configuration::oracleSqlplus);
		// No method reference for compatibility with Flyway 5.x
		map.from(properties.getOracleSqlplusWarn())
				.to((oracleSqlplusWarn) -> configuration.oracleSqlplusWarn(oracleSqlplusWarn));
		map.from(properties.getStream()).to(configuration::stream);
		map.from(properties.getUndoSqlMigrationPrefix()).to(configuration::undoSqlMigrationPrefix);
		// No method reference for compatibility with Flyway 6.x
		map.from(properties.getCherryPick()).to((cherryPick) -> configuration.cherryPick(cherryPick));
		// No method reference for compatibility with Flyway 6.x
		map.from(properties.getJdbcProperties()).whenNot(Map::isEmpty)
				.to((jdbcProperties) -> configuration.jdbcProperties(jdbcProperties));
		// No method reference for compatibility with Flyway 6.x
		map.from(properties.getKerberosConfigFile())
				.to((configFile) -> configuration.kerberosConfigFile(configFile));
		// No method reference for compatibility with Flyway 6.x
		map.from(properties.getOracleKerberosCacheFile())
				.to((cacheFile) -> configuration.oracleKerberosCacheFile(cacheFile));
		// No method reference for compatibility with Flyway 6.x
		map.from(properties.getOutputQueryResults())
				.to((outputQueryResults) -> configuration.outputQueryResults(outputQueryResults));
		map.from(properties.getSqlServerKerberosLoginFile()).whenNonNull()
				.to(this::configureSqlServerKerberosLoginFile);
		// No method reference for compatibility with Flyway 6.x
		map.from(properties.getSkipExecutingMigrations())
				.to((skipExecutingMigrations) -> configuration.skipExecutingMigrations(skipExecutingMigrations));
		// No method reference for compatibility with Flyway < 7.8
		map.from(properties.getIgnoreMigrationPatterns()).whenNot(List::isEmpty)
				.to((ignoreMigrationPatterns) -> configuration
						.ignoreMigrationPatterns(ignoreMigrationPatterns.toArray(new String[0])));
		// No method reference for compatibility with Flyway version < 7.9
		map.from(properties.getDetectEncoding())
				.to((detectEncoding) -> configuration.detectEncoding(detectEncoding));
		// No method reference for compatibility with Flyway version < 8.0
		map.from(properties.getBaselineMigrationPrefix())
				.to((baselineMigrationPrefix) -> configuration.baselineMigrationPrefix(baselineMigrationPrefix));
 	}
 	
 	private static class LocationResolver {

		private static final String VENDOR_PLACEHOLDER = "{vendor}";

		private final DataSource dataSource;

		LocationResolver(DataSource dataSource) {
			this.dataSource = dataSource;
		}

		List<String> resolveLocations(List<String> locations) {
			if (usesVendorLocation(locations)) {
				DatabaseDriver databaseDriver = getDatabaseDriver();
				return replaceVendorLocations(locations, databaseDriver);
			}
			return locations;
		}

		private List<String> replaceVendorLocations(List<String> locations, DatabaseDriver databaseDriver) {
			if (databaseDriver == DatabaseDriver.UNKNOWN) {
				return locations;
			}
			String vendor = databaseDriver.getId();
			return locations.stream().map((location) -> location.replace(VENDOR_PLACEHOLDER, vendor))
					.collect(Collectors.toList());
		}

		private DatabaseDriver getDatabaseDriver() {
			try {
				String url = JdbcUtils.extractDatabaseMetaData(this.dataSource, DatabaseMetaData::getURL);
				return DatabaseDriver.fromJdbcUrl(url);
			}
			catch (MetaDataAccessException ex) {
				throw new IllegalStateException(ex);
			}

		}

		private boolean usesVendorLocation(Collection<String> locations) {
			for (String location : locations) {
				if (location.contains(VENDOR_PLACEHOLDER)) {
					return true;
				}
			}
			return false;
		}

	}
}