package com.unisinos.modelsimulator;

import java.util.*;

import static com.unisinos.modelsimulator.EntitySetMode.*;
import java.util.ArrayList;
import java.util.Optional;

//FIXME adicionei alguns atributos a mais pra controlar as estatisticas do set (currentTime, lastLogTime e etc.). nao sei se sao necessarios mesmo
//Podem apagar se achar que nao faz sentido

public class EntitySet {

    private String name;
    private int id;
    private List<Entity> entities;
    private List<Entity> allEntities = new ArrayList<>();
    private EntitySetMode mode;
    private int maxPossibleSize;
    private double currentTime;
    private double lastLogTime;
    private double timeGap;
    private Map<Double, Integer> log = new LinkedHashMap<>();
    private boolean isLogging;
    private double entitySetCreationTime;
    private Map<Integer, Double> entitiesTimeInSet = new HashMap<>();
    private Map<Double, Integer> entitiesSizeInTime = new HashMap<>();
    private Scheduler scheduler;


    public EntitySet(String name, EntitySetMode mode, int maxPossibleSize) {
        this.name = name;
        this.maxPossibleSize = maxPossibleSize;
//        TODO implement other types of modes
        this.mode = NONE;
        this.entities = new ArrayList<>();
    }

    public EntitySet(String name, ArrayList<Entity> entities, int maxPossibleSize) {
        this.name = name;
        this.maxPossibleSize = maxPossibleSize;
        this.entities = entities;
        this.mode = NONE;
        this.entities = new ArrayList<>();
    }

    public EntitySet(String name, ArrayList<Entity> entities, int maxPossibleSize, Scheduler scheduler) {
        this.name = name;
        this.maxPossibleSize = maxPossibleSize;
        this.entities = entities;
        this.mode = NONE;
        this.entities = new ArrayList<>();
        this.scheduler = scheduler;
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

    public void setMode(EntitySetMode mode) {
        this.mode = mode;
    }

    public void insert(Entity entity) {
        entities.add(entity);
        allEntities.add(entity);
        entitiesTimeInSet.put(entity.getId(), currentTime);
        updateEntitiesSizeInTime(entity);
    }

    public void updateEntitiesSizeInTime(Entity entity) {
        var currentSizeInTime = this.entitiesSizeInTime.get(this.currentTime);
        if (currentSizeInTime != null) {
            this.entitiesSizeInTime.put(this.currentTime, this.entitiesSizeInTime.get(this.currentTime) + 1);
        } else {
            this.entitiesSizeInTime.put(this.currentTime, 1);
        }
    }

    public Entity getById(int id) {
        Optional<Entity> entityOptional = entities.stream().filter(entity -> entity.getId() == id).findFirst();
        if (entityOptional.isPresent()) {
            Entity entity = entityOptional.get();
            return entity;
        }
        return null;
    }

    public Entity remove() {
        if (this.entities.size() == 0) {
            return null;
        }
        switch(this.mode) {
            case FIFO:
                return this.entities.remove(0);
            case LIFO:
                return this.entities.remove(this.entities.size() - 1);
            case PRIORITY:
                return this.entities.remove(this.getIndexEntityMaxPriority());
            default:
                return this.entities.remove((int) (Math.random() * this.entities.size()));
        }
    }

    public int getIndexEntityMaxPriority() {
        var entityWithHighestPriority = this.entities
                .stream()
                .max((entity, t1) -> entity.getPriority() > t1.getPriority() ? 1 : -1);
        return this.entities.indexOf(entityWithHighestPriority);
    }

    public Entity removeById(int id) {
        Optional<Entity> entityOptional = entities
                .stream()
                .filter(entity -> entity.getId() == id).findFirst();
        if (entityOptional.isEmpty()){
            System.out.println("Entity with id " + id + " not found for removal.");
            return null;
        }
        Entity entity = entityOptional.get();
        entities.remove(entity);
        return entity;
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
    }

    public void logTime() {
        while (shouldLogTime()) {
            log.put(lastLogTime + timeGap, entities.size());
            lastLogTime += timeGap;
        }
    }

    private boolean shouldLogTime() {
        return isLogging && lastLogTime + timeGap <= scheduler.getTime();
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
}
