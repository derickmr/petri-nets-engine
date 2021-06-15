package com.unisinos.modelsimulator.restaurante.events;

import com.unisinos.modelsimulator.Event;
import com.unisinos.modelsimulator.Resource;
import com.unisinos.modelsimulator.Scheduler;

public class SaidaDaMesa2Lugares extends Event {

  public SaidaDaMesa2Lugares(String name, Resource resource, Scheduler scheduler) {
    super(name);
  }

  @Override
  public void execute() {
    super.execute();
  }
  
}
