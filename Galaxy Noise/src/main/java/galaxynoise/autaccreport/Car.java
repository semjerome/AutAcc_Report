package galaxynoise.autaccreport;

/**
 * Team name Galaxy Noise
 * Created by semjeromers on 11/7/2016.
 * Class object for car
 */

public class Car {

    String plateNumber;
    String carMake;
    String carModel;
    int carYEar;

    public Car()
    {

    }
    public Car(String plateNumber, String carMake, String carModel, int carYEar)
    {
       plateNumber=plateNumber;
        carMake=carMake;
        carModel=carModel;
        carYEar=carYEar;
    }

    public int getCarYEar() {
        return carYEar;
    }

    public String getCarMake() {
        return carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public void setCarYEar(int carYEar) {
        this.carYEar = carYEar;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }
}
