package garage;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public class GarageTests {
    Garage garage;

    Car car1;
    Car car2;

    @Before
    public void setUp() {
        garage = new Garage();
        car1 = new Car("Opel", 240, 24000.0);
        car2 = new Car("Peugeot", 220, 27000.0);
    }

    @Test
    public void testGetCars() {
        garage.addCar(car1);
        garage.addCar(car2);
        StringBuilder sb = new StringBuilder();
        Collection<Car> cars = garage.getCars();
        for (Car car : cars) {
            sb.append(car.getBrand()).append(car.getMaxSpeed()).append(car.getPrice());
        }
        assertEquals("Opel24024000.0Peugeot22027000.0", sb.toString());
    }

    @Test
    public void testGetCount() {
        garage.addCar(car1);
        garage.addCar(car2);
        assertEquals(2, garage.getCount());
    }

    @Test
    public void testFindAllCarsWithMaxSpeedAbove() {
        Car car3 = new Car("Nissan",200, 12000.0);
        garage.addCar(car1);
        garage.addCar(car2);
        garage.addCar(car3);
        assertEquals(2, garage.findAllCarsWithMaxSpeedAbove(210).size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCarWithNullThrows() {
        garage.addCar(null);
    }

    @Test
    public void testAddCarWorksFine() {
        Car car3 = new Car("Nissan",200, 12000.0);
        garage.addCar(car1);
        garage.addCar(car2);
        garage.addCar(car3);
        assertEquals("Opel", garage.getCars().get(0).getBrand());
        assertEquals(240, garage.getCars().get(0).getMaxSpeed());
        assertEquals(24000.0, garage.getCars().get(0).getPrice(), 0.01);
    }

    @Test
    public void testGetTheMostExpensiveCar() {
        Car car3 = new Car("Nissan", 200, 12000.0);
        garage.addCar(car1);
        garage.addCar(car2);
        garage.addCar(car3);
        assertEquals(car2, garage.getTheMostExpensiveCar());
    }

    @Test
    public void testFindAllCarsByBrand() {
        Car car3 = new Car("Opel", 200, 12000.0);
        garage.addCar(car1);
        garage.addCar(car2);
        garage.addCar(car3);
        List<Car> opel = garage.findAllCarsByBrand("Opel");
        assertEquals(2, opel.size());
        assertEquals(car1.getPrice(), opel.get(0).getPrice(), 0.01);
        assertEquals(car3.getPrice(), opel.get(1).getPrice(), 0.01);
    }
}