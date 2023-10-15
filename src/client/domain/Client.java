package client.domain;


import client.ui.ClientView;
import server.domain.Server;

public class Client {
    private String name;
    private ClientView clientView;
    private Server server;
    private boolean connected;

    public Client(ClientView clientView, Server server) {
        this.clientView = clientView;
        this.server = server;
    }

    public boolean connectToServer(String name) {
        this.name = name;
        if (server.connectUser(this)) {
            showOnWindow("Вы успешно подключились!\n");
            connected = true;
            String log = server.getHistory();
            if (log != null) {
                showOnWindow(log);
            }
            return true;
        } else {
            showOnWindow("Подключение не удалось");
            return false;
        }
    }

    //мы посылаем
    public void message(String text) {
        if (connected) {
            if (!text.isEmpty()) {
                server.message(name + ": " + text);
            }
        } else {
            showOnWindow("Нет подключения к серверу");
        }
    }

    //нам посылают
    public void answerFromServer(String text) {
        showOnWindow(text);
    }

    public String getName() {
        return name;
    }

    public void disconnectFromServer() {
        if (connected){
            connected=false;
            clientView.disconnectFromServer();
            server.disconnectUser(this);
            showOnWindow("Вы были отключены от сервера!");
        }
    }

    private void showOnWindow(String text) {
        clientView.showMessage(text + "\n");
    }
}

