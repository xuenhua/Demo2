package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public abstract class ConnectionFactory {

	
	 
	private static final String propertiesFileName="using_which_dbms";
	static synchronized public Connection getConnection() throws Exception{
		String dbSystem=null;
		Connection conn;
		//数据库类型配置文件
		ResourceBundle db=ResourceBundle.getBundle("dbsystem");
		//读取数据库类型属性
		dbSystem=db.getString(propertiesFileName);
		//读取数据库连接配置文件
		ResourceBundle rb=ResourceBundle.getBundle(dbSystem);
		//创建连接实例
		Class.forName(rb.getString("database.driver")).newInstance();
		conn=DriverManager.getConnection(rb.getString("database.url"),rb.getString("database.username"),rb.getString("database.password"));
		//关闭自动提交
		conn.setAutoCommit(false);
		return conn;
	}
	public static String getCurrentDBMS(boolean echoable) {
		String dbsystem=null;
		ResourceBundle rb=ResourceBundle.getBundle("dbsystem");
		dbsystem=rb.getString(propertiesFileName);
		if (echoable) {
			System.out.println("the database system what you using are "+dbsystem);
			System.out.println(
					"database.url="+rb.getString("database.url")+"\n"+
					"database.username="+rb.getString("database.username")+"\n"+
					"database.password="+rb.getString("database.password"));
		}
		return dbsystem;
		
	}
	
}
