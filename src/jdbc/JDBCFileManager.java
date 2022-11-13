/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jdbc;
import ifaces.*;
import java.io.File;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pojos.Patient;

/**
 *
 * @author carlo
 */
public class JDBCFileManager implements FileManager {
    private JDBCManager manager;
    private PatientManager patientManager;
    
    public JDBCFileManager(JDBCManager m) {
		this.manager = m;	
    }
    
    public void setPatientManager(PatientManager patientManager){
        this.patientManager = patientManager;
    } 
    
    @Override
    public void addFile(File file, int patientId) throws SQLException{
        String sql = "INSERT INTO files (name, patientId) VALUES (?,?)";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1,file.getName());
		prep.setInt(2,patientId);
                // C:/Users/Carlota/etc...
                // patients/<PATIENT_ID>/YYYYMMDD-HHMMSS_<PATIENT_ID>.txt
		prep.executeUpdate();
		prep.close();
	
    }
    
    @Override
    public File getFileByName(String name) throws SQLException {
        String sql = "SELECT * FROM files WHERE name = ?";
        PreparedStatement prep = manager.getConnection().prepareStatement(sql);
        prep.setString(1, name);
        File file = new File(name);
        return file;
    }
    
    @Override
    public List<String> getPatientsFileNamesById(int patientId) throws SQLException {
        String sql = "SELECT * FROM files WHERE patientId = ?";
        PreparedStatement prep = manager.getConnection().prepareStatement(sql);
        prep.setInt(1, patientId);
        List<String> fileNames = new ArrayList<String>();
        ResultSet rs = prep.executeQuery();
		
        while (rs.next()) {
                String name = rs.getString("name");
                fileNames.add(name);		
        }
        prep.close();
        rs.close();
        return fileNames;  
    }
}
