import java.util.Collection;

public interface Garage {
    Collection<Owner> allCarsUniqueOwners();

    Collection<Car> topThreeCarsByMaxVelocity();

    Collection<Car> allCarsOfBrand(String brand);

    Collection<Car> carsWithPowerMoreThan(int power);

    Collection<Car> allCarsOfOwner(Owner owner);

    int meanOwnersAgeOfCarBrand(String brand);

    int meanCarNumberForEachOwner();

    Car removeCar(int carId);

    void addNewCar(Car car, Owner owner);
}

