package com.unisinos.modelsimulator;

import java.text.DecimalFormat;
import java.util.*;

public class Resource {

    private String name;
    private int id; // atribuído pelo Scheduler
    private int quantity; // quantidade de recursos disponíveis
    private Scheduler scheduler;
    private double totalAllocationTime;
    private LinkedHashMap<Double, Integer> allocatedResourcesOverTime;
    private Tuple<Double, Integer> lastAllocation;
    private int initialQuantity;

    public Resource(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public Resource(String name, int quantity, Scheduler scheduler) {
        this.name = name;
        this.quantity = quantity;
        this.initialQuantity = quantity;
        this.scheduler = scheduler;
        this.allocatedResourcesOverTime = new LinkedHashMap<>();
        allocatedResourcesOverTime.put(scheduler.getTime(), 0);
        lastAllocation = new Tuple<>(scheduler.getTime(), initialQuantity - this.quantity);
        this.totalAllocationTime = 0;
    }

    public boolean allocate(int quantity) { // true se conseguiu alocar os recursos
        if (this.quantity < quantity) {
            scheduler.logMessage("Não foi possível alocar " + quantity + " quantidades do recurso " + name + " pois não há a quantidade disponível.");
            scheduler.nextStep();
            return false;
        }
        scheduler.logMessage("Alocando " + quantity + " quantidades do recurso " + name);
        scheduler.nextStep();
        this.quantity -= quantity;
        saveAllocationStatistics();
        return true;
    }

    public void release(int quantity) {
        scheduler.logMessage("Liberando " + quantity + " quantidades do recurso " + name);
        scheduler.nextStep();
        this.quantity += quantity;
        saveAllocationStatistics();
    }

    private void saveAllocationStatistics() {
        allocatedResourcesOverTime.put(scheduler.getTime(), initialQuantity - this.quantity);
        if (lastAllocation.value != 0) {
            totalAllocationTime += scheduler.getTime() - lastAllocation.key;
        }
        lastAllocation = new Tuple<>(scheduler.getTime(), initialQuantity - this.quantity);
    }

    // coleta de estatísticas

    public double allocationRate() { // percentual do tempo (em relação ao tempo total simulado) em que estes recursos foram alocados

        //recursos nunca foram alocados
        if (allocatedResourcesOverTime.size() == 1) {
            System.out.println("Resource " + name + " allocation rate: 0.0%" );
            return 0.0;
        }

        saveAllocationStatistics();

        double result = totalAllocationTime/scheduler.getTime();
        System.out.println("Resource " + name + " allocation rate: " +  new DecimalFormat("#.##").format(result * 100) + "%");
        return result;
    }

    public double averageAllocation() { // quantidade média destes recursos que foram alocados (em relação ao tempo total simulado)
        double result = allocatedResourcesOverTime.values().stream().mapToInt(integer -> integer).sum() / scheduler.getTime();
        System.out.println("Resource " + name + " average allocation: " +  result);
        return result;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
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

    public LinkedHashMap<Double, Integer> getAllocatedResourcesOverTime() {
        return allocatedResourcesOverTime;
    }

    public void setAllocatedResourcesOverTime(LinkedHashMap<Double, Integer> allocatedResourcesOverTime) {
        this.allocatedResourcesOverTime = allocatedResourcesOverTime;
    }

    public Tuple<Double, Integer> getLastAllocation() {
        return lastAllocation;
    }

    public void setLastAllocation(Tuple<Double, Integer> lastAllocation) {
        this.lastAllocation = lastAllocation;
    }
}
