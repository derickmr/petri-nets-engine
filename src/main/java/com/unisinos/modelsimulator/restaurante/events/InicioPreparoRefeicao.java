package com.unisinos.modelsimulator.restaurante.events;

import com.unisinos.modelsimulator.Entity;
import com.unisinos.modelsimulator.Event;
import com.unisinos.modelsimulator.Scheduler;

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
  }
  
}
