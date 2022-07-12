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
import model.message.MessageReaction;

import java.io.ByteArrayInputStream;

public class ReactionsCell extends ListCell<MessageReaction> {

    private HBox hBox = new HBox();
    private Label label = new Label();
    private Circle circle = new Circle();
    private ClientSocket clientSocket;

    public ReactionsCell(){


        this.clientSocket = ClientSocket.getClientSocket();

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
    public void updateItem(MessageReaction item, boolean empty) {

        super.updateItem(item, empty);
        if (item == null || empty) {
            setGraphic(null);
        } else {
            Image image = null;


            switch(item.getReaction()){
                case LIKE -> image = new Image("/image/like.png");
                case DISLIKE -> image = new Image("/image/dislike.png");
                case LAUGH -> image = new Image("/image/laugh.png");
            }

            if(image == null){
                setGraphic(null);
            }
            else{

                label.setText(item.getSenderId());
                circle.setFill(new ImagePattern(image));

                setGraphic(hBox);
            }
        }
    }
}
