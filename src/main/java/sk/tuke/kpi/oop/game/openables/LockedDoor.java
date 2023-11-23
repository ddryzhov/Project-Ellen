package sk.tuke.kpi.oop.game.openables;

public class LockedDoor extends Door{
    private boolean isLocked;

    public void lock() {
        isLocked = true;
        this.close();
    }
    public void unlock() {
        isLocked = false;
        this.open();
    }
    public boolean isLocked() {
        return isLocked;
    }
}
