
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lab7
 */
public class MainClass {
    public static void main(String[] args) throws UnknownHostException, SocketException, IOException{
        
        //print the hostname
        System.out.println("Host name: " + InetAddress.getLocalHost().getHostName());
        String ip = "123.4.5.6";
        //print the local ip address
        String localAddressString = byteArrayToIpAddress(InetAddress.getLocalHost().getAddress());
        System.out.println("Local IP Address: " +localAddressString);
        
        //print any remote host ipaddress by giving its hostname
        InetAddress remoteHost = InetAddress.getByName("www.google.com");
        System.out.println("Ip address for www.google.com: " + remoteHost.getHostAddress());
        
        //print remote hostname by giving ip address
        remoteHost = InetAddress.getByName("172.16.85.45");
        System.out.println("Host name for 172.16.85.45: " + remoteHost.getHostName());
    
        //print MAC address of local host
        NetworkInterface localInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
        String localInterfaceAddressString = byteArrayToMacAddress(localInterface.getHardwareAddress());
        System.out.println(localInterfaceAddressString);
        
        //take ip input and return ip class
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the ip address: ");
        System.out.println(getIpAddressClass(reader.readLine()));
        
    }
    
    public static String byteArrayToIpAddress(byte[] bytes){
        String address = "";
        for (byte b : bytes)
            address += "." + (0xFF & b);
        return address.substring(1);
    }
    
    public static String byteArrayToMacAddress(byte[] bytes){
        String address = "";
        for(byte b : bytes)
            address += ":" + String.format("%02X", b);
        return address.substring(1);
    }
    
    public static String getIpAddressClass(String address){
        int classDeterminer = Integer.parseInt(address.split("\\.")[0]);
        if(classDeterminer >= 240)
            return "Class E";
        else if(classDeterminer >= 224)
            return "Class D";
        else if(classDeterminer >= 192)
            return "Class C";
        else if(classDeterminer >= 128)
            return "Class B";
        else if(classDeterminer <= 126)
            return "Class A";
        else
            return "Not a valid IP class";
    }
    
}
