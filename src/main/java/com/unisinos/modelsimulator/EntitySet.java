package com.unisinos.modelsimulator;

import java.util.*;

import static com.unisinos.modelsimulator.EntitySetMode.*;
import java.util.ArrayList;
import java.util.Optional;

public class EntitySet {

    private String name;
    private int id;
    private List<Entity> entities;
    private EntitySetMode mode;
    private int maxPossibleSize;
    private double lastLogTime;
    private double timeGap;
    private Map<Double, Integer> log = new LinkedHashMap<>();
    private boolean isLogging;
    private double entitySetCreationTime;
    private Map<Integer, Double> entitiesTimeInSet = new HashMap<>();
    private Map<Integer, Double> lastUpdateTime = new HashMap<>();
    private Map<Double, Integer> entitiesSizeInTime = new HashMap<>();
    private Scheduler scheduler;

    public EntitySet(String name, ArrayList<Entity> entities, int maxPossibleSize, Scheduler scheduler) {
        this.name = name;
        this.maxPossibleSize = maxPossibleSize;
        this.entities = entities;
        this.mode = NONE;
        this.entities = new ArrayList<>();
        this.scheduler = scheduler;
        this.entitySetCreationTime = scheduler.getTime();
    }

    /**
     * Atualiza o tempo de permanência no set da entidade especificada no parâmetro
     */
    public void updateEntitityTimeInSet(Entity entity) {
        entitiesTimeInSet.put(entity.getId(), entitiesTimeInSet.get(entity.getId()) + (scheduler.getTime() - lastUpdateTime.get(entity.getId())));
        lastUpdateTime.put(entity.getId(), scheduler.getTime());
    }

    public EntitySetMode getMode() {
        return mode;
    }

    public void setMode(EntitySetMode mode) {
        this.mode = mode;
    }

    public void insert(Entity entity) {
        scheduler.logMessage("\nInserindo entidade com id " + entity.getId() + " e nome " + entity.getName() + " na fila " + name);
        if (isFull()) {
            scheduler.logMessage("\nNão foi possível inserir entidade com id " + entity.getId() + " e nome " + entity.getName() + " na fila " + name + " pois a fila está cheia.");
        }
        else {
            entities.add(entity);
            List<EntitySet> entitySets = entity.getSets();
            entitySets.add(this);
            entity.setSets(entitySets);
            lastUpdateTime.put(entity.getId(), scheduler.getTime());
            entitiesTimeInSet.putIfAbsent(entity.getId(), 0.0);
            updateEntitiesSizeInTime();
        }
        scheduler.checkStepByStepExecution();
    }

    public void updateEntitiesSizeInTime() {
        entitiesSizeInTime.put(scheduler.getTime(), getSize());
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
        Entity removedEntity;
        switch(this.mode) {
            case FIFO:
                removedEntity = this.entities.remove(0);
                break;
            case LIFO:
                removedEntity = this.entities.remove(this.entities.size() - 1);
                break;
            case PRIORITY:
                removedEntity = this.entities.remove(this.getIndexEntityMaxPriority());
                break;
            default:
                removedEntity = this.entities.remove((int) (Math.random() * this.entities.size()));
                break;
        }

        scheduler.logMessage("\nRemovendo entidade com id " + removedEntity.getId() + " e nome " + removedEntity.getName() + " da fila " + name);
        scheduler.checkStepByStepExecution();
        List<EntitySet> entitySets = removedEntity.getSets();
        entitySets.remove(this);
        removedEntity.setSets(entitySets);
        updateEntitiesSizeInTime();
        updateEntitityTimeInSet(removedEntity);
        return removedEntity;
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
            scheduler.logMessage("Entidade com id " + id + " não encontrada para remoção.");
            scheduler.checkStepByStepExecution();
            return null;
        }
        Entity entity = entityOptional.get();
        scheduler.logMessage("\nRemovendo entidade com id " + entity.getId() + " e nome " + entity.getName() + " da fila " + name);
        scheduler.checkStepByStepExecution();
        entities.remove(entity);
        updateEntitiesSizeInTime();
        updateEntitityTimeInSet(entity);
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
        double sumEntitiesSizeInSet = entitiesSizeInTime.values().stream().mapToInt(v -> v).sum();
        return sumEntitiesSizeInSet / entitiesSizeInTime.size();
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

    public Map<Double, Integer> getLog() {
        return log;
    }

    public int getSize() {
        return this.entities.size();
    }

    public double getSetTotalTime() {
        return scheduler.getTime() - entitySetCreationTime;
    }

    //Média de tempo que as entidades ficam no set
    public double averageTimeInSet() {
        if (!entities.isEmpty()) {
            entities.forEach(this::updateEntitityTimeInSet);
        }
        
        double sumEntitiesTimeInSet = entitiesTimeInSet.values().stream().mapToDouble(v -> v).sum();
        return sumEntitiesTimeInSet / entitiesTimeInSet.size();
    }

    //Máximo de tempo que as entidades ficaram no set
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

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

}
