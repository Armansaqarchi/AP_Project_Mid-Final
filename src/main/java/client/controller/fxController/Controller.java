package client.controller.fxController;

import client.ClientSocket;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller{
    protected final ClientSocket clientSocket = ClientSocket.getClientSocket();


    public FXMLLoader changeView(String newView, Event event){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/" + newView + ".fxml"));

            clientSocket.getReceiver().setLoader(loader);

            Parent homeParent = loader.load();

            Stage window = (Stage) ((Button)event.getSource()).getScene().getWindow();

            window.setScene(new Scene(homeParent));

            return loader;

        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("could not load " + newView + ".fxml class");
        }

        return null;
    }
}
