package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.ActorContainer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Backpack implements ActorContainer<Collectible> {
    private final int capacity;
    private final String name;
    private final List<Collectible> inTheBag = new ArrayList<>();

    public Backpack(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }
    @Override
    public @NotNull List<Collectible> getContent() {
        return List.copyOf(inTheBag);
    }
    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public void add(@NotNull Collectible actor) {
        if (inTheBag.size() == capacity) {
            throw new IllegalStateException(getName() + " is full");
        }
        inTheBag.add(actor);
    }

    @Override
    public void remove(@NotNull Collectible actor) {
        inTheBag.remove(actor);
    }

    @Nullable
    @Override
    public Collectible peek() {
        if (inTheBag.isEmpty()) {
            return null;
        }
            return inTheBag.get(inTheBag.size() - 1);
    }

    @Override
    public void shift() {
        if (inTheBag.size() == 1 || inTheBag.isEmpty()) {
            return;
        }
        inTheBag.add(0, inTheBag.remove(inTheBag.size() - 1));
    }

    @Override
    public int getSize() {
        return inTheBag.size();
    }


    @NotNull
    @Override
    public Iterator<Collectible> iterator() {
        return inTheBag.iterator();
    }
}
