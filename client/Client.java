package pac.client;
import pac.server.Server;
import pac.server.ServerGUI;

public class Client {
    private String name;
    private String password;
    private String ip;
    private String port;
    public ClientView clientView;
    private Server server;
    public boolean connected;

    public Client(ClientView clientView, Server server) {
        this.server = server;
        this.clientView = clientView;
    }

    public boolean connectToServer() {
        if (isConnectServer()) {
            connected = true;
            return true;
        }
        return false;
    }

    public boolean isConnectServer() { // проверка сервера на доступность
        if (server.connected) {
            return true;
        } else {
            clientView.disconnectFromServer();
            return false;
        }
    }

    public void printText(String text) {
        clientView.showMessage(text);
    }

    public void sendMessage(String text) { // отправка сообщений на сервер
        if (connected) {
            server.textAdd(text);
        } else {
            clientView.disconnectFromServer();
        }
    }

    public void changeCondition(boolean connect) { // метод изменения состояния клиента (цвет кнопок)
        if (!connect) {
            this.connected = false;
            clientView.disconnectFromServer();
        }
    }
}
