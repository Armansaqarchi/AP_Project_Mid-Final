package client.controller.fxController.cell;

import javafx.scene.control.ListCell;
import javafx.scene.shape.Circle;

public class ImageCell extends ListCell<String> {

    private Circle circle = new Circle();


    public ImageCell(){

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
            //set circle image

            setGraphic(circle);
        }

    }
}
