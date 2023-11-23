package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Computer extends AbstractActor implements EnergyConsumer {
    private boolean isEnergy;

    public Computer(){
        Animation normalAnimation = new Animation("sprites/computer.png", 80, 48, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(normalAnimation); // cntrl + medzerik dlz png
    }
    public  int add(int a, int b){
        if(isEnergy) {
            return a + b;
        }
        else {
            return 0;
        }
    }
    public float add(float a, float b){
        if(isEnergy) {
            return a + b;
        }
        else {
            return 0;
        }
    }
    public int sub(int a, int b){
        if(isEnergy) {
            return a - b;
        }
        else {
            return 0;
        }
    }
    public float sub(float a, float b){
        if(isEnergy) {
            return a - b;
        }
        else {
            return 0;
        }
    }
    public void setPowered(boolean isEnergy) {
        this.isEnergy = isEnergy;
        if (isEnergy == false) {
            this.getAnimation().pause();
        }
        this.getAnimation().play();
    }
}


