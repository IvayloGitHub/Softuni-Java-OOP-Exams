package catHouse.core;

import catHouse.entities.cat.Cat;
import catHouse.entities.cat.LonghairCat;
import catHouse.entities.cat.ShorthairCat;
import catHouse.entities.houses.House;
import catHouse.entities.houses.LongHouse;
import catHouse.entities.houses.ShortHouse;
import catHouse.entities.toys.Ball;
import catHouse.entities.toys.Mouse;
import catHouse.entities.toys.Toy;
import catHouse.repositories.Repository;
import catHouse.repositories.ToyRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import static catHouse.common.ConstantMessages.*;
import static catHouse.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {
    private Repository toys;
    private Collection<House> houses;

    public ControllerImpl() {
        this.toys = new ToyRepository();
        this.houses = new ArrayList<>();
    }

    @Override
    public String addHouse(String type, String name) {
        House house;
        if (type.equals("ShortHouse")) {
            house = new ShortHouse(name);
        } else if (type.equals("LongHouse")) {
            house = new LongHouse(name);
        } else {
            throw new NullPointerException(INVALID_HOUSE_TYPE);
        }
        this.houses.add(house);
        return String.format(SUCCESSFULLY_ADDED_HOUSE_TYPE, type);
    }

    @Override
    public String buyToy(String type) {
        Toy toy;
        if (type.equals("Ball")) {
            toy = new Ball();
        } else if (type.equals("Mouse")) {
            toy = new Mouse();
        } else {
            throw new IllegalArgumentException(INVALID_TOY_TYPE);
        }
        this.toys.buyToy(toy);
        return String.format(SUCCESSFULLY_ADDED_TOY_TYPE, type);
    }

    @Override
    public String toyForHouse(String houseName, String toyType) {
        Toy toy = this.toys.findFirst(toyType);
        House house = this.houses.stream().filter(h -> h.getName().equals(houseName)).findFirst().orElse(null);
        if (toy == null) {
            throw new IllegalArgumentException(String.format(NO_TOY_FOUND, toyType));
        }
        this.toys.removeToy(toy);
        house.buyToy(toy);

        return String.format(SUCCESSFULLY_ADDED_TOY_IN_HOUSE, toyType, houseName);
    }

    @Override
    public String addCat(String houseName, String catType, String catName, String catBreed, double price) {
        House house = this.houses.stream().filter(h -> h.getName().equals(houseName)).findFirst().orElse(null);
        Cat cat;
        if (catType.equals("ShorthairCat")) {
            cat = new ShorthairCat(catName, catBreed, price);
        } else if (catType.equals("LonghairCat")) {
            cat = new LonghairCat(catName, catBreed, price);
        } else {
            throw new IllegalArgumentException(INVALID_CAT_TYPE);
        }

        String match = house.getClass().getSimpleName().replace("House", "");
        if (!cat.getClass().getSimpleName().startsWith(match)) {
            return UNSUITABLE_HOUSE;
        }

        house.addCat(cat);
        return String.format(SUCCESSFULLY_ADDED_CAT_IN_HOUSE, catType, houseName);
    }

    @Override
    public String feedingCat(String houseName) {
        House house = this.houses.stream().filter(h -> h.getName().equals(houseName)).findFirst().orElse(null);
        house.feeding();
        return String.format(FEEDING_CAT, house.getCats().size());
    }

    @Override
    public String sumOfAll(String houseName) {
        House house = this.houses.stream().filter(h -> h.getName().equals(houseName)).findFirst().orElse(null);
        double sumOfPriceOfCats = house.getCats().stream().mapToDouble(Cat::getPrice).sum();
        double sumOfPriceOfToys = house.getToys().stream().mapToDouble(Toy::getPrice).sum();

        double totalSum = sumOfPriceOfCats + sumOfPriceOfToys;
        return String.format(VALUE_HOUSE, houseName, totalSum);
    }

    @Override
    public String getStatistics() {
        return this.houses.stream().map(House::getStatistics).collect(Collectors.joining(System.lineSeparator()));
    }
}
