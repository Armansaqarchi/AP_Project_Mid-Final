package client.controller.fxController.cell;

import client.ClientSocket;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.exception.ResponseNotFoundException;
import model.request.server.GetServerInfoReq;
import model.request.user.GetUserProfileReq;
import model.response.Response;
import model.response.server.GetServerInfoRes;
import model.response.user.GetUserProfileRes;
import model.user.ServerIDs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ImageCell extends ListCell<ServerIDs> {

    private Circle circle = new Circle();
    StackPane stackPane = new StackPane();
    private ClientSocket clientSocket;


    public ImageCell(){

        stackPane.getChildren().add(circle);
        stackPane.setStyle("-fx-background-color: none");

        circle.setRadius(25);

        clientSocket = ClientSocket.getClientSocket();

        setStyle("-fx-background-color: none");

        setPrefWidth(-1); // use preferred size for cell width
        setPrefHeight(55); // use preferred size for cell width
    }

    @Override
    public void updateItem(ServerIDs item, boolean empty){
        super.updateItem(item, empty);
        if(item == null || empty){
            setGraphic(null);
        }
        else if(item.getId().equals("Add server")){
            circle.setFill(Color.BLACK);
            Label label = new Label("+");
            label.setStyle("-fx-font-size: 30;" +
                    "-fx-text-fill: green");

            stackPane.getChildren().add(label);

            setGraphic(stackPane);
        }
        else{
            byte[] fileImage = null;

            try {
                fileImage = Files.readAllBytes(Path.of("image/servers/" + item.getId()));
            }
            catch(IOException e){
                System.out.println(item.getId() + "'s image not found");

            }

            if(fileImage != null){
                circle.setFill(new ImagePattern(new Image("image/servers/" + item.getId())));
            }
            else {
                circle.setFill(Color.BLACK);
                Label label = new Label(item.getId().substring(0, 2));
                label.setStyle("-fx-font-weight: bold;" +
                        "-fx-font-size: 16;" +
                        "-fx-text-fill: white");



                stackPane.getChildren().add(label);
            }

            setGraphic(stackPane);
        }

    }

    public byte[] getImageById(String id){
        clientSocket.send(new GetServerInfoReq(clientSocket.getId(), id));
        try{
            Response response = clientSocket.getReceiver().getResponse();
            if (response instanceof GetServerInfoRes) {
                if(response.isAccepted()) {

                    return ((GetServerInfoRes) response).getImage();
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
