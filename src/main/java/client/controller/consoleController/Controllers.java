package client.controller.consoleController;

import client.ClientSocket;
import model.user.User;

public class Controllers {

    //this class is go-between class
    //between controller's class and menu class

    //singleton design pattern
    private static Controllers controllers;

    private final ProfileController profileController;
    private final ServerController serverController;
    private final SignInController signInController;
    private final SignUpController signUpController;
    private final UserController userController;
    private final ChannelController channelController;

    private Controllers(ClientSocket clientSocket){
        this.userController = new UserController(clientSocket);
        this.profileController = new ProfileController(clientSocket);
        this.signUpController = new SignUpController(clientSocket);
        this.serverController = new ServerController(clientSocket);
        this.channelController = new ChannelController(clientSocket);
        this.signInController = new SignInController(clientSocket);
    }

    public static Controllers getControllers(ClientSocket clientSocket) {
        if(controllers == null){
            return new Controllers(clientSocket);
        }
        return controllers;
    }

    public static void setControllers(Controllers controllers) {
        Controllers.controllers = controllers;
    }

    public ProfileController getProfileController() {
        return profileController;
    }

    public ServerController getServerController() {
        return serverController;
    }

    public SignInController getSignInController() {
        return signInController;
    }

    public SignUpController getSignUpController() {
        return signUpController;
    }

    public UserController getUserController() {
        return userController;
    }

    public ChannelController getChannelController() {
        return channelController;
    }
}
