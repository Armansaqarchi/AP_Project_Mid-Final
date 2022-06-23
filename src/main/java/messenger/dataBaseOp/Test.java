package messenger.dataBaseOp;

import messenger.service.model.user.User;
import org.springframework.aop.scope.ScopedObject;

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
            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Discord",
                    "root", "Arman");

            UserOp userOperator = new UserOp(connection);




            userOperator.insertUser("1", "Arman", "sagharichiha", "arman.saghari81@gmail.com", "09301847526");



        }
        catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        finally{

        }



    }
}
