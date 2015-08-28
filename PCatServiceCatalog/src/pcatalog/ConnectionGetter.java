package pcatalog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/*
 * 	A Connection generator class
 * */
public class ConnectionGetter
{
	Connection con;
	public ConnectionGetter()
	{
	try{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:8080:orcl", "PCatalogue", "password" );
					
		
		
		} 
	catch (SQLException e)
	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	catch(ClassNotFoundException e)
	{
	    	System.out.println(e.getMessage());
	    }

}
	
	/*
	 * 	Getter function for Connection con;
	 * */
	public Connection getConnection()
	{
		return con;
	}
	}
