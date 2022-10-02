package goldDigger.repositories;

import goldDigger.models.discoverer.Discoverer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class DiscovererRepository<T extends Discoverer> implements Repository<T> {

    private Collection<T> discoverers;

    public DiscovererRepository() {
        this.discoverers = new ArrayList<>();
    }

    @Override
    public Collection<T> getCollection() {
        return Collections.unmodifiableCollection(this.discoverers);
    }

    @Override
    public void add(T entity) {
        this.discoverers.add(entity);
    }

    @Override
    public boolean remove(T entity) {
        return this.discoverers.remove(entity);
    }

    @Override
    public T byName(String name) {
        return this.discoverers.stream().filter(d -> d.getName().equals(name)).findFirst().orElse(null);
    }
}
