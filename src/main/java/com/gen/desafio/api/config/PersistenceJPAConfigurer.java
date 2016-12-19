package com.gen.desafio.api.config;


import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement 							          // JPA 2.1
@EnableJpaRepositories(basePackages="com.gen.desafio.api.dal")    // Spring JPA DAOs
@ComponentScan({ "com.gen.desafio.api" })                         // Mapped Entities
@PropertySource("classpath:application.properties")               // System App Properties (db)
@Configuration
public class PersistenceJPAConfigurer extends JpaRepositoryConfigExtension {

	@Autowired
    private Environment env;
	
	
	/********** PERSISTENCE CONFIG  ***************/
 
	   @Bean // BASIC DATA SOURCE (JDBC)
	   public DataSource dataSource() {
		   
		   DriverManagerDataSource dataSource = new DriverManagerDataSource(); 
		    dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName")); 
	        
	        if(isONDevMode()){
	        	// DEV
	        	dataSource.setUsername(env.getProperty("jdbc.username")); 
	        	dataSource.setPassword(env.getProperty("jdbc.password")); 
		        dataSource.setUrl(env.getProperty("jdbc.url"));
		        System.out.println("Connecting to Local Database -> " + env.getProperty("jdbc.url").toString());
	        }
	        else{	       
		        // PROD (Heroku ClearDB)
	        	String herokuClearDBUrl = env.getProperty("hibernate.connection.heroku.url").toString();
	        	dataSource.setUsername(env.getProperty("hibernate.connection.username"));
	        	dataSource.setPassword(env.getProperty("hibernate.connection.password"));
	        	dataSource.setUrl(herokuClearDBUrl);
		        System.out.println("Connecting to Heroku Database -> " + herokuClearDBUrl);
	        }

	       return dataSource;
	   }


	   // HIBERNATE JPA PROPERTIES
	   protected Properties buildProperties() {
	       Properties hibernateProperties = new Properties();
	        hibernateProperties.setProperty("hibernate.connection.driver_class", env.getProperty("hibernate.connection.driver_class"));
	        hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
	        if(isONDevMode()){
	        	hibernateProperties.setProperty("hibernate.connection.url", env.getProperty("hibernate.connection.dev.url"));
	        }
	        else{
	        	hibernateProperties.setProperty("hibernate.connection.url", env.getProperty("hibernate.connection.heroku.url"));
	        	// Configure Pool Connections on PROD
	        	//hibernateProperties.setProperty("hibernate.c3p0.min_size", env.getProperty("hibernate.c3p0.min_size"));
		        //hibernateProperties.setProperty("hibernate.c3p0.max_size", env.getProperty("hibernate.c3p0.max_size"));
		        //hibernateProperties.setProperty("hibernate.c3p0.timeout", env.getProperty("hibernate.c3p0.timeout"));
		        //hibernateProperties.setProperty("hibernate.c3p0.max_statements", env.getProperty("hibernate.c3p0.max_statements"));
		        //hibernateProperties.setProperty("hibernate.c3p0.idle_test_period", env.getProperty("hibernate.c3p0.idle_test_period"));
	        }
	        hibernateProperties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
	        hibernateProperties.setProperty("hibernate.use_sql_comments", env.getProperty("hibernate.use_sql_comments"));
	        hibernateProperties.setProperty("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
	        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
	        hibernateProperties.setProperty("hibernate.hbm2ddl.import_files", env.getProperty("hibernate.hbm2ddl.import_files"));
	        hibernateProperties.setProperty("hibernate.hbm2ddl.import_files_sql_extractor", env.getProperty("hibernate.hbm2ddl.import_files_sql_extractor"));
	        hibernateProperties.setProperty("hibernate.generate_statistics", env.getProperty("hibernate.generate_statistics"));
	        hibernateProperties.setProperty("javax.persistence.validation.mode", env.getProperty("javax.persistence.validation.mode"));
	        hibernateProperties.setProperty("hibernate.globally_quoted_identifiers", env.getProperty("hibernate.globally_quoted_identifiers"));
	        //hibernateProperties.setProperty("hibernate.event.merge.entity_copy_observer", env.getProperty("hibernate.event.merge.entity_copy_observer"));

	       return hibernateProperties;
	   }

	   @Bean // TRANSACTION MANAGER
	   public PlatformTransactionManager transactionManager() {
		   JpaTransactionManager transactionManager = new JpaTransactionManager();
		    transactionManager.setEntityManagerFactory( this.entityManagerFactory() );
		    transactionManager.setDataSource( this.dataSource() );
		   
		   return transactionManager;
	   }
	   
	   @Bean // EXCEPTION TRANSLATION
	   public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
	       return new PersistenceExceptionTranslationPostProcessor();
	   }
	
