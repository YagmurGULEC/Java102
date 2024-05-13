package Database;

import java.sql.*;
import java.util.ArrayList;

public class DB {
    private Connection connection = null;

    public  Connection connectDB() {
        try {
            // Get database credentials from DatabaseConfig class
            var jdbcUrl = DatabaseConfig.getUrl();
            var user = DatabaseConfig.getUsername();
            var password = DatabaseConfig.getPassword();

            // Open a connection
            this.connection=DriverManager.getConnection(jdbcUrl, user, password);

        } catch (SQLException  e) {
            throw new RuntimeException(e);

        }
        return this.connection;
    }
   public static Connection getInstance()
   {
         return new DB().connectDB();
   }

   public static Object parseData(String colType,String name,ResultSet resultSet ) throws SQLException {

         Object data=null;
       if (colType.equalsIgnoreCase("text") || colType.equalsIgnoreCase("varchar"))
       {
           data=resultSet.getString(name);

       }
       else if (colType.equalsIgnoreCase("int4") || colType.equalsIgnoreCase("serial"))
       {
           data=resultSet.getInt(name);

       }

       return data;
   }




}
