package ac.uk.aston.hhs.registration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class HHSRegistrationSystem {

    private final Map<String, Patient> registeredPatients = new HashMap<>();
    private final Map<LocalDateTime, Patient> newRegistrations = new HashMap<>();

    public HHSRegistrationSystem() {
        retrieveRegisteredPatients();
        retrieveAlreadySubmittedPatients();
    }

    private void retrieveRegisteredPatients() {
        Patient johnDoe = new Patient.PatientBuilder()
                .email("johndoe@gmail.com")
                .fullName("John Doe")
                .dob(10, 2, 1995)
                .mobileNumber("07123456789")
                .postCode("BA1 1AB")
                .build();

        Patient janeDoe = new Patient.PatientBuilder()
                .email("janedoe@gmail.com")
                .fullName("Jane Doe")
                .dob(9,4,1996)
                .mobileNumber("07987654321")
                .postCode("BA1 1AB")
                .build();
        registeredPatients.put(UUID.randomUUID().toString(), johnDoe);
        registeredPatients.put(UUID.randomUUID().toString(), janeDoe);
    }

    private void retrieveAlreadySubmittedPatients() {
        Patient daveWalsh = new Patient.PatientBuilder()
                .email("davewalsh@gmail.com")
                .fullName("Dave Walsh")
                .dob(1, 1, 1970)
                .mobileNumber("07111111111")
                .postCode("M8 6PQ")
                .build();

        Patient susanSmith = new Patient.PatientBuilder()
                .email("sussys@gmail.com")
                .fullName("Susan Smith")
                .dob(13,11,1976)
                .mobileNumber("07922222222")
                .postCode("SK12 0HD")
                .build();
        newRegistrations.put(LocalDateTime.now(), daveWalsh);
        newRegistrations.put(LocalDateTime.now(), susanSmith);
    }

    public boolean isPatientAlreadyRegistered(final String patientId) {
        return registeredPatients.containsKey(patientId);
    }

    public boolean isPatientAlreadyRegistered(final Patient patient) {
        final int dayDOB = patient.getDob().getDayOfMonth();
        final int monthDOB = patient.getDob().getMonthValue();
        final int yearDOB = patient.getDob().getYear();
        final String patientFullName = patient.getFullName();
        return isPatientAlreadyRegistered(dayDOB, monthDOB, yearDOB, patientFullName);
    }

    public boolean isPatientAlreadyRegistered(final int day, final int month, final int year, final String fullName) {
        final List<Patient> patientsFound = new ArrayList<>(registeredPatients.values());

        for (final Patient currentPatient : patientsFound) {
            if (currentPatient.getFullName().equalsIgnoreCase(fullName)) {
                final LocalDateTime currentPatientDob = currentPatient.getDob();
                if (currentPatientDob.isEqual(LocalDateTime.of(year, month, day, 0, 0))) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isNewPatientAlreadySubmitted(final Patient patient) {
        final int dayDOB = patient.getDob().getDayOfMonth();
        final int monthDOB = patient.getDob().getMonthValue();
        final int yearDOB = patient.getDob().getYear();
        final String patientFullName = patient.getFullName();
        return isNewPatientAlreadySubmitted(dayDOB, monthDOB, yearDOB, patientFullName);
    }

    public boolean isNewPatientAlreadySubmitted(final int day, final int month, final int year, final String fullName) {
        final List<Map.Entry<LocalDateTime,Patient>> currentSubmittedPatientsFound = new ArrayList<>(newRegistrations.entrySet());

        for (final Map.Entry<LocalDateTime,Patient> currentNewPatientEntry : currentSubmittedPatientsFound) {
            final Patient currentPatient = currentNewPatientEntry.getValue();
            final LocalDateTime prevSubmittedTime = currentNewPatientEntry.getKey(); //previous applications time if any

            if (currentPatient.getFullName().equalsIgnoreCase(fullName)) {
                final LocalDateTime currentPatientDob = currentPatient.getDob();
                if (currentPatientDob.isEqual(LocalDateTime.of(year, month, day, 0, 0))) {
                    //24-hour submission check attempt
                    LocalDateTime newSubmissionTime = LocalDateTime.now();
                    if (prevSubmittedTime.isBefore(newSubmissionTime.plusDays(1))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean submitNewPatientApplication(final Patient patient) {
        boolean isAlreadySubmittedCriteriaMet = isNewPatientAlreadySubmitted(patient);
        if (!isAlreadySubmittedCriteriaMet) {
            newRegistrations.put(LocalDateTime.now(), patient);
        }
        return !isAlreadySubmittedCriteriaMet; //this is opposite to application already submitted
    }

}
