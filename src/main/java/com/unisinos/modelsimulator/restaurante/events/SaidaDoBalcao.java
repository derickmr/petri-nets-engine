package com.unisinos.modelsimulator.restaurante.events;

import com.unisinos.modelsimulator.EntitySet;
import com.unisinos.modelsimulator.Entity;
import com.unisinos.modelsimulator.Event;
import com.unisinos.modelsimulator.Resource;
import com.unisinos.modelsimulator.Scheduler;
import com.unisinos.modelsimulator.restaurante.entities.GrupoCliente;

public class SaidaDoBalcao extends Event {

  private final EntitySet filaBalcao;
  private final Resource balcao;
  private final Entity grupo;

  public SaidaDoBalcao(String name, Entity grupo, Resource resource, Scheduler scheduler) {
      super(name);
      this.resource = resource;
      this.filaBalcao = scheduler.getEntitySetByName("filaBalcao");
      this.balcao = scheduler.getResourceByName("balcao");
      this.scheduler = scheduler;
      this.grupo = grupo;
    }

  @Override
  public void execute() {
    super.execute();
    balcao.release(1);
    scheduler.destroyEntity(grupo.getId());
    if (!filaBalcao.isEmpty()) {
      scheduler.scheduleNow(scheduler.createEvent(new ChegadaNoBalcao("Chegada 1 lugar", (GrupoCliente) filaBalcao.remove(), scheduler)));
    }
  }
}
