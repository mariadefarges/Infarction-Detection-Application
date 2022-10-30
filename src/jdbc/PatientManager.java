/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jdbc;

import ifaces.JDBCPatientManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import pojos.Patient;

/**
 *
 * @author mariadefarges
 */
public class PatientManager implements JDBCPatientManager {
    private JDBCManager manager;
    private DoctorManager dm;
    
    public PatientManager(JDBCManager m, DoctorManager dm) {
		this.manager = m;
		this.dm = dm;		
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
			p.setDoctors(dm.getDoctorsOfPatient(patientId));
		}
		prep.close();
		rs.close();
		return p;
	}
			
	@Override
	public List<Patient> searchPatientbyName(String name, int doctorId) throws SQLException { 
		Patient p = null;
		String sql = "SELECT * FROM patients AS p JOIN patient_doctor AS pd ON p.patientId = pd.patient_pd WHERE p.name LIKE ? AND pd.doctor_pd = ? ";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1, name);
		prep.setInt(2, doctorId);
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
	public List<Patient> searchPatientbySurname(String surname, int dentistId) throws SQLException { 
		Patient p = null;
		String sql = "SELECT * FROM patients AS p JOIN patient_doctor AS pd ON p.patientId = pd.patient_pd WHERE p.surname LIKE ? AND pd.doctor_pd = ? ";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1, surname);
		prep.setInt(2, dentistId);
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
	public List<Patient> getPatientsOfDoctor(int doctorId) throws SQLException {
		Patient p = null;
		String sql = "SELECT * FROM patients AS p JOIN patient_doctor AS pd ON p.patientId = pd.patient_pd WHERE pd.doctor_pd = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, doctorId);
		ResultSet rs = prep.executeQuery();
		List <Patient> patients = new ArrayList<Patient>();
		while(rs.next()){
			int id = rs.getInt("patientId");
			String name = rs.getString("name");
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
    
}
