package net.roxis;

import javax.swing.*;
import java.awt.*;

public class GetSerialThread extends Thread {
    MyConnection myConnection = null;
    JTextArea textArea = null;
    boolean running = false;
    public GetSerialThread(){
        System.out.println("Boş oluştu");
    }

    public GetSerialThread(MyConnection myConnection, JTextArea textArea) {
        this.myConnection = myConnection;
        this.textArea = textArea;
    }
    public void setRun(boolean running){
        this.running = running;
    }
    @Override
    public void run() {
        super.run();
        while (running) {

            try {
                String read = myConnection.serialRead(1);
                textArea.append("\n" + read);
                sleep(10);
                System.out.println("gelen-veri -> " + read);
            } catch (Exception e) {
                System.out.println("Hata Kodu -> 0x00142");
                System.out.println("Hata " + e.getLocalizedMessage());
                System.out.println("Hata " + e.getMessage());
                e.printStackTrace();
            }
        }


    }
}
