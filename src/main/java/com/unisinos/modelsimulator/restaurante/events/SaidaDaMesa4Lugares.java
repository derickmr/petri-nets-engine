package com.unisinos.modelsimulator.restaurante.events;

import com.unisinos.modelsimulator.EntitySet;
import com.unisinos.modelsimulator.Entity;
import com.unisinos.modelsimulator.Event;
import com.unisinos.modelsimulator.Resource;
import com.unisinos.modelsimulator.Scheduler;

public class SaidaDaMesa4Lugares extends Event {

  public SaidaDaMesa4Lugares(String name, Entity grupo, Resource resource, Scheduler scheduler) {
    super(name);
    this.resource = resource;
    this.filaMesa4Lugares = scheduler.getEntitySetByName("filaMesa4Lugares");
    this.mesa4Lugares = scheduler.getResourceByName("mesa4Lugares");
    this.scheduler = scheduler;
  }

  @Override
  public void execute() {
    super.execute();
    mesa4Lugares.release(1);
    if (mesa4Lugares.allocate(1)) {
      scheduler.scheduleNow(scheduler.createEvent(new ChegadaNaMesa4Lugares("Chegada 4 lugares", filaMesa4Lugares.remove(), scheduler));
    }
  }
}
