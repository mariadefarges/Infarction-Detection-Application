/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jdbc;

import ifaces.JDBCDoctorManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import pojos.Doctor;

/**
 *
 * @author mariadefarges
 */
public class DoctorManager implements JDBCDoctorManager {
        private JDBCManager manager;
	private PatientManager pm;

	public DoctorManager(JDBCManager m, PatientManager pm) {
		this.manager = m;
	}

	public void setPatientmanager(PatientManager pm) {
		this.pm = pm;
	}
	
	
	@Override
	public void addDoctor(Doctor d) throws SQLException {
		String sql = "INSERT INTO dentists (name, surname, email) VALUES (?,?,?)";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1, d.getName());
		prep.setString(2, d.getSurname());
		prep.setString(3,d.getEmail());
		prep.executeUpdate();
		prep.close();	
	}
	
	@Override
	public List<Doctor> getDoctorsOfPatient(int patientId) throws SQLException {
		String sql = "SELECT * from doctors AS d JOIN patient_doctor AS pd ON d.doctorId = pd.doctor_pd WHERE pd.patient_pd=?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, patientId);
		ResultSet rs = prep.executeQuery();
		List <Doctor> doctors = new ArrayList<Doctor>();
		while (rs.next()) {
			int doctorId = rs.getInt("dentistId");
			String name = rs.getString("name");
			String surname = rs.getString("surname");
			String email = rs.getString("email");
			Doctor doctor = new Doctor(doctorId, name, surname, email);
			doctors.add(doctor);		
		}
		prep.close();
		rs.close();
		return doctors;
	}

	@Override
	public void assignDoctorPatient(int doctorId, int patientId) throws SQLException {
		String sql = "INSERT INTO patient_dentist (patient_pd, dentist_pd) VALUES (?,?)";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, patientId);
		prep.setInt(2, doctorId);
	prep.executeUpdate();
	prep.close();
		
	}

	@Override
	public List<Doctor> searchDoctorByName(String name) throws SQLException {
		Doctor d = null;
		String sql = "SELECT * FROM doctors WHERE name = ? ";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1, name);
		ResultSet rs = prep.executeQuery();
		List<Doctor> doctors = new ArrayList<Doctor>();
		while(rs.next()){ 
			int doctorId = rs.getInt("doctorId");
			String surname = rs.getString("surname");
			String email = rs.getString("email");
			
			d = new Doctor(doctorId,name,surname, email);
			d.setPatients(pm.getPatientsOfDoctor(doctorId));
			doctors.add(d);		
		}
		prep.close();
		rs.close();
		return doctors;
	}
	
	@Override
	public List<Doctor> searchDoctorBySurname(String surname) throws SQLException {
		Doctor d = null;
		String sql = "SELECT * FROM doctors WHERE surname = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setString(1, surname);
		ResultSet rs = prep.executeQuery();
		List<Doctor> doctors = new ArrayList<Doctor>();
		while(rs.next()){ 
			int doctorId = rs.getInt("doctorId");
			String name = rs.getString("name");
			String email = rs.getString("email");
			d = new Doctor(doctorId,name,surname, email);
			d.setPatients(pm.getPatientsOfDoctor(doctorId));
			doctors.add(d);		
		}
		prep.close();
		rs.close();
		return doctors;
	}

	@Override
	public Doctor searchDoctorById(int doctorId) throws SQLException { 
		Doctor d = null;
		String sql = "SELECT * FROM dentists WHERE dentistId = ?";
		PreparedStatement prep = manager.getConnection().prepareStatement(sql);
		prep.setInt(1, doctorId);
		ResultSet rs = prep.executeQuery();
		while(rs.next()){ 
			String name = rs.getString("name");
			String surname = rs.getString("surname");
			String email = rs.getString("email");
			d = new Doctor(doctorId, name, surname, email);
			d.setPatients(pm.getPatientsOfDoctor(doctorId));	
		}
		prep.close();
		rs.close();
		return d;
	}

   
    
}
