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
        BufferedReader bufferedReader = null;
        int opcion = 0;
        try{
            opcion = bufferedReader.read();
        }catch(IOException e){
            e.printStackTrace();
        }
        switch(opcion){
            case 1:
                try{
                    sendPatient();
                }catch(IOException e){
                    e.printStackTrace();
                }catch(SQLException s){
                    s.printStackTrace();
                }
                break;
            case 2:
                try{
                    sendPatientsFileNames();
                }catch(IOException e){
                    e.printStackTrace();
                }catch(SQLException s){
                    s.printStackTrace();
                }
                break;
            case 3:
                try{
                    receiveAndSafeSignal();
                }catch(IOException e){
                    e.printStackTrace();
                }catch(SQLException s){
                    s.printStackTrace();
                }
                break;
        } 
    }
    
    public void receiveAndSafeSignal() throws IOException, SQLException{
        BufferedReader bufferedReader = null;
        int patientId = bufferedReader.read();
        LocalDateTime current  = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy.HH-mm-ss");
        String formattedDateTime = current.format(format);
        String userHome = System.getProperty("user.home");
        // patients/<PATIENT_ID>/YYYYMMDD-HHMMSS_<PATIENT_ID>.txt
        String path = "/patient" + patientId + "_" + formattedDateTime + ".txt";
        File file = new File(userHome + path);
        FileWriter fileWriter = null;
        fileWriter = new FileWriter(file);
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        while ((line = bufferedReader.readLine()) != null) { 
            System.out.println(line);
            fileWriter.write(line);
        }
        fileManager.addFile(file, patientId);
        releaseResourcesClient(bufferedReader, socket);
    }
    
    public void sendPatient() throws IOException, SQLException{
        Patient patient = null;
        BufferedReader bufferedReader = null;
        int patientId = bufferedReader.read();
        patient = patientManager.searchPatientById(patientId);
        String patientSend = patient.toString2();
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.println(patientSend);
    }
    
    public void sendPatientsFileNames() throws IOException, SQLException {
        Patient patient = null;
        BufferedReader bufferedReader = null;
        int patientId = bufferedReader.read();
        List<String> fileNames = new ArrayList<String>();
        fileNames = fileManager.getPatientsFileNamesById(patientId);
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.println(fileNames);
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

