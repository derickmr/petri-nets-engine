package com.unisinos.modelsimulator.restaurante.events;

import com.unisinos.modelsimulator.*;

public class ChegadaNoBalcao extends Event {

    private Entity grupo;
    private EntitySet filaCozinha;
    private EntitySet esperandoNoBalcao;

    public ChegadaNoBalcao(String name, Entity grupo, Scheduler scheduler) {
        super(name, scheduler);
        this.grupo = grupo;
        this.entitySet = scheduler.getEntitySetByName("filaBalcao");
        this.resource = scheduler.getResourceByName("bancos");
        this.filaCozinha = scheduler.getEntitySetByName("filaCozinha");
        this.esperandoNoBalcao = scheduler.getEntitySetByName("esperandoNoBalcao");
    }

    @Override
    public void execute() {
      super.execute();

      if (!resource.allocate(1)) { //'Grupo' não conseguiu lugar. Coloca na fila
         entitySet.insert(grupo);
      } else {
        if (this.filaCozinha.getById(grupo.getId()) != null) { // se refeicao ja ta pronta. Se não, TerminoPreparoRefeicao irá agendar
          filaCozinha.removeById(grupo.getId());
          scheduler.scheduleNow(scheduler.createEvent(new InicioRefeicao("Inicio Refeição",grupo, scheduler)));
        } else {
          esperandoNoBalcao.insert(grupo);
        }
      }
    }

}
