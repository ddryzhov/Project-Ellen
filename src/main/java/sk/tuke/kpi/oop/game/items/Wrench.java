package sk.tuke.kpi.oop.game.items;


import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.DefectiveLight;


public class Wrench extends BreakableTool<DefectiveLight> implements Collectible{
    private int numUses;

    public Wrench() {
        super(2);
        Animation wrenchAnim = new Animation("sprites/wrench.png");
        setAnimation(wrenchAnim);
    }

    public int getNumUses() {
        return this.numUses;
    }

    @Override
    public void useWith(DefectiveLight actor)
    {
        if(actor == null){
            return;
        }
        if (actor.repair()) {
            super.useWith(actor);
        }
    }

    @Override
    public Class<DefectiveLight> getUsingActorClass() {
        return DefectiveLight.class;
    }
}

