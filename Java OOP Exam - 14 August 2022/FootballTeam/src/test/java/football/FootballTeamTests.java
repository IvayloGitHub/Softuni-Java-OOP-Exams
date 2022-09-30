package football;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class FootballTeamTests {

    FootballTeam footballTeam;

    Footballer footballer1;
    Footballer footballer2;

    @Before
    public void setUp() {
        this.footballTeam = new FootballTeam("Arsenal", 3);
        this.footballer1 = new Footballer("George");
        this.footballer2 = new Footballer("Boris");
    }

    @Test(expected = NullPointerException.class)
    public void testFootballTeamWithNull() {
        FootballTeam footballTeam1 = new FootballTeam(null, 5);
    }

    @Test(expected = NullPointerException.class)
    public void testFootballTeamWithEmptySpaces() {
        FootballTeam footballTeam2 = new FootballTeam("   ", 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVacantPositionsWithNegative() {
        FootballTeam footballTeam2 = new FootballTeam("Arsenal", - 5);
    }

    @Test
    public void testGetCount() {
        this.footballTeam.addFootballer(footballer1);
        this.footballTeam.addFootballer(footballer2);
        assertEquals(2, footballTeam.getCount());
    }

    @Test
    public void testGetName() {
        assertEquals("Arsenal", footballTeam.getName());
    }

    @Test
    public void testGetVacantPositions() {
        assertEquals(3, footballTeam.getVacantPositions());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddFootballerThrows() {
        Footballer footballer3 = new Footballer("Ivan");
        Footballer footballer4 = new Footballer("Hristo");
        this.footballTeam.addFootballer(footballer1);
        this.footballTeam.addFootballer(footballer2);
        this.footballTeam.addFootballer(footballer3);
        this.footballTeam.addFootballer(footballer4);
    }

    @Test
    public void testAddFootballerWorks() {
        this.footballTeam.addFootballer(footballer1);
        this.footballTeam.addFootballer(footballer2);
        assertEquals(2, footballTeam.getCount());
        assertEquals(footballer1, footballTeam.footballerForSale("George"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveFootballerThrows() {
        this.footballTeam.addFootballer(footballer1);
        this.footballTeam.addFootballer(footballer2);

        this.footballTeam.removeFootballer("Ivan");
    }

    @Test
    public void testRemoveFootballerWorks() {
        this.footballTeam.addFootballer(footballer1);
        this.footballTeam.addFootballer(footballer2);

        this.footballTeam.removeFootballer("George");
        assertEquals(1, footballTeam.getCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFootballerForSaleThrows() {
        this.footballTeam.addFootballer(footballer1);
        this.footballTeam.addFootballer(footballer2);

        this.footballTeam.footballerForSale("Ivan");
    }

    @Test
    public void testFootballerForSaleWorks() {
        this.footballTeam.addFootballer(footballer1);
        this.footballTeam.addFootballer(footballer2);
        footballTeam.footballerForSale("George");

        assertFalse(footballer1.isActive());
    }

    @Test
    public void testGetStatisticsWorks() {
        this.footballTeam.addFootballer(footballer1);
        this.footballTeam.addFootballer(footballer2);
        footballTeam.footballerForSale("George");

        assertEquals("The footballer George, Boris is in the team Arsenal.", footballTeam.getStatistics());
    }
}
