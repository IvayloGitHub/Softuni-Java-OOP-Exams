package spaceStation.core;

import spaceStation.models.astronauts.Astronaut;
import spaceStation.models.astronauts.Biologist;
import spaceStation.models.astronauts.Geodesist;
import spaceStation.models.astronauts.Meteorologist;
import spaceStation.models.mission.Mission;
import spaceStation.models.mission.MissionImpl;
import spaceStation.models.planets.Planet;
import spaceStation.models.planets.PlanetImpl;
import spaceStation.repositories.AstronautRepository;
import spaceStation.repositories.PlanetRepository;
import spaceStation.repositories.Repository;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static spaceStation.common.ConstantMessages.*;
import static spaceStation.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {

    private Repository<Astronaut> astronauts;
    private Repository<Planet> planets;
    private Mission mission;

    private int exploredPlanets;

    public ControllerImpl() {
        this.astronauts = new AstronautRepository<>();
        this.planets = new PlanetRepository<>();
        this.mission = new MissionImpl();
    }

    @Override
    public String addAstronaut(String type, String astronautName) {
        Astronaut astronaut;
        if (type.equals("Biologist")) {
            astronaut = new Biologist(astronautName);
        } else if (type.equals("Geodesist")) {
            astronaut = new Geodesist(astronautName);
        } else if (type.equals("Meteorologist")) {
            astronaut = new Meteorologist(astronautName);
        } else {
            throw new IllegalArgumentException(ASTRONAUT_INVALID_TYPE);
        }
        astronauts.add(astronaut);
        return String.format(ASTRONAUT_ADDED, type, astronautName);
    }

    @Override
    public String addPlanet(String planetName, String... items) {
        Planet planet = new PlanetImpl(planetName);
        planet.getItems().addAll(Arrays.asList(items));
        planets.add(planet);
        return String.format(PLANET_ADDED, planetName);
    }

    @Override
    public String retireAstronaut(String astronautName) {
        Astronaut astronaut = this.astronauts.findByName(astronautName);
        if (astronaut == null) {
            throw new IllegalArgumentException(String.format(ASTRONAUT_DOES_NOT_EXIST, astronautName));
        } else {
            astronauts.remove(astronaut);
        }
        return String.format(ASTRONAUT_RETIRED, astronautName);
    }

    @Override
    public String explorePlanet(String planetName) {
        List<Astronaut> appropriateAstronauts = astronauts
                .getModels()
                .stream()
                .filter(a -> a.getOxygen() > 60)
                .collect(Collectors.toList());

        Planet planet = planets.findByName(planetName);

        if (appropriateAstronauts.isEmpty()) {
            throw new IllegalArgumentException(PLANET_ASTRONAUTS_DOES_NOT_EXISTS);
        }

        mission.explore(planet, appropriateAstronauts);

        List<Astronaut> deadAstronauts = astronauts.getModels().stream().filter(a -> !a.canBreath()).collect(Collectors.toList());

        this.exploredPlanets++;

        return String.format(PLANET_EXPLORED, planetName, deadAstronauts.size());
    }

    @Override
    public String report() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(REPORT_PLANET_EXPLORED, this.exploredPlanets));
        sb.append(System.lineSeparator());
        sb.append(REPORT_ASTRONAUT_INFO);
        sb.append(System.lineSeparator());
        for (Astronaut astronaut : astronauts.getModels()) {
            sb.append(String.format(REPORT_ASTRONAUT_NAME, astronaut.getName()));
            sb.append(System.lineSeparator());
            sb.append(String.format(REPORT_ASTRONAUT_OXYGEN, astronaut.getOxygen()));
            sb.append(System.lineSeparator());

            List<String> items = (List<String>)astronaut.getBag().getItems();

            String bagItems = items.isEmpty() ? "none" : String.join(REPORT_ASTRONAUT_BAG_ITEMS_DELIMITER, items);
            sb.append(String.format(REPORT_ASTRONAUT_BAG_ITEMS, bagItems));
            sb.append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
