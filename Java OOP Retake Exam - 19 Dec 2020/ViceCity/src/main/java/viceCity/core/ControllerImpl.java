package viceCity.core;

import viceCity.core.interfaces.Controller;
import viceCity.models.guns.Gun;
import viceCity.models.guns.Pistol;
import viceCity.models.guns.Rifle;
import viceCity.models.neighbourhood.GangNeighbourhood;
import viceCity.models.neighbourhood.Neighbourhood;
import viceCity.models.players.CivilPlayer;
import viceCity.models.players.MainPlayer;
import viceCity.models.players.Player;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;

import static viceCity.common.ConstantMessages.*;

public class ControllerImpl implements Controller {

    private Collection<Player> civilPlayers;
    private Queue<Gun> guns;
    private Neighbourhood neighbourhood;
    private Player tommy;

    public ControllerImpl() {
        civilPlayers = new ArrayList<>();
        guns = new ArrayDeque<>();
        neighbourhood = new GangNeighbourhood();
        tommy = new MainPlayer();
    }

    @Override
    public String addPlayer(String name) {
        Player player = new CivilPlayer(name);
        civilPlayers.add(player);
        return String.format(PLAYER_ADDED, name);
    }

    @Override
    public String addGun(String type, String name) {
        Gun gun;
        if (type.equals("Pistol")) {
            gun = new Pistol(name);
        } else if (type.equals("Rifle")) {
            gun = new Rifle(name);
        } else {
            return GUN_TYPE_INVALID;
        }
        guns.offer(gun);
        return String.format(GUN_ADDED, name, type);
    }

    @Override
    public String addGunToPlayer(String name) {

        if (this.guns.isEmpty()) {
            return GUN_QUEUE_IS_EMPTY;
        } else if (name.equals("Vercetti")) {
            Gun firstAddedGun = this.guns.poll();
            tommy.getGunRepository().add(firstAddedGun);
            return String.format(GUN_ADDED_TO_MAIN_PLAYER, firstAddedGun.getName(), tommy.getName());
        }
        for (Player civilPlayer : civilPlayers) {
            if (civilPlayer.getName().equals(name)) {
                Gun firstAddedGun = this.guns.poll();
                civilPlayer.getGunRepository().add(firstAddedGun);
                return String.format(GUN_ADDED_TO_CIVIL_PLAYER, firstAddedGun.getName(), civilPlayer.getName());
            }
        }
        return CIVIL_PLAYER_DOES_NOT_EXIST;
    }

    @Override
    public String fight() {
        int mainPlayerPoints = tommy.getLifePoints();
        long aliveCivilPlayers = this.civilPlayers.stream().filter(Player::isAlive).count();
        int civilPlayersLifePoints = this.civilPlayers.stream().mapToInt(Player::getLifePoints).sum();

        neighbourhood.action(tommy, civilPlayers);

        int mainPlayerPointsAfterFight = tommy.getLifePoints();
        long aliveCivilPlayersAfterFight = this.civilPlayers.stream().filter(Player::isAlive).count();
        int civilPlayersLifePointsAfterFight = this.civilPlayers.stream().mapToInt(Player::getLifePoints).sum();
        String message;
        if (mainPlayerPoints == mainPlayerPointsAfterFight &&
                aliveCivilPlayers == aliveCivilPlayersAfterFight &&
                civilPlayersLifePoints == civilPlayersLifePointsAfterFight) {
            message = FIGHT_HOT_HAPPENED;

        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(FIGHT_HAPPENED).append(System.lineSeparator());
            sb.append(String.format(MAIN_PLAYER_LIVE_POINTS_MESSAGE, mainPlayerPointsAfterFight));
            sb.append(System.lineSeparator());
            sb.append(String.format(MAIN_PLAYER_KILLED_CIVIL_PLAYERS_MESSAGE, aliveCivilPlayers - aliveCivilPlayersAfterFight));
            sb.append(System.lineSeparator());
            sb.append(String.format(CIVIL_PLAYERS_LEFT_MESSAGE, aliveCivilPlayersAfterFight));

            message = sb.toString().trim();
        }

        return message;
    }
}
