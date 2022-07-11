package client.controller.fxController;
import client.controller.InfoVerifier;
import client.controller.fxController.type.EditProfileType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.exception.*;
import model.request.user.SetMyProfileReq;
import model.response.Response;

import java.io.IOException;

public class EditProfileController extends Controller
{
    private EditProfileType type;

    @FXML
    private GridPane pane;

    @FXML
    private Label topLabel;

    @FXML
    private Label editValue;

    @FXML
    private TextField value;

    @FXML
    private Label message;

    @FXML
    private Button cancel;
    @FXML
    private Button done;

    @FXML
    private void done(ActionEvent event)
    {
        switch (type)
        {
            case ID :
            {
                try
                {
                    InfoVerifier.checkUserValidity(value.getText());
                    clientSocket.send(new SetMyProfileReq(clientSocket.getId() , value.getText()
                            , null , null , null ,
                            null , null , null));

                    Response response = clientSocket.getReceiver().getResponse();

                    if(!response.isAccepted())
                    {
                        setMessage(response.getMessage());
                        return;
                    }

                    clientSocket.setId(value.getText());

                    closeScene();
                }
                catch (InvalidUsernameException e)
                {
                    setMessage(e.getMessage());
                }
                catch (ResponseNotFoundException e)
                {
                    System.out.println(e.getMessage());
                    setMessage("failed to change id");
                }

                break;
            }
            case NAME:
            {
                try
                {
                    clientSocket.send(new SetMyProfileReq(clientSocket.getId() , null , value.getText()
                            , null , null ,
                            null , null , null));

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
                    setMessage("failed to change name");
                }

                break;
            }
            case PASSWORD:
            {
                try
                {
                    InfoVerifier.checkPasswordValidity(value.getText());
                    clientSocket.send(new SetMyProfileReq(clientSocket.getId() , null
                            , null , value.getText() , null ,
                            null , null , null));

                    Response response = clientSocket.getReceiver().getResponse();

                    if(!response.isAccepted())
                    {
                        setMessage(response.getMessage());
                        return;
                    }

                    closeScene();
                }
                catch (InvalidPasswordException e)
                {
                    setMessage(e.getMessage());
                }
                catch (ResponseNotFoundException e)
                {
                    System.out.println(e.getMessage());
                    setMessage("failed to change password");
                }
                break;
            }
            case EMAIL:
            {
                try
                {
                    InfoVerifier.checkEmailValidity(value.getText());
                    clientSocket.send(new SetMyProfileReq(clientSocket.getId() , null
                            , null , null , value.getText() ,
                            null , null , null));

                    Response response = clientSocket.getReceiver().getResponse();

                    if(!response.isAccepted())
                    {
                        setMessage(response.getMessage());
                        return;
                    }

                    closeScene();
                }
                catch (InvalidEmailFormatException e)
                {
                    setMessage(e.getMessage());
                }
                catch (ResponseNotFoundException e)
                {
                    System.out.println(e.getMessage());
                    setMessage("failed to change email");
                }
                break;
            }
            case PHONE_NUMBER:
            {
                try
                {
                    InfoVerifier.checkPhoneNumberValidity(value.getText());
                    clientSocket.send(new SetMyProfileReq(clientSocket.getId() , null
                            , null , null , null ,
                            value.getText() , null , null));

                    Response response = clientSocket.getReceiver().getResponse();

                    if(!response.isAccepted())
                    {
                        setMessage(response.getMessage());
                        return;
                    }

                    closeScene();
                }
                catch (InvalidPhoneNumberException e)
                {
                    setMessage(e.getMessage());
                }
                catch (ResponseNotFoundException e)
                {
                    System.out.println(e.getMessage());
                    setMessage("failed to change phone number");
                }
                break;
            }
        }
    }

    @FXML
    private void cancel(ActionEvent event)
    {
        closeScene();
    }

    public void setType(EditProfileType type)
    {
        this.type = type;

        initialize();
    }

    private void initialize()
    {
        switch (type)
        {
            case ID :
            {
                topLabel.setText("change your id");
                editValue.setText("user id");
                break;
            }
            case NAME:
            {
                topLabel.setText("change your name");
                editValue.setText("name");
                break;
            }
            case PASSWORD:
            {
                topLabel.setText("change your password");
                editValue.setText("password");
                break;
            }
            case EMAIL:
            {
                topLabel.setText("change your email");
                editValue.setText("email");
                break;
            }
            case PHONE_NUMBER:
            {
                topLabel.setText("change your phone number");
                editValue.setText("phone number");
                break;
            }
        }
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
}
