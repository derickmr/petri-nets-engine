package com.unisinos.modelsimulator;

import com.unisinos.petri.PetriNet;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    private String name;
    private int id; // atribuído pelo Scheduler
    private double creationTime; // atribuído pelo Scheduler
    private double destructionTime; //atribuído pelo Scheduler
    private int priority; // sem prioridade: -1 (0: + alta e 255: + baixa)
    private PetriNet petriNet;
    private Scheduler scheduler;
    private List<EntitySet> sets;

    public Entity(String name, Scheduler scheduler) {
        this.name = name;
        this.scheduler = scheduler;
        sets = new ArrayList<>();
        creationTime = scheduler.getTime();
    }

    public Entity(String name, Scheduler scheduler, PetriNet petriNet) {
        this.name = name;
        this.petriNet = petriNet;
        this.scheduler = scheduler;
        sets = new ArrayList<>();
        creationTime = scheduler.getTime();
    }

    public List<EntitySet> getSets() { // retorna lista de EntitySets nas quais a entidade está inserida
        return sets;
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

    public double getCreationTime() {
        return creationTime;
    }

    public double getTimeSinceCreation() {
        return scheduler.getTime() - this.creationTime;
    }

    public void setCreationTime(double creationTime) {
        this.creationTime = creationTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public PetriNet getPetriNet() {
        return petriNet;
    }

    public void setPetriNet(PetriNet petriNet) {
        this.petriNet = petriNet;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void setSets(List<EntitySet> sets) {
        this.sets = sets;
    }

    public double getDestructionTime() {
        return destructionTime;
    }

    public void setDestructionTime(double destructionTime) {
        this.destructionTime = destructionTime;
    }
}
