package com.unisinos.modelsimulator.restaurante.events;

import com.unisinos.modelsimulator.*;
import com.unisinos.modelsimulator.restaurante.entities.GrupoCliente;

import java.util.Optional;

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

        if (resource.allocate(1)) {
            if (this.filaComidaPronta.getById(grupo.getId()) != null) { // se refeicao ja ta pronta. Se não, TerminoPreparoRefeicao irá agendar
                scheduler.scheduleNow(scheduler.createEvent(new InicioRefeicao("Inicio Refeição", grupo, scheduler)));
            } else {
                esperandoNoBalcao.insert(grupo);

                Optional<Entity> oGrupoComRefeicaoPronta = esperandoNoBalcao.getEntities().stream().filter(
                        grupoEsperando -> esperandoNoBalcao.getById(grupoEsperando.getId()) != null
                ).findFirst();

                oGrupoComRefeicaoPronta.ifPresent(entity -> scheduler.scheduleNow(scheduler.createEvent(new InicioRefeicao("Inicio Refeição", (GrupoCliente) entity, scheduler))));

            }
        } else {
            entitySet.insert(grupo);
        }
    }

}
