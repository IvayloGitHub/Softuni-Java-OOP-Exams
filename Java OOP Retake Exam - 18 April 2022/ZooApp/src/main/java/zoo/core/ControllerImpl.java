package zoo.core;

import zoo.entities.animals.Animal;
import zoo.entities.animals.AquaticAnimal;
import zoo.entities.animals.TerrestrialAnimal;
import zoo.entities.areas.Area;
import zoo.entities.areas.LandArea;
import zoo.entities.areas.WaterArea;
import zoo.entities.foods.Food;
import zoo.entities.foods.Meat;
import zoo.entities.foods.Vegetable;
import zoo.repositories.FoodRepository;
import zoo.repositories.FoodRepositoryImpl;

import java.util.ArrayList;
import java.util.Collection;

import static zoo.common.ConstantMessages.*;
import static zoo.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {

    private FoodRepository food;
    private Collection<Area> areas;


    public ControllerImpl() {
        this.food = new FoodRepositoryImpl();
        this.areas = new ArrayList<>();
    }

    @Override
    public String addArea(String areaType, String areaName) {
        Area area;
        if(areaType.equals("WaterArea")) {
            area = new WaterArea(areaName);
        } else if (areaType.equals("LandArea")) {
            area = new LandArea(areaName);
        } else {
            throw new NullPointerException(INVALID_AREA_TYPE);
        }
        this.areas.add(area);
        return String.format(SUCCESSFULLY_ADDED_AREA_TYPE, areaType);
    }

    @Override
    public String buyFood(String foodType) {
        Food food1;
        if (foodType.equals("Vegetable")) {
            food1 = new Vegetable();
        } else if (foodType.equals("Meat")) {
            food1 = new Meat();
        } else {
            throw new IllegalArgumentException(INVALID_FOOD_TYPE);
        }
        this.food.add(food1);
        return String.format(SUCCESSFULLY_ADDED_FOOD_TYPE, foodType);
    }

    @Override
    public String foodForArea(String areaName, String foodType) {
        Food food1 = this.food.findByType(foodType);
        if (food1 == null) {
            throw new IllegalArgumentException(String.format(NO_FOOD_FOUND, foodType));
        }
        Area area = this.areas.stream().filter(a -> a.getName().equals(areaName)).findFirst().orElse(null);

        area.addFood(food1);
        return String.format(SUCCESSFULLY_ADDED_FOOD_IN_AREA, foodType, areaName);
    }

    @Override
    public String addAnimal(String areaName, String animalType, String animalName, String kind, double price) {
        Animal animal;
        if (animalType.equals("AquaticAnimal")) {
            animal = new AquaticAnimal(animalName, kind, price);
        } else if (animalType.equals("TerrestrialAnimal")) {
            animal = new TerrestrialAnimal(animalName, kind, price);
        } else {
            throw new IllegalArgumentException(INVALID_ANIMAL_TYPE);
        }
        Area area = this.areas.stream().filter(a -> a.getName().equals(areaName)).findFirst().orElse(null);
        String result = null;
        if (area.getClass().getSimpleName().equals("WaterArea") && area.getAnimals().size() == 10) {
            result = NOT_ENOUGH_CAPACITY;
        } else if (area.getClass().getSimpleName().equals("LandArea") && area.getAnimals().size() == 25) {
            result = NOT_ENOUGH_CAPACITY;
        } else if (area.getClass().getSimpleName().equals("WaterArea") && !animal.getClass().getSimpleName().equals("AquaticAnimal")) {
            result = AREA_NOT_SUITABLE;
        } else if (area.getClass().getSimpleName().equals("LandArea") && !animal.getClass().getSimpleName().equals("TerrestrialAnimal")) {
            result = AREA_NOT_SUITABLE;
        } else {
            area.addAnimal(animal);
            result = String.format(SUCCESSFULLY_ADDED_ANIMAL_IN_AREA, animalType, areaName);
        }
        return result;
    }

    @Override
    public String feedAnimal(String areaName) {
        Area area = this.areas.stream().filter(a -> a.getName().equals(areaName)).findFirst().orElse(null);
        area.feed();
        return String.format(ANIMALS_FED, area.getAnimals().size());
    }

    @Override
    public String calculateKg(String areaName) {
        Area area = this.areas.stream().filter(a -> a.getName().equals(areaName)).findFirst().orElse(null);
        double valueArea = area.getAnimals().stream().mapToDouble(Animal::getKg).sum();
        return String.format(KILOGRAMS_AREA, areaName, valueArea);
    }

    @Override
    public String getStatistics() {
        StringBuilder sb = new StringBuilder();
        for (Area area : areas) {
            sb.append(area.getInfo());
            sb.append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
