package client.controller.fxController.cell;

import client.controller.consoleController.Controllers;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import model.user.User;

import java.awt.*;

public class FriendCell extends ListCell<String> {
    private HBox hBox = new HBox();
    private Label label = new Label();
    private Circle circle = new Circle();



    public FriendCell(){

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
                label.setText(item);
                label.setPadding(new Insets(0, 0, 8, 0));
            }
            else {

                label.setStyle("-fx-text-fill: white;");
                //placing the image into the circle
            }

            setGraphic(hBox);
        }

    }
}
