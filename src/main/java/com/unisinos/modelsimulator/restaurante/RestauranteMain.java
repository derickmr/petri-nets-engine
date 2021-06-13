package com.unisinos.modelsimulator.restaurante;

import com.unisinos.modelsimulator.Event;
import com.unisinos.modelsimulator.Scheduler;

import java.util.ArrayList;

public class RestauranteMain {

    public static void main (String[] args) {

        Scheduler scheduler = new Scheduler();

        int filaCaixa1Id = scheduler.createEntitySet("Fila caixa 1", new ArrayList<>(), 100);
        int filaCaixa2Id = scheduler.createEntitySet("Fila caixa 2", new ArrayList<>(), 100);
        int caixa1Id = scheduler.createResource("Caixa 1", 1);
        int caixa2Id = scheduler.createResource("Caixa 2", 1);
        Event chegadaGrupo = scheduler.createEvent(new ChegadaGrupo("Chegada grupo", scheduler.getEntitySet(filaCaixa1Id), scheduler.getEntitySet(filaCaixa2Id),
                scheduler.getResource(caixa1Id), scheduler.getResource(caixa2Id), scheduler));
        scheduler.scheduleNow(chegadaGrupo);

        scheduler.simulate();

    }

}
