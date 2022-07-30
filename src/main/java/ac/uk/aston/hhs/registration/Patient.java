package ac.uk.aston.hhs.registration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Patient {

    private final String fullName;
    private final String mobileNumber;
    private final LocalDateTime dob;
    private final String postCode;
    private final String email;


    public Patient(String fullName, String mobileNumber, LocalDateTime dob , String postCode, String email) {
        this.fullName = fullName;
        this.mobileNumber = mobileNumber;
        this.dob = dob;
        this.postCode = postCode;
        this.email = email;
    }


    public String getFullName() {
        return fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public LocalDateTime getDob() {
        return dob;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Patient: " + this.fullName+", "+this.dob+", "+this.mobileNumber+", "+this.email+", "+this.postCode;
    }

    public static class PatientBuilder {
        private String fullName;
        private String mobileNumber;
        private LocalDateTime dob;
        private String postCode;
        private String email;

        public PatientBuilder fullName (String fullName) {
            this.fullName = fullName;
            return this;
        }

        public PatientBuilder mobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
            return this;
        }

        public PatientBuilder dob(int day, int month, int year) {
            //with custom formatter
            this.dob = LocalDateTime.of(year, month, day, 0, 0); //2015-12-22
            return this;
        }

        public PatientBuilder postCode(String postCode) {
            this.postCode = postCode;
            return this;
        }

        public PatientBuilder email(String email) {
            this.email = email;
            return this;
        }

        public Patient build() {
            Patient newPatient = new Patient(fullName, mobileNumber, dob, postCode, email);
            return newPatient;
        }
    }

    //...

}
