package com.gen.desafio.api.dal.manager;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *  SINGLETON CONNECTION HELPER
 *  
 * @author GeronimoEzequiel
 */
public class JDBConnectionFactory {
	
   private static final Logger log = LoggerFactory.getLogger(JDBConnectionFactory.class);
   
   private volatile static JDBConnectionFactory instance;
   private static Connection connection;
   
   private static final String JDBC_PROPERTIES_FILENAME = "application.properties";
   private static final String HOST_URL_PROPERTY = "jdbc.url";
   private static  String DRIVER;      // = "com.mysql.jdbc.Driver";
   private static  String DATABASE;    // = "desafioapp";
   private static  String DB_USERNAME; // = "root";
   private static  String DB_PASSWORD; // = ""
   private static  String HOST_URL;    // =  "jdbc:mysql://localhost/" + DATABASE;

   
   /* Default Constructor */
   private JDBConnectionFactory() {}
   
   
   /* Singleton Synchronized Instance */
   public static JDBConnectionFactory getInstance() {
       if (instance == null) {
            synchronized (JDBConnectionFactory.class){
         if (instance == null) {
             instance = new JDBConnectionFactory();
             instance.connect();
          }
         }
       }
       return instance;
   }
         
   
   
   static {
        File propertiesFile = new File(JDBC_PROPERTIES_FILENAME); // locate correct path to file here
        if (propertiesFile.exists() == false) {
        	log.error("Cannot find file: " + JDBC_PROPERTIES_FILENAME);
        }

        Properties props = new Properties();
        FileInputStream inStream = null;
        try {
            inStream = new FileInputStream(propertiesFile);
            props.load(inStream);
        } 
	    catch (Exception e) {
		    log.error("Exception reading JDBC properties file: " + e.getMessage());
        } 
        finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (Exception e) {
                	log.error("Exception closing jdbc.properties: " + e.getMessage());
                }
            }
        }

        String hostUrlSymbol = props.getProperty(HOST_URL_PROPERTY).trim(); // rb.getString(HOST_URL_PROPERTY);
        if (hostUrlSymbol.isEmpty() || hostUrlSymbol.equals("")) {
        	log.error("No Host URL symbol in the JDBC properties");
        }
        String hostUrl = props.getProperty(hostUrlSymbol).trim();
        if (hostUrl.isEmpty() || hostUrl.equals("")) {
        	log.error("JDBC Properties does not contain the Symbol: " + hostUrlSymbol);
        }

        HOST_URL = hostUrl; // URL + DATABASE
        DRIVER = props.getProperty("jdbc.driverClassName").trim();
        DATABASE = props.getProperty("jdbc.database").trim();
        DB_USERNAME = props.getProperty("jdbc.username").trim();
        DB_PASSWORD = props.getProperty("jdbc.password").trim();
    }
 
   
   /* Establish the New Connection */
   private Connection connect() {
     try{        
         Class.forName(DRIVER); // newInstance()
         connection = (Connection) DriverManager.getConnection(HOST_URL, DB_USERNAME, DB_PASSWORD);
 
        if (connection!=null){
        	log.error("Connection Established OK with Persistent Layer from " + DATABASE);
        }
      }
      catch(Exception e){
    	 log.error("ERROR - Cannot established connection with DataBase now -- " + e.getMessage());
      } 
       return connection;
   }
   
   /**
    * It's called when the DAO is needed.
    * Established the unique connection for the Application
    * @return DriverManagerConnectionToBDMySQL 
   */
   public static Connection getConnection(){
	   log.debug("Getting new connection from Pool...");
      return connection;
   }
   
      
 /**
   @return Disconect JDBC 
   */
   protected void close() {
	   log.debug("Closing connection...");
       connection = null;
   }

	
}

