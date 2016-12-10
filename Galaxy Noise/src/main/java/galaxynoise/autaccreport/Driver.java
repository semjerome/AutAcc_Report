package galaxynoise.autaccreport;

/**
 * Created by semjeromers on 11/7/2016.
 * Class object fopr  Driver
 * Team name Galaxy Noise
 */

public class Driver {
    String driverLicense;
    String firstName;
    String lastName;
    String gender;
    String insuranceNumber;

    public Driver()
    {

    }
    public Driver(String driverLicense, String firstName, String lastName, String gender, String insuranceNumber)
    {
        insuranceNumber=insuranceNumber;
        driverLicense=driverLicense;
        firstName=firstName;
        lastName=lastName;
        gender=gender;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public String getDriverLicense() {
        return driverLicense;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getGender() {
        return gender;
    }

    public String getLastName() {
        return lastName;
    }

    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
