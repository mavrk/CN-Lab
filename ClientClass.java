
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lab7
 */
public class ClientClass {
    
    private ObjectInputStream cInput;		
    private ObjectOutputStream cOutput;		
    private Socket socket;
    private int port;
    private String serverIp;
    
    public ClientClass(int port, String serverIp) {
        this.port = port;
        this.serverIp = serverIp;
    }
    
    public boolean startConnection() {
        try {
            socket = new Socket(serverIp, port);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        
        String msg = "Conncetion accepted " + socket.getInetAddress() + " : " + socket.getPort();
        System.out.println(""+msg);
        
        try {
            cInput  = new ObjectInputStream(socket.getInputStream());
            cOutput = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException eIO) {
            System.out.println("Exception creating new Input/output Streams: " + eIO);
            return false;
	}
        
        new ListenFromServer().start();
        
        return true;
    }
    
    public void sendObject(Object obj) {
        try {
            System.out.println("Sending object" + obj.toString());
            cOutput.writeObject(obj);
        } catch(Exception e){
            System.out.println("Error sending object " + e);
        }
    }
    
    class ListenFromServer extends Thread {
        public void run() {
            while(true) {
                try {
                    Object obj = cInput.readObject();
                    System.out.println(obj.toString());
                } catch(Exception e){
                    e.printStackTrace();
                }
                
            }
        }
    }
    
    public static void main(String argv[]) throws Exception {
        ClientClass cc = new ClientClass(1080,"localhost");
        cc.startConnection();
        Scanner sc = new Scanner(System.in);
        while(true){
            String input = sc.nextLine();
            if(input.contains("-1"))
                System.exit(0);
            cc.sendObject(input);
        }
        
        
    }
   
}
