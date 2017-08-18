
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lab7
 */
public class ServerClass {
    
    private static int uniqueId = 0;
    private ArrayList<ClientThread> clients;
    private int port;
    private boolean keepGoing;
    String ip;
    
    public ServerClass(int port) {
        this.port = port;
        clients = new ArrayList();
    }
    
    public void start() {
        keepGoing = true;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            // keep waiting for clients to connect
            while(keepGoing) {
                System.out.println("Waiting for clients on port " + port);
                
                Socket socket = serverSocket.accept();
                
                if(!keepGoing)
                    break;
                ClientThread t = new ClientThread(socket);
                clients.add(t);
                t.start();
            }
        } catch (Exception e) {
            String msg = "Exception on new ServerSocket: " + e + "\n";
                System.out.println(""+msg);
        }
    }
    
    public static void main(String argv[]) throws Exception {
        ServerClass sc = new ServerClass(1080);
        sc.start();
    
    }
    
    class ClientThread extends Thread {
        Socket socket;
        ObjectInputStream cInput;
        ObjectOutputStream cOutput;
        
        int connectionId;
        String date;
        
        ClientThread(Socket socket) {
            //add func to set userId
            connectionId = ++uniqueId;
            this.socket = socket;
            System.out.println("Thread trying to create Object Input/Output Streams");
            try {
                cOutput = new ObjectOutputStream(socket.getOutputStream());
                cInput = new ObjectInputStream(socket.getInputStream());
            } catch (Exception e) {
                System.out.println("Error creating streams");
            }
        }
        
        public void run() {
            while(true) {
                try {
                    Object obj = cInput.readObject();
                    System.out.println("Received Message : " + obj.toString() + " from " + connectionId);
                    //send ack
                    if(obj.toString().contains("getDate"))
                        sendObject(new Date().toString());
                    sendObject("Your message was received " + connectionId);
                } catch (Exception e) {
                    System.out.println("Error reading object from client " + connectionId);
                    close();
                    break;
                }
            }
        }
        
        private void close() {
            try {
                if(cOutput != null)
                    cOutput.close();
                if(cInput != null)
                    cInput.close();
                if(socket != null)
                    socket.close();
            } catch (Exception e) {
            }
        }
        
        public boolean sendObject(Object obj) {
            try {
                if(!socket.isConnected()) {
                    close();
                    return false;
                }
                cOutput.writeObject(obj);
            } catch (Exception e) {
                System.out.println("Error sending object to client " + connectionId);
                e.printStackTrace();
            }
            return true;
        }
    }
}
