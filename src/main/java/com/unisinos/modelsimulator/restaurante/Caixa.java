package com.unisinos.modelsimulator.restaurante;

import com.unisinos.modelsimulator.EntitySet;
import com.unisinos.modelsimulator.Resource;
import com.unisinos.modelsimulator.Scheduler;

public class Caixa extends Resource {

    private EntitySet filaCaixa;

    public Caixa(String name, int quantity) {
        super(name, quantity);
    }

    public Caixa(String name, int quantity, Scheduler scheduler) {
        super(name, quantity, scheduler);
    }
}
