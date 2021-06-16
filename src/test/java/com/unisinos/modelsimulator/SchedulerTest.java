package com.unisinos.modelsimulator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.math.MathException;
import org.junit.Before;
import org.junit.Test;

public class SchedulerTest {
  Scheduler scheduler;
  @Before
  public void before() {
    this.scheduler = new Scheduler();
  }
  @Test
  public void simpleEventsTest() throws MathException {
    Event futureEvent = new Event("fim do semestre");
    scheduler.createEvent(futureEvent);
    scheduler.scheduleIn(futureEvent, Scheduler.exponential(10));
    Event nowEvent = new Event("trabalho do grau b");
    scheduler.createEvent(nowEvent);
    scheduler.scheduleNow(nowEvent);
    scheduler.simulate();
    assertTrue(futureEvent.executed);
    assertTrue(nowEvent.executed);
  }

  @Test
  public void stepByStepEventsTest() throws MathException {
    Event futureEvent = new Event("fim do semestre");
    scheduler.createEvent(futureEvent);
    scheduler.scheduleIn(futureEvent, Scheduler.exponential(10));
    Event nowEvent = new Event("trabalho do grau b");
    scheduler.createEvent(nowEvent);
    scheduler.scheduleNow(nowEvent);
    scheduler.simulateOneStep();
    assertFalse(futureEvent.executed);
    assertTrue(nowEvent.executed);
    scheduler.simulateOneStep();
    assertTrue(futureEvent.executed);
    assertTrue(nowEvent.executed);
  }

  @Test
  public void exponentialLambdaTest() {
    assertTrue(Scheduler.exponential(50) > 50);
  }
}