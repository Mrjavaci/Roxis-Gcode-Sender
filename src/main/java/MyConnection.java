import com.fazecast.jSerialComm.SerialPort;

import java.awt.*;
import java.io.PrintWriter;
import java.util.Scanner;

public class MyConnection {
    private SerialPort comPort;
    private String portDescription;
    private int baud_rate;

    public MyConnection() {
        //empty constructor if port undecided
    }
    public MyConnection(String portDescription) {
        this.portDescription = portDescription;
        comPort = SerialPort.getCommPort(this.portDescription);
    }

    public MyConnection(String portDescription, int baud_rate) {
        this.portDescription = portDescription;
        comPort = SerialPort.getCommPort(this.portDescription);
        this.baud_rate = baud_rate;
        comPort.setBaudRate(this.baud_rate);
    }


    public boolean openConnection(){
        if(comPort.openPort()){
            try {Thread.sleep(100);} catch(Exception e){}
            return true;
        }
        else {
            System.out.println("Error code -> 0x0023");
            return false;
        }
    }

    public void closeConnection() {
        comPort.closePort();
    }

    public void setPortDescription(String portDescription){
        this.portDescription = portDescription;
        comPort = SerialPort.getCommPort(this.portDescription);
    }
    public void setBaudRate(int baud_rate){
        this.baud_rate = baud_rate;
        comPort.setBaudRate(this.baud_rate);
    }

    public String getPortDescription(){
        return portDescription;
    }

    public SerialPort getSerialPort(){
        return comPort;
    }


    public String serialRead(){
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        String out="";
        Scanner in = new Scanner(comPort.getInputStream());
        try
        {
            while(in.hasNext())
                out += (in.next()+"\n");
            in.close();
        } catch (Exception e) { e.printStackTrace(); }
        return out;
    }

    public String serialRead(int limit){
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        String out="";
        int count=0;
        Scanner in = new Scanner(comPort.getInputStream());
        try
        {
            while(in.hasNext()&&count<=limit){
                out += (in.next()+"\n");
                count++;
            }
            in.close();
        } catch (Exception e) { e.printStackTrace(); }
        return out;
    }

    public void serialWrite(String s){
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        try{Thread.sleep(5);} catch(Exception e){}
        PrintWriter pout = new PrintWriter(comPort.getOutputStream());
        pout.print(s);
        pout.flush();

    }
    public void serialWrite(String s,int noOfChars, int delay){
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        try{Thread.sleep(5);} catch(Exception e){}
        PrintWriter pout = new PrintWriter(comPort.getOutputStream());
        for(int i=0;i<s.length();i+=noOfChars){
            pout.write(s.substring(i,i+noOfChars));
            pout.flush();
            System.out.println(s.substring(i,i+noOfChars));
            try{Thread.sleep(delay);}catch(Exception e){}
        }
        pout.write(noOfChars);
        pout.flush();

    }

    public void serialWrite(char c){
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        try{Thread.sleep(5);} catch(Exception e){}
        PrintWriter pout = new PrintWriter(comPort.getOutputStream());
        pout.write(c);
        pout.flush();
    }

    public void serialWrite(char c, int delay){
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        try{Thread.sleep(5);} catch(Exception e){}
        PrintWriter pout = new PrintWriter(comPort.getOutputStream());pout.write(c);
        pout.flush();
        try{Thread.sleep(delay);}catch(Exception e){}
    }
}
