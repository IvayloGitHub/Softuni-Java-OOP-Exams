package blueOrigin;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SpaceshipTests {
    private Spaceship spaceship;

    private Astronaut astronaut1;
    private Astronaut astronaut2;


    @Before
    public void setUp() {
        this.spaceship = new Spaceship("Earth", 2);
        astronaut1 = new Astronaut("Ivan", 25.00);
        astronaut2 = new Astronaut("George", 30.00);
        this.spaceship.add(astronaut1);
        this.spaceship.add(astronaut2);
    }

    @Test
    public void testGetCapacity() {
        assertEquals(2, spaceship.getCapacity());
    }

    @Test
    public void testGetName() {
        assertEquals("Earth", spaceship.getName());
    }

    @Test
    public void testGetCount() {
        assertEquals(2, spaceship.getCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddWithEqualCapacity() {
        Astronaut astronaut3 = new Astronaut("Boris", 20.00);
        this.spaceship.add(astronaut3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddAstronautExist() {
        this.spaceship.remove("George");
        this.spaceship.add(astronaut1);
    }

    @Test
    public void testAddAstronautWorks() {
        this.spaceship.remove("George");
        this.spaceship.add(astronaut2);
    }

    @Test
    public void testRemoveAstronautWorks() {
        assertTrue(this.spaceship.remove("George"));
        assertFalse(this.spaceship.remove("John"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetCapacityUnderZero() {
        Spaceship spaceship1 = new Spaceship("Jupiter", -1);
    }

    @Test(expected = NullPointerException.class)
    public void testSetSpaceShipNameToNullOrBlank() {
        Spaceship spaceship1 = new Spaceship(null, -1);
        Spaceship spaceship2 = new Spaceship("   ", -1);
    }





}
