/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jdbc;
import java.sql.*;

/**
 *
 * @author mariadefarges
 */
public class JDBCManager {

    private Connection c = null;

    public JDBCManager() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:./db/dentalClinic.db");
            c.createStatement().execute("PRAGMA foreign_keys=ON");
            System.out.println("Database connection opened.");

            this.createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Libraries not loaded");
        }
    }

    public void disconnect() {
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return c;
    }

    private void createTables() {
        // Create Tables
        try {
            Statement stmt = c.createStatement();
            String sql = "CREATE TABLE patients ("
                    + "	patientId INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "	name	  TEXT NOT NULL,"
                    + " surname   TEXT NOT NULL,"
                    + "	gender	  TEXT NOT NULL,"
                    + "	birthDate DATE NOT NULL,"                   
                    + " bloodType TEXT NOT NULL,"
                    + "	email     TEXT NOT NULL,"
                    + "	diagnosis     TEXT NOT NULL,"
                    + "	ECG     TEXT NOT NULL,"
                    + ");";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE doctors ("
                    + "	doctorId INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "	name	 TEXT NOT NULL,"
                    + "	surname	 TEXT NOT NULL,"
                    + "	email    DATE NOT NULL,"
                    + " );";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE patient_doctor ("
                    + "	patient_pd INTEGER REFERENCES patients(patientId) ON DELETE CASCADE,"
                    + "	doctor_pd INTEGER REFERENCES doctors(doctorId)  ON DELETE CASCADE,"
                    + "	PRIMARY KEY(patient_pd,doctor_pd)\r\n"
                    + ");";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            if (!e.getMessage().contains("Already exists")) {
                e.printStackTrace();
            }
        }
    }
}
