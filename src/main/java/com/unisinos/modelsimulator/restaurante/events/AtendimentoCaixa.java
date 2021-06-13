package com.unisinos.modelsimulator.restaurante.events;

import com.unisinos.modelsimulator.*;
import org.apache.commons.math.MathException;

public class AtendimentoCaixa extends Event {

    private Entity grupo;

    public AtendimentoCaixa(String name) {
        super(name);
    }

    public AtendimentoCaixa(String name, Resource caixa, Entity grupo, EntitySet filaCaixa, Scheduler scheduler) {
        super(name);
        this.grupo = grupo;

        setResource(caixa);
        setEntitySet(filaCaixa);
        setScheduler(scheduler);
    }

    @Override
    public void execute() {
        super.execute();
        if (grupo != null || !getEntitySet().isEmpty()) {

            if (getResource().allocate(1)) { //conseguiu alocar caixa pra atender
                //Agenda final do atendimento em normal (8,2) minutos
                try {
                    getScheduler().scheduleIn(getScheduler().createEvent(new FinalizarAtendimentoCaixa("Finalizar atendimento caixa", getResource(), grupo, getEntitySet(), getScheduler())), getScheduler().normal(8, 2));
                } catch (MathException e) {
                    e.printStackTrace();
                }
            }

            else {  //caixa não disponível, coloca grupo na fila
                getEntitySet().insert(grupo);
            }

        }
    }
}
