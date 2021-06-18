package com.unisinos.modelsimulator.restaurante.entities;

import com.unisinos.modelsimulator.Entity;
import com.unisinos.petri.PetriNet;

public class GrupoCliente extends Entity {

    private int quantity;

    public GrupoCliente(String name) {
        super(name);
    }

    public GrupoCliente(String name, PetriNet petriNet) {
        super(name, petriNet);
    }

    public GrupoCliente(String name, int quantity) {
        super(name);
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
