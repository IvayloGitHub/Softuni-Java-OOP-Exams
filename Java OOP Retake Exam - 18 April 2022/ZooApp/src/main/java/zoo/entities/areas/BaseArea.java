package zoo.entities.areas;

import zoo.entities.animals.Animal;
import zoo.entities.foods.Food;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import static zoo.common.ExceptionMessages.*;

public abstract class BaseArea implements Area {

    private String name;
    private int capacity;
    private Collection<Food> foods;
    private Collection<Animal> animals;

    protected BaseArea(String name, int capacity) {
        this.setName(name);
        this.setCapacity(capacity);
        this.foods = new ArrayList<>();
        this.animals = new ArrayList<>();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Collection<Animal> getAnimals() {
        return this.animals;
    }

    @Override
    public Collection<Food> getFoods() {
        return this.foods;
    }

    @Override
    public int sumCalories() {
        return this.foods.stream().mapToInt(Food::getCalories).sum();
    }

    @Override
    public void addAnimal(Animal animal) {
        if(this.capacity > this.animals.size()) {
            this.animals.add(animal);
        } else {
            throw new IllegalStateException(NOT_ENOUGH_CAPACITY);
        }
    }

    @Override
    public void removeAnimal(Animal animal) {
       this.animals.remove(animal);
    }

    @Override
    public void addFood(Food food) {
        this.foods.add(food);
    }

    @Override
    public void feed() {
        this.animals.forEach(Animal::eat);
    }

    @Override
    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s (%s):", this.getName(), this.getClass().getSimpleName()));
        sb.append(System.lineSeparator());
        sb.append("Animals: ");
        sb.append(this.animals.isEmpty() ? "none" : this.animals.stream().map(Animal::getName).collect(Collectors.joining(" ")));
        sb.append(System.lineSeparator());
        sb.append(String.format("Foods: %d", this.foods.size()));
        sb.append(System.lineSeparator());
        sb.append(String.format("Calories: %d", this.sumCalories()));
        return sb.toString().trim();
    }

    private void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException(AREA_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    private void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
