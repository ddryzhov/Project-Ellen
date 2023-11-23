package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

import java.util.Objects;


public class Energy extends AbstractActor implements Usable<Alive> {
    public Energy() {
        Animation energyAnim = new Animation("sprites/energy.png", 16, 16);
        setAnimation(energyAnim);
    }
    @Override
    public void useWith(Alive actor) {
        if (actor == null)
        {
            return;
        }
        if (actor.getHealth().getValue() == 100)
        {
            return;
        }
        if (actor.getHealth().getValue() < 100 && actor.getHealth().getValue() != 100)
        {
            actor.getHealth().restore();
            (Objects.requireNonNull(getScene())).removeActor(this);
        }
    }



    @Override
    public Class<Alive> getUsingActorClass() {
        return Alive.class;
    }
}
