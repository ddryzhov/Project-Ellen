package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;

import java.util.HashSet;
import java.util.Set;

public class Reactor extends AbstractActor implements Switchable, Repairable{
    private int temperature;
    private int damage;
    private final Animation normalAnimation;
    private final Animation reactorAnimation;
    private final Animation reactorAnimation3;
    private final Animation reactorCrush;
    private final Animation reactorOff;
    private final Animation exTheFire;
    private boolean isOn;
    private final Set<EnergyConsumer> devices;

    public Reactor(){
        devices = new HashSet<>();
        this.temperature = 0;
        this.damage = 0;
        this.normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.reactorCrush = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.reactorAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.5f, Animation.PlayMode.LOOP_PINGPONG);
        this.reactorAnimation3 = new Animation("sprites/reactor_hot.png", 80, 80, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        this.exTheFire = new Animation("sprites/reactor_extinguished.png", 80, 80);
        this.reactorOff = new Animation("sprites/reactor.png", 80, 80 );
        setAnimation(reactorOff);

    }
    public boolean isOn(){
        return isOn;
    }
    public int getTemperature() {
        return temperature;
    }
    public int getDamage() {
        return damage;
    }
    public void turnOn(){
        if(damage < 100){
            isOn = true;
            updateAnimation();
        }
        else{
            isOn = false;
            updateAnimation();
        }
    }
    public void turnOff(){
            isOn = false;
            setAnimation(reactorOff);
    }

    private void updateAnimation(){

        if(damage == 100 || temperature >= 6000){
            setAnimation(reactorCrush);
        }
        if(temperature >= 3320 && temperature <= 4640){
            setAnimation(reactorAnimation);
        }
        if(temperature > 4640 && temperature < 6000){
            setAnimation(reactorAnimation3);
        }
        if(temperature < 3320){
            setAnimation(normalAnimation);
        }
        updateAllDevicesTwo();
    }
    private void updateAllDevices(EnergyConsumer device){
        device.setPowered(isOn());
    }
    private void updateAllDevicesTwo(){
        this.devices.forEach(this::updateAllDevices);
    }


    public void increaseTemperature(int increment) {
       if(increment < 0){
            return;
       }
       if(isOn ) {
           if (damage > 66) {
               temperature = (increment * 2) + temperature;
               updateAnimation();
           }
           if (damage >= 33 && damage <= 66) {
               temperature = (int) ((increment * 1.5F) + temperature);
               updateAnimation();
           }
           if(damage < 33){
               temperature = temperature + increment;
               updateAnimation();
           }
           if (temperature > 2000) {
               this.damage = Math.round((100*(temperature - 2000)) / 4000);
           }
           if (damage >= 100) {
               damage = 100;
               isOn = false;
               updateAnimation();
           }
           updateAnimation();
       }

    }

    public void decreaseTemperature(int decrement){
        if(decrement < 0 || damage == 100){
            return;
        }
        if(isOn) {
            if (damage >= 50 && decrement > 0) {
                this.temperature = temperature - (decrement / 2);
            }
            if (damage < 50 && decrement > 0) {
                temperature = temperature - decrement;
            }
            updateAnimation();
        }
    }
    public boolean repair(){
        if (damage > 0 && damage < 100) {
            temperature = ((damage - 50) * 40) + 2000;
            if (damage > 50) {
                damage = damage - 50;
                updateAnimation();
            }
            if(damage <= 0){
                damage = 0;
                temperature = 0;
                setAnimation(reactorOff);
            }
            return true;
        }
        else {
            return false;
        }
    }
    public void addDevice(EnergyConsumer device){
        if(device == null){
            return;
        }
        this.devices.add(device);
        if(isOn() && damage == 0){
            device.setPowered(true);
        }
        device.setPowered(isOn());
    }
    public void removeDevice(EnergyConsumer device){
        if(device == null){
            return;
        }
        device.setPowered(false);
        this.devices.remove(device);
    }
    public boolean extinguish(){
        if (!isOn || damage == 0) {
            return false;
        }
        else {
            this.temperature = temperature - 4000;
            if(temperature < 0)
            {
                temperature = 0;
            }
            setAnimation(exTheFire);
            return  true;
        }
    }
    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new PerpetualReactorHeating(1).scheduleFor(this);
    }


}


