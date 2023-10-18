package pac.repository;

import pac.client.ClientView;
import pac.server.Server;
import java.io.*;

public class Repository implements Repositorable {

    Server server;
    String file;

    public Repository(Server server) {
        this.server = server;
        file = server.file;
    }

    @Override
    public void readFile(ClientView client) {
        try { // восстановление окна сообщений из лог файла при условии подключения клиента
            BufferedReader br = new BufferedReader(new FileReader(file));
            String str;
            while ((str = br.readLine()) != null) {
                client.showMessage(str);
            }
            br.close();
        } catch (IOException ex) {
            throw new RuntimeException("Ошибка чтения файла");
        }
    }

    @Override
    public void writeFile(String text) {
        try {
            FileWriter file = new FileWriter(this.file, true);
            file.append(text + "\n");
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}