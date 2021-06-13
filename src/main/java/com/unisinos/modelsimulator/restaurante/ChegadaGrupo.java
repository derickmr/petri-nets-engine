package com.unisinos.modelsimulator.restaurante;

import com.unisinos.modelsimulator.*;
import org.apache.commons.math.MathException;

public class ChegadaGrupo extends Event {

    private Scheduler scheduler;
    private EntitySet filaCaixa1;
    private EntitySet filaCaixa2;
    private Resource caixa1;
    private Resource caixa2;

    private static final double THREE_HOURS_IN_SECONDS = 10800;
    private static final double THREE_MINUTES_IN_SECONDS = 180;

    public ChegadaGrupo(String name, EntitySet filaCaixa1, EntitySet filaCaixa2, Resource caixa1, Resource caixa2, Scheduler scheduler) {
        super(name);
        this.scheduler = scheduler;
        this.filaCaixa1 = filaCaixa1;
        this.filaCaixa2 = filaCaixa2;
        this.caixa1 = caixa1;
        this.caixa2 = caixa2;
    }

    @Override
    public void execute() {

        //Grupo pode ser de 1 a 4 pessoas (sorteio randomico).
        int quantity = (int) (Math.random() * 4) + 1;
        Entity grupo = new GrupoCliente("Grupo de " + quantity + " clientes");
        grupo.setQuantity(quantity);

        //O grupo sempre escolhe a menor fila.
        if (filaCaixa1.getSize() < filaCaixa2.getSize()) {
            scheduler.scheduleNow(scheduler.createEvent(new AtendimentoCaixa("Atendimento Caixa 1", caixa1, grupo, filaCaixa1, scheduler)));
        }
        else {
            scheduler.scheduleNow(scheduler.createEvent(new AtendimentoCaixa("Atendimento Caixa 2", caixa2, grupo, filaCaixa2, scheduler)));

        }

        //Gerar grupos de clientes por 3 horas.
        //TODO verificar se a geração de tempo está correta.
        if (scheduler.getTime() < THREE_HOURS_IN_SECONDS) {
            //A cada exponencial (3) minutos chega um grupo de clientes
            double eventTime = 0;
            try {
                eventTime = scheduler.exponential(THREE_MINUTES_IN_SECONDS);
            } catch (MathException e) {
                e.printStackTrace();
            }
            scheduler.scheduleIn(scheduler.createEvent(new ChegadaGrupo("Chegada grupo", filaCaixa1, filaCaixa2, caixa1, caixa2, scheduler)), eventTime);
        }

    }
}

