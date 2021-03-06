/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author swapn
 */
public class Client {
    /**
     * @param args the command line arguments
     */
    
    static boolean logIn( String userName, String password ){
        
        try {
            Socket clientSocket = new Socket( "localhost", 1235 );
            
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

            outToServer.writeBytes("login" + '\n');
            
            String out = userName + ":" + password;
            
            outToServer.writeBytes(out + '\n');
            
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            
            String confirmation = inFromServer.readLine();
            
            
            if( "ok".equals(confirmation) ){
                
                System.out.println("Successfully logged In! ");
                
                ClientOperations newclient = new ClientOperations(clientSocket, 1 );
                newclient.start();
                
                ClientOperations newclient2 = new ClientOperations(clientSocket, 2 );
                newclient2.start();

                return true;
                
            }else {
                
                System.out.println("Wrong Username or password! ");
                return false;
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    static boolean registerUser( String name, String userName, String password ){
        
        try {
            Socket clientSocket = new Socket( "localhost", 1235 );
            
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            
            outToServer.writeBytes("register" + '\n');
            
            String out =  userName + ":" + password + ":" + name;
            
            outToServer.writeBytes(out + '\n');
            
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            
            String confirmation = inFromServer.readLine();
            
            if( "ok".equals(confirmation) ){
                
                System.out.println("Successfully Register Account! ");
                
                ClientOperations newclient = new ClientOperations(clientSocket, 1);
                newclient.start();
                
                ClientOperations newclient2 = new ClientOperations(clientSocket, 2 );
                newclient2.start();

                return true;
                
            }else {
                
                System.out.println("Username already exixts! ");
                return false;
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static void main(String[] args) {
        
        System.out.println( "Do you have an Account ? ( Yes/No ) ");
            
        Scanner sc = new Scanner( System.in  );
            
        String choose  = sc.next();
            
        if( "Yes".equals(choose) ){
            
            System.out.print("Enter UserName: ");
            String userName = sc.next();
            
            System.out.print("Enter Password: ");
            String password = sc.next();
            
            while( !logIn( userName, password ) ){
                
                System.out.print("Enter UserName: ");
                userName = sc.next();
                
                System.out.print("Enter Password: ");
                password = sc.next();
                
            }
        }else{
            
            System.out.print("Enter Your Name: ");
            sc.nextLine();
            String name = sc.nextLine();
            
            System.out.print("Enter UserName: ");
            String userName = sc.next();
            
            System.out.print("Enter Password: ");
            String pass = sc.next();
            
            while( !registerUser( name, userName, pass ) ){
                
                System.out.print("Enter Your Name: ");
                sc.nextLine();
                name = sc.nextLine();
                
                System.out.print("Enter UserName: ");
                userName = sc.next();
                
                System.out.print("Enter Password: ");
                pass = sc.next();
            }
        }
            
    }
}
