package spaceStation.repositories;

import spaceStation.models.astronauts.Astronaut;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class AstronautRepository<T extends Astronaut> implements Repository<T>{
    private Collection<T> astronauts;

    public AstronautRepository() {
        astronauts = new ArrayList<>();
    }

    @Override
    public Collection<T> getModels() {
        return Collections.unmodifiableCollection(this.astronauts);
    }

    @Override
    public void add(T model) {
        astronauts.add(model);
    }

    @Override
    public boolean remove(T model) {
        return astronauts.remove(model);
    }

    @Override
    public T findByName(String name) {
        return astronauts.stream().filter(a -> a.getName().equals(name)).findFirst().orElse(null);
    }
}
