package pac.repository;
import pac.client.ClientView;

public interface Repositorable {

    void readFile(ClientView client);
    void writeFile(String text);
}
