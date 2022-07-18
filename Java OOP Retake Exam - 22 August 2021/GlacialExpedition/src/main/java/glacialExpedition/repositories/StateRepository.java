package glacialExpedition.repositories;

import glacialExpedition.models.states.State;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class StateRepository<T extends State> implements Repository<T>{
    private Collection<T> states;

    public StateRepository() {
        this.states = new ArrayList<>();
    }

    @Override
    public Collection<T> getCollection() {
        return Collections.unmodifiableCollection(this.states);
    }

    @Override
    public void add(T entity) {
        this.states.add(entity);
    }

    @Override
    public boolean remove(T entity) {
        return this.states.remove(entity);
    }

    @Override
    public T byName(String name) {
        return this.states.stream().filter(s -> s.getName().equals(name)).findFirst().orElse(null);
    }
}
