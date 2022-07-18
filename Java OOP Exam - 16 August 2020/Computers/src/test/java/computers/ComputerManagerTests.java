package computers;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ComputerManagerTests {

    private ComputerManager computerManager;

    private Computer computer;
    private Computer computer2;


    @Before
    public void setUp() {
        this.computerManager = new ComputerManager();
        computer = new Computer("Acer", "5740", 300.00);
        computer2 = new Computer("Asus", "Rog", 500.00);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetComputers() {
        computerManager.getComputers().remove(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullAdd() {
        computerManager.addComputer(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddForExist() {
        computerManager.addComputer(computer);
        computerManager.addComputer(computer);
    }

    @Test
    public void testAdd() {
        computerManager.addComputer(computer);
        Assert.assertEquals(1, this.computerManager.getCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetManufacturerNull() {
        computerManager.getComputer(null, "test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetModelNull() {
        computerManager.getComputer("Test", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNotExist() {
        computerManager.getComputer(computer.getManufacturer(), computer.getModel());
    }

    @Test
    public void testGetExist() {
        computerManager.addComputer(computer);
        computerManager.addComputer(computer2);
        Computer returned = this.computerManager.getComputer(computer.getManufacturer(), computer.getModel());
        Assert.assertNotNull(returned);
        Assert.assertEquals(returned.getManufacturer(), computer.getManufacturer());
        Assert.assertEquals(returned.getModel(), computer.getModel());
    }

    @Test
    public void testGetByManufacturer() {
        computerManager.addComputer(computer);
        computerManager.addComputer(computer2);
        List<Computer> computersByManufacturer = this.computerManager.getComputersByManufacturer(computer.getManufacturer());
        Assert.assertNotNull(computersByManufacturer);
        Assert.assertEquals(computersByManufacturer.get(0).getManufacturer(), computer.getManufacturer());
    }

    @Test
    public void testGetByManufacturerWhenEmpty() {

        List<Computer> computersByManufacturer = this.computerManager.getComputersByManufacturer(computer.getManufacturer());
        Assert.assertNotNull(computersByManufacturer);
        Assert.assertTrue(computersByManufacturer.isEmpty());
    }

    @Test
    public void testRemoveComputer(){
        this.computerManager.addComputer(computer);
        Assert.assertEquals(computer, this.computerManager.removeComputer("Acer", "5740"));
    }
}
