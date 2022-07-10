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

import java.io.IOException;
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
                System.out.println(response);

            }
        }
        catch(ResponseNotFoundException e){
            System.out.println(e.getMessage());
        }
        Image image = new Image("image/user-default.png");
        ImagePattern pattern = new ImagePattern(image);
        this.image.setFill(pattern);

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
}
