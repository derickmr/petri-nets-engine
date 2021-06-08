package com.unisinos.modelsimulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Resource {
    private String name;
    private int id; // atribuído pelo Scheduler
    private int quantity; // quantidade de recursos disponíveis
    private Scheduler scheduler;
    private double initialTime;
    private Map<Double, Integer> quantityOverTime;

    public Resource(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public Resource(String name, int quantity, Scheduler scheduler) {
        this.name = name;
        this.quantity = quantity;
        this.scheduler = scheduler;
        this.initialTime = scheduler.getTime();
        this.quantityOverTime = new HashMap<>();
        quantityOverTime.put(scheduler.getTime(), quantity);
    }

    public boolean allocate(int quantity) { // true se conseguiu alocar os recursos
        if (this.quantity < quantity) {
            return false;
        }
        this.quantity -= quantity;
        quantityOverTime.put(scheduler.getTime(), this.quantity);
        return true;
    }

    public void release(int quantity) { // deve ter um jeito melhor de fazer issae
        this.quantity += quantity;
        quantityOverTime.put(scheduler.getTime(), this.quantity);
    }

    // coleta de estatísticas

    public double allocationRate() { // percentual do tempo (em relação ao tempo total simulado) em que estes recursos foram alocados
//        double currentTime = scheduler.getTime();
//        return (currentTime - initialTime) / currentTime;
        return 0;
    }

    public double averageAllocation() { // quantidade média destes recursos que foram alocados (em relação ao tempo total simulado)
        return quantityOverTime.values().stream().mapToInt(integer -> integer).sum() / quantityOverTime.size();
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public double getInitialTime() {
        return initialTime;
    }

    public void setInitialTime(double initialTime) {
        this.initialTime = initialTime;
    }
}
