/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pojos;

import BITalino.BITalino;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author mariadefarges
 */
public class Patient implements Serializable {
    
    private static final long serialVersionUID = -1L;
    
    private final Integer patientId;
    private String name;
    private String surname;
    private String gender;
    private final String email;
    private String diagnosis;
    private BITalino bitalino;
    private final String username;
    private String password;

    public Patient(Integer patientId, String name, String surname, String gender, String email, String diagnosis, BITalino bitalino, String username, String password) {
        this.patientId = patientId;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.email = email;
        this.diagnosis = diagnosis;
        this.bitalino = bitalino;
        this.username = username;
        this.password = password;
    }


    public Integer getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
    public BITalino getBitalino() {
        return bitalino;
    }

    public void setBitalino(BITalino bitalino) {
        this.bitalino = bitalino;
    }
    
    
    
       @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.patientId);
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + Objects.hashCode(this.surname);
        hash = 89 * hash + Objects.hashCode(this.gender);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Patient other = (Patient) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.surname, other.surname)) {
            return false;
        }
        if (!Objects.equals(this.gender, other.gender)) {
            return false;
        }
        return Objects.equals(this.patientId, other.patientId);
    }
    
    
}
