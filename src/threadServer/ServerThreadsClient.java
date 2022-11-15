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
import java.time.LocalDate;
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
        InputStream inputStream = null;
        try{
            //inputStream = socket.getInputStream();
            /*int opcion = 0;
            try{
            opcion = inputStream.read();
            }catch(IOException e){
                e.printStackTrace();
            }
            switch(opcion){
                case 1:
                    try{
                        sendPatient();
                    }catch(IOException | SQLException e){
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try{
                        sendPatientsFileNames();
                    }catch(IOException | SQLException e){
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    try{
                        receiveAndSafeSignal();
                    }catch(IOException | SQLException e){
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    */try{
                        register();
                    }catch(IOException | SQLException e){
                        e.printStackTrace();
                    }
                /*    break;
                case 5:
                    try{
                        login();
                    }catch(IOException | SQLException e){
                        e.printStackTrace();
                    }
                    break;
            }*/
        }//catch(IOException ex){
           // Logger.getLogger(ServerThreadsClient.class.getName()).log(Level.SEVERE, null, ex);
        //}
        finally {
          /*try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(ServerThreadsClient.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            /*try {
                inputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(ServerThreadsClient.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        }
    }
    
    public void register() throws IOException, SQLException{
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        String[] atributes = new String[10];
        //System.out.println(bufferedReader.readLine());
        //System.out.println(bufferedReader.readLine());
        /*for(int i = 0; i<10; i++){
        //while ((line = bufferedReader.readLine()) != null) {
            atributes[i] = bufferedReader.readLine();
//atributes.add(line);
            
        }*/
        //System.out.println(atributes);
        //int patientId = Integer.parseInt(bufferedReader.readLine());
        String name = bufferedReader.readLine();
        System.out.println(name);
        String surname = bufferedReader.readLine();
        System.out.println(surname);
        String gender = bufferedReader.readLine();
        System.out.println(gender);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-DD");
        //LocalDate d = LocalDate.parse(bufferedReader.readLine(), formatter);
        Date birthDate = Date.valueOf(bufferedReader.readLine());
        System.out.println(birthDate);
        String bloodType = bufferedReader.readLine();
        String email = bufferedReader.readLine();
        byte[] password = bufferedReader.readLine().getBytes();
        System.out.println(password);
        String symptoms = bufferedReader.readLine();
        System.out.println(symptoms);
        String bitalino = bufferedReader.readLine();
        System.out.println(bitalino);
        
        
        /*System.out.println(name);
        String surname = atributes[2];
        System.out.println(surname);
        String gender = atributes[3];
        System.out.println(gender);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-DD");
        LocalDate birthDate = LocalDate.parse(atributes[4], formatter);
        String bloodType = atributes[5];
        String email = atributes[6];
        byte[] password = atributes[7].getBytes();
        String symptoms = atributes[8];
        String bitalino = atributes[9];*/
        
        Patient patient = new Patient(name, surname, gender, birthDate,  bloodType, email, password, symptoms, bitalino);
        System.out.println(patient);
        patientManager.addPatient(patient);
        System.out.println(patient);
    }
    
    public void login() throws IOException, SQLException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String email = bufferedReader.readLine();
        String password = bufferedReader.readLine();
        int patientId = patientManager.getPatientId(email, password);
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.println(patientId);
    }
    
    public void receiveAndSafeSignal() throws IOException, SQLException{
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
        while ((line = bufferedReader.readLine()) != null) { 
            System.out.println(line);
            fileWriter.write(line);
        }
        fileManager.addFile(file, patientId);
        releaseResourcesClient(bufferedReader, socket);
    }
    
    public void sendPatient() throws IOException, SQLException{
        Patient patient = null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        int patientId = bufferedReader.read();
        patient = patientManager.searchPatientById(patientId);
        String patientSend = patient.toString2();
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.println(patientSend);
    }
    
    public void sendPatientsFileNames() throws IOException, SQLException {
        Patient patient = null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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