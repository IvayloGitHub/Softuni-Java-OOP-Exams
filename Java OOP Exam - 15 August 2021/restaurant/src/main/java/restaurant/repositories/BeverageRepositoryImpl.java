package restaurant.repositories;

import restaurant.entities.drinks.interfaces.Beverages;
import restaurant.repositories.interfaces.BeverageRepository;

public class BeverageRepositoryImpl<T extends Beverages> extends RepositoryImpl<T> implements BeverageRepository<T> {

    @Override
    public T beverageByName(String drinkName, String drinkBrand) {
        return this.getAllEntities()
                .stream()
                .filter(e -> e.getName().equals(drinkName) && e.getBrand().equals(drinkBrand))
                .findFirst()
                .orElse(null);
    }
}
