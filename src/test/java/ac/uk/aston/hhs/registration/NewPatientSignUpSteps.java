package ac.uk.aston.hhs.registration;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class NewPatientSignUpSteps {

    private final Patient.PatientBuilder patientBuilder = new Patient.PatientBuilder();
    private final HHSRegistrationSystem hhsRegistrationSystem = new HHSRegistrationSystem();
    private Patient patientToSubmit;

    @Given("I provide my name as {string}")
    public void iProvideMyNameAs(final String fullName) {
        patientBuilder.fullName(fullName);
    }

    @And("I provide my mobile phone number as {string}")
    public void iProvideMyMobilePhoneNumberAs(final String phoneNo) {
        patientBuilder.mobileNumber(phoneNo);
    }

    @And("I provide my Date Of Birth as {int}\\/{int}\\/{int}")
    public void iProvideMyDateOfBirthAs(int day, int month, int year) {
        patientBuilder.dob(day, month, year);
    }

    @And("I provide my postcode as {string}")
    public void iProvideMyPostcodeAs(String postCode) {
        patientBuilder.postCode(postCode);
    }

    @And("I provide my email as {string}")
    public void iProvideMyEmailAs(String email) {
        patientBuilder.email(email);
    }

    @And("I have not previously registered")
    public void iHaveNotPreviouslyRegistered() {
        patientToSubmit = patientBuilder.build();
        Assert.assertFalse(hhsRegistrationSystem.isPatientAlreadyRegistered(patientToSubmit));
    }

    @When("I apply for the first time")
    public void iApplyForTheFirstTime() {
        Assert.assertFalse(hhsRegistrationSystem.isNewPatientAlreadySubmitted(patientToSubmit));
    }

    @Then("register me as a new patient")
    public void registerMeAsANewPatient() {
        Assert.assertTrue(hhsRegistrationSystem.submitNewPatientApplication(patientToSubmit));
    }

    @And("I have previously registered")
    public void iHavePreviouslyRegistered() {
        patientToSubmit = patientBuilder.build();
        Assert.assertTrue(hhsRegistrationSystem.isPatientAlreadyRegistered(patientToSubmit));
    }

    @When("I apply again within 24 hours")
    public void iApplyAgainWithinHours() {
        Assert.assertTrue(hhsRegistrationSystem.isNewPatientAlreadySubmitted(patientToSubmit));
    }

    @Then("Do not register me as a new patient")
    public void doNotRegisterMeAsANewPatient() {
        Assert.assertFalse(hhsRegistrationSystem.submitNewPatientApplication(patientToSubmit));
    }

    @And("I have previously submitted an application")
    public void iHavePreviouslySubmittedAnApplication() {
        Assert.assertTrue(hhsRegistrationSystem.isNewPatientAlreadySubmitted(patientToSubmit));
    }
}
