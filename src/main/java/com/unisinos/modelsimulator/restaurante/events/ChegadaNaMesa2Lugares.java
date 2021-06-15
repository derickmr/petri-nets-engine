package com.unisinos.modelsimulator.restaurante.events;

import com.unisinos.modelsimulator.Entity;
import com.unisinos.modelsimulator.Event;
import com.unisinos.modelsimulator.Scheduler;

public class ChegadaNaMesa2Lugares extends Event {

  private Entity grupo;

  public ChegadaNaMesa2Lugares(String name, Entity grupo, Scheduler scheduler) {
    super(name, scheduler);
    this.grupo = grupo;
    this.resource = scheduler.getResourceByName("mesa2Lugares");
    this.entitySet = scheduler.getEntitySetByName("filaMesa2Lugares");
  }

  @Override
  public void execute() {
    super.execute();

    if (!resource.allocate(1)) {
      entitySet.insert(grupo);
    }
  }
  
}
