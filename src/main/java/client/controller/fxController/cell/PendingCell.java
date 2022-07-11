package client.controller.fxController.cell;

import client.Client;
import client.ClientSocket;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;

import java.util.Map;
import java.util.UUID;

public class PendingCell extends ListCell<Map.Entry<UUID, String>> {


    private HBox hBox = new HBox();
    private Label label = new Label();
    private ClientSocket clientSocket = ClientSocket.getClientSocket();


    public PendingCell(){


        this.clientSocket = ClientSocket.getClientSocket();

        label.setWrapText(true);

        label.setTextAlignment(TextAlignment.CENTER); // center text
        label.setAlignment(Pos.CENTER);
        label.setPadding(new Insets(0, 0, 0, 10));



        hBox.getChildren().add(label);

        hBox.setHgrow(label, Priority.ALWAYS);

        setPrefWidth(Double.MAX_VALUE); // use preferred size for cell width
        setPrefHeight(30); // use preferred size for cell width


    }

    @Override
    public void updateItem(Map.Entry<UUID, String> item, boolean empty){

        super.updateItem(item, empty);

        if(item == null || empty){
            setGraphic(null);
        }
        else{

            label.setText(item.getValue());
            label.setStyle("-fx-background-color: none;" +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: white;" +
                    "-fx-font-size: 30");


            setGraphic(hBox);
        }
    }
}
