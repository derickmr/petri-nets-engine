package com.unisinos.modelsimulator;

import java.util.*;

import static com.unisinos.modelsimulator.EntitySetMode.*;

//FIXME adicionei alguns atributos a mais pra controlar as estatisticas do set (currentTime, lastLogTime e etc.). nao sei se sao necessarios mesmo
//Podem apagar se achar que nao faz sentido

public class EntitySet {

    private String name;
    private int id;
    private List<Entity> entities;
    private List<Entity> allEntities;
    private EntitySetMode mode;
    private int size;
    private int maxPossibleSize;
    private double currentTime;
    private double lastLogTime;
    private double timeGap;
    private Map<Double, Integer> log;
    private boolean isLogging;
    private double entitySetCreationTime;
    private Map<Integer, Double> entitiesTimeInSet;


    public EntitySet(String name, EntitySetMode mode, int maxPossibleSize) {
        this.name = name;
        this.maxPossibleSize = maxPossibleSize;
//        TODO implement other types of modes
        this.mode = NONE;
        this.entities = new ArrayList<>();
    }

    public EntitySet(String name, EntitySetMode mode, int maxPossibleSize, double currentTime) {
        this.name = name;
        this.maxPossibleSize = maxPossibleSize;
//        TODO implement other types of modes
        this.mode = NONE;
        this.entities = new ArrayList<>();
        this.currentTime = currentTime;
        this.entitySetCreationTime = currentTime;
    }

    /**
     * Atualiza o tempo de permanência no set das entities que ainda estão associadas a esse set
     */
    public void updateEntitiesTimeInSet() {
        entitiesTimeInSet.entrySet().stream().filter(
                entry -> getById(entry.getKey()) != null
        ).forEach(
                entry -> entitiesTimeInSet.put(entry.getKey(), getSetTotalTime())
        );
    }

    public EntitySetMode getMode() {
        return mode;
    }

    public void insert(Entity entity) {
        entities.add(entity);
        allEntities.add(entity);
        entitiesTimeInSet.put(entity.getId(), currentTime);
    }

    public Entity remove() {
        return entities.remove(entities.size() - 1);
    }

    public Entity getById(int id) {
        Optional<Entity> entityOptional = entities.stream().filter(entity -> entity.getId() == id).findFirst();
        if (entityOptional.isPresent()) {
            Entity entity = entityOptional.get();
            return entity;
        }
        return null;
    }

    public Entity removeById(int id) {
        Optional<Entity> entityOptional = entities.stream().filter(entity -> entity.getId() == id).findFirst();
        if (entityOptional.isPresent()) {
            Entity entity = entityOptional.get();
            entities.remove(entity);
            return entity;
        }
        System.out.println("Entity with id " + id + " not found for removal.");
        return null;
    }

    public boolean isEmpty() {
        return entities.isEmpty();
    }

    public boolean isFull() {
        return entities.size() >= maxPossibleSize;
    }

    public Entity findEntity(int id) {
        return this.entities.stream()
                .filter(entity -> entity.getId() == id)
                .findFirst().orElse(null);
    }

    public double averageSize() {
//        TODO talvez criar um Map<Integer, Double> que indique o entities.size() no tempo x? Parecido com entitiesTimeInSet
        return 0;
    }

    public Map<Double, Integer> getLog() {
        return log;
    }

    public int getSize() {
        return this.entities.size();
    }

    public int getMaxPossibleSize() {
        return this.maxPossibleSize;
    }

    public void setMaxPossibleSize(int maxPossibleSize) {
        this.maxPossibleSize = maxPossibleSize;
    }

    public double getSetTotalTime() {
        return currentTime - entitySetCreationTime;
    }

    public double averageTimeInSet() {
        double sumEntitiesTimeInSet = entitiesTimeInSet.values().stream().mapToDouble(v -> v).sum();
        return sumEntitiesTimeInSet / getSetTotalTime();
    }

    public double maxTimeInSet() {
        return entitiesTimeInSet.values().stream().mapToDouble(v -> v).max().getAsDouble();
    }

    public void startLog(double timeGap) {
        isLogging = true;
        this.timeGap = timeGap;
    }

    public void stopLog() {
        isLogging = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setMode(EntitySetMode mode) {
        this.mode = mode;
    }

    private void logTime() {
        if (shouldLogTime()) {
            log.put(currentTime, entities.size());
            lastLogTime += timeGap;
        }
    }

    private boolean shouldLogTime() {
        return isLogging && lastLogTime + timeGap <= currentTime;
    }

}
