package com.unisinos.modelsimulator;

import java.util.*;

public class Resource {

    private String name;
    private int id; // atribuído pelo Scheduler
    private int quantity; // quantidade de recursos disponíveis
    private Scheduler scheduler;
    private double initialTime;
    private double totalAllocationTime;
    private LinkedHashMap<Double, Integer> quantityOverTime;
    private Tuple<Double, Integer> lastAllocation;

    public Resource(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public Resource(String name, int quantity, Scheduler scheduler) {
        this.name = name;
        this.quantity = quantity;
        this.scheduler = scheduler;
        this.initialTime = scheduler.getTime();
        this.quantityOverTime = new LinkedHashMap<>();
        quantityOverTime.put(scheduler.getTime(), this.quantity);
        lastAllocation = new Tuple<>(scheduler.getTime(), this.quantity);
        this.totalAllocationTime = 0;

    }

    public boolean allocate(int quantity) { // true se conseguiu alocar os recursos
        if (this.quantity < quantity) {
            return false;
        }
        this.quantity -= quantity;
        quantityOverTime.put(scheduler.getTime(), this.quantity);
        if (lastAllocation.value != 0) {
            totalAllocationTime += scheduler.getTime() - lastAllocation.key;
        }
        lastAllocation = new Tuple<>(scheduler.getTime(), quantity);
        return true;
    }

    public void release(int quantity) { // deve ter um jeito melhor de fazer issae
        this.quantity += quantity;
        quantityOverTime.put(scheduler.getTime(), this.quantity);
        if (lastAllocation.value != 0) {
            totalAllocationTime += scheduler.getTime() - lastAllocation.key;
        }
        lastAllocation = new Tuple<>(scheduler.getTime(), this.quantity);
    }

    // coleta de estatísticas

    public double allocationRate() { // percentual do tempo (em relação ao tempo total simulado) em que estes recursos foram alocados

        if (quantityOverTime.size() == 1) {
            int firstValue = quantityOverTime.entrySet().stream().findFirst().get().getValue();
            if (firstValue == 0) {
                return 0.0;
            }
            else {
                return 1.0;
            }
        }

        if (lastAllocation.value != 0) {
            return (totalAllocationTime + scheduler.getTime() - lastAllocation.key)/scheduler.getTime();
        }

        return totalAllocationTime/scheduler.getTime();
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

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalAllocationTime() {
        return totalAllocationTime;
    }

    public void setTotalAllocationTime(double totalAllocationTime) {
        this.totalAllocationTime = totalAllocationTime;
    }

    public LinkedHashMap<Double, Integer> getQuantityOverTime() {
        return quantityOverTime;
    }

    public void setQuantityOverTime(LinkedHashMap<Double, Integer> quantityOverTime) {
        this.quantityOverTime = quantityOverTime;
    }

    public Tuple<Double, Integer> getLastAllocation() {
        return lastAllocation;
    }

    public void setLastAllocation(Tuple<Double, Integer> lastAllocation) {
        this.lastAllocation = lastAllocation;
    }
}
