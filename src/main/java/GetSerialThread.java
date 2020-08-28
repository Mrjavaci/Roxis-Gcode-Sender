import java.awt.*;

public class GetSerialThread extends Thread {
    MyConnection myConnection = null;
    TextArea textArea = null;
    boolean running = false;
    public GetSerialThread(){
        System.out.println("Boş oluştu");
    }

    public GetSerialThread(MyConnection myConnection, TextArea textArea) {
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
            textArea.append("\n" + myConnection.serialRead(1));
                sleep(10);
            } catch (Exception e) {
                System.out.println("Hata Kodu -> 0x00142");
                System.out.println("Hata " + e.getLocalizedMessage());
                System.out.println("Hata " + e.getMessage());
                e.printStackTrace();
            }
        }


    }
}
