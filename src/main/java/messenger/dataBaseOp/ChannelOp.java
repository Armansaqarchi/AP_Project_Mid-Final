package messenger.dataBaseOp;

import messenger.service.model.server.Channel;
import messenger.service.model.user.User;

import java.sql.Connection;

public class ChannelOp extends Op{


    public ChannelOp(Connection connection){
        super(connection);
    }

    public void findById(String id){}

    public void findByName(String name){}

    public void updateChannel(String type, String newValue){

    }

    public void insertChannel(String id, String name){

    }

    public void deleteById(String id){

    }

    public void deleteByName(String name){

    }






}
