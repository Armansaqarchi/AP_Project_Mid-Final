package messenger.dataBaseOp;

import org.springframework.util.SerializationUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Op {

    protected Connection connection;
    protected Statement statement;

    public Op(Connection connection){
        this.connection = connection;

        try{
            statement = connection.createStatement();

        }
        catch(SQLException e){
            printSQLException(e);
        }
    }

    public static void printSQLException(SQLException ex) {

        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                    e.printStackTrace(System.err);
                    System.err.println("SQLState: " +
                            ((SQLException)e).getSQLState());

                    System.err.println("Error Code: " +
                            ((SQLException)e).getErrorCode());

                    System.err.println("Message: " + e.getMessage());

                    Throwable t = ex.getCause();
                    while(t != null) {
                        System.out.println("Cause: " + t);
                        t = t.getCause();
                    }

            }
        }
    }

    public Object byteConvertor(byte[] bytes) throws IOException, ClassNotFoundException{

        return SerializationUtils.deserialize(bytes);
    }

    public byte[] objectConvertor(Object o) throws IOException{

        return SerializationUtils.serialize(o);
    }





}