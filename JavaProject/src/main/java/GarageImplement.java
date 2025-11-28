import org.jetbrains.annotations.NotNull;

import java.util.*;

public class GarageImplement implements Garage {
  private final Map<Owner, Set<Car>> carsByOwner = new HashMap<>();
  private final Map<Integer, Owner> ownersById = new HashMap<>();
  private final Map<String, Set<Car>> carsByBrand = new HashMap<>();
  private final Map<Integer, Car> cars = new HashMap<>();
  private final NavigableSet<Car> velocities =
      new TreeSet<>(Comparator.comparingInt(Car::maxVelocity).reversed().thenComparingInt(Car::carId));
  private final NavigableSet<Car> power =
      new TreeSet<>(Comparator.comparingInt(Car::power).thenComparingInt(Car::carId));


  @Override
  public Set<Owner> allCarsUniqueOwners() {
    return Collections.unmodifiableSet(carsByOwner.keySet());
  }

  @Override
  public Set<Car> allCarsOfBrand(String brand) {
    return Collections.unmodifiableSet(
        carsByBrand.getOrDefault(brand, Collections.emptySet()));
  }

  @Override
  public Set<Car> allCarsOfOwner(Owner owner) {
    return Collections.unmodifiableSet(
        carsByOwner.getOrDefault(owner, Collections.emptySet()));
  }

  @Override
  public void addNewCar(Car car, Owner owner) {
    if (car == null || owner == null) {
      throw new IllegalArgumentException("Parameters must not be null");
    }

    Car currentCar = cars.putIfAbsent(car.carId(), car);
    if (currentCar != null) { return; }

    carsByOwner.computeIfAbsent(owner, own -> new HashSet<>()).add(car);
    carsByBrand.computeIfAbsent(car.brand(), brand -> new HashSet<>()).add(car);

    velocities.add(car);
    power.add(car);
    ownersById.putIfAbsent(car.ownerId(), owner);
  }

  private void removeFromOwner(@NotNull Car car) {
    Owner owner = ownersById.get(car.ownerId());
    carsByOwner.computeIfPresent(owner, (own, cars) -> {
      cars.remove(car);
      return cars.isEmpty() ? null : cars;
    });

    if (carsByOwner.get(owner) == null) { ownersById.remove(car.ownerId()); }
  }

  private void removeFromBrand(@NotNull Car car) {
    carsByBrand.computeIfPresent(car.brand(), (brand, car_set) -> {
      car_set.remove(car);
      return car_set.isEmpty() ? null : car_set;
    });
  }

  @Override
  public Car removeCar(int carId) {
    Car car = cars.remove(carId);
    if (car == null) { return null;}

    removeFromOwner(car);
    removeFromBrand(car);

    velocities.remove(car);
    power.remove(car);
    return car;
  }

  @Override
  public int meanCarNumberForEachOwner() {
    if (carsByOwner.isEmpty()) {
      return 0;
    }
    return cars.size() / carsByOwner.size();
  }

  @Override
  public int meanOwnersAgeOfCarBrand(String brand) {
    int age = 0;
    Set<Owner> owners = new HashSet<>();
    Set<Car> carsOfBrand = allCarsOfBrand(brand);
    for (Car car : carsOfBrand) {
      Owner owner = ownersById.get(car.ownerId());
      if (!owners.contains(owner)) {
        age += owner.age();
        owners.add(owner);
      }
    }
    if (owners.isEmpty()) { return 0; }
    return age / owners.size();
  }

  @Override
  public Set<Car> carsWithPowerMoreThan(int power) {
    if (power == Integer.MAX_VALUE) { return Collections.emptySet(); }
    return this.power.tailSet(new Car(-1, "", "", 0, power+1, 0));
  }

  @Override
  public List<Car> topThreeCarsByMaxVelocity() {
    return velocities.stream().limit(3).toList();
  }
}
