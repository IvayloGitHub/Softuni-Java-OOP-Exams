package spaceStation.models.mission;

import spaceStation.models.astronauts.Astronaut;
import spaceStation.models.planets.Planet;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MissionImpl implements Mission{
    @Override
    public void explore(Planet planet, Collection<Astronaut> astronauts) {
        List<String> items = planet.getItems().stream().collect(Collectors.toList());

        for (Astronaut astronaut : astronauts) {
            for (int i = 0; i < items.size(); i++) {
                if (astronaut.canBreath() && !planet.getItems().isEmpty()) {
                    String item = items.remove(i);
                    astronaut.getBag().getItems().add(item);
                    astronaut.breath();
                    i--;
                } else {
                    break;
                }
            }
        }
        planet.getItems().clear();
        planet.getItems().addAll(items);
    }
}
