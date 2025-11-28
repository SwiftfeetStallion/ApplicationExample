import org.junit.*;
import io.qameta.allure.*;

import java.util.*;

import static org.junit.Assert.*;

public class TestGarage {
  private Garage garage;
  private final ArrayList<Owner> ows = new ArrayList<>(10);
  private final ArrayList<Car> cars = new ArrayList<>(10);
  private final String[] brands = {"One", "Two", "One", "Three", "Four", "One", "Two", "Five", "Four", "One"};
  private final int[] powers = {1, 2, 5, 6, 3, 8, 9, 3, 2, 5};
  private final int[] vel = {88, 98, 100, 23, 57, 89, 56, 34, 100, 67};

  {
    for (int i = 0; i < 10; ++i) {
      ows.add(new Owner(i,"Max", "L", i + 10));
      cars.add(new Car(i, brands[i], "M", vel[i], powers[i], powers[i]));
    }
  }

  @Before
  public void createGarage() {
    garage = new GarageImplement();
  }

  @Test
  public void testEmpty() {
    assertEquals(Collections.emptySet(), garage.allCarsUniqueOwners());

    assertEquals(Collections.emptySet(), garage.allCarsOfBrand("brand"));

    assertEquals(Collections.emptySet(), garage.allCarsOfOwner(null));

    assertEquals(Collections.emptySet(), garage.carsWithPowerMoreThan(0));
    assertEquals(Collections.emptyList(), garage.topThreeCarsByMaxVelocity());

    assertEquals(0, garage.meanCarNumberForEachOwner());
    assertEquals(0, garage.meanOwnersAgeOfCarBrand(null));
  }

  @Test
  public void testAddRemove() {
    try {
      garage.addNewCar(null, ows.getFirst());
      fail();
    } catch (IllegalArgumentException ex) {}

    try {
      garage.addNewCar(new Car(1, "", "", 0, 8, 9), null);
      fail();
    } catch (IllegalArgumentException ex) {}

    garage.addNewCar(cars.getFirst(), ows.get(1));
    garage.addNewCar(new Car(0, "", "", 1, 2, 8), ows.get(1));

    assertEquals(11, garage.meanOwnersAgeOfCarBrand("One"));
    assertTrue(garage.allCarsUniqueOwners().contains(ows.get(1)));
    assertEquals(cars.getFirst(), garage.removeCar(0));
    assertNull(garage.removeCar(0));
    assertEquals(Collections.emptySet(), garage.allCarsUniqueOwners());
    assertEquals(0, garage.meanCarNumberForEachOwner());
  }

  @Test
  public void testWork() {
    for (int i = 0; i < 10; ++i) {
      garage.addNewCar(cars.get(i), ows.get(powers[i]));
    }
    assertEquals(Collections.emptySet(), garage.carsWithPowerMoreThan(Integer.MAX_VALUE));

    Collection<Owner> owners = garage.allCarsUniqueOwners();
    assertEquals(7, owners.size());
    Collection<Car> topCars = garage.topThreeCarsByMaxVelocity();
    List<Integer> vel = new ArrayList<>();
    topCars.forEach(elem -> vel.add(elem.maxVelocity()));

    assertEquals(List.of(100, 100, 98), vel);
    assertTrue(garage.carsWithPowerMoreThan(Integer.MAX_VALUE).isEmpty());

    Collection<Car> topPowers = garage.carsWithPowerMoreThan(5);
    Set<Integer> powers = new HashSet<>();
    topPowers.forEach(elem -> powers.add(elem.power()));

    assertEquals(Set.of(6, 8, 9), powers);
    assertEquals(1, garage.meanCarNumberForEachOwner());
    assertEquals(0, garage.meanOwnersAgeOfCarBrand("brand"));
    assertEquals(14, garage.meanOwnersAgeOfCarBrand("One"));
    assertEquals(2, garage.allCarsOfOwner(ows.get(2)).size());
    assertTrue(garage.allCarsOfOwner(ows.getFirst()).isEmpty());
    assertEquals(4, garage.allCarsOfBrand("One").size());

    List<Integer> remove = List.of(1, 2, 3, 4, 5, 6, 7, 8);
    remove.forEach(id -> garage.removeCar(id));
    assertEquals(2, garage.topThreeCarsByMaxVelocity().size());
    assertEquals(1, garage.meanCarNumberForEachOwner());
    assertEquals(13, garage.meanOwnersAgeOfCarBrand("One"));
    assertEquals(2, garage.allCarsOfBrand("One").size());

    garage.addNewCar(new Car(2, null, null, 8, 9, 0), ows.getFirst());
    assertEquals(1, garage.allCarsOfBrand(null).size());
    assertEquals(10, garage.meanOwnersAgeOfCarBrand(null));
  }

}
