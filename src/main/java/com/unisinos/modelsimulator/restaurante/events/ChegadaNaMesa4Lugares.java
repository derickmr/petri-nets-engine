package com.unisinos.modelsimulator.restaurante.events;

import com.unisinos.modelsimulator.Entity;
import com.unisinos.modelsimulator.EntitySet;
import com.unisinos.modelsimulator.Event;
import com.unisinos.modelsimulator.Scheduler;

public class ChegadaNaMesa4Lugares extends Event {

  private Entity grupo;
  private EntitySet filaCozinha;
  private EntitySet esperandoM4;

  public ChegadaNaMesa4Lugares(String name, Entity grupo, Scheduler scheduler) {
    super(name, scheduler);
    this.grupo = grupo;
    this.resource = scheduler.getResourceByName("mesa4Lugares");
    this.entitySet = scheduler.getEntitySetByName("filaMesa4Lugares");
    this.filaCozinha = scheduler.getEntitySetByName("filaCozinha");
    this.esperandoM4 = scheduler.getEntitySetByName("esperandoM4");
  }

  @Override
  public void execute() {
    super.execute();

    if (!resource.allocate(1)) {
        entitySet.insert(grupo);
    } else {
      if (this.filaCozinha.getById(grupo.getId()) != null) { // se refeicao ja ta pronta. Se não, TerminoPreparoRefeicao irá agendar
        filaCozinha.removeById(grupo.getId());
        scheduler.scheduleNow(scheduler.createEvent(new InicioRefeicao("Inicio Refeição",grupo, scheduler)));
      } else {
        esperandoM4.insert(grupo);
      }
    }
  }
  
}
