package com.unisinos.modelsimulator.restaurante.events;

import com.unisinos.modelsimulator.Entity;
import com.unisinos.modelsimulator.EntitySet;
import com.unisinos.modelsimulator.Event;
import com.unisinos.modelsimulator.Scheduler;
import com.unisinos.modelsimulator.restaurante.entities.GrupoCliente;

import java.util.Optional;

public class ChegadaNaMesa4Lugares extends Event {

    private GrupoCliente grupo;
    private EntitySet filaComidaPronta;
    private EntitySet esperandoM4;

    public ChegadaNaMesa4Lugares(String name, GrupoCliente grupo, Scheduler scheduler) {
        super(name, scheduler);
        this.grupo = grupo;
        this.resource = scheduler.getResourceByName("mesa4Lugares");
        this.entitySet = scheduler.getEntitySetByName("filaMesa4Lugares");
        this.filaComidaPronta = scheduler.getEntitySetByName("filaComidaPronta");
        this.esperandoM4 = scheduler.getEntitySetByName("esperandoM4");
    }

    @Override
    public void execute() {
        super.execute();

        if (resource.allocate(1)) {
            if (this.filaComidaPronta.getById(grupo.getId()) != null) { // se refeicao ja ta pronta. Se não, TerminoPreparoRefeicao irá agendar
                scheduler.scheduleNow(scheduler.createEvent(new InicioRefeicao("Inicio Refeição", grupo, scheduler)));
            } else {
                esperandoM4.insert(grupo);

                Optional<Entity> oGrupoComRefeicaoPronta = esperandoM4.getEntities().stream().filter(
                        grupoEsperando -> esperandoM4.getById(grupoEsperando.getId()) != null
                ).findFirst();

                oGrupoComRefeicaoPronta.ifPresent(entity -> scheduler.scheduleNow(scheduler.createEvent(new InicioRefeicao("Inicio Refeição", (GrupoCliente) entity, scheduler))));

            }
        } else {
            entitySet.insert(grupo);
        }
    }

}
