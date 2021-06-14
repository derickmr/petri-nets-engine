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
        if (!getEntitySet().isEmpty()) {

            if (getResource().allocate(1)) { //conseguiu alocar caixa pra atender
                //Agenda final do atendimento em normal (8,2) minutos
                try {
                    Entity grupo = entitySet.remove();
                    Scheduler scheduler = getScheduler();
                    scheduler.scheduleIn(scheduler.createEvent(new FinalizarAtendimentoCaixa("Finalizar atendimento caixa", resource, grupo, entitySet, scheduler)), Scheduler.normal(8, 2));
                } catch (MathException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
