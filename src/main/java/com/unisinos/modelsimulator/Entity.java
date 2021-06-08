package com.unisinos.modelsimulator;

import com.unisinos.petri.PetriNet;
import java.util.Date;

//ok
public class Entity {
    private String name;
    private int id; // atribuído pelo Scheduler
    private double creationTime; // atribuído pelo Scheduler
    private int priority; // sem prioridade: -1 (0: + alta e 255: + baixa)
    private PetriNet petriNet;

    public Entity(String name) {
        this.name = name;
    }

    public Entity(String name, PetriNet petriNet) {
        this.name = name;
        this.petriNet = petriNet;
    }

    public EntitySet getSets() { // retorna lista de EntitySets nas quais a entidade está inserida
        return null; // tem que implementar isso, mas não sei se faz sentido
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

    public double getCreationTime() {
        return creationTime;
    }

    public double getTimeSinceCreation() {
        return new Date().getTime() - this.creationTime;
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
}
