package com.unisinos.modelsimulator;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.NormalDistributionImpl;

public class Scheduler {

    private double time;
    private double timeLimit;
    private boolean timeLimitMode;
    private boolean stepByStepExecutionMode;
    private List<Event> events;
    private List<Resource> resources;
    private List<EntitySet> entitySets;
    private List<Entity> entities;
    private List<Entity> destroyedEntities;
    private int maxEntities;

    public Scheduler() {
        time = 0;
        timeLimit = 0;
        timeLimitMode = false;
        events = new ArrayList<>();
        resources = new ArrayList<>();
        entitySets = new ArrayList<>();
        entities = new ArrayList<>();
        destroyedEntities = new ArrayList<>();
        stepByStepExecutionMode = false;
        maxEntities = 0;
    }

    //Variável pra controlar os ids
    private int currentId = 1;

    public double getTime() { // retorna o tempo atual do modelo
        return time;
    }

    // disparo de eventos

    public void scheduleNow(Event event) {
        event.setEventTime(time);
    }

    public void scheduleIn(Event event, double timeToEvent) {
        event.setEventTime(time + timeToEvent);
    }

    public void scheduleAt(Event event, double absoluteTime) {
        event.setEventTime(absoluteTime);
    }

    public void waitFor(double time) {
        this.time += time;
    }

    //Próximo evento a ser executado
    public Event getNextEvent() {
        for (Event event : getEvents()) {
            if (event.getEventTime() >= time) {
                return event;
            }
        }
        return null;
    }

    //Eventos sempre ordenados pelo tempo a ser executado
    public List<Event> getEvents() {
        events.sort(Comparator.comparingDouble(Event::getEventTime));
        return events.stream().filter(e -> !e.executed).collect(Collectors.toList());
    }

    // controlando tempo de execução

    public void simulate() {
        while (getNextEvent() != null) {
            Event event = getNextEvent();
            logMessage("Iniciando execução do evento " + event.getName());
            checkStepByStepExecution();
            executeEvent(event);
        }
    }

    public void simulateOneStep() {
        executeEvent(getNextEvent());
    }

    public void simulateStepByStep() {
        stepByStepExecutionMode = true;
        simulate();
    }

