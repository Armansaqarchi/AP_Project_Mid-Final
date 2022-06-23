package messenger.dataBaseOp;

import java.sql.Connection;

public class TextMessageOp extends Op {


    public TextMessageOp(Connection connection){
        super(connection);
    }

    public MessageOp findById(){

    }

    public MessageOp findBySenderId(){

    }

    public MessageOp findByReceiverId(){

    }

    public void insertMessage(){

    }

    public void updateMessage(String content){

    }

    public void deleteTextMessageById(String id){

    }
}
