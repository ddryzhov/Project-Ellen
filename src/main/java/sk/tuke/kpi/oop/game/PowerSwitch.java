package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Color;



public class PowerSwitch extends AbstractActor {
    private Switchable switchable;
    //private Reactor reactor;

    public PowerSwitch(Switchable switchable){
        Animation switches = new Animation("sprites/switch.png", 16, 16);
        setAnimation(switches);
        this.switchable = switchable;
    }

    public Switchable getDevice(){
        return this.switchable;
    }

    public void switchOn() {
        if(switchable == null){
            return;
        }
        if(switchable != null) {
            this.switchable.turnOn();
            getAnimation().setTint(Color.WHITE);
        }
    }
    public void switchOff() {
        if(switchable == null){
            return;
        }
        if(switchable != null) {
            this.switchable.turnOff();
            getAnimation().setTint(Color.GRAY);
        }
    }
}
