/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ifaces;

import pojos.Doctor;
import java.sql.*;
import java.util.List;

/**
 *
 * @author mariadefarges
 */
public interface JDBCDoctorManager {
    
    public void addDoctor (Doctor d) throws SQLException; 
	
	public void assignDoctorPatient (int doctorId, int patientId)throws SQLException;
	
	public List<Doctor> getDoctorsOfPatient(int patientId)throws SQLException;
	
	public List<Doctor> searchDoctorByName (String name) throws SQLException;
	
	public List<Doctor> searchDoctorBySurname(String surname) throws SQLException;
	
	public Doctor searchDoctorById (int doctorId) throws SQLException;
    
}
