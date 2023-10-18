package pac.client;

public interface ClientView {
    void showMessage(String text);
    void disconnectFromServer();
    void connectToServer();
}
