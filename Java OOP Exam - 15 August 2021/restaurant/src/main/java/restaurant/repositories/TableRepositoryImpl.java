package restaurant.repositories;

import restaurant.entities.tables.interfaces.Table;
import restaurant.repositories.interfaces.TableRepository;

public class TableRepositoryImpl<T extends Table> extends RepositoryImpl<T> implements TableRepository<T> {
    @Override
    public T byNumber(int number) {
        return this.getAllEntities()
                .stream()
                .filter(t -> t.getTableNumber() == number)
                .findFirst()
                .orElse(null);
    }
}
