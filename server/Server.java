package pac.server;

import pac.client.Client;
import pac.client.ClientView;
import pac.repository.Repositorable;
import pac.repository.Repository;

import java.util.ArrayList;

public class Server implements Repositorable, ServerView {

    ServerView serverView;
    public boolean connected; // начальное состояние сервера (выкл)
    Repositorable repository;
    public ArrayList<Client> clients = new ArrayList<>(); // список клиентских окон, которые будут создаваться
    public String file = "./log.txt";

    public Server() {
        repository = new Repository(this);
        this.serverView = new ServerGUI(this);
    }

    public void readFile(ClientView client) {
        repository.readFile(client);
    }

    public void writeFile(String text){ // сохранение передаваемого текста в лог файла
        repository.writeFile(text);
    }

    public void textAdd(String text) {  // добавление сообщений в окно сервера и отправка сообщений всем экземплярам клиентского окна
        serverView.textAdd(text);
        for (Client client : clients) {
            if (client.connected) {
                client.printText(text); // вызов у клиента метода добавления сообщения в окно чата
            }
        }
        writeFile(text); // вызов метода добавления сообщения в лог файл
    }

    public boolean getConnected(){
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public ArrayList<Client> getClients(){
        return clients;
    }
}
