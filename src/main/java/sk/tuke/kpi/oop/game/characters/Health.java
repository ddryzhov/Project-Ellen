package sk.tuke.kpi.oop.game.characters;


import java.util.ArrayList;
import java.util.List;

public class Health {
    private int currentHealth;
    private int maxHealth;
    private List<ExhaustionEffect> fire;


    public Health(int intHealth, int maxHealth){
        this.currentHealth = intHealth;
        this.maxHealth = maxHealth;
        fire = new ArrayList<>();
    }
    public Health(int intHealth) {
        this.currentHealth = intHealth;
        maxHealth = this.currentHealth;
    }
    public void drain (int amount) {
        if (currentHealth != 0)
        {
            if (currentHealth > amount)
            {
                currentHealth = currentHealth - amount;
            }
            else
            {
                exhaust();
            }
        }
    }

    public int getValue() {
        return this.currentHealth;
    }
    public void refill (int amount) {
        if (currentHealth + amount <= maxHealth)
        {
            currentHealth = currentHealth + amount;
        }
        else
        {
            restore();
        }
    }
    public void restore() {
        currentHealth = maxHealth;
    }
    public void exhaust() {
        if (currentHealth != 0)
        {
            currentHealth = 0;
            if (fire != null)
            {
                fire.forEach(ExhaustionEffect::apply);
            }
        }
    }
    @FunctionalInterface
    public interface ExhaustionEffect {
        void apply();
    }
    public void onExhaustion(ExhaustionEffect effect)
    {
        if (fire != null)
        {
            fire.add(effect);
        }
    }
}
