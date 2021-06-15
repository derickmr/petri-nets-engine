package com.unisinos.modelsimulator.restaurante.events;

import com.unisinos.modelsimulator.Entity;
import com.unisinos.modelsimulator.Event;
import com.unisinos.modelsimulator.Resource;
import com.unisinos.modelsimulator.Scheduler;

public class TerminoPreparoRefeicao extends Event {

  private Entity grupo;

  public TerminoPreparoRefeicao(String name, Entity grupo, Resource resource, Scheduler scheduler) {
    super(name, resource, scheduler);
    this.grupo = grupo;
    this.resource = resource;
    this.entitySet = scheduler.getEntitySetByName("filaCozinha");
  }

  @Override
  public void execute() {
    super.execute();
      try {
        resource.release(1);
        Scheduler scheduler = getScheduler();
        scheduler.scheduleNow(new InicioRefeicao("Inicio Refeição",grupo, scheduler));

        if(!entitySet.isEmpty()) {
          scheduler.scheduleNow(new InicioPreparoRefeicao("Inicio Preparo Refeição", scheduler));
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
  }
}
