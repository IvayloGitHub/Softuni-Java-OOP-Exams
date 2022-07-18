package petStore;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PetStoreTests {
    PetStore petStore;
    Animal animal1;
    Animal animal2;

    @Before
    public void setUp() {
        this.petStore = new PetStore();
        this.animal1 = new Animal("cat", 4, 150.00);
        this.animal2 = new Animal("guinea pig", 1, 30.00);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testToModifyCollectionThrows() {
        this.petStore.addAnimal(this.animal1);
        this.petStore.getAnimals().remove(this.animal1);
    }

    @Test
    public void testGetCount() {
        this.petStore.addAnimal(this.animal1);
        this.petStore.addAnimal(this.animal2);
        assertEquals(2, this.petStore.getCount());
    }

    @Test
    public void testFindAllAnimalsWithMaxKilograms() {
        this.petStore.addAnimal(this.animal1);
        this.petStore.addAnimal(this.animal2);

        assertEquals(this.animal1, this.petStore.findAllAnimalsWithMaxKilograms(2).get(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddAnimalThrowsWithNull() {
        this.petStore.addAnimal(null);
    }

    @Test
    public void testAddAnimalWorksFine() {
        this.petStore.addAnimal(this.animal1);
        this.petStore.addAnimal(this.animal2);

        assertEquals("cat", this.petStore.getAnimals().get(0).getSpecie());
        assertEquals(150.00, this.petStore.getAnimals().get(0).getPrice(), 0.01);
    }

    @Test
    public void testGetTheMostExpensiveAnimal() {
        this.petStore.addAnimal(this.animal1);
        this.petStore.addAnimal(this.animal2);

        assertEquals(this.animal1, this.petStore.getTheMostExpensiveAnimal());
    }

    @Test
    public void testFindAllAnimalBySpecie() {
        this.petStore.addAnimal(this.animal1);
        this.petStore.addAnimal(this.animal2);

        assertEquals("cat", this.petStore.findAllAnimalBySpecie("cat").get(0).getSpecie());
        assertEquals(150.00, this.petStore.findAllAnimalBySpecie("cat").get(0).getPrice(), 0.01);
    }

}

