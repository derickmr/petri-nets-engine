package com.unisinos.modelsimulator.restaurante.events;

import com.unisinos.modelsimulator.Entity;
import com.unisinos.modelsimulator.EntitySet;
import com.unisinos.modelsimulator.Event;
import com.unisinos.modelsimulator.Scheduler;
import com.unisinos.modelsimulator.restaurante.entities.GrupoCliente;

import java.util.Optional;

public class ChegadaNaMesa2Lugares extends Event {

    private GrupoCliente grupo;
    private EntitySet filaComidaPronta;
    private EntitySet esperandoM2;

    public ChegadaNaMesa2Lugares(String name, GrupoCliente grupo, Scheduler scheduler) {
        super(name, scheduler);
        this.grupo = grupo;
        this.resource = scheduler.getResourceByName("mesa2Lugares");
        this.entitySet = scheduler.getEntitySetByName("filaMesa2Lugares");
        this.filaComidaPronta = scheduler.getEntitySetByName("filaComidaPronta");
        this.esperandoM2 = scheduler.getEntitySetByName("esperandoM2");
    }

    @Override
    public void execute() {
        super.execute();

        if (resource.allocate(1)) {
            if (this.filaComidaPronta.getById(grupo.getId()) != null) { // se refeicao ja ta pronta. Se não, TerminoPreparoRefeicao irá agendar
                scheduler.scheduleNow(scheduler.createEvent(new InicioRefeicao("Inicio Refeição", grupo, scheduler)));
            } else {
                esperandoM2.insert(grupo);

                Optional<Entity> oGrupoComRefeicaoPronta = esperandoM2.getEntities().stream().filter(
                        grupoEsperando -> esperandoM2.getById(grupoEsperando.getId()) != null
                ).findFirst();

                oGrupoComRefeicaoPronta.ifPresent(entity -> scheduler.scheduleNow(scheduler.createEvent(new InicioRefeicao("Inicio Refeição", (GrupoCliente) entity, scheduler))));

            }
        } else {
            entitySet.insert(grupo);
        }

    }

}
