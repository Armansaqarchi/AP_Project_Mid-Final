package client.controller.fxController;

import client.controller.fxController.type.SetImageType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.exception.ResponseNotFoundException;
import model.request.server.GetServerInfoReq;
import model.request.server.RenameServerReq;
import model.response.Response;
import model.response.server.GetServerInfoRes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class RenameServerController extends Controller
{
    private String serverId;
    private Stage stage;

    @FXML
    private GridPane pane;

    @FXML
    private Label message;


    @FXML
    private Button done;
    @FXML
    private Button cancel;
    @FXML
    private Button setImage;

    @FXML
    private Circle image;

    @FXML
    private TextField textField;

    @FXML
    private void done(ActionEvent event)
    {
        try{
            //sends the related req
            clientSocket.send(new RenameServerReq(clientSocket.getId() , serverId , textField.getText()));

            Response response = clientSocket.getReceiver().getResponse();

            if(!response.isAccepted())
            {
                setMessage(response.getMessage());
                return;
            }

            closeScene();
        }
        catch(ResponseNotFoundException e)
        {
            System.out.println(e.getMessage());
            setMessage("failed to change name");
        }
    }

    @FXML
    private void cancel(ActionEvent event)
    {
        closeScene();
    }

    @FXML
    private void setImage(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/setImage.fxml"));
        Parent parent = loader.load();

        SetImageController controller = loader.getController();

        controller.initialize( SetImageType.SERVER , serverId);

        showScene(new Scene(parent));
    }

    private void closeScene()
    {
        Stage stage = (Stage)pane.getScene().getWindow();
        stage.hide();
    }

    private void setMessage(String text)
    {
        message.setVisible(true);
        message.setText(text);
    }

    public void setServerId(String serverId)
    {
        this.serverId = serverId;
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
            clientSocket.send(new GetServerInfoReq(clientSocket.getId() , serverId));

            GetServerInfoRes response = (GetServerInfoRes) clientSocket.getReceiver().getResponse();

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
    }

    private void setImage(byte[] image)
    {
        Image serverImage;

        if(null == image)
        {
            serverImage = new Image("image/server-default.jpg");
        }
        else
        {
            InputStream inputStream = new ByteArrayInputStream(image);
            serverImage = new Image(inputStream);
        }

        ImagePattern pattern = new ImagePattern(serverImage);

        this.image.setFill(pattern);
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
}
