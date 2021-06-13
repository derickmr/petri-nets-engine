package com.unisinos.modelsimulator.restaurante.events;

import com.unisinos.modelsimulator.*;

public class FinalizarAtendimentoCaixa extends Event {

    private Entity grupo;

    public FinalizarAtendimentoCaixa(String name) {
        super(name);
    }

    public FinalizarAtendimentoCaixa (String name, Resource caixa, Entity grupo, EntitySet filaCaixa, Scheduler scheduler) {
        super(name);
        this.grupo = grupo;
        this.resource = caixa;
        this.entitySet = filaCaixa;
        this.scheduler = scheduler;
    }

    @Override
    public void execute() {
        super.execute();
        getResource().release(1);

        if (grupo.getQuantity() == 1) {
            //TODO
            //Se for grupo de 1 cliente, vai para o Balcão; se não houver banco disponível, aguarda na FilaBalc.
            
        }

        else if (grupo.getQuantity() == 2) {
            //TODO
            //Se for de 2 a 4 clientes, vai para as mesas; grupo de 2 devem tentar sentar em mesas de 2 lugares.
            //Caso não hajam mesas disponíveis, o grupo aguarda em FilaMesas;
        }

        else {
            //TODO
            //Grupos de 3 ou 4 devem tentar mesas de 4 lugares;
            //Caso não hajam mesas disponíveis, o grupo aguarda em FilaMesas;
        }

        getResource().release(1);

        if (!getEntitySet().isEmpty()) {
            getScheduler().scheduleNow(getScheduler().createEvent(new AtendimentoCaixa("Atendimento Caixa", getResource(), getEntitySet().remove(), getEntitySet(), getScheduler())));
        }
    }
}
