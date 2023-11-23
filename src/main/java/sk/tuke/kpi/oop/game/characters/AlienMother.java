package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;


public class AlienMother extends Alien {

    private Animation aliMotherAnim;
    public AlienMother(int healthValue,Behaviour<? super Alien> behaviour) {
        super(healthValue, behaviour);
        aliMotherAnim = new Animation("sprites/mother.png", 112, 162, 0.2f);
        setAnimation(aliMotherAnim);
    }
}
