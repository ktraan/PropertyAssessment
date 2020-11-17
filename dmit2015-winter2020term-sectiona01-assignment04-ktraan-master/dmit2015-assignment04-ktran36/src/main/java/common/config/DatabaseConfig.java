package common.config;

import javax.enterprise.context.ApplicationScoped;
import javax.annotation.sql.DataSourceDefinition;
import javax.annotation.sql.DataSourceDefinitions;

/**
 * This class is for the defining the database configurations that are listed in the persistence.xml
 * 
 * @author Kevin Tran
 * @version 2020-04-08
 *
 */

@DataSourceDefinitions({
	@DataSourceDefinition(
		name="java:app/datasources/mssqlDS",
		className="com.microsoft.sqlserver.jdbc.SQLServerDataSource",
		url="jdbc:sqlserver://localhost;databaseName=DMIT2015DB",
		user="user2015",
		password="Password2015"),

//	@DataSourceDefinition(
//		name="java:app/datasources/oracleDS",
//		className="oracle.jdbc.pool.OracleDataSource",
//		url="jdbc:oracle:thin:@localhost:11521/xepdb1",
//		user="user2015",
//		password="Password2015"),	
//
//	@DataSourceDefinition(
//		name="java:app/datasources/mysqlDS",
//		className="com.mysql.cj.jdbc.MysqlDataSource",
//		url="jdbc:mysql://localhost:13306/DMIT2015DB",
//		user="user2015",
//		password="Password2015"),
//
//	@DataSourceDefinition(
//		name="java:app/datasources/mariadbDS",
//		className="org.mariadb.jdbc.MariaDbDataSource",
//		url="jdbc:mariadb://localhost:13306/dmit2015DB",
//		user="user2015",
//		password="Password2015"),
//
//	@DataSourceDefinition(
//		name="java:app/datasources/postgresqlDS",
//		className="org.postgresql.ds.PGPoolingDataSource",
//		url="jdbc:postgresql://localhost:15432/DMIT2015DB",
//		user="user2015",
//		password="Password2015"),

})

@ApplicationScoped
public class DatabaseConfig {

}
