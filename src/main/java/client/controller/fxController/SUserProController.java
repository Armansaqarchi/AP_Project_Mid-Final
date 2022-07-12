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
import model.request.server.GetRulesServerReq;
import model.request.server.GetServerInfoReq;
import model.request.user.GetMyProfileReq;
import model.request.user.GetUserProfileReq;
import model.response.server.GetRulesServerRes;
import model.response.server.GetServerInfoRes;
import model.response.user.GetMyProfileRes;
import model.response.user.GetUserProfileRes;
import model.server.Rule;
import model.server.RuleType;
import model.user.UserStatus;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

public class SUserProController extends Controller
{
    private String UserId;
    private String serverId;

    private Stage stage;
    @FXML
    private GridPane pane;

    @FXML
    private Button editRole;

    @FXML
    private Circle image;
    @FXML
    private Circle status;

    @FXML
    private Label userId;
    @FXML
    private Label name;

    @FXML
    private Label role1;
    @FXML
    private Label role2;
    @FXML
    private Label role3;
    @FXML
    private Label role4;
    @FXML
    private Label role5;
    @FXML
    private Label role6;
    @FXML
    private Label role7;
    @FXML
    private Label role8;
    @FXML
    private Label role9;

    @FXML
    private void editRole(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/editRole.fxml"));
        Parent parent = loader.load();

        stage.setScene(new Scene(parent));

        EditRoleController controller = loader.getController();
        controller.setId(serverId , UserId);

        stage.show();
    }

    public void initialize(String userId , String serverId)
    {
        this.UserId = userId;
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

        //show edit role button for owner of server
        try
        {
            clientSocket.send(new GetServerInfoReq(clientSocket.getId(), serverId));

            GetServerInfoRes response = (GetServerInfoRes) clientSocket.getReceiver().getResponse();

            if(!response.isAccepted())
            {
                //close the scene if getting roles failed
                closeScene();
            }

            if(response.getOwnerId().equals(clientSocket.getId()))
            {
                editRole.setVisible(true);
            }
        }
        catch (ResponseNotFoundException e)
        {
            //close the scene if getting roles failed
            System.out.println(e.getMessage());
            closeScene();
        }

        try
        {
            clientSocket.send(new GetRulesServerReq(clientSocket.getId(), serverId));

            GetRulesServerRes response = (GetRulesServerRes)clientSocket.getReceiver().getResponse();

            if(!response.isAccepted())
            {
                //close the scene if getting roles failed
                closeScene();
            }

            if(null == response.getRules().get(UserId))
            {
                return;
            }

            LinkedList<RuleType> rules = new LinkedList<>(response.getRules().get(UserId).getRules());


            Label[] role = new Label[9];

            role[0] = role1;
            role[1] = role2;
            role[2] = role3;
            role[3] = role4;
            role[4] = role5;
            role[5] = role6;
            role[6] = role7;
            role[7] = role8;
            role[8] = role9;

            //set labels to invisible
            for(int i = 0 ; i < 9 ; i ++)
            {
                role[i].setVisible(false);
            }

            //show roles of this user
            for(int i = 0 ; i < rules.size() ; i++)
            {
                role[i].setVisible(true);
                role[i].setText(rules.get(i).getValue());
            }
        }
        catch (ResponseNotFoundException e)
        {
            //close the scene if getting roles failed
            System.out.println(e.getMessage());
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
        stage.hide();
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

    public void setUserId(String userId) {
        UserId = userId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }
}
