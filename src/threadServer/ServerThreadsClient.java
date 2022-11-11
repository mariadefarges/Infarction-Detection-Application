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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class ServerThreadsClient implements Runnable {

    //int byteRead;
    Socket socket;
    String line;
    JDBCManager jdbcManager = new JDBCManager();
    JDBCFileManager fileManager = new JDBCFileManager(jdbcManager);
    JDBCPatientManager patientManager = new JDBCPatientManager(jdbcManager);
    //fileManager.setPatientManager(patientManager);
    
    public ServerThreadsClient(Socket socket) {
        this.socket = socket;
    }
        
    
    @Override
    public void run() {  
        int patientId = 0;
        BufferedReader bufferedReader = null;
        try{
            String id = bufferedReader.readLine();
            patientId = Integer.parseInt(id);
        }catch(IOException e){
            e.printStackTrace();
        }
        LocalDateTime current  = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy.HH-mm-ss");
        String formattedDateTime = current.format(format);
        
        String userHome = System.getProperty("user.home");
        // patients/<PATIENT_ID>/YYYYMMDD-HHMMSS_<PATIENT_ID>.txt
        String path = "/patient" + patientId + "_" + formattedDateTime + ".txt";
        File file = new File(userHome + path);
        FileWriter fileWriter = null;
        try{
            fileWriter = new FileWriter(file);
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
        String patientSend = patient.toString2();
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

