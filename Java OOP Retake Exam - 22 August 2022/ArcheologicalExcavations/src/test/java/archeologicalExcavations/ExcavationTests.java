package archeologicalExcavations;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExcavationTests {

    Excavation excavation;

    Archaeologist archaeologist1;

    Archaeologist archaeologist2;

    @Before
    public void setUp() {
        this.excavation = new Excavation("Varna", 3);
        this.archaeologist1 = new Archaeologist("Pesho", 30);
        this.archaeologist2 = new Archaeologist("Gosho", 40);
    }

    @Test
    public void testGetCount() {
        this.excavation.addArchaeologist(archaeologist1);
        this.excavation.addArchaeologist(archaeologist2);

        assertEquals(2, this.excavation.getCount());
    }

    @Test
    public void testGetName() {

        assertEquals("Varna", this.excavation.getName());
    }

    @Test
    public void testGetCapacity() {

        assertEquals(3, this.excavation.getCapacity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddArchaeologistNotEnoughCapacityThrows() {
        Archaeologist archaeologist3 = new Archaeologist("Kiro", 20);
        Archaeologist archaeologist4 = new Archaeologist("Miladin", 30);

        this.excavation.addArchaeologist(archaeologist1);
        this.excavation.addArchaeologist(archaeologist2);
        this.excavation.addArchaeologist(archaeologist3);
        this.excavation.addArchaeologist(archaeologist4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddArchaeologistNameExistsThrows() {

        this.excavation.addArchaeologist(archaeologist1);
        this.excavation.addArchaeologist(archaeologist1);
    }

    @Test
    public void testAddArchaeologistWorks() {

        this.excavation.addArchaeologist(archaeologist1);
        this.excavation.addArchaeologist(archaeologist2);

        assertEquals(2, this.excavation.getCount());
    }

    @Test
    public void testAddArchaeologistRemoveTrue() {

        this.excavation.addArchaeologist(archaeologist1);
        this.excavation.addArchaeologist(archaeologist2);

        assertTrue(this.excavation.removeArchaeologist("Pesho"));
        assertEquals(1, this.excavation.getCount());
    }

    @Test
    public void testAddArchaeologistRemoveFalse() {

        this.excavation.addArchaeologist(archaeologist1);
        this.excavation.addArchaeologist(archaeologist2);

        assertFalse(this.excavation.removeArchaeologist("Ivan"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetCapacityNegativeThrows() {

        Excavation excavation1 = new Excavation("Plovdiv", -5);
    }

    @Test(expected = NullPointerException.class)
    public void testSetNameNullThrows() {

        Excavation excavation1 = new Excavation(null, 5);
    }

    @Test(expected = NullPointerException.class)
    public void testSetNameWithBlankNameThrows() {

        Excavation excavation1 = new Excavation("  ", 5);
    }


}
