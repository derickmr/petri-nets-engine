package com.unisinos.modelsimulator.restaurante.entities;

import com.unisinos.modelsimulator.Entity;
import com.unisinos.modelsimulator.Scheduler;
import com.unisinos.petri.PetriNet;

public class GrupoCliente extends Entity {

    private int quantity;

    public GrupoCliente(String name, int quantity, Scheduler scheduler) {
        super(name, scheduler);
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
