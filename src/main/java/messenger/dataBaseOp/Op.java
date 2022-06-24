package messenger.dataBaseOp;

import messenger.service.model.message.Message;
import messenger.service.model.message.Reaction;
import org.springframework.util.SerializationUtils;

import javax.xml.transform.Result;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.*;
import java.util.LinkedList;

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


    protected <T> LinkedList<T> updateByType(UpdateType type, LinkedList<T> targetList, T t){

        switch(type.showValue()){


            case "Add" :
                targetList =  addToLists(targetList, t);
                break;


            case "Remove" :
                targetList = removeFromList(targetList, t);
                break;


        }

        return targetList;

    }



    protected  <T> LinkedList<T> addToLists(LinkedList<T> list, T t){


        if(list == null){
            list = new LinkedList<>();
        }

        list.add(t);
        return list;
    }

    protected  <T> LinkedList<T> removeFromList(LinkedList<T> list, T t){
        list.remove(t);
        return list;
    }

    public boolean deleteById(String id, String tableName) throws SQLException{

        String query = "DELETE FROM " + tableName + " WHERE user_id = ?";

        PreparedStatement pst2 = connection.prepareStatement(query);
        pst2.setString(1, id);
        int affectedRows = pst2.executeUpdate();

        if(affectedRows == 0) return false;

        return true;
    }

    protected ResultSet findByConfig(String config, String columnName, String tableName)
            throws IOException, SQLException, ClassNotFoundException{

        String query = "SELECT * FROM message WHERE " + columnName + " = ?";


        PreparedStatement pStatement = connection.prepareStatement(query);
        pStatement.setString(1, config);
        ResultSet resultSet = pStatement.executeQuery();

        if(resultSet.next()){
            return resultSet;
        }


        return null;
    }



}
