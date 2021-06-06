package com.unisinos.modelsimulator;
import java.util.ArrayList;

public class EntitySet {
    private String name;
    private int id;
    private ArrayList<Entity> mode;
    private int size;
    private int maxPossibleSize;

    public EntitySet(String name, ArrayList mode, int maxPossibleSize) {
        this.name = name;
        this.mode = mode;
        this.maxPossibleSize = maxPossibleSize;
    }

    public ArrayList getMode() {
        return mode;
    }

    public void insert(Entity entity) {
        this.mode.add(entity);
    }

    public Entity remove() {
        return this.mode.remove(this.mode.size() - 1);
    }

    public Entity removeById(int id) {
        //implement
        return new Entity("implement something here");
    }

    public boolean isEmpty() {
        return this.mode.isEmpty();
    }

    public Entity findEntity(int id) {
        return this.mode.stream().filter(entity -> {
            return entity.getId() == id;
        }).findFirst().get();
    }

    public double averageSize() {
        // não sei se é isso pra falar a real
        return this.mode.size() / 2;
    }

    public int getSize() {
        return this.mode.size();
    }

    public int getMaxPossibleSize() {
        return this.maxPossibleSize;
    }

    public void setMaxPossibleSize(int maxPossibleSize) {
        this.maxPossibleSize = maxPossibleSize;
    }

    public double averageTimeInSet() {
        //implement
        return 0.0;
    }

    public double maxTimeInSet() {
        //implement
        return 0.0;
    }

    public void startLog(int timeGap) { // não sei se é int
        //implement
    }

// vvvvvvv tem que fazer isso daqui, fiquei em dúvida vvvvvvvvvvvvvvv
//    public ArrayList<Integer, Integer> getLog() {
//        return new ArrayList<Integer, Integer>(0,0);
//    }
}
