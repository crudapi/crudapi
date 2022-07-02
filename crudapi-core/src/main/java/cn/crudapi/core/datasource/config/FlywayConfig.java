package cn.crudapi.core.datasource.config;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.flywaydb.core.api.Location;
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
    	log.info("begin migrate multi datasource...");
    	Map<String, FlywayProperties> flywayPropertiesMap = getFlywayPropertiesMap();
    	
        Map<Object, Object> dataSources = dynamicDataSourceProvider.getTargetDataSourcesMap();
        dataSources.forEach((k, v) -> {
        	String datasourceName = k.toString();
        	log.info("datasource = " + datasourceName);
        	FlywayProperties properties = flywayPropertiesMap.get(k);
        	if (properties != null) {
        		log.info("datasource " + datasourceName + " do flyway migrate!");
        		
        		DataSource dataSource = (DataSource)v;
                
                FluentConfiguration fluentConfiguration = Flyway.configure().dataSource(dataSource);
                configureProperties(fluentConfiguration, properties); 
                
                Location[] locations = fluentConfiguration.getLocations();
                for (Location location: locations) {
                	 log.info("datasource " + datasourceName + " locations = " + location.toString());
                }
               
                Flyway flyway = fluentConfiguration.load();
                flyway.migrate();
        	} else {
        		log.info("datasource " + datasourceName + " skip flyway migrate!");
        	}
        });
        
        log.info("migrate multi datasource is done!");
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
 	
 	private void configureProperties(FluentConfiguration configuration, FlywayProperties properties) {
		PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
		String[] locations = new LocationResolver(configuration.getDataSource())
				.resolveLocations(properties.getLocations()).toArray(new String[0]);
		map.from(locations).to(configuration::locations);
		map.from(properties.getEncoding()).to(configuration::encoding);
		map.from(properties.getConnectRetries()).to(configuration::connectRetries);
		map.from(properties.getSchemas()).as(StringUtils::toStringArray).to(configuration::schemas);
		map.from(properties.getTable()).to(configuration::table);
		// No method reference for compatibility with Flyway 5.x
		map.from(properties.getTablespace()).whenNonNull().to((tablespace) -> configuration.tablespace(tablespace));
		map.from(properties.getBaselineDescription()).to(configuration::baselineDescription);
		map.from(properties.getBaselineVersion()).to(configuration::baselineVersion);
		map.from(properties.getInstalledBy()).to(configuration::installedBy);
		map.from(properties.getPlaceholders()).to(configuration::placeholders);
		map.from(properties.getPlaceholderPrefix()).to(configuration::placeholderPrefix);
		map.from(properties.getPlaceholderSuffix()).to(configuration::placeholderSuffix);
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
		map.from(properties.isIgnoreMissingMigrations()).to(configuration::ignoreMissingMigrations);
		map.from(properties.isIgnoreIgnoredMigrations()).to(configuration::ignoreIgnoredMigrations);
		map.from(properties.isIgnorePendingMigrations()).to(configuration::ignorePendingMigrations);
		map.from(properties.isIgnoreFutureMigrations()).to(configuration::ignoreFutureMigrations);
		map.from(properties.isMixed()).to(configuration::mixed);
		map.from(properties.isOutOfOrder()).to(configuration::outOfOrder);
		map.from(properties.isSkipDefaultCallbacks()).to(configuration::skipDefaultCallbacks);
		map.from(properties.isSkipDefaultResolvers()).to(configuration::skipDefaultResolvers);
		map.from(properties.isValidateOnMigrate()).to(configuration::validateOnMigrate);
		// Pro properties
		map.from(properties.getBatch()).whenNonNull().to(configuration::batch);
		map.from(properties.getDryRunOutput()).whenNonNull().to(configuration::dryRunOutput);
		map.from(properties.getErrorOverrides()).whenNonNull().to(configuration::errorOverrides);
		map.from(properties.getLicenseKey()).whenNonNull().to(configuration::licenseKey);
		map.from(properties.getOracleSqlplus()).whenNonNull().to(configuration::oracleSqlplus);
		// No method reference for compatibility with Flyway 5.x
		map.from(properties.getOracleSqlplusWarn()).whenNonNull()
				.to((oracleSqlplusWarn) -> configuration.oracleSqlplusWarn(oracleSqlplusWarn));
		map.from(properties.getStream()).whenNonNull().to(configuration::stream);
		map.from(properties.getUndoSqlMigrationPrefix()).whenNonNull().to(configuration::undoSqlMigrationPrefix);
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
				String url = JdbcUtils.extractDatabaseMetaData(this.dataSource, "getURL");
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