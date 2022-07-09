package client.controller.fxController.cell;

import client.ClientSocket;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import model.exception.ResponseNotFoundException;
import model.message.FileMessage;
import model.message.Message;
import model.message.TextMessage;
import model.request.user.GetUserProfileReq;
import model.response.Response;
import model.response.user.GetUserProfileRes;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ChatCell extends ListCell<Message> {
    private HBox hBox = new HBox(8);
    private HBox innerInnerHBox1 = new HBox(8);

    private VBox innerVBox = new VBox();
    private Label senderlabel = new Label();
    private Label datelabel = new Label();
    private Label messageLabel = new Label();
    private Circle circle = new Circle();
    private ClientSocket clientSocket;

    public ChatCell(){
        clientSocket = ClientSocket.getClientSocket();

        circle.setRadius(15);
        senderlabel.setWrapText(true);
        senderlabel.setPrefWidth(USE_COMPUTED_SIZE);
        senderlabel.setPrefHeight(USE_COMPUTED_SIZE);
        senderlabel.setMaxHeight(Double.MAX_VALUE);
        senderlabel.setMaxWidth(Double.MAX_VALUE);
        senderlabel.setTextAlignment(TextAlignment.CENTER); // center text
        senderlabel.setAlignment(Pos.CENTER);
        senderlabel.setPadding(new Insets(0, 0, 0, 10));



        innerInnerHBox1.getChildren().addAll(senderlabel, datelabel);
        innerInnerHBox1.setPrefHeight(USE_COMPUTED_SIZE);

        messageLabel.setPrefHeight(USE_COMPUTED_SIZE);
        messageLabel.setPadding(new Insets(0, 0, 10, 10));

        innerVBox.setPrefHeight(USE_COMPUTED_SIZE);


        innerVBox.setMaxHeight(60);
        innerVBox.setSpacing(0);
        innerVBox.setStyle("-fx-background-color: white;" +
                "-fx-background-radius: 10");

        innerVBox.getChildren().addAll(innerInnerHBox1, messageLabel);

        messageLabel.setStyle("-fx-text-fill: Black;");
        messageLabel.setPadding(new Insets(0, 0, 0, 10));


        hBox.getChildren().addAll(circle, innerVBox);




        setPrefWidth(Double.MAX_VALUE); // use preferred size for cell width
        setPrefHeight(80); // use preferred size for cell width
    }

    @Override
    public void updateItem(Message item, boolean empty){



        super.updateItem(item, empty);
        if(item == null || empty){
            setGraphic(null);
        }
        else {
            byte[] fileImage = null;

            try {
                fileImage = Files.readAllBytes(Path.of("image/friends/" + item.getSenderId()));
            }
            catch(IOException e){
                System.out.println(item.getSenderId() + "'s image not found");

            }

            if(fileImage != null) {
                Image image = new Image(new ByteArrayInputStream(fileImage));
                circle.setFill(new ImagePattern(image));
            }
            else{
                circle.setFill(new ImagePattern(new Image("/image/no-profile-logo.png")));
            }


            hBox.setStyle("-fx-background-color: white");

            senderlabel.setText(item.getSenderId());
            senderlabel.setStyle("-fx-font-weight: bold");

            datelabel.setText(item.getDate().toString());
            datelabel.setStyle("-fx-font-weight: bold");

            if(item instanceof TextMessage){
                messageLabel.setText(((TextMessage) item).getContent());
            }
            else if (item instanceof FileMessage){
                System.out.println("4332442");
                messageLabel.setText("File message : " + ((FileMessage) item).getFileName() );
                innerVBox.setStyle("-fx-background-color: #555555;");
            }

            setGraphic(hBox);
        }

    }


}
