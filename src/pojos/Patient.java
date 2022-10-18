/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pojos;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author mariadefarges
 */
public class Patient implements Serializable {
    
    private static final long serialVersionUID = -1L;
    
    private final Integer patientId;
    private final String name;
    private final String surname;
    private final String gender;

    public Patient(Integer patientId, String name, String surname, String gender) {
        this.patientId = patientId;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getGender() {
        return gender;
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
