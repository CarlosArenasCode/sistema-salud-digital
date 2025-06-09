package com.clinica.salud.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Configuración de Base de Datos
 * Maneja diferentes perfiles (dev, test, prod) con configuraciones optimizadas
 */
@Configuration
public class DatabaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

    private final Environment environment;

    public DatabaseConfig(Environment environment) {
        this.environment = environment;
    }    /**
     * Configuración de DataSource para desarrollo
     */
    @Bean
    @Primary
    @Profile("dev")
    public DataSource devDataSource() {
        logger.info("Configurando DataSource para DESARROLLO");
        
        String jdbcUrl = environment.getProperty("spring.datasource.url");
        String username = environment.getProperty("spring.datasource.username");
        String password = environment.getProperty("spring.datasource.password");
        String driverClassName = environment.getProperty("spring.datasource.driver-class-name");
        
        // Para H2, el driver podría no estar especificado en el perfil
        if (driverClassName == null) {
            driverClassName = "org.h2.Driver";
        }
        
        HikariConfig config = createBaseHikariConfig(jdbcUrl, username, password, driverClassName);
        
        // Configuración específica para desarrollo
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(20000);
        config.setIdleTimeout(300000);
        config.setMaxLifetime(1200000);
        config.setLeakDetectionThreshold(60000);
        
        // Configuración de debugging para desarrollo
        config.addDataSourceProperty("loggerLevel", "DEBUG");
        config.addDataSourceProperty("loggerFile", "HikariCP-dev.log");
        
        return new HikariDataSource(config);
    }

    /**
     * Configuración de DataSource para testing
     */
    @Bean
    @Primary
    @Profile("test")
    public DataSource testDataSource() {
        logger.info("Configurando DataSource para TESTING");
        
        String jdbcUrl = environment.getProperty("spring.datasource.url");
        String username = environment.getProperty("spring.datasource.username");
        String password = environment.getProperty("spring.datasource.password");
        String driverClassName = environment.getProperty("spring.datasource.driver-class-name");
        
        // Para H2, el driver podría no estar especificado en el perfil
        if (driverClassName == null) {
            driverClassName = "org.h2.Driver";
        }
        
        HikariConfig config = createBaseHikariConfig(jdbcUrl, username, password, driverClassName);
        
        // Configuración específica para testing
        config.setMaximumPoolSize(5);
        config.setMinimumIdle(1);
        config.setConnectionTimeout(10000);
        config.setIdleTimeout(120000);
        config.setMaxLifetime(600000);
        
        return new HikariDataSource(config);
    }/**
     * Configuración de DataSource para producción
     */
    @Bean
    @Primary
    @Profile("prod")
    public DataSource prodDataSource() {
        logger.info("Configurando DataSource para PRODUCCIÓN");
        
        String jdbcUrl = environment.getProperty("spring.datasource.url");
        String username = environment.getProperty("spring.datasource.username");
        String password = environment.getProperty("spring.datasource.password");
        String driverClassName = environment.getProperty("spring.datasource.driver-class-name");
        
        HikariConfig config = createBaseHikariConfig(jdbcUrl, username, password, driverClassName);
        
        // Configuración específica para producción
        config.setMaximumPoolSize(50);
        config.setMinimumIdle(10);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);
        config.setLeakDetectionThreshold(120000);
        
        // Configuraciones de seguridad para producción
        config.addDataSourceProperty("ssl", "true");
        config.addDataSourceProperty("sslmode", "require");
        config.addDataSourceProperty("prepareThreshold", "3");
        
        // Configuraciones de rendimiento para producción
        config.addDataSourceProperty("reWriteBatchedInserts", "true");
        config.addDataSourceProperty("defaultRowFetchSize", "50");
        
        return new HikariDataSource(config);    }
      /**
     * Configuración de DataSource para PostgreSQL
     */
    @Bean
    @Primary
    @Profile("postgresql")
    public DataSource postgresqlDataSource() {
        logger.info("Configurando DataSource para POSTGRESQL");
        
        String jdbcUrl = environment.getProperty("spring.datasource.url");
        String username = environment.getProperty("spring.datasource.username");
        String password = environment.getProperty("spring.datasource.password");
        String driverClassName = environment.getProperty("spring.datasource.driver-class-name");
        
        logger.debug("Propiedades PostgreSQL - URL: {}, Usuario: {}, Driver: {}", jdbcUrl, username, driverClassName);
        
        // Validar que las propiedades no sean nulas
        if (jdbcUrl == null || username == null || password == null || driverClassName == null) {
            logger.error("Propiedades de base de datos faltantes: URL={}, Usuario={}, Driver={}", 
                jdbcUrl, username, driverClassName);
            throw new IllegalStateException("Configuración de base de datos incompleta para PostgreSQL");
        }
        
        HikariConfig config = createBaseHikariConfig(jdbcUrl, username, password, driverClassName);
        
        // Configuración específica para PostgreSQL
        config.setMaximumPoolSize(20);
        config.setMinimumIdle(5);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);
        config.setLeakDetectionThreshold(120000);
        
        // Configuraciones específicas de PostgreSQL
        config.addDataSourceProperty("ApplicationName", "SistemasSaludDigital");
        config.addDataSourceProperty("tcpKeepAlive", "true");
        config.addDataSourceProperty("socketTimeout", "30");
        config.addDataSourceProperty("loginTimeout", "10");
        config.addDataSourceProperty("prepareThreshold", "3");
        config.addDataSourceProperty("reWriteBatchedInserts", "true");
        
        return new HikariDataSource(config);
    }/**
     * Configuración base de HikariCP
     */
    private HikariConfig createBaseHikariConfig(String jdbcUrl, String username, String password, String driverClassName) {
        HikariConfig config = new HikariConfig();
        
        // Configuración básica
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driverClassName);
        
        // Pool name
        config.setPoolName("SaludDigital-" + String.join(",", environment.getActiveProfiles()));
        
        // Configuraciones comunes
        config.setAutoCommit(true);
        config.setConnectionTestQuery("SELECT 1");
        config.setValidationTimeout(5000);
        config.setInitializationFailTimeout(10000);
        
        // Configuraciones de conexión
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("useLocalSessionState", "true");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("cacheResultSetMetadata", "true");
        config.addDataSourceProperty("cacheServerConfiguration", "true");
        config.addDataSourceProperty("elideSetAutoCommits", "true");
        config.addDataSourceProperty("maintainTimeStats", "false");
        
        // Configuraciones específicas de PostgreSQL
        if (driverClassName != null && driverClassName.contains("postgresql")) {
            config.addDataSourceProperty("ApplicationName", "SistemasSaludDigital");
            config.addDataSourceProperty("tcpKeepAlive", "true");
            config.addDataSourceProperty("socketTimeout", "30");
            config.addDataSourceProperty("loginTimeout", "10");
        }
        
        logger.debug("HikariCP configurado con URL: {}", maskUrl(jdbcUrl));
        
        return config;
    }

    /**
     * Configuraciones JPA adicionales por perfil
     */
    @Bean
    @Profile("dev")
    public Properties devJpaProperties() {
        Properties props = new Properties();
        props.setProperty("hibernate.show_sql", "true");
        props.setProperty("hibernate.format_sql", "true");
        props.setProperty("hibernate.use_sql_comments", "true");
        props.setProperty("hibernate.generate_statistics", "true");
        return props;
    }    @Bean
    @Profile("prod")
    public Properties prodJpaProperties() {
        Properties props = new Properties();
        props.setProperty("hibernate.show_sql", "false");
        props.setProperty("hibernate.format_sql", "false");
        props.setProperty("hibernate.use_sql_comments", "false");
        props.setProperty("hibernate.generate_statistics", "false");
        props.setProperty("hibernate.cache.use_second_level_cache", "true");
        props.setProperty("hibernate.cache.use_query_cache", "true");
        props.setProperty("hibernate.jdbc.batch_size", "50");
        props.setProperty("hibernate.order_inserts", "true");
        props.setProperty("hibernate.order_updates", "true");
        return props;
    }

    @Bean
    @Profile("postgresql")
    public Properties postgresqlJpaProperties() {
        Properties props = new Properties();
        props.setProperty("hibernate.show_sql", "true");
        props.setProperty("hibernate.format_sql", "true");
        props.setProperty("hibernate.use_sql_comments", "true");
        props.setProperty("hibernate.generate_statistics", "true");
        props.setProperty("hibernate.jdbc.batch_size", "25");
        props.setProperty("hibernate.order_inserts", "true");
        props.setProperty("hibernate.order_updates", "true");
        return props;
    }

    /**
     * Enmascara la URL para logging seguro
     */
    private String maskUrl(String url) {
        if (url == null) return "null";
        return url.replaceAll("password=[^&;]*", "password=***");
    }
}