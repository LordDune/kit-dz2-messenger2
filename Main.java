package pac;

import pac.client.ClientGUI;
import pac.server.Server;

public class Main {
    public static void main(String[] args) {

        Server server = new Server(); // создание сервера
        new ClientGUI(server); // создание клиента
        new ClientGUI(server); // создание клиента
    }
}