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
import java.util.ArrayList;
import java.util.List;

public class ServerThreadsClient implements Runnable {

    //int byteRead;
    Socket socket;
    String line;
    JDBCManager jdbcManager = new JDBCManager();
    FileManager fileManager = new FileManager(jdbcManager);
    PatientManager patientManager = new PatientManager(jdbcManager, fileManager);
    //fileManager.setPatientManager(patientManager);
    
    public ServerThreadsClient(Socket socket) {
        this.socket = socket;
    }
    
    /*public void receiveAndStoreParam(int patientId){
        
    }*/
        
    
    @Override
    public void run() {  // receives ECG file

        // se le tiene que pasar el patientId
        // qué file path?
        
        BufferedReader bufferedReader = null;
        int patientId = 1; // ??
        File file = new File("pathname");
        FileWriter fileWriter = null;
        try{
            fileWriter = new FileWriter(file.getName());
        }catch(IOException e){
            e.printStackTrace();
        }
        
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while ((line = bufferedReader.readLine()) != null) { 
                System.out.println(line);
                fileWriter.write(line);
            }
            try{
                fileManager.addFile(file, patientId);
            }catch(SQLException e){
                    e.printStackTrace(); // ??
            } 
            
        } catch (IOException ex) {
            Logger.getLogger(ServerThreadsClient.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            releaseResourcesClient(bufferedReader, socket);
        }
    }
    
    public void sendPatient(int patientId) {
        Patient patient = null;
        try{
            patient = patientManager.searchPatientById(patientId);
        }catch(SQLException e){
            e.printStackTrace(); //??
        }   
        String patientSend = patient.toString();
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(patientSend);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void sendPatientsFileNames(int patientId) {
        Patient patient = null;
        List<String> fileNames = new ArrayList<String>();
        try{
            fileNames = fileManager.getPatientsFileNamesById(patientId);
        }catch(SQLException e){
            e.printStackTrace(); //??
        }   
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(fileNames);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

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

