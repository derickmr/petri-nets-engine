package com.unisinos.modelsimulator.restaurante.events;

import com.unisinos.modelsimulator.*;

public class ChegadaNoBalcao extends Event {

    private Entity grupo;

    public ChegadaNoBalcao(String name, Scheduler scheduler) {
        super(name, scheduler);
    }

    public ChegadaNoBalcao(String name, Entity grupo, Scheduler scheduler) {
        super(name, scheduler);
        this.grupo = grupo;
        this.entitySet = scheduler.getEntitySetByName("filaBalcao");
        this.resource = scheduler.getResourceByName("bancos");
    }

    @Override
    public void execute() {

      if (!getResource().allocate(1)) { //'Grupo' não conseguiu lugar. Coloca na fila
         getEntitySet().insert(grupo);
      } else {
        
      }

    }

}
