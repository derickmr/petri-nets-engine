package com.unisinos.modelsimulator;

//ok
public class Event {

    private String name;
    private int eventId; // atribuído pelo Scheduler
    private double eventTime; //atribuído pelo Scheduler
    protected Scheduler scheduler;
    protected EntitySet entitySet;
    protected Resource resource;
    public boolean executed = false;

    public Event(String name) {
        this.name = name;
    }

    public Event(String name, Scheduler scheduler) {
        this.name = name;
        this.scheduler = scheduler;
    }

    public Event(String name, Resource resource, Scheduler scheduler) {
        this.name = name;
        this.resource = resource;
        this.scheduler = scheduler;
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


    public EntitySet getEntitySet() {
        return entitySet;
    }

    public Resource getResource() {
        return resource;
    }
}
