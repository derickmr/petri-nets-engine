package com.unisinos.modelsimulator.restaurante.events;

import com.unisinos.modelsimulator.Entity;
import com.unisinos.modelsimulator.Event;
import com.unisinos.modelsimulator.Scheduler;

public class ChegadaNaMesa2Lugares extends Event {

  private Entity grupo;

  public ChegadaNaMesa2Lugares(String name, Entity grupo, Scheduler scheduler) {
    super(name, scheduler);
    this.grupo = grupo;
  }

  @Override
  public void execute() {
    super.execute();
  }
  
}
