package com.unisinos.modelsimulator.restaurante.events;

import com.unisinos.modelsimulator.*;
import com.unisinos.modelsimulator.restaurante.entities.GrupoCliente;

public class ChegadaNoBalcao extends Event {

    private GrupoCliente grupo;
    private EntitySet filaComidaPronta;
    private EntitySet esperandoNoBalcao;

    public ChegadaNoBalcao(String name, GrupoCliente grupo, Scheduler scheduler) {
        super(name, scheduler);
        this.grupo = grupo;
        this.entitySet = scheduler.getEntitySetByName("filaBalcao");
        this.resource = scheduler.getResourceByName("bancos");
        this.filaComidaPronta = scheduler.getEntitySetByName("filaComidaPronta");
        this.esperandoNoBalcao = scheduler.getEntitySetByName("esperandoNoBalcao");
    }

    @Override
    public void execute() {
      super.execute();

      if (!resource.allocate(1)) { //'Grupo' não conseguiu lugar. Coloca na fila
         entitySet.insertFirstPosition(grupo);
      } else {
        if (this.filaComidaPronta.getById(grupo.getId()) != null) { // se refeicao ja ta pronta. Se não, TerminoPreparoRefeicao irá agendar
          filaComidaPronta.removeById(grupo.getId());
          scheduler.scheduleNow(scheduler.createEvent(new InicioRefeicao("Inicio Refeição",grupo, scheduler)));
        } else {
          esperandoNoBalcao.insert(grupo);
        }
      }
    }

}
