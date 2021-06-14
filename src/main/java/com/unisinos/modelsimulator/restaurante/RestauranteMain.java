package com.unisinos.modelsimulator.restaurante;

import java.util.ArrayList;

import com.unisinos.modelsimulator.Event;
import com.unisinos.modelsimulator.Scheduler;
import com.unisinos.modelsimulator.restaurante.events.ChegadaGrupo;

public class RestauranteMain {

    public static void main (String[] args) {

        Scheduler scheduler = new Scheduler();

        scheduler.createEntitySet("filaCaixa1", new ArrayList<>(), 100);
        scheduler.createEntitySet("filaCaixa2", new ArrayList<>(), 100);
        scheduler.createResource("caixa1", 1);
        scheduler.createResource("caixa2", 1);
        Event chegadaGrupo = scheduler.createEvent(new ChegadaGrupo("Chegada grupo", scheduler));
        scheduler.scheduleNow(chegadaGrupo);

        scheduler.simulate();

    }

}
