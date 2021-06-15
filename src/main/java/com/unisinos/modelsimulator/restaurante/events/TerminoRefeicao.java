package com.unisinos.modelsimulator.restaurante.events;

import com.unisinos.modelsimulator.Entity;
import com.unisinos.modelsimulator.Event;
import com.unisinos.modelsimulator.Resource;
import com.unisinos.modelsimulator.Scheduler;
import org.apache.commons.math.MathException;

public class TerminoRefeicao extends Event {
    private Entity grupo;


    public TerminoRefeicao(String name, Entity grupo, Resource resource, Scheduler scheduler) {
        super(name, resource, scheduler);
        this.grupo = grupo;
    }

    @Override
    public void execute() {
        super.execute();
        Scheduler scheduler = getScheduler();
        scheduler.scheduleNow(scheduler.createEvent(RemoveFromCorrectTable(scheduler)));

    }

    private Event RemoveFromCorrectTable(Scheduler scheduler) {
        Event eventValue = null;
        if (this.grupo.getQuantity() == 1) {
            eventValue = new SaidaDoBalcao("Saida Balcao", grupo, resource, scheduler);
        } else if (this.grupo.getQuantity() == 2) {
            eventValue = new SaidaDaMesa2Lugares("Saida Mesa 2 Lugares", grupo, resource, scheduler);
        } else {
            eventValue = new SaidaDaMesa4Lugares("Saida Mesa 4 Lugares", grupo, resource, scheduler);
        }
        return eventValue;
    }
}