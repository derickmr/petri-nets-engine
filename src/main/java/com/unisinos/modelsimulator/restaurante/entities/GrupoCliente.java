package com.unisinos.modelsimulator.restaurante.entities;

import com.unisinos.modelsimulator.Entity;
import com.unisinos.petri.PetriNet;

public class GrupoCliente extends Entity {

    public GrupoCliente(String name) {
        super(name);
    }

    public GrupoCliente(String name, PetriNet petriNet) {
        super(name, petriNet);
    }
}
