package conexionSQL;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DbConnection {
	
//	Paramentros de conexion
	
   
   static String login = "root";
   static String password = "root1234";
   static String url = "jdbc:mysql://localhost/hotelalura?useTimeZone=true&serverTimeZone=UTC";

	Connection connection  = null;	
	
	
	
//	Constructor de DbConnection
	
	   public DbConnection(){
		   
		 try {
			 //obtenemos el driver de para mysql
			 Class.forName("com.mysql.jdbc.Driver");
			//obtenemos la conexión
			 connection = DriverManager.getConnection(
					   url, login, password);
			
			   if (connection!=null){
//		            System.out.println("Conexión a base de datos OK\n");
		         }
		} catch (SQLException | ClassNotFoundException e) {
			   System.out.println(e);
		}   
 
		 
	   }
//	Permite retornar la conexión

	public Connection getConnection() {
		return connection;
	}
   
   public void desconectar(){

	   try {
		connection.close();
	} catch (SQLException e) {
		   System.out.println(e);

	}
	   
	   }

}
