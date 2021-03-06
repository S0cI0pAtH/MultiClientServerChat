/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author swapn
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    
    public static ArrayList<ClientHandler> clients =new ArrayList<ClientHandler>();
    
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            
            FriendRequestMessageHandler obj = new FriendRequestMessageHandler();
            
            obj.start();

            ServerSocket serverSocket = new ServerSocket(1235);
            
            while( true ){
                Socket connectionSocket = serverSocket.accept();
            
                System.out.println( "New Client "  );
                
                LogInHandler newlogIn = new LogInHandler(connectionSocket);
                
                newlogIn.start();

            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
