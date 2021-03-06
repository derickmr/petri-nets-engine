package com.unisinos.modelsimulator.restaurante.events;

import com.unisinos.modelsimulator.Entity;
import com.unisinos.modelsimulator.Event;
import com.unisinos.modelsimulator.Scheduler;
import com.unisinos.modelsimulator.restaurante.entities.GrupoCliente;
import org.apache.commons.math.MathException;

public class InicioPreparoRefeicao extends Event {

  private GrupoCliente grupo;

  public InicioPreparoRefeicao(String name, GrupoCliente grupo, Scheduler scheduler) {
    super(name, scheduler);
    this.grupo = grupo;
    this.resource = scheduler.getResourceByName("cozinha");
    this.entitySet = scheduler.getEntitySetByName("filaCozinha");
  }

  @Override
  public void execute() {

    super.execute();
    if (resource.allocate(1)){

      try {
        Scheduler scheduler = getScheduler();
        scheduler.scheduleIn(scheduler.createEvent(new TerminoPreparoRefeicao("Termino Preparo Refeicao", grupo, resource, scheduler)), Scheduler.normal(14, 5));
      } catch (MathException e) {
        e.printStackTrace();
      }
    } else {
      entitySet.insert(grupo);
    }
  }
}
