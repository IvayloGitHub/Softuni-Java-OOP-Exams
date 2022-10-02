package goldDigger.core;

import goldDigger.models.discoverer.Anthropologist;
import goldDigger.models.discoverer.Archaeologist;
import goldDigger.models.discoverer.Discoverer;
import goldDigger.models.discoverer.Geologist;
import goldDigger.models.operation.Operation;
import goldDigger.models.operation.OperationImpl;
import goldDigger.models.spot.Spot;
import goldDigger.models.spot.SpotImpl;
import goldDigger.repositories.DiscovererRepository;
import goldDigger.repositories.Repository;
import goldDigger.repositories.SpotRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static goldDigger.common.ConstantMessages.*;
import static goldDigger.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {

    private Repository<Discoverer> discoverers;
    private Repository<Spot> spots;
    private Operation operation;

    private int inspectedSpotCount;

    public ControllerImpl() {
        this.discoverers = new DiscovererRepository<>();
        this.spots = new SpotRepository<>();
        this.operation = new OperationImpl();
        this.inspectedSpotCount = 0;
    }

    @Override
    public String addDiscoverer(String kind, String discovererName) {
        Discoverer discoverer;
        if (kind.equals("Archaeologist")) {
            discoverer = new Archaeologist(discovererName);
        } else if (kind.equals("Anthropologist")) {
            discoverer = new Anthropologist(discovererName);
        } else if (kind.equals("Geologist")) {
            discoverer = new Geologist(discovererName);
        } else {
            throw new IllegalArgumentException(DISCOVERER_INVALID_KIND);
        }
        this.discoverers.add(discoverer);
        return String.format(DISCOVERER_ADDED, kind, discovererName);
    }

    @Override
    public String addSpot(String spotName, String... exhibits) {
        Spot spot = new SpotImpl(spotName);
        spot.getExhibits().addAll(Arrays.asList(exhibits));
        this.spots.add(spot);
        return String.format(SPOT_ADDED, spotName);
    }

    @Override
    public String excludeDiscoverer(String discovererName) {
        Discoverer discoverer = this.discoverers.byName(discovererName);
        if (discoverer == null) {
            throw new IllegalArgumentException(String.format(DISCOVERER_DOES_NOT_EXIST, discovererName));
        }
        this.discoverers.remove(discoverer);
        return String.format(DISCOVERER_EXCLUDE, discovererName);
    }

    @Override
    public String inspectSpot(String spotName) {
        Spot spot = this.spots.byName(spotName);
        List<Discoverer> discoverersWithEnoughEnergy = this.discoverers
                .getCollection()
                .stream()
                .filter(d -> d.getEnergy() > 45)
                .collect(Collectors.toList());

        if (discoverersWithEnoughEnergy.isEmpty()) {
            throw new IllegalArgumentException(SPOT_DISCOVERERS_DOES_NOT_EXISTS);
        }
        operation.startOperation(spot, discoverersWithEnoughEnergy);

        this.inspectedSpotCount++;

        long excludedDiscoverers = this.discoverers
                .getCollection()
                .stream()
                .filter(d -> !d.canDig())
                .count();
        return String.format(INSPECT_SPOT, spotName, excludedDiscoverers);
    }

    @Override
    public String getStatistics() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(FINAL_SPOT_INSPECT, this.inspectedSpotCount));
        sb.append(System.lineSeparator());
        sb.append(FINAL_DISCOVERER_INFO);
        sb.append(System.lineSeparator());
        for(Discoverer discoverer: this.discoverers.getCollection()) {
            sb.append(String.format(FINAL_DISCOVERER_NAME, discoverer.getName()));
            sb.append(System.lineSeparator());
            sb.append(String.format(FINAL_DISCOVERER_ENERGY, discoverer.getEnergy()));
            sb.append(System.lineSeparator());

            if (discoverer.getMuseum().getExhibits().isEmpty()) {
                sb.append(String.format(FINAL_DISCOVERER_MUSEUM_EXHIBITS,"None"));
                sb.append(System.lineSeparator());
            } else {
                String output = String.join(FINAL_DISCOVERER_MUSEUM_EXHIBITS_DELIMITER, discoverer.getMuseum().getExhibits());
                sb.append(String.format(FINAL_DISCOVERER_MUSEUM_EXHIBITS, output));
                sb.append(System.lineSeparator());
            }
        }
        return sb.toString().trim();
    }
}
