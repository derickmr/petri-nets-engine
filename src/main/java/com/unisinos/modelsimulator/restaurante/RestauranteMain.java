package com.unisinos.modelsimulator.restaurante;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

import com.unisinos.modelsimulator.EntitySetMode;
import com.unisinos.modelsimulator.Event;
import com.unisinos.modelsimulator.Scheduler;
import com.unisinos.modelsimulator.restaurante.events.ChegadaGrupo;

public class RestauranteMain {

    public static void main (String[] args) {

        Scheduler scheduler = new Scheduler();

        scheduler.createEntitySet("filaCaixa1", new ArrayList<>(), 1000).setMode(EntitySetMode.FIFO);
        scheduler.createEntitySet("filaCaixa2", new ArrayList<>(), 1000).setMode(EntitySetMode.FIFO);
        scheduler.createEntitySet("filaMesa2Lugares", new ArrayList<>(), 1000).setMode(EntitySetMode.FIFO);
        scheduler.createEntitySet("filaMesa4Lugares", new ArrayList<>(), 1000).setMode(EntitySetMode.FIFO);
        scheduler.createEntitySet("filaBalcao", new ArrayList<>(), 1000).setMode(EntitySetMode.FIFO);
        scheduler.createEntitySet("esperandoNoBalcao", new ArrayList<>(), 1000).setMode(EntitySetMode.FIFO);
        scheduler.createEntitySet("esperandoM2", new ArrayList<>(), 1000).setMode(EntitySetMode.FIFO);
        scheduler.createEntitySet("esperandoM4", new ArrayList<>(), 1000).setMode(EntitySetMode.FIFO);
        scheduler.createEntitySet("filaCozinha", new ArrayList<>(), 1000).setMode(EntitySetMode.FIFO);
        scheduler.createEntitySet("filaComidaPronta", new ArrayList<>(), 1000).setMode(EntitySetMode.FIFO);
        scheduler.createResource("caixa1", 1);
        scheduler.createResource("caixa2", 1);
        scheduler.createResource("cozinha", 3);
        scheduler.createResource("mesa2Lugares", 4);
        scheduler.createResource("mesa4Lugares", 4);
        scheduler.createResource("balcao", 6);
        Event chegadaGrupo = scheduler.createEvent(new ChegadaGrupo("Chegada grupo", scheduler));
        scheduler.scheduleNow(chegadaGrupo);

        scheduler.getEntitySets().forEach(set -> set.startLog(14400));
        Scanner scanner = new Scanner(System.in);

        //Simulate all
        scheduler.simulate();

        //SimulateBy
//        while (scheduler.canExecute()) {
//            System.out.println("Digite por quanto tempo (em segundos) deseja executar o simulador: ");
//            String value = scanner.nextLine();
//            scheduler.simulateBy(Double.parseDouble(value));
//            scheduler.collectLogs();
//        }

        //SimulateUntil
//        while (scheduler.canExecute()) {
//            System.out.println("Digite até quando (tempo absoluto, em segundos) deseja executar o simulador: ");
//            String value = scanner.nextLine();
//            scheduler.simulateUntil(Double.parseDouble(value));
//            scheduler.collectLogs();
//        }

        //Step by step
//        scheduler.simulateStepByStep();

        scheduler.collectLogs();


    }

}
