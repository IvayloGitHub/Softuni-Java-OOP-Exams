package restaurant.repositories.interfaces;

import restaurant.entities.healthyFoods.interfaces.HealthyFood;

public interface HealthFoodRepository<T> extends Repository<T> {
    T foodByName(String name);
}
