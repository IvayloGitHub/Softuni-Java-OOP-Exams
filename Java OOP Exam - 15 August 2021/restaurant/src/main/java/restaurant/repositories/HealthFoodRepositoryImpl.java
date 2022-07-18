package restaurant.repositories;

import restaurant.entities.healthyFoods.interfaces.HealthyFood;
import restaurant.repositories.interfaces.HealthFoodRepository;

public class HealthFoodRepositoryImpl<T extends HealthyFood> extends RepositoryImpl<T> implements HealthFoodRepository<T> {
    @Override
    public T foodByName(String name) {
        return this.getAllEntities()
                .stream()
                .filter(f -> f.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
