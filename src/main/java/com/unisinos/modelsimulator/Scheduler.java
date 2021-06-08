package com.unisinos.modelsimulator;
import java.util.ArrayList;
import java.util.Collections;

public class Scheduler {
    private double time;

    public double getTime() { // retorna o tempo atual do modelo
        return time;
    }

    // disparo de eventos e processos

    public void scheduleNow(Event event) {
        //implement
    }

    public void scheduleIn(Event event, int timeToEvent) { // sei lá se é int, vou deixar como int por hora
        //implement
    }

    public void scheduleAt(Event event, int absoluteTime) { // sei lá se é int, vou deixar como int por hora
        //implement
    }

    public void startProcessNow(int processId) {
        //implement
    }

    public void startProcessIn(int processId, int timeToStart) { // sei lá se é int, vou deixar como int por hora
        //implement
    }

    public void startProcessAt(int processId, int absoluteTime) { // sei lá se é int, vou deixar como int por hora
        //implement
    }

    public void waitFor(double time) {
        //implement
    }

    // controlando tempo de execução

    public void simulate() {
        //implement
    }

    public void simulateOneStep() {
        //implement
    }

    public void simulateBy(double duration) {
        //implement
    }

    public void simulateUntil(double absoluteTime) {
        //implement
    }

    // criação destruição e acesso para componentes

    public void createEntity(Entity entity) {
        //implement
    }

    public void destroyEntity(int id) {
        //implement
    }

    public Entity getEntity(int id) {
        //implement
        return new Entity("implement");
    }

    public int createResource(String name, int quantity) {
        //implement
        return 0; // retorna o id
    }

    public Resource getResource(int id) {
        //implement
        return new Resource("implement", 1);
    }

    public int createEvent(String name) {
        //implement
        return 0; //return event id of the newly created event
    }

    public Event getEvent(int eventId) {
        //implement
        return new Event("implement");
    }

    public int createEntitySet(String name, ArrayList mode, int maxPossibleSize) {
        //implement
        return 0; // return entity set id of the newly created entity set
    }

    public EntitySet getEntitySet(int id) {
        //implement
        return null;
    }

    // random variates

    public double uniform(double minValue, double maxValue) {
        //implement
        return 0;
    }

    public double exponential(double meanValue) {
        //implement
        return 0;
    }

    public double normal(double meanValue, double stdDeviationValue) {
        //implement
        return 0;
    }

    // coleta de estatística

    public int getEntityTotalQuantity() {
        //implement
        return 0;
    }

    public int getEntityTotalQuantity(String name) {
        //implement
        return 0;
    }

    public double averageTimeInModel() {
        //implement
        return 0;
    }

    public int maxEntitiesPresent() {
        //implement
        return 0;
    }
}
