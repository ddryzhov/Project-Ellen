package sk.tuke.kpi.oop.game.items;


import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

public class AccessCard extends AbstractActor implements Collectible, Usable<LockedDoor> {

    public AccessCard() {
        Animation key = new Animation("sprites/key.png", 16, 16);
        setAnimation(key);
    }

    @Override
    public void useWith(LockedDoor actor) {
        if(actor.isLocked())
        {
            actor.unlock();
        }
        else
        {
            actor.lock();
        }
    }

    @Override
    public Class<LockedDoor> getUsingActorClass() {
        return LockedDoor.class;
    }
}
