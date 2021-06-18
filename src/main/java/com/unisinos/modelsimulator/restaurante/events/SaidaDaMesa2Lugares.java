package com.unisinos.modelsimulator.restaurante.events;

import com.unisinos.modelsimulator.*;
import com.unisinos.modelsimulator.restaurante.entities.GrupoCliente;

public class SaidaDaMesa2Lugares extends Event {

  private final EntitySet filaMesa2Lugares;
  private final Resource mesa2Lugares;

  public SaidaDaMesa2Lugares(String name, Entity grupo, Resource resource, Scheduler scheduler) {
    super(name);
    this.resource = resource;
    this.filaMesa2Lugares = scheduler.getEntitySetByName("filaMesa2Lugares");
    this.mesa2Lugares = scheduler.getResourceByName("mesa2Lugares");
    this.scheduler = scheduler;
  }

  @Override
  public void execute() {
    super.execute();
    mesa2Lugares.release(1);
    if (!filaMesa2Lugares.isEmpty()) {
      scheduler.scheduleNow(scheduler.createEvent(new ChegadaNaMesa2Lugares("Chegada 2 lugares", (GrupoCliente) filaMesa2Lugares.remove(), scheduler)));
    }
  }
}
