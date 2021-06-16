package com.unisinos.modelsimulator.restaurante.events;

import com.unisinos.modelsimulator.*;

public class FinalizarAtendimentoCaixa extends Event {

    private Entity grupo;

    public FinalizarAtendimentoCaixa (String name, Resource caixa, EntitySet filaCaixa, Scheduler scheduler) {
        super(name);
        this.resource = caixa;
        this.entitySet = filaCaixa;
        this.grupo = entitySet.remove();
        this.scheduler = scheduler;
    }

    @Override
    public void execute() {
        super.execute();
        resource.release(1);

        if (grupo.getQuantity() == 1) {
            //Se for grupo de 1 cliente, vai para o Balcão; se não houver banco disponível, aguarda na FilaBalc.
            scheduler.scheduleNow(scheduler.createEvent(new ChegadaNoBalcao("Chegada Balcão", grupo, scheduler)));
        }

        else if (grupo.getQuantity() == 2) {
            scheduler.scheduleNow(scheduler.createEvent(new ChegadaNaMesa2Lugares("Chegada 2 lugares", grupo, scheduler)));
            //Se for de 2 a 4 clientes, vai para as mesas; grupo de 2 devem tentar sentar em mesas de 2 lugares.
            //Caso não hajam mesas disponíveis, o grupo aguarda em FilaMesas;
        }

        else {
            scheduler.scheduleNow(scheduler.createEvent(new ChegadaNaMesa4Lugares("Chegada 4 lugares", grupo, scheduler)));
            //Grupos de 3 ou 4 devem tentar mesas de 4 lugares;
            //Caso não hajam mesas disponíveis, o grupo aguarda em FilaMesas;
        }

        scheduler.scheduleNow(scheduler.createEvent(new InicioPreparoRefeicao("Inicio Preparo Refeição", grupo, scheduler)));

        if (!getEntitySet().isEmpty()) {
            scheduler.scheduleNow(scheduler.createEvent(new AtendimentoCaixa("Atendimento " + resource.getName(), resource, entitySet, scheduler)));
        }
    }
}
