package aquarium.core;

import aquarium.entities.aquariums.Aquarium;
import aquarium.entities.aquariums.FreshwaterAquarium;
import aquarium.entities.aquariums.SaltwaterAquarium;
import aquarium.entities.decorations.Decoration;
import aquarium.entities.decorations.Ornament;
import aquarium.entities.decorations.Plant;
import aquarium.entities.fish.Fish;
import aquarium.entities.fish.FreshwaterFish;
import aquarium.entities.fish.SaltwaterFish;
import aquarium.repositories.DecorationRepository;
import aquarium.repositories.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

import static aquarium.common.ConstantMessages.*;
import static aquarium.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {

    private Repository decorations;
    private Collection<Aquarium> aquariums;

    public ControllerImpl() {
        decorations = new DecorationRepository();
        aquariums = new ArrayList<>();
    }

    @Override
    public String addAquarium(String aquariumType, String aquariumName) {
        Aquarium aquarium;

        if (aquariumType.equals("FreshwaterAquarium")) {
            aquarium = new FreshwaterAquarium(aquariumName);
        } else if (aquariumType.equals("SaltwaterAquarium")) {
            aquarium = new SaltwaterAquarium(aquariumName);
        } else {
            throw new NullPointerException(INVALID_AQUARIUM_TYPE);
        }
        aquariums.add(aquarium);
        return String.format(SUCCESSFULLY_ADDED_AQUARIUM_TYPE, aquariumType);
    }

    @Override
    public String addDecoration(String type) {
        Decoration decoration;
        if (type.equals("Ornament")) {
            decoration = new Ornament();
        } else if (type.equals("Plant")) {
            decoration = new Plant();
        } else {
            throw new IllegalArgumentException(INVALID_DECORATION_TYPE);
        }
        decorations.add(decoration);
        return String.format(SUCCESSFULLY_ADDED_DECORATION_TYPE, type);
    }

    @Override
    public String insertDecoration(String aquariumName, String decorationType) {
        Aquarium aquarium = this.aquariums.stream().filter(a -> a.getName().equals(aquariumName)).findFirst().orElse(null);
        Decoration decoration = decorations.findByType(decorationType);
        if (decoration == null) {
           throw new IllegalArgumentException(String.format(NO_DECORATION_FOUND, decorationType));
        }
        if (aquarium != null) {
            aquarium.addDecoration(decoration);
        }
        decorations.remove(decoration);
        return String.format(SUCCESSFULLY_ADDED_DECORATION_IN_AQUARIUM, decorationType, aquariumName);
    }

    @Override
    public String addFish(String aquariumName, String fishType, String fishName, String fishSpecies, double price) {
        Fish fish;
        if (fishType.equals("FreshwaterFish")) {
            fish = new FreshwaterFish(fishName, fishSpecies, price);
        } else if (fishType.equals("SaltwaterFish")) {
            fish = new SaltwaterFish(fishName, fishSpecies, price);
        } else {
            throw new IllegalArgumentException(INVALID_FISH_TYPE);
        }
        Aquarium aquarium = this.aquariums.stream().filter(a -> a.getName().equals(aquariumName)).findFirst().orElse(null);
        String aquariumType = aquarium.getClass().getSimpleName().replace("Aquarium", "Fish");
        if (!aquariumType.equals(fishType)) {
            return WATER_NOT_SUITABLE;
        }
        aquarium.addFish(fish);

        return String.format(SUCCESSFULLY_ADDED_FISH_IN_AQUARIUM, fishType, aquariumName);
    }

    @Override
    public String feedFish(String aquariumName) {
        Aquarium aquarium = this.aquariums.stream().filter(a -> a.getName().equals(aquariumName)).findFirst().orElse(null);

        aquarium.feed();

        return String.format(FISH_FED, aquarium.getFish().size());
    }

    @Override
    public String calculateValue(String aquariumName) {
        Aquarium aquarium = this.aquariums.stream().filter(a -> a.getName().equals(aquariumName)).findFirst().orElse(null);

        double sumOfFish = aquarium.getFish().stream().mapToDouble(Fish::getPrice).sum();
        double sumOfDecoration = aquarium.getDecorations().stream().mapToDouble(Decoration::getPrice).sum();
        double total = sumOfFish + sumOfDecoration;
        return String.format(VALUE_AQUARIUM, aquariumName, total);
    }

    @Override
    public String report() {
        return this.aquariums.stream().map(Aquarium::getInfo).collect(Collectors.joining(System.lineSeparator()));
    }
}
