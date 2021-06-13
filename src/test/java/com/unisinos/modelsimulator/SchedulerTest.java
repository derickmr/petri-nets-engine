package com.unisinos.modelsimulator;

import static org.junit.Assert.assertNotNull;
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
  public void uniformTest() {
    double distribution = scheduler.uniform(10, 50);
    assertTrue("greater than 10 and lesser than 50", distribution >= 10 && distribution <= 50);
  }

  @Test
  public void exponentialTest() throws MathException {
    double distribution = scheduler.exponential(50);
    assertNotNull("distribution is null", distribution);

  }

  @Test
  public void normalTest() throws MathException {
    double distribution = scheduler.normal(50, 2);
    assertNotNull("distribution is null", distribution);

  }
}