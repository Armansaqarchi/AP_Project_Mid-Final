package client.controller.fxController;

import client.controller.fxController.type.SetImageType;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.exception.ResponseNotFoundException;
import model.request.server.GetServerInfoReq;
import model.request.server.SetServerImageReq;
import model.request.user.GetMyProfileReq;
import model.request.user.SetMyProfileReq;
import model.response.Response;
import model.response.server.GetServerInfoRes;
import model.response.user.GetMyProfileRes;
import model.user.UserStatus;

import java.io.*;
import java.net.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;


public class SetImageController extends Controller
{
    private String id;
    private SetImageType type;
    private Stage stage;

    @FXML
    private GridPane pane;

    @FXML
    private Button choose;

    @FXML
    private Button setStatus;

    @FXML
    private Button done;
    @FXML
    private Button cancel;

    @FXML
    private Label message;

    @FXML
    private Circle image;

    @FXML
    private Circle status;

    private byte[] imageFile;

    @FXML
    private void choose(ActionEvent actionEvent)
    {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);

        if(null == file)
        {
            return;
        }

        try
        {
            InputStream inputStream = new FileInputStream(file);

            Image image = null;

            image = new Image(inputStream);

            if(image.isError())
            {
                setMessage("Invalid file type!");
                return;
            }

            ImagePattern imagePattern = new ImagePattern(image);

            this.image.setFill(imagePattern);

            imageFile = Files.readAllBytes(Path.of(file.getPath()));
        }
        catch (IOException e)
        {
            setMessage("Failed to open the chosen file!");
        }
    }

    @FXML
    private void status(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/setStatus.fxml"));
        Parent parent = loader.load();
        showScene(new Scene(parent));
    }
    @FXML
    private void done(ActionEvent actionEvent)
    {
        switch (type)
        {
            case USER :
            {
                clientSocket.send(new SetMyProfileReq(clientSocket.getId() , null
                        , null , null , null ,
                        null , imageFile , null));
                break;
            }
            case SERVER :
            {
                clientSocket.send(new SetServerImageReq(clientSocket.getId() , id , imageFile));
                break;
            }
        }

        try
        {
            Response response = clientSocket.getReceiver().getResponse();

            if(!response.isAccepted())
            {
                setMessage(response.getMessage());
                return;
            }

            closeScene();
        }
        catch (ResponseNotFoundException e)
        {
            System.out.println(e.getMessage());
            setMessage("Failed to set image");
        }

    }

    @FXML
    private void cancel(ActionEvent actionEvent)
    {
        closeScene();
    }

    private void setMessage(String text)
    {
        message.setVisible(true);
        message.setText(text);
    }

    public void initialize(SetImageType type , String id)
    {
        this.type = type;
        this.id = id;

        initialize();
    }

    private void initialize()
    {
        stage = new Stage();

        stage.focusedProperty().addListener((obs , oldFocus , newFocus) -> focusHandler(newFocus , stage));
        stage.setOnHidden(e -> hideHandler());

        switch (type)
        {
            case USER :
            {
                status.setVisible(true);
                setStatus.setVisible(true);

                try
                {
                    clientSocket.send(new GetMyProfileReq(clientSocket.getId()));

                    GetMyProfileRes response = (GetMyProfileRes)clientSocket.getReceiver().getResponse();

                    if(!response.isAccepted())
                    {
                        closeScene();
                    }

                        setImage(response.getProfileImage());
                        setStatus(response.getUserStatus());
                }
                catch(ResponseNotFoundException e)
                {
                    closeScene();
                }

                break;
            }
            case SERVER:
            {
                try
                {
                    clientSocket.send(new GetServerInfoReq(clientSocket.getId() , id));

                    GetServerInfoRes response = (GetServerInfoRes) clientSocket.getReceiver().getResponse();

                    System.out.println(response.getMessage());
                    if(!response.isAccepted())
                    {
                        closeScene();
                    }

                    setImage(response.getImage());
                }
                catch (ResponseNotFoundException e)
                {
                    System.out.println(e.getMessage());
                    closeScene();
                }

                break;
            }
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
        stage.hide();
    }

    private void setImage(byte[] image)
    {
        Image proImage;

        if(null == image && SetImageType.USER == type)
        {
            proImage = new Image("image/user-default.png");
        }
        else if(null == image && SetImageType.SERVER == type)
        {
            proImage = new Image("image/server-default.jpg");
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
