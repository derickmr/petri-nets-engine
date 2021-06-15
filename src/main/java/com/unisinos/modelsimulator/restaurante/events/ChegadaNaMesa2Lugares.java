package com.unisinos.modelsimulator.restaurante.events;

import com.unisinos.modelsimulator.Entity;
import com.unisinos.modelsimulator.EntitySet;
import com.unisinos.modelsimulator.Event;
import com.unisinos.modelsimulator.Scheduler;

public class ChegadaNaMesa2Lugares extends Event {

  private Entity grupo;
  private EntitySet filaCozinha;
  private EntitySet esperandoM2;

  public ChegadaNaMesa2Lugares(String name, Entity grupo, Scheduler scheduler) {
    super(name, scheduler);
    this.grupo = grupo;
    this.resource = scheduler.getResourceByName("mesa2Lugares");
    this.entitySet = scheduler.getEntitySetByName("filaMesa2Lugares");
    this.filaCozinha = scheduler.getEntitySetByName("filaCozinha");
    this.esperandoM2 = scheduler.getEntitySetByName("esperandoM2");
  }

  @Override
  public void execute() {
    super.execute();

    if (!resource.allocate(1)) { // nao conseguiu alocar, vai pra fila
      entitySet.insert(grupo);
    } else {
      if (this.filaCozinha.getById(grupo.getId()) != null) { // se refeicao ja ta pronta. Se não, TerminoPreparoRefeicao irá agendar
        filaCozinha.removeById(grupo.getId());
        scheduler.scheduleNow(scheduler.createEvent(new InicioRefeicao("Inicio Refeição",grupo, scheduler)));
      } else {
        esperandoM2.insert(grupo);
      }
    }
  }
  
}
