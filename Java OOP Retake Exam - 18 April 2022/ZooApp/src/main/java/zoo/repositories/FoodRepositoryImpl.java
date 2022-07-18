package zoo.repositories;

import zoo.entities.foods.Food;

import java.util.ArrayList;
import java.util.Collection;

public class FoodRepositoryImpl implements FoodRepository {

    private Collection<Food> food;

    public FoodRepositoryImpl() {
        this.food = new ArrayList<>();
    }

    @Override
    public void add(Food food) {
        this.food.add(food);
    }

    @Override
    public boolean remove(Food food) {
        return this.food.remove(food);
    }

    @Override
    public Food findByType(String type) {
        return this.food.stream().filter(f -> f.getClass().getSimpleName().equals(type)).findFirst().orElse(null);
    }
}
