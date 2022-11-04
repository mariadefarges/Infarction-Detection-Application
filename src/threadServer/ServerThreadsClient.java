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
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import pojos.*;
import jdbc.*;
import java.sql.*;

public class ServerThreadsClient implements Runnable {

    //int byteRead;
    Socket socket;
    String line;

    public ServerThreadsClient(Socket socket) {
        this.socket = socket;
    }
    
    
    @Override
    public void run() {  // receives ECG file

        BufferedReader bufferedReader = null;
        PatientManager patientManager; // ??
        int patientId; // ??

        try {

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        
            while ((line = bufferedReader.readLine()) != null) { 
                System.out.println(line);
                /*try{
                    patientManager.addECG(patientId, line);
                }catch(SQLException e){
                    e.printStackTrace(); //??
                }*/
                
                
            } 
            
        } catch (IOException ex) {
            Logger.getLogger(ServerThreadsClient.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            releaseResourcesClient(bufferedReader, socket);
        }

    }
    
    /*public void sendPatient(Patient patient) {
        String patientSend = patient.toString();
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(patientSend);
        } catch (IOException ex) {
            System.out.println("An error occured tying to send the client");
            ex.printStackTrace();
        }
    }*/

    private static void releaseResourcesClient(BufferedReader bufferedReader, Socket socket) {
        try {
            bufferedReader.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerThreads.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerThreads.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

