package com.unisinos.modelsimulator.restaurante.events;

import com.unisinos.modelsimulator.Entity;
import com.unisinos.modelsimulator.Event;
import com.unisinos.modelsimulator.Scheduler;
import org.apache.commons.math.MathException;

public class InicioRefeicao extends Event {
    private Entity grupo;

    public InicioRefeicao(String name, Entity grupo, Scheduler scheduler) {
        super(name, scheduler);
        this.grupo = grupo;
        this.entitySet = scheduler.getEntitySetByName("filaComidaPronta");

        SetResourceByQuantity(grupo);
    }

    @Override
    public void execute() {
        super.execute();


        Entity groupOrder = entitySet.removeById(grupo.getId());

        /* Se comida do grupo não está pronta retorna e aguarda a finalização da cozinha chamar esse evento  */
        if(groupOrder == null) {
            return;
        }

        try {
            Scheduler scheduler = getScheduler();
            scheduler.scheduleIn(scheduler.createEvent(new TerminoRefeicao("Termino Refeicao", grupo, resource, scheduler)), Scheduler.normal(20, 8));
        } catch (MathException e) {
            e.printStackTrace();
        }
    }

    private void SetResourceByQuantity(Entity grupo) {
        if (grupo.getQuantity() == 1) {
            this.resource = scheduler.getResourceByName("balcao");
        } else if (grupo.getQuantity() == 2) {
            this.resource = scheduler.getResourceByName("mesa2Lugares");
        } else {
            this.resource = scheduler.getResourceByName("mesa4Lugares");
        }
    }
}
