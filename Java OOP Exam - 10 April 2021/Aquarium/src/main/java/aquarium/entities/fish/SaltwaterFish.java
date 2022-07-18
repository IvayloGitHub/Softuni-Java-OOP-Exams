package aquarium.entities.fish;

public class SaltwaterFish extends BaseFish{
    public SaltwaterFish(String name, String species, double price) {
        super(name, species, price);
    }

    @Override
    public void eat() {
        super.setSize(super.getSize() + 2);
    }
}
