package client.controller.fxController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.exception.ResponseNotFoundException;
import model.request.user.GetMyProfileReq;
import model.request.user.GetUserProfileReq;
import model.response.user.GetMyProfileRes;
import model.response.user.GetUserProfileRes;
import model.user.UserStatus;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class UserProfileController extends Controller
{
    private String UserId;

    private Stage stage;

    @FXML
    private GridPane pane;

    @FXML
    private Button escape;
    @FXML
    private Button sendMessage;
    @FXML
    private Button viewMore;

    @FXML
    private Button friend;
    @FXML
    private Button block;

    @FXML
    private Label userId;
    @FXML
    private Label name;

    @FXML
    private Circle image;
    @FXML
    private Circle status;

    @FXML
    private void escape(ActionEvent event)
    {
        closeScene();
    }

    @FXML
    private void sendMessage(ActionEvent event)
    {
        escape.fire();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Home.fxml"));

        clientSocket.getReceiver().setLoader(loader);


        Stage stage = clientSocket.getReceiver().getStage();
        Scene scene = stage.getScene();


        try {

             scene.setRoot(loader.load());

            stage.setScene(scene);


        }
        catch(IOException e){
            e.printStackTrace();
        }




        ((HomeController)clientSocket.getReceiver().getLoader()
                .getController()).friendHandler(userId.getText());
    }

    @FXML
    private void viewMore(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/viewMoreUP.fxml"));
        Parent parent = loader.load();

        stage.setScene(new Scene(parent));

        ViewMoreUPController controller = loader.getController();
        controller.initialize(UserId);

        stage.show();
    }

    public void initialize(String userId)
    {
        this.UserId = userId;
        initialize();
    }

    private void initialize()
    {
        stage = new Stage();
        stage.focusedProperty().addListener((obs , oldFocus , newFocus) -> focusHandler(newFocus , stage));

        stage.setOnHidden(e -> hideHandler());

        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);

        try
        {
            clientSocket.send(new GetUserProfileReq(clientSocket.getId() , UserId));

            GetUserProfileRes response = (GetUserProfileRes) clientSocket.getReceiver().getResponse();

            if(!response.isAccepted())
            {
                closeScene();
            }

            userId.setText(response.getId());
            name.setText(response.getName());

            setImage(response.getProfileImage());
            setStatus(response.getUserStatus());
        }
        catch(ResponseNotFoundException e)
        {
            closeScene();
        }
    }

    private void focusHandler(boolean isFocused , Stage stage)
    {
        if(!isFocused)
        {
            stage.close();
        }
    }

    private void hideHandler()
    {
        stage.close();
        initialize();
    }

    private void closeScene()
    {
        Stage stage = (Stage)pane.getScene().getWindow();
        stage.close();
    }

    private void setImage(byte[] image)
    {
        Image proImage;

        if(null == image)
        {
            proImage = new Image("image/user-default.png");
        }
        else
        {
            InputStream inputStream = new ByteArrayInputStream(image);
            proImage = new Image(inputStream);
        }

        ImagePattern pattern = new ImagePattern(proImage);

        this.image.setFill(pattern);
    }
    private void setStatus(UserStatus status)
    {
        Image statusImage = null;

        switch (status)
        {
            case ONLINE -> statusImage = new Image("image/online.png");
            case OFFLINE, IDLE -> statusImage = new Image("image/idle.png");
            case DO_NOT_DISTURB -> statusImage = new Image("image/disturb.png");
            case INVISIBLE -> statusImage = new Image("image/invisible.png");
        }

        ImagePattern pattern = new ImagePattern(statusImage);

        this.status.setFill(pattern);
    }
}
