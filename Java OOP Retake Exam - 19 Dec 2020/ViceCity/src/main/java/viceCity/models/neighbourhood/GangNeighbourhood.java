package viceCity.models.neighbourhood;

import viceCity.models.guns.Gun;
import viceCity.models.players.Player;

import java.util.Collection;

public class GangNeighbourhood implements Neighbourhood {
    @Override
    public void action(Player mainPlayer, Collection<Player> civilPlayers) {
      for(Gun currentGun : mainPlayer.getGunRepository().getModels()) {
          for (Player civilPlayer : civilPlayers) {
              while (civilPlayer.isAlive() && currentGun.canFire()) {
                  civilPlayer.takeLifePoints(currentGun.fire());
              }
              if (!currentGun.canFire()) {
                  break;
              }
          }
      }

        for (Player civilPlayer : civilPlayers) {
            if (!civilPlayer.isAlive()) {
                continue;
            }
            for (Gun currentGun: civilPlayer.getGunRepository().getModels()) {
                while (mainPlayer.isAlive() && currentGun.canFire()) {
                    mainPlayer.takeLifePoints(currentGun.fire());
                }
                if (!mainPlayer.isAlive()) {
                    break;
                }
            }
            if (!mainPlayer.isAlive()) {
                break;
            }
        }
    }
}
