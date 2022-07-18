package restaurant.repositories.interfaces;

import restaurant.entities.drinks.interfaces.Beverages;

public interface BeverageRepository<T> extends Repository<T> {
    T beverageByName(String drinkName,String drinkBrand);
}
