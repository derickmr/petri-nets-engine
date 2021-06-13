package com.unisinos.modelsimulator.restaurante;

import com.unisinos.modelsimulator.EntitySet;
import com.unisinos.modelsimulator.Event;
import com.unisinos.modelsimulator.Resource;
import com.unisinos.modelsimulator.Scheduler;
import com.unisinos.modelsimulator.restaurante.events.ChegadaGrupo;

import java.util.ArrayList;

public class RestauranteMain {

    public static void main (String[] args) {

        Scheduler scheduler = new Scheduler();

        EntitySet filaCaixa1 = scheduler.createEntitySet("Fila caixa 1", new ArrayList<>(), 100);
        EntitySet filaCaixa2 = scheduler.createEntitySet("Fila caixa 2", new ArrayList<>(), 100);
        Resource caixa1 = scheduler.createResource("Caixa 1", 1);
        Resource caixa2 = scheduler.createResource("Caixa 2", 1);
        Event chegadaGrupo = scheduler.createEvent(new ChegadaGrupo("Chegada grupo", filaCaixa1, filaCaixa2,
                caixa1, caixa2, scheduler));
        scheduler.scheduleNow(chegadaGrupo);

        scheduler.simulate();

    }

}
