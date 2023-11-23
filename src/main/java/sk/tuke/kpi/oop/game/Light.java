package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;


public class Light extends AbstractActor implements EnergyConsumer, Switchable {
    private final Animation lightOn;
    private final Animation lightOff;

    private boolean isOn;

    private boolean powerIs;


    public Light() {
        this.lightOn = new Animation("sprites/light_on.png", 16, 16);
        this.lightOff = new Animation("sprites/light_off.png", 16, 16);
        setAnimation(lightOff);
        this.isOn = false;
        this.powerIs = false;
    }

    public void turnOn() {
        this.isOn = true;
        if (this.powerIs && isOn) {
            setAnimation(lightOn);
        } else {
            setAnimation(lightOff);
        }
    }


    public void turnOff() {
        this.isOn = false;
        if (this.powerIs && isOn) {
            setAnimation(lightOn);
        } else {
            setAnimation(lightOff);
        }
    }

    public boolean isOn() {
        return isOn;
    }

    public void toggle() {
        if(isOn){
            isOn = false;
        }
        else{
            isOn = true;
        }
        if (this.powerIs && isOn) {
            setAnimation(lightOn);
        } else {
            setAnimation(lightOff);
        }
    }

    public void setPowered(boolean isEnergy) {
        this.powerIs = isEnergy;
        if (this.powerIs && isOn) {
            setAnimation(lightOn);
        } else {
            setAnimation(lightOff);
        }
    }
}
