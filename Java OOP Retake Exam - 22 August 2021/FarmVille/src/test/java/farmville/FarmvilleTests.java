package farmville;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FarmvilleTests {
    Farm farm;
    Animal cow;

    @Before
    public void setUp() {
        farm = new Farm("Elena", 2);
        cow = new Animal("cow", 5);

    }

    @Test
    public void testGetCount() {
        farm.add(cow);
        assertEquals(1, farm.getCount());
    }

    @Test
    public void testGetName() {
        assertEquals("Elena", farm.getName());
    }

    @Test
    public void testGetCapacity() {
        assertEquals(2, farm.getCapacity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddAnimalThrowsWithSizeEqualToTheCapacity() {
        Animal dog = new Animal("dog", 2);
        Animal cat = new Animal("cat", 1);
        farm.add(cow);
        farm.add(dog);
        farm.add(cat);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddAnimalThrowsWithTheSameTypeOfAnimal() {
        Animal dog = new Animal("dog", 2);
        Animal dog2 = new Animal("dog", 1);
        farm.add(dog);
        farm.add(dog2);
    }

    @Test
    public void testAddAnimalWorksFine() {
        farm.add(cow);
        assertEquals(1, farm.getCount());
    }

    @Test
    public void testRemoveWorksFineByType() {
        Animal dog = new Animal("dog", 2);

        farm.add(cow);
        farm.add(dog);

        assertTrue(farm.remove("dog"));
        assertFalse(farm.remove("cat"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateFarmWithNegativeCapacityThrows() {
    Farm farm1 = new Farm("Milk", -5);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateFarmWithNullNameThrows() {
        Farm farm1 = new Farm(null, 5);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateFarmWithEmptyNameThrows() {
        Farm farm1 = new Farm(" ", 5);
    }




}
