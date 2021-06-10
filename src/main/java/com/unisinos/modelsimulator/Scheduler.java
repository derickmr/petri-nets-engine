package com.unisinos.modelsimulator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Scheduler {
    private double time;
    private List<Event> events;
    private List<Resource> resources;
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
        getEvents().forEach(Event::execute);
    }

    public void simulateOneStep() {
        //TODO lógica para executar a cada entrada do teclado
        getNextEvent().execute();
    }

    public void simulateBy(double duration) {
        //implement
    }

    public void simulateUntil(double absoluteTime) {
        //implement
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

        return 0; // retorna o id
    }

    public Resource getResource(int id) {
        //implement
        return new Resource("implement", 1);
    }

    public int createEvent(String name) {
        Event event = new Event(name);
        event.setEventId(currentId++);
        events.add(event);
        return event.getEventId(); //return event id of the newly created event
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
        //implement
        return 0;
    }

    public double exponential(double meanValue) {
        //implement
        return 0;
    }

    public double normal(double meanValue, double stdDeviationValue) {
        //implement
        return 0;
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
            if (event.getEventTime() >= time) {
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
