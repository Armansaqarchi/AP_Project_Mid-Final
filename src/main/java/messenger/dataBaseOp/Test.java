package messenger.dataBaseOp;

import messenger.service.model.message.MessageType;
import messenger.service.model.message.TextMessage;
import messenger.service.model.user.User;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

public class Test {

    public static void main(String[] args) throws SQLException, IOException {

        Database database = Database.getDatabase();

        database.getUserOp().insertUser(new User("1" , "1" , "1" , "1" , "1"));
        database.getMessageOp().insertMessage(new TextMessage(UUID.randomUUID() , "1" , "2" ,
                MessageType.PRIVATE_CHAT , LocalDateTime.now() , "ggg"));

    }
}
