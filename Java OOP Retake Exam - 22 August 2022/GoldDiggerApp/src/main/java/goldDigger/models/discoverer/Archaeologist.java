package goldDigger.models.discoverer;

public class Archaeologist extends BaseDiscoverer {
    public static final int INITIAL_ENERGY = 60;
    public Archaeologist(String name) {
        super(name, INITIAL_ENERGY);
    }
}
