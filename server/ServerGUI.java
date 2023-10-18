package pac.server;
import pac.client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerGUI extends JFrame implements ServerView {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 350;

    JTextArea logArea;
    JButton start = new JButton("Start");
    JButton stop = new JButton("Stop");

    Server server;

    public ServerGUI(Server server) {
        this.server = server;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setTitle("Server");
        panelBottom();
        panelLog();
        setVisible(true);
    }

    private void panelLog() { // добавление поля, где отображается чат
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        add(new JScrollPane(logArea));
    }

    public void changeCondition(boolean connected){ // метод изменения состояния сервера (цвет кнопок)
        if (server.getConnected() != connected) {
            server.setConnected(connected);
        }
        if (connected) {
            stop.setBackground(Color.LIGHT_GRAY);
            start.setBackground(Color.GREEN);
            logArea.setText("");
        } else {
            start.setBackground(Color.LIGHT_GRAY);
            stop.setBackground(Color.RED);
        }
    }

    public void panelBottom(){ // добавление поля с кнопками
        JPanel panelBottom = new JPanel(new GridLayout(1, 2));
        panelBottom.add(start);
        panelBottom.add(stop);
        add(panelBottom, BorderLayout.SOUTH);

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // старт сервера, считывание лога чата из файла
                changeCondition(true);
            }
        });
        stop.addActionListener(new ActionListener() { // стоп сервера, вызов у всех клиентов метода отключения
            @Override
            public void actionPerformed(ActionEvent e) {
                changeCondition(false);
                for (Client client : server.getClients()) {
                    client.changeCondition(false);
                }
            }
        });
    }

    @Override
    public void textAdd(String text) {
        logArea.append(text + "\n");
    }
}