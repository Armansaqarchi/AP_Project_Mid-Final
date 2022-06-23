package messenger.dataBaseOp;


import java.sql.*;

public abstract class DBOp {


    private Connection connection;
    private String URL;
    private String username;
    private String password;

    public DBOp(String URL, String username, String password) {
        this.URL = URL;
        this.username = username;
        this.password = password;
        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, username, password);
        }
        catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }







}