	   @Bean // ENTITY MANAGER FACTORY
	   public EntityManagerFactory entityManagerFactory() {
		   HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
			 vendorAdapter.setGenerateDdl(Boolean.TRUE);
			 vendorAdapter.setShowSql(Boolean.TRUE);
			 
		  LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		    em.setDataSource(this.dataSource());
		    em.setPackagesToScan("com.gen.desafio.api.*");
		    em.setJpaVendorAdapter(vendorAdapter);
		    em.setJpaProperties(this.buildProperties());
		    em.afterPropertiesSet();
		    
		   System.out.println("Creating JPA EntityManager to work with Hibernate ORM on MySQL Database...");
		 
		   return em.getObject();
		}
	   
	   
	   /********** HIBERNATE CONFIG *******************
	   
	   @Bean(name="sessionFactory") // SESSION FACTORY (HIBERNATE)
	   public SessionFactory getSessionFactory() {
	       LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(this.dataSource());
	        sessionBuilder.addAnnotatedClasses(Client.class);
	        sessionBuilder.addAnnotatedClasses(Sector.class);
	        sessionBuilder.addAnnotatedClasses(User.class);
	        sessionBuilder.addAnnotatedClasses(AuthorityRole.class);
	        sessionBuilder.addAnnotatedClasses(UserPreferences.class);
		    sessionBuilder.addAnnotatedClasses(Ticket.class);
		    sessionBuilder.addAnnotatedClasses(Match.class);
		    sessionBuilder.addAnnotatedClasses(Team.class);
		    sessionBuilder.addAnnotatedClasses(Bet.class);
		    sessionBuilder.addAnnotatedClasses(Reward.class);
		    sessionBuilder.addAnnotatedClasses(UserBet.class);
		    sessionBuilder.addAnnotatedClasses(UserBetId.class);
		    sessionBuilder.addAnnotatedClasses(Post.class);
		    sessionBuilder.addAnnotatedClasses(Like.class);
	        sessionBuilder.addProperties(this.buildProperties()); // Hibernate 4 Props
	        
	       return sessionBuilder.buildSessionFactory();
	   }
	   
	   
	   @Bean(name="localSessionFactory")  // LOCAL SESSION FACTORY
	   public SessionFactory getLocalSessionFactory() {
	       LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
	        factoryBean.setDataSource(this.dataSource());
	        factoryBean.setHibernateProperties(this.buildProperties());
	        factoryBean.setPackagesToScan("com.gen.desafio.api.domain.model");
	        try {
				factoryBean.afterPropertiesSet();
			} catch (IOException e) {
				System.err.println("Could not set SessionFactory properties..." + e.getMessage());
			}
	        
	       return factoryBean.getObject();
	   }
	   

	   @Bean // HIBERNATE TEMPLATE
	   public HibernateTemplate getHibernateTemplate() {
	       HibernateTemplate hibernateTemplate = new HibernateTemplate( this.getLocalSessionFactory() );
	       return hibernateTemplate;
	   }
	   
	   *************** ENDS HIBERNATE CONFIG ***************/
	   
	   
	   private boolean isONDevMode(){
	    	boolean isDEV = false;
	    	isDEV = Boolean.valueOf(env.getProperty("context.api.devmode"));
	    	String environment = isDEV ? "DEV" : "PROD";
	    	System.out.println("Desafio API starting on "+environment+" Environment mode...");
	    	return isDEV;
	   }
	
}
