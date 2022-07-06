package client.controller.fxController.cell;

import client.controller.consoleController.Controllers;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import model.user.User;

public class FriendCell extends ListCell<String> {
    private HBox hBox = new HBox();
    private Label label;
    private Circle circle = new Circle();


    public FriendCell(){
        label.setWrapText(true);
        label.setPrefWidth(label.getMaxWidth());
        label.setPrefHeight(label.getMaxHeight());
        label.setMaxHeight(Double.MAX_VALUE);
        label.setMaxWidth(Double.MAX_VALUE);

        label.setTextAlignment(TextAlignment.CENTER); // center text
        label.setAlignment(Pos.CENTER);

        hBox.getChildren().addAll(circle, label);

        setPrefWidth(-1); // use preferred size for cell width
        setPrefHeight(-1); // use preferred size for cell width
    }

    @Override
    public void updateItem(String item, boolean empty){
        super.updateItem(item, empty);
        if(item == null || empty){
            setGraphic(null);
        }
        else{
            label.setText(item);
            //sends a request and gets friend image
            if(item.equals("friends")){
                circle.setFill(new ImagePattern(new Image("src\\main\\resources\\image\\human-icon.png")));
            }
            else {
                label.setStyle("-fx-text-fill: white;");
                //placing the image into the circle
            }

            setGraphic(hBox);
        }

    }
}
