package com.unisinos.modelsimulator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.ExponentialDistributionImpl;
import org.apache.commons.math.distribution.NormalDistributionImpl;

public class Scheduler {
    private double time;
    private List<Event> events = new ArrayList<>();
    private List<Resource> resources = new ArrayList<>();
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
            executeEvent(getNextEvent());
        }
    }

    public void simulateOneStep() {
        //TODO lógica para executar a cada entrada do teclado
        executeEvent(getNextEvent());
    }

    public void simulateBy(double duration) {
        double timeLimit = time + duration;

        while (time <= timeLimit) {
            executeEvent(getNextEvent());
        }
    }

    protected void executeEvent (Event event) {
        time = event.getEventTime();
        event.execute();
    }

    public void simulateUntil(double absoluteTime) {
        while (time <= absoluteTime) {
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

    public int createResource(String name, int quantity) {
        Resource resource = new Resource(name, quantity, this);
        resource.setId(currentId++);

        return resource.getId(); // retorna o id
    }

    public Resource getResource(int id) {
        //implement
        return new Resource("implement", 1);
    }

    public Event createEvent(String name) {
        Event event = new Event(name);
        event.setEventId(currentId++);
        events.add(event);
        return event;
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

    public int createEntitySet(String name, ArrayList<Entity> entities, int maxPossibleSize) {
        EntitySet entitySet = new EntitySet(name, entities, maxPossibleSize);
        entitySet.setId(currentId++);
        return entitySet.getId();
    }

    public EntitySet getEntitySet(int id) {
        //implement
        return null;
    }

    // random variates

    public double uniform(double minValue, double maxValue) {
        double difference = maxValue - minValue;
        double res = minValue;
        res += Math.random() * difference;
        return res;
    }

    public double exponential(double meanValue) throws MathException {
        ExponentialDistributionImpl exponential = new ExponentialDistributionImpl(meanValue);
        return exponential.sample();
    }

    public double normal(double meanValue, double stdDeviationValue) throws MathException {
        NormalDistributionImpl normal = new NormalDistributionImpl(meanValue, stdDeviationValue);
        return normal.sample();
    }

    // coleta de estatística

    public int getEntityTotalQuantity() {
        //implement
        return 0;
    }

    public int getEntityTotalQuantity(String name) {
        return 0;
    }

    public double averageTimeInModel() {
        //implement
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
            if (event.getEventTime() > time) {
                return event;
            }
        }
        return null;
    }

    //Eventos sempre ordenados pelo tempo a ser executado
    public List<Event> getEvents() {
        events.sort(Comparator.comparingDouble(Event::getEventTime));
        return events;
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
}
