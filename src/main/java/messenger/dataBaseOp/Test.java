package messenger.dataBaseOp;

import messenger.service.model.exception.ConfigNotFoundException;
import messenger.service.model.message.MessageType;
import messenger.service.model.message.TextMessage;
import messenger.service.model.server.ChannelType;
import messenger.service.model.user.User;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

public class Test {

    public static void main(String[] args) throws SQLException, IOException {

        Database database = Database.getDatabase();



        try {
            database.getServerOp().insertServer("1", "2eee2", " Arman");
            database.getPrivateChatOp().insertPrivateMessage("32-442332");
            database.getMessageOp().insertMessage(UUID.randomUUID().toString(), "1",
                    "32", MessageType.PRIVATE_CHAT, LocalDateTime.now(), "yeasda");
            database.getChannelOp().insertChannel("dsa", "arman", ChannelType.TEXT);

        }
        catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}
