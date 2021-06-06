package com.unisinos.modelsimulator;
public class Resource {
    private String name;
    private int id;
    private int quantity;

    public Resource(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public boolean allocate(int quantity) {
        //tem que ter lógica aqui, por hora vou deixar assim
        this.quantity += quantity;
        return true;
    }

    public void release(int quantity) { // deve ter um jeito melhor de fazer issae
        if (this.quantity >= quantity) {
            this.quantity -= quantity;
        } else {
            this.quantity = 0;
        }
    }

    public double allocationRate() {
        //implement
        return 0;
    }
}
