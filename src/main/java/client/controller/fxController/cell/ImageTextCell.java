package client.controller.fxController.cell;

import client.ClientSocket;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import model.exception.ResponseNotFoundException;
import model.request.user.GetUserProfileReq;
import model.response.Response;
import model.response.user.GetUserProfileRes;

import java.io.ByteArrayInputStream;


public class ImageTextCell extends ListCell<String> {
    private HBox hBox = new HBox();
    private Label label = new Label();
    private Circle circle = new Circle();
    private ClientSocket clientSocket;



    public ImageTextCell(ClientSocket clientSocket){

        this.clientSocket = clientSocket;

        circle.setRadius(15);

        label.setWrapText(true);
        label.setPrefWidth(USE_COMPUTED_SIZE);
        label.setPrefHeight(USE_COMPUTED_SIZE);
        label.setMaxHeight(Double.MAX_VALUE);
        label.setMaxWidth(Double.MAX_VALUE);


        label.setTextAlignment(TextAlignment.CENTER); // center text
        label.setAlignment(Pos.CENTER);
        label.setPadding(new Insets(0, 0, 0, 10));
        hBox.getChildren().addAll(circle, label);

        setPrefWidth(Double.MAX_VALUE); // use preferred size for cell width
        setPrefHeight(-1); // use preferred size for cell width
    }

    @Override
    public void updateItem(String item, boolean empty){
        super.updateItem(item, empty);
        if(item == null || empty){
            setGraphic(null);
        }
        else {
            label.setText(item);
            label.setStyle("-fx-font-weight: bold;" +
                    "-fx-text-fill: #ffffff");

            if (item.equals("Friends")) {
                circle.setFill(new ImagePattern(new Image("/image/human-icon.png")));

            } else if (item.equals("DIRECT MESSAGES")){
                circle.setRadius(0);
                label.setStyle("-fx-font-size: 12;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold");
                label.setPadding(new Insets(0, 0, 8, 0));
            }
            else {
                label.setStyle("-fx-text-fill: white;");
                byte[] friendImageInByte = getImageById(item);
                if(friendImageInByte == null){
                    circle.setFill(new ImagePattern(new Image("/image/no-profile-logo.png")));
                }
                else{
                    circle.setFill(new ImagePattern(new Image(new ByteArrayInputStream(getImageById(item)))));
                }


            }

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
