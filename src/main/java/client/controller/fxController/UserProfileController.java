package client.controller.fxController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
        //incomplete
    }

    @FXML
    private void viewMore(ActionEvent event)
    {

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

    private void showScene(Scene scene)
    {
        stage.setScene(scene);

        stage.show();
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
