package glacialExpedition.core;

import glacialExpedition.models.explorers.AnimalExplorer;
import glacialExpedition.models.explorers.Explorer;
import glacialExpedition.models.explorers.GlacierExplorer;
import glacialExpedition.models.explorers.NaturalExplorer;
import glacialExpedition.models.mission.Mission;
import glacialExpedition.models.mission.MissionImpl;
import glacialExpedition.models.states.State;
import glacialExpedition.models.states.StateImpl;
import glacialExpedition.repositories.ExplorerRepository;
import glacialExpedition.repositories.Repository;
import glacialExpedition.repositories.StateRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static glacialExpedition.common.ConstantMessages.*;
import static glacialExpedition.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {

    private Repository<Explorer> explorers;
    private Repository<State> states;
    private Mission mission;

    private int exploredStatesCount;


    public ControllerImpl() {
        this.explorers = new ExplorerRepository<>();
        this.states = new StateRepository<>();
        this.mission = new MissionImpl();
        this.exploredStatesCount = 0;
    }

    @Override
    public String addExplorer(String type, String explorerName) {
        Explorer explorer;
        if (type.equals("NaturalExplorer")) {
            explorer = new NaturalExplorer(explorerName);
        } else if (type.equals("GlacierExplorer")) {
            explorer = new GlacierExplorer(explorerName);
        } else if (type.equals("AnimalExplorer")) {
            explorer = new AnimalExplorer(explorerName);
        } else {
            throw new IllegalArgumentException(EXPLORER_INVALID_TYPE);
        }
        this.explorers.add(explorer);
        return String.format(EXPLORER_ADDED, type, explorerName);
    }

    @Override
    public String addState(String stateName, String... exhibits) {
        State state = new StateImpl(stateName);
        state.getExhibits().addAll(Arrays.asList(exhibits));
        this.states.add(state);

        return String.format(STATE_ADDED, stateName);
    }

    @Override
    public String retireExplorer(String explorerName) {
        Explorer explorer = this.explorers.byName(explorerName);
        if (explorer == null) {
            throw new IllegalArgumentException(String.format(EXPLORER_DOES_NOT_EXIST, explorerName));
        }
        this.explorers.remove(explorer);
        return String.format(EXPLORER_RETIRED, explorerName);
    }

    @Override
    public String exploreState(String stateName) {
        State state = this.states.byName(stateName);
        List<Explorer> explorersWithEnoughEnergy = this.explorers
                .getCollection()
                .stream()
                .filter(e -> e.getEnergy() > 50)
                .collect(Collectors.toList());
        if (explorersWithEnoughEnergy.isEmpty()) {
            throw new IllegalArgumentException(STATE_EXPLORERS_DOES_NOT_EXISTS);
        }

        mission.explore(state, explorersWithEnoughEnergy);
        this.exploredStatesCount++;

        long retiredExplorers = this.explorers
                .getCollection()
                .stream()
                .filter(e -> !e.canSearch())
                .count();
        return String.format(STATE_EXPLORER, stateName, retiredExplorers);
    }

    @Override
    public String finalResult() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(FINAL_STATE_EXPLORED, this.exploredStatesCount));
        sb.append(System.lineSeparator());
        sb.append(FINAL_EXPLORER_INFO);
        sb.append(System.lineSeparator());
        for (Explorer explorer : this.explorers.getCollection()) {
            sb.append(String.format(FINAL_EXPLORER_NAME, explorer.getName()));
            sb.append(System.lineSeparator());
            sb.append(String.format(FINAL_EXPLORER_ENERGY, explorer.getEnergy()));
            sb.append(System.lineSeparator());

            if (explorer.getSuitcase().getExhibits().isEmpty()) {
                sb.append(String.format(FINAL_EXPLORER_SUITCASE_EXHIBITS, "None"));
                sb.append(System.lineSeparator());
            } else {
                String output = String.join(FINAL_EXPLORER_SUITCASE_EXHIBITS_DELIMITER, explorer.getSuitcase().getExhibits());
                sb.append(String.format(FINAL_EXPLORER_SUITCASE_EXHIBITS, output));
                sb.append(System.lineSeparator());
            }
        }
        return sb.toString().trim();
    }
}
