package client.controller.fxController;


import client.controller.fxController.type.EditProfileType;
import client.controller.fxController.type.SetImageType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import model.response.user.GetMyProfileRes;
import model.user.UserStatus;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;


public class MyProfileController extends Controller implements Initializable
{
    @FXML
    private Stage stage;

    @FXML
    private GridPane pane;

    @FXML
    private Label message;


    @FXML
    private Button escape;

    @FXML
    private Button editId;
    @FXML
    private Button editName;
    @FXML
    private Button editEmail;
    @FXML
    private Button editPhoneNumber;

    @FXML
    private Button editImage;
    @FXML
    private Button changePassword;
    @FXML
    private Button logout;

    @FXML
    private Label userId;
    @FXML
    private Label name;
    @FXML
    private Label email;
    @FXML
    private Label phoneNumber;

    @FXML
    private Circle image;
    @FXML
    private Circle status;

    @FXML
    private void editProfile(ActionEvent event) throws IOException
    {

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/fxml/editProfile.fxml"));

        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        EditProfileController controller = loader.getController();

        Object source = event.getSource();
        if (editId.equals(source))
        {
            controller.setType(EditProfileType.ID);
        }
        else if (editName.equals(source))
        {
            controller.setType(EditProfileType.NAME);
        }
        else if (changePassword.equals(source))
        {
            controller.setType(EditProfileType.PASSWORD);
        }
        else if (editEmail.equals(source))
        {
            controller.setType(EditProfileType.EMAIL);
        }
        else if (editPhoneNumber.equals(source))
        {
            controller.setType(EditProfileType.PHONE_NUMBER);
        }

        showScene(scene);
    }

    @FXML
    private void editImage(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/fxml/setImage.fxml"));

        Parent parent = loader.load();

        SetImageController controller = loader.getController();

        controller.setType(SetImageType.USER);

        Scene scene = new Scene(parent);

        showScene(scene);
    }

    @FXML
    private void escape(ActionEvent event)
    {
        Stage stage = (Stage)pane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void logout(ActionEvent event)
    {
        //incomplete
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        stage = new Stage();

        stage.focusedProperty().addListener((obs , oldFocus , newFocus) -> focusHandler(newFocus , stage));

        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);

        try
        {
            clientSocket.send(new GetMyProfileReq(clientSocket.getId()));

            GetMyProfileRes response = (GetMyProfileRes)clientSocket.getReceiver().getResponse();

            if(!response.isAccepted())
            {
                closeScene();
            }

            userId.setText(response.getId());
            name.setText(response.getName());
            email.setText(response.getEmail());

            setPhoneNumber(response.getPhoneNumber());
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
    private void setPhoneNumber(String phoneNumber)
    {
        if(null == phoneNumber || phoneNumber.equals(""))
        {
            this.phoneNumber.setText("don't added yet");
        }
        else
        {
            this.phoneNumber.setText(phoneNumber);
        }
    }
}
