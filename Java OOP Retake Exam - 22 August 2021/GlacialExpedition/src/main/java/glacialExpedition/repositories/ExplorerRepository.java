package glacialExpedition.repositories;

import glacialExpedition.models.explorers.Explorer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ExplorerRepository<T extends Explorer> implements Repository<T>{
    private Collection<T> explorers;

    public ExplorerRepository() {
        explorers = new ArrayList<>();
    }

    @Override
    public Collection<T> getCollection() {
        return Collections.unmodifiableCollection(this.explorers);
    }

    @Override
    public void add(T entity) {
        this.explorers.add(entity);
    }

    @Override
    public boolean remove(T entity) {
        return this.explorers.remove(entity);
    }

    @Override
    public T byName(String name) {
        return this.explorers.stream().filter(e -> e.getName().equals(name)).findFirst().orElse(null);
    }
}
