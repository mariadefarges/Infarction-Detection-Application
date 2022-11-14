/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadServer;

/**
 *
 * @author carlo
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ui.InputOutput;

public class ServerThreads {

    public static void main(String args[]) throws IOException {
        //Create a service that is waiting in port 9000
        ServerSocket serverSocket = new ServerSocket(9000);
        Boolean clientsConnected = false;
        
        try {
            System.out.println("Welcome to the Infarction Application Server.");
            System.out.println("\n1.Close the server\n");
            String option = null;
            option = InputOutput.get_String();
            while (true) {
                if (option.equals("1")){
                    if(clientsConnected){
                        System.out.println("There are clients still connected. \n Are you sure you want to close the server? Y/N");
                        option = InputOutput.get_String();
                        if (option.equals("Y")){
                            releaseResourcesServer(serverSocket);
                            System.exit(0);
                        }
                    }
                    else{
                        releaseResourcesServer(serverSocket);
                        System.exit(0);
                    }
                }
                Socket socket = serverSocket.accept();
                new Thread(new ServerThreadsClient(socket)).start();
                if(!socket.isClosed()){
                    clientsConnected = true;
                }
                
            }
        } 
        catch (SocketException ex){
            ex.printStackTrace();
        }
        finally {
            releaseResourcesServer(serverSocket);
        }
    }

    

    private static void releaseResourcesServer(ServerSocket serverSocket) {
        try {
            serverSocket.close();
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(ServerThreads.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    }

