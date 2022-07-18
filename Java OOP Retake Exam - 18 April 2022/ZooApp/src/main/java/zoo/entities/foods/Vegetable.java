package zoo.entities.foods;

public class Vegetable extends BaseFood{

    private static final int INITIAL_CALORIES = 50;
    private static final double INITIAL_PRICE = 5;
    public Vegetable() {
        super(INITIAL_CALORIES, INITIAL_PRICE);
    }
}
