package goldDigger.repositories;

import goldDigger.models.spot.Spot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SpotRepository<T extends Spot> implements Repository<T> {

    private Collection<T> spots;

    public SpotRepository() {
        this.spots = new ArrayList<>();
    }

    @Override
    public Collection<T> getCollection() {
        return Collections.unmodifiableCollection(this.spots);
    }

    @Override
    public void add(T entity) {
        this.spots.add(entity);
    }

    @Override
    public boolean remove(T entity) {
        return this.spots.remove(entity);
    }

    @Override
    public T byName(String name) {
        return this.spots.stream().filter(s -> s.getName().equals(name))
                .findFirst().orElse(null);
    }
}
