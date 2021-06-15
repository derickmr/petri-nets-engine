package com.unisinos.modelsimulator.restaurante.events;

import com.unisinos.modelsimulator.Entity;
import com.unisinos.modelsimulator.Event;
import com.unisinos.modelsimulator.Scheduler;
import org.apache.commons.math.MathException;

public class InicioPreparoRefeicao extends Event {

  private Entity grupo;

  public InicioPreparoRefeicao(String name, Entity grupo, Scheduler scheduler) {
    super(name, scheduler);
    this.grupo = grupo;
    this.resource = scheduler.getResourceByName("cozinha");
    this.entitySet = scheduler.getEntitySetByName("filaCozinha");
  }

  public InicioPreparoRefeicao(String name, Scheduler scheduler) {
    super(name, scheduler);
    this.resource = scheduler.getResourceByName("cozinha");
    this.entitySet = scheduler.getEntitySetByName("filaCozinha");
  }

  @Override
  public void execute() {

    super.execute();
    if (grupo != null) {
      entitySet.insert(grupo);
    }

    if (getResource().allocate(1)){
      Entity orderGroup = entitySet.remove();
      try {
        Scheduler scheduler = getScheduler();
        scheduler.scheduleIn(scheduler.createEvent(new TerminoPreparoRefeicao("Termino Preparo Refeicao", orderGroup, resource, scheduler)), Scheduler.normal(14, 5));
      } catch (MathException e) {
        e.printStackTrace();
      }
    }
  }
}
