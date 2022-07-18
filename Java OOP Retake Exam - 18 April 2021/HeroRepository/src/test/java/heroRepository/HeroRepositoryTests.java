package heroRepository;


import org.junit.Before;
import org.junit.Test;
import java.util.Collection;


import static org.junit.Assert.*;

public class HeroRepositoryTests {
    private HeroRepository heroRepository;
    Hero hero;

    @Before
    public void setUp() {
        this.heroRepository = new HeroRepository();
        hero = new Hero("Ivo", 1);
    }

    @Test
    public void testGetCount() {
        this.heroRepository.create(hero);
        assertEquals(1, this.heroRepository.getCount());
    }

    @Test(expected = NullPointerException.class)
    public void testCreateWithNull() {
        this.heroRepository.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithExistingHero() {
        this.heroRepository.create(hero);
        this.heroRepository.create(hero);
    }

    @Test
    public void testCreateHeroWorksFine() {
        assertEquals("Successfully added hero Ivo with level 1", this.heroRepository.create(hero));
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveHeroWithNullAndEmpty() {
        this.heroRepository.create(hero);
        this.heroRepository.remove(null);
        this.heroRepository.remove(" ");
    }

    @Test
    public void testRemoveHeroExisting() {
        Hero hero1 = new Hero("Boris", 2);
        this.heroRepository.create(hero);
        this.heroRepository.create(hero1);
        assertTrue(this.heroRepository.remove("Boris"));
    }

    @Test
    public void testRemoveHeroNotExisting() {
        Hero hero1 = new Hero("Boris", 2);
        this.heroRepository.create(hero);
        this.heroRepository.create(hero1);
        assertFalse(this.heroRepository.remove("John"));
    }

    @Test
    public void testGetHeroWithHighestLevel() {
        this.heroRepository.create(hero);
        Hero hero1 = new Hero("Boris", 2);
        this.heroRepository.create(hero1);
        assertEquals(hero1, this.heroRepository.getHeroWithHighestLevel());
    }

    @Test
    public void testGetHeroWorksFine() {
        Hero hero1 = new Hero("Boris", 2);
        this.heroRepository.create(hero);
        this.heroRepository.create(hero1);
        assertEquals(hero1, this.heroRepository.getHero("Boris"));
    }

    @Test
    public void testGetHeroesWorksFine() {
        Hero hero1 = new Hero("Boris", 2);
        this.heroRepository.create(hero);
        this.heroRepository.create(hero1);

        StringBuilder sb = new StringBuilder();
        Collection<Hero> heroes = this.heroRepository.getHeroes();
        for (Hero h : heroes) {
            sb.append(h.getName()).append(h.getLevel());
        }
        String actual = sb.toString();
        String expected = "Ivo1Boris2";

        assertEquals(expected, actual);
    }

}
