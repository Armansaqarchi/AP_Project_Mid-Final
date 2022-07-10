package client.controller.fxController.cell;

import client.ClientSocket;
import client.controller.fxController.LoginController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.concurrent.Executors;

public class testFx extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        ClientSocket clientSocket = ClientSocket.getClientSocket();

        Executors.newCachedThreadPool().execute(clientSocket);

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(testFx.class.getResource("/fxml/Login.fxml"));
        Scene scene = new Scene(loader.load());

        stage.setScene(scene);
        stage.setMinHeight(400);
        stage.setMinHeight(400);

        stage.show();


    }

    public static void main(String[] args){
        launch(args);
    }


    public static void setOnClose(Stage stage){
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Platform.exit();
            }
        });
    }
}

