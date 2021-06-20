package com.unisinos.modelsimulator.restaurante.events;

import com.unisinos.modelsimulator.Entity;
import com.unisinos.modelsimulator.EntitySet;
import com.unisinos.modelsimulator.Event;
import com.unisinos.modelsimulator.Resource;
import com.unisinos.modelsimulator.Scheduler;
import com.unisinos.modelsimulator.restaurante.entities.GrupoCliente;

public class ChegadaGrupo extends Event {

    private Scheduler scheduler;
    private EntitySet filaCaixa1;
    private EntitySet filaCaixa2;
    private Resource caixa1;
    private Resource caixa2;

    private static final double THREE_HOURS_IN_SECONDS = 3 * 60 * 60;
    public ChegadaGrupo(String name, Scheduler scheduler) {
        super(name);
        this.scheduler = scheduler;
        this.filaCaixa1 = scheduler.getEntitySetByName("filaCaixa1");
        this.filaCaixa2 = scheduler.getEntitySetByName("filaCaixa2");
        this.caixa1 = scheduler.getResourceByName("caixa1");
        this.caixa2 = scheduler.getResourceByName("caixa2");
    }

    @Override
    public void execute() {
        super.execute();
        //Grupo pode ser de 1 a 4 pessoas (sorteio randomico).
        int quantity = (int) (Math.random() * 4) + 1;
        Entity grupo = scheduler.createEntity(new GrupoCliente("Grupo de " + quantity + " clientes", quantity, scheduler));

        //O grupo sempre escolhe a menor fila.
        if (filaCaixa1.getSize() < filaCaixa2.getSize()) {
            filaCaixa1.insert(grupo);
            scheduler.scheduleNow(scheduler.createEvent(new AtendimentoCaixa("Atendimento Caixa 1", caixa1, filaCaixa1, scheduler)));
        }
        else {
            filaCaixa2.insert(grupo);
            scheduler.scheduleNow(scheduler.createEvent(new AtendimentoCaixa("Atendimento Caixa 2", caixa2, filaCaixa2, scheduler)));

        }

        //Gerar grupos de clientes por 3 horas.
        if (scheduler.getTime() < THREE_HOURS_IN_SECONDS) {
            //A cada exponencial (3) minutos chega um grupo de clientes
            double eventTime = 0;
            eventTime = Scheduler.exponential(3);
            scheduler.scheduleIn(scheduler.createEvent(new ChegadaGrupo("Chegada grupo", scheduler)), eventTime);
        }

    }
}

