package cats;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HouseTests {
    House house;
    Cat cat;

    @Before
    public void setUp() {
        house = new House("TomHouse", 2);
        cat = new Cat("Tom");
    }

    @Test
    public void testGetName() {
        assertEquals("TomHouse", house.getName());
    }

    @Test
    public void testGetCapacity() {
        assertEquals(2, house.getCapacity());
    }

    @Test
    public void testGetCount() {
        house.addCat(cat);
        assertEquals(1, house.getCount());
    }

    @Test(expected = NullPointerException.class)
    public void testToSetNullHouseNameThrows() {

        House house = new House(null, 5);
    }

    @Test(expected = NullPointerException.class)
    public void testToSetEmptyHouseNameThrows() {

        House house = new House(" ", 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToSetNegativeHouseCapacityThrows() {
        House house = new House("LeonHouse", -5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCatWithEqualCapacityThrows() {
        Cat cat1 = new Cat("Leon");
        Cat cat2 = new Cat("Boris");
        house.addCat(cat);
        house.addCat(cat1);
        house.addCat(cat2);
    }

    @Test
    public void testAddCatWorksFine() {
        house.addCat(cat);
        assertEquals(1, house.getCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveCatWithNotExistingNameThrows() {
        house.addCat(cat);
        house.removeCat("Boris");
    }

    @Test
    public void testRemoveCatWorksFine() {
        house.addCat(cat);
        house.removeCat("Tom");
        assertEquals(0, house.getCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCatForSaleWithNotExistingNameThrows() {
        house.addCat(cat);
        house.catForSale("Boris");
    }

    @Test
    public void testCatForSaleWorksFine() {
        house.addCat(cat);
        house.catForSale("Tom");
        assertFalse(cat.isHungry());
    }

    @Test
    public void testStatisticsWorksFine() {
        Cat cat2 = new Cat("Boris");
        house.addCat(cat);
        house.addCat(cat2);
        assertEquals("The cat Tom, Boris is in the house TomHouse!", house.statistics());
    }

}
