package goldDigger.models.operation;

import goldDigger.models.discoverer.Discoverer;
import goldDigger.models.spot.Spot;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class OperationImpl implements Operation {
    @Override
    public void startOperation(Spot spot, Collection<Discoverer> discoverers) {
        List<String> exhibits = spot.getExhibits().stream().collect(Collectors.toList());

        for(Discoverer discoverer: discoverers) {
            for(int i = 0; i < exhibits.size(); i++) {
                if (discoverer.canDig()) {
                    String exhibit = exhibits.remove(i);
                    discoverer.getMuseum().getExhibits().add(exhibit);
                    discoverer.dig();
                    i--;
                } else {
                    break;
                }
            }
        }
        spot.getExhibits().clear();
        spot.getExhibits().addAll(exhibits);
    }
}
