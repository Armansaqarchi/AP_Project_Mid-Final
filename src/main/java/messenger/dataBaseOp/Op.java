package messenger.dataBaseOp;

import messenger.service.model.exception.ConfigNotFoundException;
import messenger.service.model.exception.UserNotFoundException;
import org.springframework.util.SerializationUtils;


import java.io.IOException;
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

    protected boolean deleteById(String id, String tableName, String idColumnName, String entity)
            throws SQLException, ConfigNotFoundException{

        String query = "DELETE FROM " + tableName + " WHERE " + idColumnName + " = ?";

        PreparedStatement pst2 = connection.prepareStatement(query);
        pst2.setString(1, id);
        int affectedRows = pst2.executeUpdate();

        if(affectedRows == 0) throw new ConfigNotFoundException(id, idColumnName, entity);

        return true;
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

    public void updateImage(byte[] image, String id)throws SQLException, ConfigNotFoundException
    {
        String query = "UPDATE users SET profile_image = ? where user_id = ?";
        PreparedStatement st = connection.prepareStatement(query);


        st.setBytes(1, image);
        st.setString(2, id);

        int res = st.executeUpdate();

        if(res == 0){
            throw new ConfigNotFoundException(id, "profile_image", "user");
        }

    }



    protected  <T> LinkedList<T> addToLists(LinkedList<T> list, T t){


        if(list == null){
            list = new LinkedList<>();
        }

        list.add(t);
        return list;
    }

    protected  <T> LinkedList<T> removeFromList(LinkedList<T> list, T t){

        if(list == null){
            return null;
        }

        list.remove(t);
        return list;
    }



    protected ResultSet findByConfig(String config, String columnName, String tableName)
            throws IOException, SQLException, ClassNotFoundException, ConfigNotFoundException{

        String query = "SELECT * FROM " + tableName + " WHERE " + columnName + " = ?";


        PreparedStatement pStatement = connection.prepareStatement(query);

        pStatement.setString(1, config);
        ResultSet resultSet = pStatement.executeQuery();

        if(resultSet.next()){
            return resultSet;
        }

        if(tableName.equals("users")){
            throw new UserNotFoundException(config, columnName);
        }
        throw new ConfigNotFoundException(config, columnName, tableName);
    }






}
