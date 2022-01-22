/*
 * This file was automatically generated by EvoSuite
 * Fri Jan 21 11:31:46 GMT 2022
 */

package eus.solaris.solaris.service.impl;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.service.impl.SolarPanelServiceImpl;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class SolarPanelServiceImpl_ESTest extends SolarPanelServiceImpl_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      SolarPanelServiceImpl solarPanelServiceImpl0 = new SolarPanelServiceImpl();
      User user0 = new User();
      // Undeclared exception!
      try { 
        solarPanelServiceImpl0.findByUser(user0);
        fail("Expecting exception: NoClassDefFoundError");
      
      } catch(NoClassDefFoundError e) {
         //
         // eus/solaris/solaris/repository/SolarPanelRepository
         //
         verifyException("eus.solaris.solaris.service.impl.SolarPanelServiceImpl", e);
      }
  }
}
