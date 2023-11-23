package sk.tuke.kpi.oop.game.weapons;

public abstract class Firearm {
    private int isAmmo;
    private int maxAmmo;

    protected abstract Fireable createBullet();
    public Firearm(int intAmmo, int maxAmmo) {
        this.isAmmo = intAmmo;
        this.maxAmmo = maxAmmo;
    }
    public Firearm(int intAmmo) {
        this.isAmmo = intAmmo;
        this.maxAmmo = intAmmo;
    }

    public int getAmmo() {
        return isAmmo;
    }
    public void reload(int newAmmo) {
        if (getAmmo() + newAmmo < maxAmmo) {
            isAmmo = isAmmo + newAmmo;
        }
        else {
            isAmmo = maxAmmo;
        }
    }
    public Fireable fire() {
        if (isAmmo != 0) {
            isAmmo = isAmmo - 1;
            return createBullet();
        }
        else {
            return null;
        }
    }




}
