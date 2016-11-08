package galaxynoise.autaccreport;

/**
 * Created by semjeromers on 11/7/2016.
 * Class object fopr  Driver
 */

public class Driver {
    String driverLicense;
    String firstName;
    String lastName;
    String gender;

    public Driver()
    {

    }
    public Driver(String driverLicense, String firstName, String lastName, String gender)
    {
        driverLicense=driverLicense;
        firstName=firstName;
        lastName=lastName;
        gender=gender;
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
