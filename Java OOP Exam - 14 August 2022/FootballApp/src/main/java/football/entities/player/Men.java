package football.entities.player;

public class Men extends BasePlayer{
    public static final double INITIAL_KG = 85.5;
    public Men(String name, String nationality, int strength) {
        super(name, nationality, INITIAL_KG, strength);
    }

    @Override
    public void stimulation() {
        super.setStrength(super.getStrength() + 145);
    }
}
