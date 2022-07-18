package glacialExpedition.models.mission;

import glacialExpedition.models.explorers.Explorer;
import glacialExpedition.models.states.State;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MissionImpl implements Mission {

    @Override
    public void explore(State state, Collection<Explorer> explorers) {
        List<String> exhibits = state.getExhibits().stream().collect(Collectors.toList());
        for (Explorer explorer : explorers) {
            for (int i = 0; i < exhibits.size(); i++) {
                if (explorer.canSearch()) {
                    String exhibit = exhibits.remove(i);
                    explorer.getSuitcase().getExhibits().add(exhibit);
                    explorer.search();
                    i--;
                } else {
                    break;
                }
            }
        }
        state.getExhibits().clear();
        state.getExhibits().addAll(exhibits);
    }
}
