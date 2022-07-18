package aquarium;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AquariumTests {
    private Aquarium aquarium;

    @Before
    public void setUp() {
        aquarium = new Aquarium("Golden", 2);
    }

    @Test
    public void testGetName() {
        assertEquals("Golden", aquarium.getName());
    }

    @Test
    public void testGetCapacity() {
        assertEquals(2, aquarium.getCapacity());
    }

    @Test
    public void testGetCount() {
        Fish firstFish = new Fish("Silver");
        aquarium.add(firstFish);
        assertEquals(1, aquarium.getCount());
    }

    @Test(expected = NullPointerException.class)
    public void testSetNameNull() {
        Aquarium aquarium1 = new Aquarium(null, 2);
    }

    @Test(expected = NullPointerException.class)
    public void testSetNameEmptyName() {
        Aquarium aquarium1 = new Aquarium("  ", 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNegativeCapacity() {
        Aquarium aquarium1 = new Aquarium("Gold", -5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddWithNoCapacity() {
        Fish firstFish = new Fish("Silver");
        Fish secondFish = new Fish("Blue");
        Fish thirdFish = new Fish("Yellow");
        aquarium.add(firstFish);
        aquarium.add(secondFish);
        aquarium.add(thirdFish);
    }

    @Test
    public void testAddWorksFine() {
        Fish firstFish = new Fish("Silver");
        aquarium.add(firstFish);
        assertEquals(1, aquarium.getCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveWithNotExistingName() {
        Fish firstFish = new Fish("Silver");
        aquarium.add(firstFish);
        aquarium.remove("Niki");
    }

    @Test
    public void testRemoveWorksFine() {
        Fish firstFish = new Fish("Silver");
        aquarium.add(firstFish);
        aquarium.remove("Silver");
        assertEquals(0, aquarium.getCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSellFishWithNotExistingName() {
        Fish firstFish = new Fish("Silver");
        aquarium.add(firstFish);
        aquarium.sellFish("Niki");
    }

    @Test
    public void testSellFishWorksFine() {
        Fish firstFish = new Fish("Silver");
        aquarium.add(firstFish);
        String actualName = aquarium.sellFish("Silver").getName();
        assertFalse(firstFish.isAvailable());
        assertEquals("Silver", actualName);
    }

    @Test
    public void testReport() {
        Fish firstFish = new Fish("Silver");
        Fish secondFish = new Fish("Blue");
        aquarium.add(firstFish);
        aquarium.add(secondFish);
        aquarium.report();
        assertEquals(aquarium.report(), "Fish available at Golden: Silver, Blue");
    }
}

