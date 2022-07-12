package client.controller.fxController.cell;

import client.ClientSocket;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import model.exception.ResponseNotFoundException;
import model.request.user.GetUserProfileReq;
import model.response.Response;
import model.response.user.GetUserProfileRes;
import model.user.User;
import model.user.UserStatus;

import java.io.ByteArrayInputStream;
import java.util.Map;

public class SMemberStatusCell extends ListCell<Map.Entry<String, UserStatus>> {

    private HBox hBox = new HBox();
    private Label label = new Label();
    private Circle circle = new Circle();
    private ImageView status = new ImageView();
    private ClientSocket clientSocket;

    public SMemberStatusCell(){
        this.clientSocket = ClientSocket.getClientSocket();


        circle.setRadius(20);

        status.setFitHeight(14);
        status.setFitWidth(14);

        label.setWrapText(true);
        label.setPrefWidth(USE_COMPUTED_SIZE);
        label.setPrefHeight(USE_COMPUTED_SIZE);
        label.setMaxHeight(Double.MAX_VALUE);
        label.setMaxWidth(Double.MAX_VALUE);

        label.setStyle("-fx-text-fill: white;" +
                "-fx-font-size: 15;" +
                "-fx-font-weight: bold");

        label.setTextAlignment(TextAlignment.CENTER); // center text
        label.setAlignment(Pos.CENTER);
        label.setPadding(new Insets(0, 0, 0, 10));
        hBox.getChildren().addAll(circle, status, label);

        setPrefWidth(Double.MAX_VALUE); // use preferred size for cell width
        setPrefHeight(55); // use preferred size for cell width

        setLineSpacing(10);
    }

    public void updateItem(Map.Entry<String, UserStatus> item, boolean empty) {
        super.updateItem(item, empty);

        if(item == null || empty){
            setGraphic(null);
        }
        else{
            byte[] friendImageInByte = getImageById(item.getKey());

            if(friendImageInByte == null){
                circle.setFill(new ImagePattern(new Image("/image/no-profile-logo.png")));
            }
                else{
                circle.setFill(new ImagePattern(new Image(new ByteArrayInputStream(friendImageInByte))));
            }

            switch(item.getValue()){
                case ONLINE -> status.setImage(new Image("/image/online.png"));
                case IDLE -> status.setImage(new Image("/image/idle.png"));
                case OFFLINE -> status.setImage(new Image("/image/invisible.png"));
                case INVISIBLE -> status.setImage(new Image("/image/invisible.png"));
                case DO_NOT_DISTURB -> status.setImage(new Image("/image/disturb.png"));
            }

                label.setText(item.getKey());

                setGraphic(hBox);


        }

    }

    public byte[] getImageById(String id){
        clientSocket.send(new GetUserProfileReq(clientSocket.getId(), id));
        try{
            Response response = clientSocket.getReceiver().getResponse();
            if (response instanceof GetUserProfileRes) {
                if(response.isAccepted()) {

                    return ((GetUserProfileRes) response).getProfileImage();
                }
                else{
                    System.out.println("access denied to get " + id + "'s image");
                }
            }


        }
        catch(ResponseNotFoundException e){
            e.printStackTrace();
            System.out.println("no response was receiver from server");;
        }

        return null;
    }
}
