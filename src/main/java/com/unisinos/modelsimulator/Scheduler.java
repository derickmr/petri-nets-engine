package com.unisinos.modelsimulator;
import java.util.*;
import java.util.stream.Collectors;

import com.unisinos.modelsimulator.restaurante.entities.GrupoCliente;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.ExponentialDistributionImpl;
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

    public Scheduler() {
        time = 0;
        timeLimit = 0;
        timeLimitMode = false;
        events = new ArrayList<>();
        resources = new ArrayList<>();
        entitySets = new ArrayList<>();
        entities = new ArrayList<>();
        stepByStepExecutionMode = false;
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

    // controlando tempo de execução

    public void simulate() {
        while (getNextEvent() != null) {
            Event event = getNextEvent();
            System.out.println("Iniciando execução do evento " + event.getName());
            nextStep();
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

    public void nextStep() {
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

    public void collectLogs() {

        resources.forEach(
                Resource::allocationRate
        );

        entitySets.forEach(
                set -> {
                    System.out.println("\nSet: " + set.getName());
                    set.getLog().forEach((key, value) -> System.out.println("Time (in minutes): " + key/60 + "; Quantity: " + value));
                }
        );

        System.out.println("Tempo atual: " + time);

    }

    public void simulateUntil(double absoluteTime) {
        timeLimit = absoluteTime;
        timeLimitMode = true;
        while (canExecute() && time < absoluteTime) {
            executeEvent(getNextEvent());
        }
    }

    // criação destruição e acesso para componentes


    public void destroyEntity(int id) {
        Entity entity = getEntity(id);

        if (entity != null) {
            //TODO o que seria o destruir? entity = null? remover de todas as filas?
        }

    }

    public Entity getEntity(int id) {
        for (Event event : getEvents()) {
            for (Entity entity : event.getEntitySet().getEntities()) {
                if (entity.getId() == id){
                    return entity;
                }
            }
        }
        return null;
    }

    public Resource createResource(String name, int quantity) {
        Resource resource = new Resource(name, quantity, this);
        resource.setId(currentId++);
        resources.add(resource);

        System.out.println("Criando recurso com nome: " + name + " e id " + resource.getId());
        nextStep();

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
        System.out.println("\nCriando entitySet com nome " + name + ", id " + entitySet.getId() + " e tamanho " + maxPossibleSize);
        nextStep();
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

    public int getEntityTotalQuantity() {
        //TODO
        return 0;
    }

    public int getEntityTotalQuantity(String name) {
        return 0;
    }

    public double averageTimeInModel() {
        //TODO
        return 0;
    }

    public int maxEntitiesPresent() {
        //implement
        return 0;
    }

    public void setTime(double time) {
        this.time = time;
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

    public Entity createEntity(Entity entity) {
        entity.setCreationTime(time);
        entity.setScheduler(this);
        entity.setId(currentId++);
        entities.add(entity);
        System.out.println("\nCriando entidade com nome: " + entity.getName() + " e id " + entity.getId());
        nextStep();
        return entity;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }
}
