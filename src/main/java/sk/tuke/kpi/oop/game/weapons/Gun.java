package sk.tuke.kpi.oop.game.weapons;

public class Gun extends Firearm {
    public Gun(int intAmmo, int maxAmmo) {
        super(intAmmo,maxAmmo);
    }
    @Override
    protected Fireable createBullet() {
        return new Bullet();
    }
}
