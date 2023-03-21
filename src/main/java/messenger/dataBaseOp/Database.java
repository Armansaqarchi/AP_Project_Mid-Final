package messenger.dataBaseOp;


import java.sql.*;

public class Database {

    //this class gathers all the operators to simplify interaction between service and
    private static Database database;

    //all the operators as a field
    private final ChannelOp channelOp;
    private final FriendRequestOp friendRequestOp;
    private final MessageOp messageOp;
    private final PrivateChatOp privateChatOp;
    private final ServerOp serverOp;
    private final UserOp userOp;

    //this class has a singleton design method
    private Database()
    {
        try
        {
            //loads the postgresql driver into the stack
            Class.forName("org.postgresql.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5432/postgres" ,
                    "mahan" , "test123123");


            configure(connection);

            //initializing all the operators
            channelOp = new ChannelOp(connection);
            friendRequestOp = new FriendRequestOp(connection);
            messageOp = new MessageOp(connection);
            serverOp = new ServerOp(connection);
            userOp = new UserOp(connection);
            privateChatOp = new PrivateChatOp(connection);

            System.out.println("database connected successfully.");
        }
        catch (SQLException | ClassNotFoundException e)
        {
            System.out.println("connection to database failed!");
            throw new RuntimeException(e);
        }
    }


    private void configure(Connection connection){
        try {
            Statement statement = connection.createStatement();

            statement.execute("CREATE TABLE IF NOT EXISTS users(user_id varchar(50), name varchar(50)," +
                    " password varchar(50), email varchar(100), phone_number varchar(20), profile_image BYTEA, user_status varchar," +
                    "friend_list BYTEA, blocked_users BYTEA, private_chats BYTEA, servers BYTEA, unread_messages BYTEA," +
                    "friend_requests BYTEA)");

            statement.execute("CREATE TABLE IF NOT EXISTS server" +
                    "(server_id  varchar(50), name varchar(50), owner_id int, image BYTEA)");


            statement.execute("CREATE TABLE IF NOT EXISTS private_chats(id varchar(50) PRIMARY KEY," +
                    " messages BYTEA)");

            statement.execute("CREATE TABLE IF NOT EXISTS message(message_id varchar(50), sender_id varchar(50), " +
                    "receiver_id int, type int, date date, reactions BYTEA, content BYTEA)");

            statement.execute("CREATE TABLE IF NOT EXISTS friend_requests(id varchar(50), type varchar(50), sender_id varchar(50), receiver_id varchar(50))");
            statement.execute("CREATE TABLE IF NOT EXISTS channel(id varchar(50), name varchar(50), channel_type varchar(50)," +
                    " users BYTEA, messages BYTEA, pinned_messages BYTEA)");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static Database getDatabase()
    {
        if(null == database)
        {
            database = new Database();
        }

        return database;
    }

    public ChannelOp getChannelOp() {
        return channelOp;
    }

    public FriendRequestOp getFriendRequestOp() {
        return friendRequestOp;
    }

    public MessageOp getMessageOp() {
        return messageOp;
    }

    public PrivateChatOp getPrivateChatOp() {
        return privateChatOp;
    }

    public ServerOp getServerOp() {
        return serverOp;
    }

    public UserOp getUserOp() {
        return userOp;
    }
}
