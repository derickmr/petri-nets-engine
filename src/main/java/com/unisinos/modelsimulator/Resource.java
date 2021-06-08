package com.unisinos.modelsimulator;
public class Resource {
    private String name;
    private int id; // atribuído pelo Scheduler
    private int quantity; // quantidade de recursos disponíveis

    public Resource(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public boolean allocate(int quantity) { // true se conseguiu alocar os recursos
        if (this.quantity < quantity) {
            return false;
        }
        this.quantity -= quantity;
        return true;
    }

    public void release(int quantity) { // deve ter um jeito melhor de fazer issae
        this.quantity += quantity;
    }

    // coleta de estatísticas

    public double allocationRate() { // percentual do tempo (em relação ao tempo total simulado) em que estes recursos foram alocados
        //implement
        return 0;
    }

    public double averageAllocation() { // quantidade média destes recursos que foram alocados (em relação ao tempo total simulado)
        //implement
        return 0;
    }
}
