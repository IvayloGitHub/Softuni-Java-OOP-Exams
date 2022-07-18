package restaurant.repositories;

import restaurant.repositories.interfaces.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class RepositoryImpl<T> implements Repository<T> {
    private Collection<T> entities;

    public RepositoryImpl() {
        this.entities = new ArrayList<>();
    }

    @Override
    public Collection<T> getAllEntities() {
        return Collections.unmodifiableCollection(this.entities);
    }

    @Override
    public void add(T entity) {
        this.entities.add(entity);
    }
}
