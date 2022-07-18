package zoo.entities.foods;

public class Meat extends BaseFood {
    private static final int INITIAL_CALORIES = 70;
    private static final double INITIAL_PRICE = 5;
    public Meat() {
        super(INITIAL_CALORIES, INITIAL_PRICE);
    }
}
