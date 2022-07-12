package client.controller.fxController.cell;


import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;

import javax.swing.text.html.ImageView;


public class ChannelCell extends ListCell<String> {

    private String serverId;

    private HBox hbox = new HBox();
    private Label channelName = new Label();
    private Label spaceLabel = new Label();


    public ChannelCell(String serverId){

        this.serverId = serverId;


        spaceLabel.setWrapText(true);
        spaceLabel.setPrefWidth(USE_COMPUTED_SIZE);
        spaceLabel.setPrefHeight(USE_COMPUTED_SIZE);

        channelName.setWrapText(true);
        channelName.setMaxHeight(Double.MAX_VALUE);
        channelName.setPrefWidth(USE_COMPUTED_SIZE);
        channelName.setPrefHeight(USE_COMPUTED_SIZE);






        HBox.setHgrow(spaceLabel, Priority.ALWAYS);

        channelName.setPadding(new Insets(0, 0, 0, 5));



        spaceLabel.setTextAlignment(TextAlignment.CENTER); // center text
        spaceLabel.setAlignment(Pos.CENTER);

        channelName.setStyle(
                "-fx-font-weight: bold;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 13");

        hbox.getChildren().add(channelName);





        setHeight(80);
        setPrefWidth(Double.MAX_VALUE); // use preferred size for cell width
    }

    public void updateItem(String item, boolean empty){
        super.updateItem(item, empty);

        if(item == null || empty){
            setGraphic(null);
        }

        else if(item.equals(serverId)){




            channelName.setText(item);

            setGraphic(hbox);

        }
        else if(item.equals("TEXT CHANNELS")){




            channelName.setText(item);
            setGraphic(hbox);


        }
        else{

            channelName.setText(item);

            setGraphic(hbox);

        }


    }

}
