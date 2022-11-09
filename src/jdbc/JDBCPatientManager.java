/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import pojos.Patient;
import ifaces.PatientManager;

/**
 *
 * @author mariadefarges
 */
public class JDBCPatientManager implements PatientManager {
    private JDBCManager manager;
    private JDBCFileManager fileManager;
    
    public JDBCPatientManager(JDBCManager m, JDBCFileManager fm) {
		this.manager = m;
                this.fileManager = fm;
    }
    
    @Override
	public void addPatient(Patient p) throws SQLException{ 
		String sql = "INSERT INTO patients (name, surname, gender, birthDate, bloodType, email, diagnosis) VALUES (?,?,?,?,?,?,?)";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1,p.getName());
		prep.setString(2,p.getSurname());
		prep.setString(3,p.getGender());
		prep.setDate  (4,p.getBirthDate());
		prep.setString(5,p.getBloodType());
                prep.setString(6,p.getEmail());
                prep.setString(7,p.getDiagnosis());
		prep.executeUpdate();
		prep.close();
	}
        
	@Override
	public Patient searchPatientById(int patientId) throws SQLException { 
		Patient p = null;
		String sql = "SELECT * FROM patients WHERE patientId= ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, patientId);
		ResultSet rs = prep.executeQuery();
		while (rs.next()) {
			String name = rs.getString("name");
			String surname = rs.getString("surname");
			String gender = rs.getString("gender");
			Date birthDate = rs.getDate("birthDate");
			String bloodType = rs.getString("bloodType");
                        String email = rs.getString("email");
                        String diagnosis = rs.getString("diagnosis");
                        p = new Patient(patientId, name, surname, gender, birthDate, bloodType, email, diagnosis);
		}
		prep.close();
		rs.close();
		return p;
	}
			
	@Override
	public List<Patient> searchPatientbyName(String name) throws SQLException { 
		Patient p = null;
		String sql = "SELECT * FROM patients WHERE name = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1, name);
		ResultSet rs = prep.executeQuery();
		List <Patient> patients = new ArrayList<Patient>();
		while(rs.next()){
			int id = rs.getInt("patientId");
			String surname = rs.getString("surname");
			String gender = rs.getString("gender");
			Date birthDate = rs.getDate("birthDate");
			String bloodType = rs.getString("bloodType");
			String email = rs.getString("email");
                        String diagnosis = rs.getString("diagnosis");
			p= new Patient(id, name, surname, gender, birthDate, bloodType, email, diagnosis);
			patients.add(p);
		}
		rs.close();	
		return patients;
	}
	
	@Override
	public List<Patient> searchPatientbySurname(String surname) throws SQLException { 
		Patient p = null;
		String sql = "SELECT * FROM patients WHERE surname = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1, surname);
		ResultSet rs = prep.executeQuery();
		List <Patient> patients = new ArrayList<Patient>();
		while(rs.next()){
			int id = rs.getInt("patientId");
			String name = rs.getString("name");
			String gender = rs.getString("gender");
			Date birthDate = rs.getDate("birthDate");
			String bloodType = rs.getString("bloodType");
			String email = rs.getString("email");
                        String diagnosis = rs.getString("diagnosis");
			p= new Patient(id, name, surname, gender, birthDate, bloodType, email, diagnosis);
			patients.add(p);
		}
		rs.close();	
		return patients;
	}
        
        @Override
        public String getPatientsFullNameById(int patientId) throws SQLException {
        String sql = "SELECT name, surname FROM patients WHERE patientId = ?";
        PreparedStatement prep = manager.getConnection().prepareStatement(sql);
        ResultSet rs = prep.executeQuery();
        prep.setInt(1, patientId);
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String fullName = name + " " + surname;
        prep.close();
        rs.close();
        return fullName;  
    }
    
}
