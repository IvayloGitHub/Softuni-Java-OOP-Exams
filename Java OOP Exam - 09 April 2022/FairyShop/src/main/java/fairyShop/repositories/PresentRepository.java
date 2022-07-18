package fairyShop.repositories;

import fairyShop.models.Present;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class PresentRepository<T extends Present> implements Repository<T> {
    private Collection<T> presents;

    public PresentRepository() {
        this.presents = new ArrayList<>();
    }

    @Override
    public Collection<T> getModels() {
        return Collections.unmodifiableCollection(this.presents);
    }

    @Override
    public void add(T model) {
        this.presents.add(model);
    }

    @Override
    public boolean remove(T model) {
        return this.presents.remove(model);
    }

    @Override
    public T findByName(String name) {
        return this.presents.stream().filter(p -> p.getName().equals(name)).findFirst().orElse(null);
    }
}
