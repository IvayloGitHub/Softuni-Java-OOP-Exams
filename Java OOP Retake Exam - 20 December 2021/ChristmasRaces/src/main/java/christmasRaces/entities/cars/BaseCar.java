package christmasRaces.entities.cars;

import static christmasRaces.common.ExceptionMessages.*;

public abstract class BaseCar implements Car {

    private String model;
    private int horsePower;
    private double cubicCentimeters;

    protected BaseCar(String model, int horsePower, double cubicCentimeters) {
        this.setModel(model);
        this.setHorsePower(horsePower);
        this.setCubicCentimeters(cubicCentimeters);
    }

    @Override
    public String getModel() {
        return this.model;
    }

    @Override
    public int getHorsePower() {
        return this.horsePower;
    }

    @Override
    public double getCubicCentimeters() {
        return this.cubicCentimeters;
    }

    @Override
    public double calculateRacePoints(int laps) {
        return (this.getCubicCentimeters() / this.getHorsePower()) * laps;
    }

    private void setModel(String model) {
        if (model == null || model.trim().isEmpty() || model.length() < 4) {
            throw new IllegalArgumentException(String.format(INVALID_MODEL, model, 4));
        }
        this.model = model;
    }

    protected void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    private void setCubicCentimeters(double cubicCentimeters) {
        this.cubicCentimeters = cubicCentimeters;
    }
}
