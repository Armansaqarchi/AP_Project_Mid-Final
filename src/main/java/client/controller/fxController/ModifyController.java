package client.controller.fxController;

import client.FileHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.exception.ResponseNotFoundException;
import model.message.Message;
import model.message.MessageReaction;
import model.message.Reaction;
import model.message.TextMessage;
import model.request.GetFileMsgReq;
import model.request.user.ReactionToMessageReq;
import model.response.GetFileMsgRes;
import model.response.Response;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ModifyController extends Controller {

    private Message message;

    @FXML
    private Button Reactions;


    @FXML
    private Button download;

    @FXML
    private Button dislike;

    @FXML
    private Button laugh;

    @FXML
    private Button like;


    @FXML
    void onDislike(ActionEvent event) {
        if (!isUserReactedBefore(message.getSenderId())) {

            clientSocket.send(new ReactionToMessageReq(clientSocket.getId(), message.getId(),
                    Reaction.DISLIKE));

            message.getReactions().add(new MessageReaction(message.getSenderId(), Reaction.DISLIKE));
        }
    }


    @FXML
    void onLike(ActionEvent event) {
        if(!isUserReactedBefore(message.getSenderId())) {
            clientSocket.send(new ReactionToMessageReq(clientSocket.getId(), message.getId(),
                    Reaction.LIKE));

            message.getReactions().add(new MessageReaction(message.getSenderId(), Reaction.LIKE));
        }
    }

    @FXML
    void onReactions(ActionEvent event) {

        Stage previousStage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        FXMLLoader loader;

        try {
            if (message.getReactions().size() == 0) {



                loader = new FXMLLoader(getClass().getResource("/fxml/NoReactions.fxml"));
                Scene scene = new Scene(loader.load());
                scene.setFill(Color.TRANSPARENT);

                previousStage.setScene(scene);
            } else {
                loader = new FXMLLoader(getClass().getResource("/fxml/Reactions.fxml"));


                Scene scene = new Scene(loader.load());

                scene.setFill(Color.TRANSPARENT);


                previousStage.setScene(scene);

                previousStage.setResizable(false);

                ReactionsController controller = loader.getController();


                controller.getReactions(message);

            }
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("could not find Reaction.fxml file ");
        }

    }

    @FXML
    public void onLaugh(ActionEvent event) {
        if(!isUserReactedBefore(message.getSenderId())) {
            clientSocket.send(new ReactionToMessageReq(clientSocket.getId(), message.getId(),
                    Reaction.LAUGH));

            message.getReactions().add(new MessageReaction(message.getSenderId(), Reaction.LAUGH));
        }
    }

    public void setMessage(Message message){
        this.message = message;
    }

    public boolean isUserReactedBefore(String senderId){
        for(MessageReaction i : message.getReactions()){
            if(i.getSenderId().equals(senderId)){
                return true;
            }
        }

        return false;
    }

    @FXML
    void onDownload(ActionEvent event) {


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if(message instanceof TextMessage){
                    FileHandler fileHandler = FileHandler.getFileHandler();

                    fileHandler.saveMessage(message);

                }
                else{

                    clientSocket.send(new GetFileMsgReq(clientSocket.getId(), message.getId()));

                    try{
                        Response response = clientSocket.getReceiver().getResponse();

                        if(response.isAccepted() && response instanceof GetFileMsgRes){
                            FileHandler.getFileHandler().saveFile((GetFileMsgRes) response);

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    resultMaker("file saved as : client/file/" + ((GetFileMsgRes) response).getFileName(), "FILE DOWNLOADED");
                                }
                            });

                        }
                        else{
                            System.out.println("access denied to get file message");
                        }

                    }
                    catch(ResponseNotFoundException e){
                        e.printStackTrace();
                    }

                }
            }
        });

        thread.start();



    }
}
