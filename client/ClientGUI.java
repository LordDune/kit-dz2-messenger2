package pac.client;

import pac.server.Server;
import pac.server.ServerGUI;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;

public class ClientGUI extends JFrame implements ClientView {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 350;

    public JButton login, send;
    public JPanel panelTop, panelBottom;
    public JPasswordField password;
    public JTextArea logArea;
    public JTextField name, ip, port, textInput;
    public JLabel clear;

    String TITLE = "Client";
    String SEND_BUTTON_TITLE = "send";
    String LOGIN_BUTTON_TITLE = "login";

    String ONLINE_MESSAGE = "Соединение установлено\n";
    String DISCONNECT_MESSAGE = "WARNING: Сервер недоступен\n";
    String SET_VALUE_MESSAGE = "WARNING: Введите ";
    ServerGUI serverGUI;
    Server server;

    private Client client;

    public ClientGUI(Server server) {
        this.serverGUI = serverGUI;
        this.server = server;
        client = new Client(this, server);
        server.clients.add(client);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setTitle(TITLE);
        login = new JButton(LOGIN_BUTTON_TITLE);
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // подключение к серверу при условии ввода всех данных и вклюенном состоянии сервера
                connectToServer();
            }
        });
        panelTop(); // добавление панели ввода данных и подключения
        log(); // добавление окна чата
        panelBottom(); // добавление панели ввода сообщения
        setVisible(true);
    }

    @Override
    public void showMessage(String text) {
        logArea.append(text + "\n");
    }

    @Override
    public void disconnectFromServer() {
        logArea.append(DISCONNECT_MESSAGE);
        panelTop.setVisible(true);
        login.setBackground(Color.RED);
    }

    @Override
    public void connectToServer() {
        if (isNotNull(name, "имя") &
            isNotNull(password, "пароль") &
            isNotNull(ip, "ip адрес сервера") &
            isNotNull(port, "порт")) {
               if (client.connectToServer()) { // если клиент успешно подключился, то скрыть панель
                   logArea.setText(""); // очистка окна сообщений
                   server.readFile(this); // вызывать метод чтобы прочитать файл лога
                   panelTop.setVisible(false);
                   login.setBackground(Color.GREEN);
                   logArea.append(ONLINE_MESSAGE);
               } else logArea.append(DISCONNECT_MESSAGE);
        }
    }


    public boolean isNotNull(JTextComponent e, String text){ // проверка полей на наличие введенных значений
        if (e.getText().length() != 0) {
            return true;
        } else {
            logArea.append(SET_VALUE_MESSAGE + text + "\n");
            return false;
        }
    }

    public void sendMessage(){
        if (textInput.getText().length() != 0) {
            String str = textInput.getText();
            String text = name.getText() + ": " + str;
            textInput.setText("");
            client.sendMessage(text);
    }}

    @Override
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            client.connected = false;
        }
    }

    private void log() {
        logArea = new JTextArea();
        logArea.setEditable(false);
        add(new JScrollPane(logArea));
    }

    private void panelBottom() { // метод добавления панели ввода сообщения
        panelBottom = new JPanel(new BorderLayout());
        textInput = new JTextField();
        send = new JButton(SEND_BUTTON_TITLE);
        panelBottom.add(textInput);
        panelBottom.add(send, BorderLayout.EAST);
        add(panelBottom, BorderLayout.SOUTH);
        send.addActionListener(new ActionListener() {
            @Override  // обработка нажатия кнопки send (отправка сообщений)
            public void actionPerformed(ActionEvent e) {
                sendMessage(); // вызов метода отправки сообщения
            }
        });

        textInput.addKeyListener(new KeyListener() { // обработка нажатия клавиши энтер при вводе сообщения

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage(); // вызов метода отправки сообщения
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    public void panelTop() { // панель ввода данных и подключения
        panelTop = new JPanel(new GridLayout(2,3));
        ip = new JTextField();
        port = new JTextField();
        name = new JTextField();
        password = new JPasswordField();
        clear = new JLabel();
        panelTop.add(ip);
        panelTop.add(port);
        panelTop.add(clear);
        panelTop.add(name);
        panelTop.add(password);
        panelTop.add(login);
        add(panelTop, BorderLayout.NORTH);
    }
        public boolean isNotNull(String e, String text){ // проверка полей на наличие введенных значений
            if (e != null) {
                return true;
            } else {
                showMessage(text);
                return false;
            }
        }
}
