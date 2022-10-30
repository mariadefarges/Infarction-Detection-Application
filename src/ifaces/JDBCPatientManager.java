/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ifaces;

import pojos.Patient;
import java.sql.*;
import java.util.List;

/**
 *
 * @author mariadefarges
 */
public interface JDBCPatientManager {
   
    public void addPatient(Patient p) throws SQLException;
    
    public List<Patient> searchPatientbyName (String name, int doctorId) throws SQLException;
	
    public List<Patient> searchPatientbySurname (String surname, int doctorId) throws SQLException;
	
    public Patient searchPatientById(int patientId) throws SQLException, Exception;
	
    public List<Patient> getPatientsOfDoctor(int doctorId) throws SQLException;
    
    
	
    
}
