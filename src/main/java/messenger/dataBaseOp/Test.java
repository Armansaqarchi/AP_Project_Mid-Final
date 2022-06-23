package messenger.dataBaseOp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {

    public static void main(String[] args) throws SQLException{
        String url = "jdbc:postgresql://localhost:5432/Discord";
        String username = "root";
        String password = "Arman";
        String query = "SELECT * FROM testing";
        Connection connection = null;


        try{
            Class.forName("org.mariadb.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Discord");



        }
        catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        finally{
            if(connection != null) {
                connection.close();
            }
        }



    }
}
