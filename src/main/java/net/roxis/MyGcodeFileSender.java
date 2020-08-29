package net.roxis;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.io.*;

public class MyGcodeFileSender extends Thread {
    boolean isRunning = false;
    MyConnection myConnection;
    JTextArea textArea;
    FileInputStream fstream;
    FileInputStream fstream2;
    JProgressBar jProgressBar;

    public void setRunning(boolean running) {
        this.isRunning = running;
    }

    public MyGcodeFileSender(String FilePath, MyConnection myConnection, JTextArea textArea, JProgressBar progressBar1) {
        this.myConnection = myConnection;
        this.jProgressBar = progressBar1;
        this.textArea = textArea;
        try {
            fstream = new FileInputStream(FilePath);
            fstream2 = new FileInputStream(FilePath);
        } catch (Exception e1) {
            System.out.println("hata -> " + e1.getMessage());
        }

    }

    @Override
    public void run() {
        super.run();
        while (isRunning) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
                BufferedReader br2 = new BufferedReader(new InputStreamReader(fstream2));
                String strLine;
                String strLine2;
                int topLines = 0;

                int nowLine = 0;
                while ((strLine2 = br2.readLine()) != null) {
                    if (!strLine2.trim().isEmpty()) {
                        topLines++;
                    }
                }
                System.out.println("Top Lines " + topLines);
                while ((strLine = br.readLine()) != null) {
                    nowLine++;
                    System.out.println("inDongu-> " + nowLine + " strline -> " + strLine);
                    int yuzdelik = (nowLine * 100) / topLines;
                    jProgressBar.setValue(yuzdelik);
                    textArea.append(strLine + "\n");
                    textArea.setCaretPosition(textArea.getDocument().getLength());
                    DefaultCaret caret = (DefaultCaret) textArea.getCaret();
                    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
                    myConnection.serialWrite(strLine + "\n");

                    //Thread.sleep(1);

                }
                fstream.close();
                this.isRunning = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
