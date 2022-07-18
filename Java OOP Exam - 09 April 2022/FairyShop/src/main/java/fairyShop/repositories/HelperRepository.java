package fairyShop.repositories;

import fairyShop.models.Helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class HelperRepository<T extends Helper> implements Repository<T> {

    private Collection<T> helpers;

    public HelperRepository() {
        this.helpers = new ArrayList<>();
    }

    @Override
    public Collection<T> getModels() {
        return Collections.unmodifiableCollection(this.helpers);
    }

    @Override
    public void add(T model) {
        this.helpers.add(model);
    }

    @Override
    public boolean remove(T model) {
        return this.helpers.remove(model);
    }

    @Override
    public T findByName(String name) {
        return this.helpers.stream().filter(h -> h.getName().equals(name)).findFirst().orElse(null);
    }
}
