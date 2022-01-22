/*
 * This file was automatically generated by EvoSuite
 * Fri Jan 21 11:17:25 GMT 2022
 */

package eus.solaris.solaris.service.multithreading;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import eus.solaris.solaris.service.multithreading.Processer;
import eus.solaris.solaris.service.multithreading.conversions.ConversionType;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class Processer_ESTest extends Processer_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      HashMap<Instant, Double> hashMap0 = new HashMap<Instant, Double>();
      Instant instant0 = MockInstant.ofEpochSecond((-2222L), 0L);
      Double double0 = new Double(0L);
      hashMap0.put(instant0, double0);
      ConversionType conversionType0 = ConversionType.TO_AVOIDED_TEMP_F;
      Map<Instant, Double> map0 = Processer.process(hashMap0, conversionType0);
      assertFalse(map0.isEmpty());
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      ConversionType conversionType0 = ConversionType.TO_AVOIDED_MM_INCREASE;
      // Undeclared exception!
      try { 
        Processer.process((Map<Instant, Double>) null, conversionType0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("eus.solaris.solaris.service.multithreading.Processer", e);
      }
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      // Undeclared exception!
      try { 
        Processer.groupPanels((Map<Instant, Double>) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("eus.solaris.solaris.service.multithreading.Processer", e);
      }
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      HashMap<Instant, Double> hashMap0 = new HashMap<Instant, Double>();
      Instant instant0 = MockInstant.ofEpochSecond((-2222L), 0L);
      Double double0 = new Double(0L);
      hashMap0.put(instant0, double0);
      Map<Instant, Double> map0 = Processer.groupPanels(hashMap0);
      assertEquals(1, map0.size());
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      HashMap<Instant, Double> hashMap0 = new HashMap<Instant, Double>();
      Map<Instant, Double> map0 = Processer.groupPanels(hashMap0);
      assertTrue(map0.isEmpty());
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      HashMap<Instant, Double> hashMap0 = new HashMap<Instant, Double>();
      ConversionType conversionType0 = ConversionType.NONE;
      Map<Instant, Double> map0 = Processer.process(hashMap0, conversionType0);
      assertTrue(map0.isEmpty());
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      HashMap<Instant, Double> hashMap0 = new HashMap<Instant, Double>();
      ConversionType conversionType0 = ConversionType.TO_AVOIDED_MM_INCREASE;
      Map<Instant, Double> map0 = Processer.process(hashMap0, conversionType0);
      assertTrue(map0.isEmpty());
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      HashMap<Instant, Double> hashMap0 = new HashMap<Instant, Double>();
      ConversionType conversionType0 = ConversionType.TO_AVOIDED_TEMP_C;
      Map<Instant, Double> map0 = Processer.process(hashMap0, conversionType0);
      assertTrue(map0.isEmpty());
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      HashMap<Instant, Double> hashMap0 = new HashMap<Instant, Double>();
      ConversionType conversionType0 = ConversionType.TO_AVOIDED_CO2;
      Map<Instant, Double> map0 = Processer.process(hashMap0, conversionType0);
      assertTrue(map0.isEmpty());
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      HashMap<Instant, Double> hashMap0 = new HashMap<Instant, Double>();
      ConversionType conversionType0 = ConversionType.TO_POUNDS;
      Map<Instant, Double> map0 = Processer.process(hashMap0, conversionType0);
      assertTrue(map0.isEmpty());
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      HashMap<Instant, Double> hashMap0 = new HashMap<Instant, Double>();
      ConversionType conversionType0 = ConversionType.TO_DOLLAR;
      Map<Instant, Double> map0 = Processer.process(hashMap0, conversionType0);
      assertTrue(map0.isEmpty());
  }

  @Test(timeout = 4000)
  public void test11()  throws Throwable  {
      HashMap<Instant, Double> hashMap0 = new HashMap<Instant, Double>();
      ConversionType conversionType0 = ConversionType.TO_EUR;
      Map<Instant, Double> map0 = Processer.process(hashMap0, conversionType0);
      assertEquals(0, map0.size());
  }
}
