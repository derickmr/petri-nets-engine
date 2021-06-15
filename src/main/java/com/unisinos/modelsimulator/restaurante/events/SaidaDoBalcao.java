package com.unisinos.modelsimulator.restaurante.events;

import com.unisinos.modelsimulator.EntitySet;
import com.unisinos.modelsimulator.Event;
import com.unisinos.modelsimulator.Resource;
import com.unisinos.modelsimulator.Scheduler;

public class SaidaDoBalcao extends Event {

  private final EntitySet filaBalcao;
  private final Resource balcao;

  public SaidaDoBalcao(String name, Resource resource, Scheduler scheduler) {
      super(name);
      this.resource = resource;
      this.filaBalcao = scheduler.getEntitySetByName("filaBalcao");
      this.balcao = scheduler.getResourceByName("balcao");
      this.scheduler = scheduler;
    }

  @Override
  public void execute() {
    super.execute();
    balcao.release(1);
    if (balcao.allocate(1)) {
      scheduler.scheduleNow(scheduler.createEvent(new ChegadaNoBalcao("Chegada 1 lugar", filaBalcao.remove(), scheduler));
    }
  }
}
