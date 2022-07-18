package viceCity.models.guns;

public class Rifle extends BaseGun {

    private static final int BULLETS_TO_SHOOT = 5;
    public Rifle(String name) {
        super(name, 50, 500);
    }

    @Override
    public int fire() {
        if (super.canFire()) {
            if(super.getBulletsPerBarrel() == 0) {
                super.setBulletsPerBarrel(50);
                super.setTotalBullets(super.getTotalBullets() - 50);
            }
            super.setBulletsPerBarrel(super.getBulletsPerBarrel() - BULLETS_TO_SHOOT);
        }
        return BULLETS_TO_SHOOT;
    }
}
