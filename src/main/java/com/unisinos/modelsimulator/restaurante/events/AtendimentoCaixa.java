package com.unisinos.modelsimulator.restaurante.events;

import com.unisinos.modelsimulator.*;
import org.apache.commons.math.MathException;

public class AtendimentoCaixa extends Event {

    public AtendimentoCaixa(String name, Resource caixa, EntitySet filaCaixa, Scheduler scheduler) {
        super(name);
        this.resource = caixa;
        this.entitySet = filaCaixa;
        this.scheduler = scheduler;
    }

    @Override
    public void execute() {
        super.execute();
        if (!entitySet.isEmpty()) {

            if (resource.allocate(1)) { //conseguiu alocar caixa pra atender
                //Agenda final do atendimento em normal (8,2) minutos
                try {
                    scheduler.scheduleIn(scheduler.createEvent(new FinalizarAtendimentoCaixa("Finalizar atendimento caixa", resource, entitySet, scheduler)), Scheduler.normal(8, 2));
                } catch (MathException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