    public void checkStepByStepExecution() {
        if (stepByStepExecutionMode) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Aperte \"ENTER\" para continuar...");
            scanner.nextLine();
        }
    }

    public void simulateBy(double duration) {
        timeLimitMode = true;
        double timeLimit = time + duration;
        this.timeLimit = timeLimit;

        while (canExecute() && time < timeLimit) {
            executeEvent(getNextEvent());
        }
    }

    public boolean canExecute() {
        return getEvents().size() > 0;
    }

    protected void executeEvent (Event event) {
        entitySets.forEach(EntitySet::logTime);
        if (timeLimitMode && timeLimit < event.getEventTime()) {
            time = timeLimit;
        }
        else {
            time = event.getEventTime();
            event.execute();
        }
    }

    public void simulateUntil(double absoluteTime) {
        timeLimit = absoluteTime;
        timeLimitMode = true;
        while (canExecute() && time < absoluteTime) {
            executeEvent(getNextEvent());
        }
    }

    public void setTime(double time) {
        this.time = time;
    }

    // criação destruição e acesso para componentes

    public Entity createEntity(Entity entity) {
        entity.setCreationTime(time);
        entity.setScheduler(this);
        entity.setId(currentId++);
        entities.add(entity);
        if (entities.size() > maxEntities) {
            maxEntities = entities.size();
        }
        logMessage("\nCriando entidade com nome: " + entity.getName() + " e id " + entity.getId());
        checkStepByStepExecution();
        return entity;
    }

    public void destroyEntity(int id) {
        Entity entity = getEntity(id);

        if (entity != null) {
            entitySets.forEach(
                    set -> set.removeById(id)
            );

            entity.setDestructionTime(time);
            entities.remove(entity);
            destroyedEntities.add(entity);
        }

    }

    public Entity getEntity(int id) {
        return entities.stream().filter(entity -> entity.getId() == id).findFirst().orElse(null);
    }

    public Resource createResource(String name, int quantity) {
        Resource resource = new Resource(name, quantity, this);
        resource.setId(currentId++);
        resources.add(resource);

        logMessage("Criando recurso com nome: " + name + " e id " + resource.getId());
        checkStepByStepExecution();

        return resource;
    }

    public Resource getResource(int id) {
        return resources.stream().filter(
                resource -> resource.getId() == id
        ).findFirst().orElse(null);
    }

    public Resource getResourceByName(String name) {
        return resources.stream().filter(
                resource -> Objects.equals(resource.getName(), name)
        ).findFirst().orElse(null);
    }

    public Event createEvent(Event event) {
        event.setEventId(currentId++);
        events.add(event);
        return event;
    }

    public Event getEvent(int eventId) {
        return events.stream()
                .filter(event -> event.getEventId() == eventId)
                .findFirst().orElse(null);
    }

    public EntitySet createEntitySet(String name, ArrayList<Entity> entities, int maxPossibleSize) {
        EntitySet entitySet = new EntitySet(name, entities, maxPossibleSize, this);
        entitySet.setId(currentId++);
        entitySets.add(entitySet);
        logMessage("\nCriando entitySet com nome " + name + ", id " + entitySet.getId() + " e tamanho " + maxPossibleSize);
        checkStepByStepExecution();
        return entitySet;
    }

    public EntitySet getEntitySetByName(String name) {
        return entitySets.stream().filter(
                entitySet -> Objects.equals(entitySet.getName(), name)
        ).findFirst().orElse(null);
    }

    public EntitySet getEntitySet(int id) {
        return entitySets.stream().filter(
                entitySet -> entitySet.getId() == id
        ).findFirst().orElse(null);
    }

    // random variates

    public static double uniform(double minValue, double maxValue) {
        double difference = maxValue - minValue;
        double res = minValue;
        res += Math.random() * difference;
        return 60 * res;
    }

    public static double exponential(double lambda) {
        Random rand = new Random();
        return 60 * Math.log(1-rand.nextDouble())/(-lambda);
    }

    public static double normal(double meanValue, double stdDeviationValue) throws MathException {
        NormalDistributionImpl normal = new NormalDistributionImpl(meanValue, stdDeviationValue);
        return 60 * normal.sample();
    }

    // coleta de estatística

    public void logMessage(String message) {
        if (stepByStepExecutionMode) {
            System.out.println(message);
        }
    }

    public void collectLogs() {

        resources.forEach(
                Resource::allocationRate
        );

        resources.forEach(
                Resource::averageAllocation
        );

        entitySets.forEach(
                set -> {
                    System.out.println("\nSet: " + set.getName());
                    set.getLog().forEach((key, value) -> System.out.println("Time (in minutes): " + key/60 + "; Quantity: " + value));
                    System.out.println("Average size: " + set.averageSize());
                    System.out.println("Average time in set: " + set.averageTimeInSet() / 60);
                    System.out.println("Max time in set: " + set.maxTimeInSet() / 60);
                }
        );

        System.out.println("\nAverage time in model: " + averageTimeInModel()/60);

        System.out.println("Tempo atual: " + time);

    }

    //Quantidade total de entidades que passaram pelo modelo
    public int getEntityTotalQuantity() {
        return entities.size() + destroyedEntities.size();
    }

    /**
     * retorna quantidade de entidades criadas cujo nome corresponde ao parâmetro, até o momento
     * @param name
     * @return
     */
    public int getEntityTotalQuantity(String name) {
        return (int) entities.stream().filter(
                entity -> entity.getName().equals(name)
        ).count();
    }

    /**
     * retorna o tempo médio que as entidades permanecem no modelo, desde sua criação até sua destruição
     * @return
     */
    public double averageTimeInModel() {
        double result = 0.0;

        if (!entities.isEmpty()) {
            result += entities.stream().mapToDouble(entity -> time - entity.getCreationTime()).sum() / entities.size();
        }
        if (!destroyedEntities.isEmpty()) {
            result += destroyedEntities.stream().mapToDouble(entity -> entity.getDestructionTime() - entity.getCreationTime()).sum() / destroyedEntities.size();
        }

        return result;
    }

    //Quantidade máxima de entidades ativas no modelo desde o inicio
    public int maxEntitiesPresent() {
        return maxEntities;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public int getCurrentId() {
        return currentId;
    }

    public void setCurrentId(int currentId) {
        this.currentId = currentId;
    }

    public List<EntitySet> getEntitySets() {
        return entitySets;
    }

    public void setEntitySets(List<EntitySet> entitySets) {
        this.entitySets = entitySets;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public boolean isStepByStepExecutionMode() {
        return stepByStepExecutionMode;
    }

    public void setStepByStepExecutionMode(boolean stepByStepExecutionMode) {
        this.stepByStepExecutionMode = stepByStepExecutionMode;
    }

}
