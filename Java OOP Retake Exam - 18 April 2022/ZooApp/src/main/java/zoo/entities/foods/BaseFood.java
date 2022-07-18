package zoo.entities.foods;

public abstract class BaseFood implements Food {
    private int calories;
    private double price;

    protected BaseFood(int calories, double price) {
        this.setCalories(calories);
        this.setPrice(price);
    }

    @Override
    public int getCalories() {
        return this.calories;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    private void setCalories(int calories) {
        this.calories = calories;
    }

    private void setPrice(double price) {
        this.price = price;
    }
}
