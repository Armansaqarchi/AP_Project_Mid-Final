package client.controller.fxController.cell;

import client.ClientSocket;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class StatusCell extends ListCell<String> {

    private HBox hBox = new HBox();
    private Label label = new Label();
    private Circle circle = new Circle();
    private Button button = new Button();
    private ClientSocket clientSocket;



    public StatusCell(){

        this.clientSocket = ClientSocket.getClientSocket();

        circle.setRadius(30);

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
        setPrefHeight(77); // use preferred size for cell width
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
                    "-fx-text-fill: #ffffff;" +
                    "-fx-font-size: 20");

            byte[] fileImage = null;

            try {
                fileImage = Files.readAllBytes(Path.of("image/friends/" + item));
            }
            catch(IOException e){
                System.out.println(item + "'s image not found");

            }

            if(fileImage != null) {
                Image image = new Image(new ByteArrayInputStream(fileImage));
                circle.setFill(new ImagePattern(image));
            }
            else{
                circle.setFill(new ImagePattern(new Image("/image/no-profile-logo.png")));
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
