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
  }

  @Override
  public void execute() {

    super.execute();

    if (getResource().allocate(1)){
      try {
        Scheduler scheduler = getScheduler();
        scheduler.scheduleIn(scheduler.createEvent(new TerminoPreparoRefeicao("Termino Preparo Refeicao", grupo, resource, scheduler)), Scheduler.normal(14, 5));
      } catch (MathException e) {
        e.printStackTrace();
      }
    }
  }
}
