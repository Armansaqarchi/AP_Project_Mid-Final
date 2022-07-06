package client.controller.fxController;

import client.ClientSocket;

public abstract class Controller {

    protected ClientSocket clientSocket;

    public Controller(ClientSocket clientSocket){
        this.clientSocket = clientSocket;
    }
}
