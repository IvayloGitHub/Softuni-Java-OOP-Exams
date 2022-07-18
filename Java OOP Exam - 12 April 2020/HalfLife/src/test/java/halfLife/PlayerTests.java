package halfLife;

import org.junit.Assert;
import org.junit.Test;


public class PlayerTests {

    @Test
    public void testConstructorCreatesInstance() {
        Player player = new Player("Pesho", 200);
        Assert.assertNotNull(player);
    }

    @Test
    public void testGetUsername() {
        Player player = new Player("Pesho", 200);
        String actualName = player.getUsername();
        String expectedName = "Pesho";
        Assert.assertEquals(expectedName, actualName);
    }

    @Test(expected = NullPointerException.class)
    public void testSetUsernameIsValid() {
        new Player(null, 200);
    }

    @Test
    public void testGetHealth() {
        Player player = new Player("Pesho", 200);
        int actualHealth = player.getHealth();
        int expectedHealth = 200;
        Assert.assertEquals(expectedHealth, actualHealth);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetInvalidHealth() {
        new Player("Pesho", -20);
    }

    @Test
    public void testGetGuns() {
        Player player = new Player("Pesho", 200);
        int actualLength = player.getGuns().size();
        int expectedLength = 0;
        Assert.assertEquals(expectedLength, actualLength);
    }

    @Test(expected = IllegalStateException.class)
    public void testTakeDamageBelowZero() {
        Player player = new Player("Pesho", 20);
        player.takeDamage(20);
        player.takeDamage(1);
    }

    @Test
    public void testTakeDamageHealthToZero() {
        Player player = new Player("Pesho", 20);
        player.takeDamage(20);
        Assert.assertEquals(0, player.getHealth());
    }

    @Test
    public void testTakeDamageSmallerThanHealth() {
        Player player = new Player("Pesho", 20);
        player.takeDamage(15);
        Assert.assertEquals(5, player.getHealth());
    }

    @Test
    public void testTakeDamage() {
        Player player = new Player("Pesho", 200);
        player.takeDamage(20);
        int actualHealth = player.getHealth();
        int expectedHealth = 180;
        Assert.assertEquals(expectedHealth, actualHealth);
    }

    @Test(expected = NullPointerException.class)
    public void testAddNullGun() {
        Player player = new Player("Pesho", 200);
        player.addGun(null);
    }

    @Test
    public void testValidAddGun() {
        Player player = new Player("Pesho", 200);
        Gun gun = new Gun("Rifle", 20);
        player.addGun(gun);
        Gun expectedGun = gun;
        Gun actualGun = player.getGun(gun.getName());
        Assert.assertEquals(expectedGun, actualGun);
    }

    @Test
    public void testRemoveGun() {
        Player player = new Player("Pesho", 200);
        Gun gun = new Gun("Rifle", 20);
        player.addGun(gun);
        boolean actualResult = player.removeGun(gun);
        Assert.assertTrue(actualResult);
    }

    @Test
    public void testGetValidGun() {
        Player player = new Player("Pesho", 200);
        Gun gun = new Gun("Rifle", 20);
        player.addGun(gun);
        Gun expectedGun = gun;
        Gun actualGun = player.getGun(gun.getName());
        Assert.assertEquals(expectedGun, actualGun);
    }

    @Test
    public void testGetInvalidGun() {
        Player player = new Player("Pesho", 200);
        Gun gun = new Gun("Rifle", 20);
        player.addGun(gun);
        Gun actualGun = player.getGun("Pistol");
        Assert.assertNull(actualGun);
    }
}
