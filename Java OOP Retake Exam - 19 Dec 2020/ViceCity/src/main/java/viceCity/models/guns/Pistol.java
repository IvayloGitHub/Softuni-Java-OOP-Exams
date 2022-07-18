package viceCity.models.guns;

public class Pistol extends BaseGun {
    private static final int BULLETS_TO_SHOOT = 1;
    public Pistol(String name) {
        super(name, 10, 100);
    }

    @Override
    public int fire() {
        if (super.canFire()) {
            if(super.getBulletsPerBarrel() == 0) {
                super.setBulletsPerBarrel(10);
                super.setTotalBullets(getTotalBullets() - 10);
            }
            super.setBulletsPerBarrel(super.getBulletsPerBarrel() - BULLETS_TO_SHOOT);
        }
        return BULLETS_TO_SHOOT;
    }
}
