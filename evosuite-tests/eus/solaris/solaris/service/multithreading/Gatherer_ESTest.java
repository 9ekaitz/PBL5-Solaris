/*
 * This file was automatically generated by EvoSuite
 * Fri Jan 21 11:10:50 GMT 2022
 */

package eus.solaris.solaris.service.multithreading;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import eus.solaris.solaris.domain.SolarPanelDataEntry;
import eus.solaris.solaris.service.multithreading.GatherBuffer;
import eus.solaris.solaris.service.multithreading.Gatherer;
import eus.solaris.solaris.service.multithreading.InitialBuffer;
import java.time.Instant;
import java.util.LinkedList;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class Gatherer_ESTest extends Gatherer_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      LinkedList<SolarPanelDataEntry> linkedList0 = new LinkedList<SolarPanelDataEntry>();
      SolarPanelDataEntry solarPanelDataEntry0 = new SolarPanelDataEntry();
      Instant instant0 = MockInstant.ofEpochSecond((-1556L), 0L);
      solarPanelDataEntry0.setTimestamp(instant0);
      linkedList0.push(solarPanelDataEntry0);
      Map<Instant, Double> map0 = Gatherer.extractData(linkedList0);
      assertFalse(map0.isEmpty());
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      LinkedList<SolarPanelDataEntry> linkedList0 = new LinkedList<SolarPanelDataEntry>();
      SolarPanelDataEntry solarPanelDataEntry0 = new SolarPanelDataEntry();
      linkedList0.push(solarPanelDataEntry0);
      // Undeclared exception!
      try { 
        Gatherer.extractData(linkedList0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("java.util.TreeMap", e);
      }
  }

  @Test(timeout = 4000)
  public void test2()  throws Throwable  {
      LinkedList<SolarPanelDataEntry> linkedList0 = new LinkedList<SolarPanelDataEntry>();
      Map<Instant, Double> map0 = Gatherer.extractData(linkedList0);
      assertEquals(0, map0.size());
  }

  @Test(timeout = 4000)
  public void test3()  throws Throwable  {
      Gatherer gatherer0 = null;
      try {
        gatherer0 = new Gatherer((InitialBuffer) null, (GatherBuffer) null);
        fail("Expecting exception: NoClassDefFoundError");
      
      } catch(NoClassDefFoundError e) {
         //
         // eus/solaris/solaris/repository/DataEntryRepository
         //
         verifyException("eus.solaris.solaris.service.multithreading.Gatherer", e);
      }
  }
}
