package com.unisinos.modelsimulator.restaurante.events;

import com.unisinos.modelsimulator.EntitySet;
import com.unisinos.modelsimulator.Entity;
import com.unisinos.modelsimulator.Event;
import com.unisinos.modelsimulator.Resource;
import com.unisinos.modelsimulator.Scheduler;
import com.unisinos.modelsimulator.restaurante.entities.GrupoCliente;

public class SaidaDaMesa4Lugares extends Event {

  private final EntitySet filaMesa4Lugares;
  private final Resource mesa4Lugares;
  private final Entity grupo;

  public SaidaDaMesa4Lugares(String name, Entity grupo, Resource resource, Scheduler scheduler) {
    super(name);
    this.resource = resource;
    this.filaMesa4Lugares = scheduler.getEntitySetByName("filaMesa4Lugares");
    this.mesa4Lugares = scheduler.getResourceByName("mesa4Lugares");
    this.scheduler = scheduler;
    this.grupo = grupo;
  }

  @Override
  public void execute() {
    super.execute();
    mesa4Lugares.release(1);
    scheduler.destroyEntity(grupo.getId());
    if (!filaMesa4Lugares.isEmpty()) {
      scheduler.scheduleNow(scheduler.createEvent(new ChegadaNaMesa4Lugares("Chegada 4 lugares", (GrupoCliente) filaMesa4Lugares.remove(), scheduler)));
    }
  }
}
