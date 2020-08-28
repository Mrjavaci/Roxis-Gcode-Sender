import com.fazecast.jSerialComm.SerialPort;

import com.fazecast.jSerialComm.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;

public class Main extends JFrame {
    static MyConnection myConnection;
    static JFrame frame = new JFrame("Roxis GCode Sender");
    static JButton btnRefresh;

    public static void main(String args[]) {
        setUpGUI();
        frame.setResizable(false);
    }
    public static void setUpGUI() {
        frame.setSize(900,300 );
        frame.setBackground(Color.black);
        frame.setForeground(Color.black);
        //frame.setPreferredSize(new Dimension(600,600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        JPanel pane = new JPanel();
        frame.add(pane, BorderLayout.CENTER);
        populateMenu();
      //  frame.pack();
        frame.getContentPane();
        frame.setVisible(true);

    }
    public static void populateMenu() {
        final MyDropdownMenu portList = new MyDropdownMenu();
        portList.refreshMenu();
        final JButton connectButton = new JButton("Bağlan");

        btnRefresh = new JButton();
        btnRefresh.setText("Yenile");
        JPanel topPanel = new JPanel();
        btnRefresh.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                portList.refreshMenu();

            }
        });
        topPanel.add(portList);
        topPanel.add(btnRefresh);
        GetSerialThread getSerialThread = new GetSerialThread();
        TextArea textArea = new TextArea();
        topPanel.add(connectButton);

        Label label = new Label("Komut Gönder: ");
        TextField textField = new TextField();
        textField.setPreferredSize(new Dimension(110, 30));
        Button sendGCode = new Button("Komut Gönder");

        topPanel.add(textArea);
        topPanel.add(label);
        topPanel.add(textField);
        topPanel.add(sendGCode);
        sendGCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("basıldı");
                myConnection.serialWrite(textField.getText());

            }
        });
        connectButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (connectButton.getText().equals("Bağlan")) {
                    myConnection = new MyConnection(portList.getSelectedItem().toString(), 9600);


                    if (myConnection.openConnection()) {
                        connectButton.setText("Bağlantıyı Kopart");
                        portList.setEnabled(false);
                        btnRefresh.setEnabled(false);
                        //myConnection.serialWrite("");
                        GetSerialThread getSerialThread = new GetSerialThread(myConnection,textArea);
                        getSerialThread.setRun(true);
                        getSerialThread.start();
                        frame.pack();
                        myConnection.serialWrite("X10");

                    }
                } else {
                    getSerialThread.setRun(false);

                    myConnection.closeConnection();
                    connectButton.setText("Bağlan");
                    portList.setEnabled(true);
                    btnRefresh.setEnabled(true);
                }
            }

        });
        frame.add(topPanel, BorderLayout.NORTH);


    }

}