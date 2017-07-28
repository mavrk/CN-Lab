
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sanatt
 */
public class MainClass {
    public static void main(String[] args) throws MalformedURLException {
        
        //print different components of URL
        URL someUrl = new URL("https://172.16.1.1:8080/httpclient.html");
        System.out.println("Protocol : " + someUrl.getProtocol());
        System.out.println("Host : " + someUrl.getHost());
        System.out.println("Port : " + someUrl.getPort());
        System.out.println("File : " + someUrl.getFile());
        
        //which ports are being listened to on the local host
        Socket socket;
        for(int i=130; i++ < 136; ){
            try{
                socket = new Socket("localhost",i);
                System.out.println("Port open : "+i);
                socket.close();
            }
            catch(Exception e){
                System.out.println("Failed port :" + i);
            }
        }
    }
}
