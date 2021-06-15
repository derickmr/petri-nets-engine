package com.unisinos.modelsimulator.restaurante.events;

import com.unisinos.modelsimulator.Entity;
import com.unisinos.modelsimulator.EntitySet;
import com.unisinos.modelsimulator.Event;
import com.unisinos.modelsimulator.Resource;
import com.unisinos.modelsimulator.Scheduler;

public class TerminoPreparoRefeicao extends Event {

  private Entity grupo;
  private EntitySet balcao;
  private EntitySet mesa2lugares;
  private EntitySet mesa4lugares;
  private EntitySet filaCozinha;

  public TerminoPreparoRefeicao(String name, Entity grupo, Resource resource, Scheduler scheduler) {
    super(name, resource, scheduler);
    this.grupo = grupo;
    this.resource = resource; // cozinheiro
    this.filaCozinha = scheduler.getEntitySetByName("filaCozinha");
    this.entitySet = scheduler.getEntitySetByName("filaComidaPronta");
    this.balcao = scheduler.getEntitySetByName("balcao");
    this.mesa2lugares = scheduler.getEntitySetByName("mesa2Lugares");
    this.mesa4lugares = scheduler.getEntitySetByName("mesa4Lugares");
  }

  @Override
  public void execute() {
    super.execute();
      try {
        resource.release(1);
        Scheduler scheduler = getScheduler();

        entitySet.insert(grupo); // insere grupo na fila de comida pronta
        
        if (balcao.getById(grupo.getId()) != null) { // verifica se o grupo já está sentado em algum lugar
          scheduler.scheduleNow(new InicioRefeicao("Inicio Refeição",grupo, scheduler));
        }

        if (mesa2lugares.getById(grupo.getId()) != null) {
          scheduler.scheduleNow(new InicioRefeicao("Inicio Refeição",grupo, scheduler));
        }

        if (mesa4lugares.getById(grupo.getId()) != null) {
          scheduler.scheduleNow(new InicioRefeicao("Inicio Refeição",grupo, scheduler));
        }
        var nextPedido = filaCozinha.remove();
        if (nextPedido != null) { //agenda próximo preparo
          scheduler.scheduleNow(new InicioPreparoRefeicao("Inicio Preparo Refeição", nextPedido, scheduler));
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
  }
}
