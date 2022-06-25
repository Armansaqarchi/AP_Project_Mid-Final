package messenger.dataBaseOp;

import messenger.service.model.PrivateChat;
import messenger.service.model.message.Message;
import messenger.service.model.message.MessageType;
import messenger.service.model.message.Reaction;
import messenger.service.model.message.TextMessage;
import messenger.service.model.server.Rule;
import messenger.service.model.server.RuleType;
import messenger.service.model.user.User;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.UUID;

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
            MessageOp messageOperator = new MessageOp(connection);
            ChannelOp channelOperator = new ChannelOp(connection);
            PrivateChatOp pChatOperator = new PrivateChatOp(connection);
            FriendRequestOp frOperator = new FriendRequestOp(connection);
            ServerOp serverOp = new ServerOp(connection);


        }
        catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        finally{

        }



    }
}
