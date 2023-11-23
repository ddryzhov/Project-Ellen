package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Reactor;

public class FireExtinguisher extends BreakableTool<Reactor> implements Collectible {

    private final int fireCount;

    public FireExtinguisher(){
        super(1);
        Animation fireOn = new Animation("sprites/extinguisher.png");
        setAnimation(fireOn);
        this.fireCount = 1;
    }
    public int getFireCount()
    {
        return this.fireCount;
    }
    @Override
    public void useWith(Reactor reactor)
    {
        if(reactor == null){
            return;
        }
        if (reactor.extinguish()) {
            super.useWith(reactor);
        }
    }

    @Override
    public Class<Reactor> getUsingActorClass() {
        return Reactor.class;
    }

}
