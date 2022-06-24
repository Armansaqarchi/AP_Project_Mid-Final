package messenger.dataBaseOp;

import messenger.service.model.user.User;
import org.springframework.aop.scope.ScopedObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

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


            boolean bool = userOperator.updateList(UpdateType.ADD, "friend_list", "3", "test23");
            System.out.println(bool);


            System.out.println(userOperator.findById("3"));
            //Object o = userOperator.byteConvertor(userOperator.objectConvertor(new String("test")));




        }
        catch(ClassNotFoundException | SQLException | IOException e){
            e.printStackTrace();
        }
        finally{

        }



    }
}
