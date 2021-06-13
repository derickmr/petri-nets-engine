package com.unisinos.modelsimulator;

//ok
public class Event {

    private String name;
    private int eventId; // atribuído pelo Scheduler
    private double eventTime; //atribuído pelo Scheduler
    private Scheduler scheduler;
    private EntitySet entitySet;
    private Resource resource;
    public boolean executed = false;

    public Event(String name) {
        this.name = name;
    }

    public void execute() {
        this.executed = true;
    }

    public void createEntity(Entity entity) {
        entity.setCreationTime(scheduler.getTime());
        entity.setScheduler(scheduler);
        entity.setId(scheduler.getCurrentId());
        scheduler.setCurrentId(scheduler.getCurrentId() + 1);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public double getEventTime() {
        return eventTime;
    }

    public void setEventTime(double eventTime) {
        this.eventTime = eventTime;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public EntitySet getEntitySet() {
        return entitySet;
    }

    public void setEntitySet(EntitySet entitySet) {
        this.entitySet = entitySet;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
