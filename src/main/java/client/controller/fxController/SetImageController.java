package client.controller.fxController;

import client.controller.fxController.type.SetImageType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;


public class SetImageController extends Controller
{
    private SetImageType type;

    @FXML
    private Button choose;
    @FXML
    private Button done;
    @FXML
    private Button cancel;

    @FXML
    private Label message;

    @FXML
    private Circle image;

    private byte[] imageFile;

    @FXML
    private void choose(ActionEvent actionEvent)
    {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);

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
    private void done(ActionEvent actionEvent)
    {
        if(null != imageFile)
        {
            //incomplete
        }
        else
        {
            setMessage("Choose a file first!");
        }
    }

    @FXML
    private void cancel(ActionEvent actionEvent)
    {
        //incomplete
    }

    private void setMessage(String text)
    {
        message.setVisible(true);
        message.setText(text);
    }

    public void setType(SetImageType type)
    {
        this.type = type;
    }
}
