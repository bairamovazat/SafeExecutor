package ru.ivmiit.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"ru.ivmiit.web.repository"})
@ComponentScan(basePackages = "ru.ivmiit.web.model")
public class JpaConfiguration {

    @Value("${db.driverClassName}")
    private String driverClassName;

    @Value("${db.url}")
    private String url;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setPackagesToScan("ru.ivmiit.web.model");
        factoryBean.setDataSource(driverManagerDataSource());
        factoryBean.setBootstrapExecutor(new SimpleAsyncTaskExecutor("jpa-bootstrap"));
        factoryBean.setJpaPropertyMap(getJpaPropertyMap());
        return factoryBean;
    }

    @Bean
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    @Bean
    public DriverManagerDataSource driverManagerDataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(driverClassName);
        driverManagerDataSource.setUrl(url);
        driverManagerDataSource.setUsername(username);
        driverManagerDataSource.setPassword(password);
        return driverManagerDataSource;
    }


    @Bean(name = "transactionManager")
    public PlatformTransactionManager testTransactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    private Map<String, Object> getJpaPropertyMap() {
        Map<String, Object> jpaProperties = new HashMap<>();

        jpaProperties.put("hibernate.format_sql", true);

        jpaProperties.put("hibernate.hbm2ddl.auto", "update");

        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");

        jpaProperties.put("hibernate.max_fetch_depth", "10");
        jpaProperties.put("spring.datasource.sql-script-encoding", "UTF-8");
        jpaProperties.put("connection.useUnicode", "true");
        jpaProperties.put("connection.characterEncoding", "UTF-8");
        jpaProperties.put("connection.charSet", "UTF-8");

        jpaProperties.put("hibernate.cache.use_second_level_cache", true);
        jpaProperties.put("hibernate.cache.use_query_cache", true);
        jpaProperties.put("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory");
        return jpaProperties;
    }
}
