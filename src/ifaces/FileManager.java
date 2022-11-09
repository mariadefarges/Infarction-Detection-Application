/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ifaces;

import java.io.File;
import java.sql.*;
import java.util.List;

/**
 *
 * @author carlo
 */
public interface FileManager {
    
    public void addFile(File file, int patientId) throws SQLException;
    
    public File getFileByName(String name) throws SQLException;
    
    public List<String> getPatientsFileNamesById(int patientId) throws SQLException;
  
}
