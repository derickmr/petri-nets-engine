package com.unisinos.modelsimulator.restaurante.entitySets;

import com.unisinos.modelsimulator.Entity;
import com.unisinos.modelsimulator.EntitySet;
import com.unisinos.modelsimulator.EntitySetMode;

import java.util.ArrayList;

public class FilaCaixa extends EntitySet {

    public FilaCaixa(String name, EntitySetMode mode, int maxPossibleSize) {
        super(name, mode, maxPossibleSize);
    }

    public FilaCaixa(String name, ArrayList<Entity> entities, int maxPossibleSize) {
        super(name, entities, maxPossibleSize);
    }

    public FilaCaixa(String name, EntitySetMode mode, int maxPossibleSize, double currentTime) {
        super(name, mode, maxPossibleSize, currentTime);
    }

}
