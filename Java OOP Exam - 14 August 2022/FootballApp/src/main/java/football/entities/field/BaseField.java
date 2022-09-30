package football.entities.field;

import football.entities.player.Player;
import football.entities.supplement.Supplement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import static football.common.ConstantMessages.*;
import static football.common.ExceptionMessages.*;


public abstract class BaseField implements Field {

    private String name;
    private int capacity;
    private Collection<Supplement> supplements;
    private Collection<Player> players;

    protected BaseField(String name, int capacity) {
        this.setName(name);
        this.setCapacity(capacity);
        this.supplements = new ArrayList<>();
        this.players = new ArrayList<>();
    }

    private void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException(FIELD_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    private int getCapacity() {
        return this.capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setSupplements(Collection<Supplement> supplements) {
        this.supplements = supplements;
    }

    public void setPlayers(Collection<Player> players) {
        this.players = players;
    }

    @Override
    public int sumEnergy() {
        return supplements.stream().mapToInt(Supplement::getEnergy).sum();
    }

    @Override
    public void addPlayer(Player player) {
        if (capacity <= players.size()) {
            throw new IllegalStateException(NOT_ENOUGH_CAPACITY);
        }
        this.players.add(player);
    }

    @Override
    public void removePlayer(Player player) {
        this.players.remove(player);
    }

    @Override
    public void addSupplement(Supplement supplement) {
        this.supplements.add(supplement);
    }

    @Override
    public void drag() {
        this.players.forEach(Player::stimulation);
    }

    @Override
    public String getInfo() {
        StringBuilder sb = new StringBuilder();

            sb.append(String.format("%s (%s):", getName(), getClass().getSimpleName()));
            sb.append(System.lineSeparator());
            sb.append("Player: ");
            sb.append(players.isEmpty() ? "none" : players.stream().map(Player::getName).collect(Collectors.joining(" ")));
            sb.append(System.lineSeparator());
            sb.append(String.format("Supplement: %d", supplements.size()));
            sb.append(System.lineSeparator());
            sb.append(String.format("Energy: %d", sumEnergy()));

        return sb.toString().trim();
    }

    @Override
    public Collection<Player> getPlayers() {
        return this.players;
    }

    @Override
    public Collection<Supplement> getSupplements() {
        return this.supplements;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
