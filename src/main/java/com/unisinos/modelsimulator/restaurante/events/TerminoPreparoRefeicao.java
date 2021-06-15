package com.unisinos.modelsimulator.restaurante.events;

import com.unisinos.modelsimulator.Entity;
import com.unisinos.modelsimulator.EntitySet;
import com.unisinos.modelsimulator.Event;
import com.unisinos.modelsimulator.Resource;
import com.unisinos.modelsimulator.Scheduler;

public class TerminoPreparoRefeicao extends Event {

  private Entity grupo;
  private EntitySet esperandoNoBalcao;
  private EntitySet esperandoM2;
  private EntitySet esperandoM4;
  private EntitySet filaCozinha;

  public TerminoPreparoRefeicao(String name, Entity grupo, Resource resource, Scheduler scheduler) {
    super(name, resource, scheduler);
    this.grupo = grupo;
    this.resource = resource; // cozinheiro
    this.filaCozinha = scheduler.getEntitySetByName("filaCozinha");
    this.entitySet = scheduler.getEntitySetByName("filaComidaPronta");
    this.esperandoNoBalcao = scheduler.getEntitySetByName("esperandoNoBalcao");
    this.esperandoM2 = scheduler.getEntitySetByName("esperandoM2");
    this.esperandoM4 = scheduler.getEntitySetByName("esperandoM4");
  }

  @Override
  public void execute() {
    super.execute();
      try {
        resource.release(1);

        entitySet.insert(grupo); // insere grupo na fila de comida pronta

        if (esperandoNoBalcao.getById(grupo.getId()) != null) { // verifica se o grupo já está sentado em algum lugar para iniciar a refeicao
          esperandoNoBalcao.removeById(grupo.getId());
          scheduler.scheduleNow(scheduler.createEvent(new InicioRefeicao("Inicio Refeição",grupo, scheduler)));
        } else if (esperandoM2.getById(grupo.getId()) != null) {
          esperandoM2.removeById(grupo.getId());
          scheduler.scheduleNow(scheduler.createEvent(new InicioRefeicao("Inicio Refeição",grupo, scheduler)));
        } else if (esperandoM4.getById(grupo.getId()) != null) {
          esperandoM4.removeById(grupo.getId());
          scheduler.scheduleNow(scheduler.createEvent(new InicioRefeicao("Inicio Refeição",grupo, scheduler)));
        }

        var nextPedido = filaCozinha.remove();
        if (nextPedido != null) { //agenda próximo preparo
          scheduler.scheduleNow(scheduler.createEvent(new InicioPreparoRefeicao("Inicio Preparo Refeição", nextPedido, scheduler)));
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
  }
}
