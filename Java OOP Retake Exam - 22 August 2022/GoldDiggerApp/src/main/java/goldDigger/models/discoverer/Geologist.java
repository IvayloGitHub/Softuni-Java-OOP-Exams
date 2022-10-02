package goldDigger.models.discoverer;

public class Geologist extends BaseDiscoverer {
    public static final int INITIAL_ENERGY = 100;
    public Geologist(String name) {
        super(name, INITIAL_ENERGY);
    }
}
