package messenger.dataBaseOp;

import messenger.service.model.server.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



class ServerOp extends Op{



    public ServerOp(Connection connection){
        super(connection);
    }

    public Server findById(String id){

    }

    public Server findByOwnerId(String ownerId){

    }

    public Server findByName(String name){

    }

    public void insertServer(String id, String ownerId, String name){

    }

    public void updateServer(String type, String newValue){

    }












}
