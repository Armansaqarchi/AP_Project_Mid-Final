package messenger.dataBaseOp;

import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;

import java.sql.Connection;

public class FriendRequestOp extends Op{

    public FriendRequestOp(Connection connection){
        super(connection);
    }


}
