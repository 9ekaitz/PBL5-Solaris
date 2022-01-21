/**
 * Scaffolding file used to store all the setups needed to run 
 * tests automatically generated by EvoSuite
 * Fri Jan 21 11:10:50 GMT 2022
 */

package eus.solaris.solaris.service.multithreading;

import org.evosuite.runtime.annotation.EvoSuiteClassExclude;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.After;
import org.junit.AfterClass;
import org.evosuite.runtime.sandbox.Sandbox;
import org.evosuite.runtime.sandbox.Sandbox.SandboxMode;

@EvoSuiteClassExclude
public class Gatherer_ESTest_scaffolding {

  @org.junit.Rule 
  public org.evosuite.runtime.vnet.NonFunctionalRequirementRule nfr = new org.evosuite.runtime.vnet.NonFunctionalRequirementRule();

  private static final java.util.Properties defaultProperties = (java.util.Properties) java.lang.System.getProperties().clone(); 

  private org.evosuite.runtime.thread.ThreadStopper threadStopper =  new org.evosuite.runtime.thread.ThreadStopper (org.evosuite.runtime.thread.KillSwitchHandler.getInstance(), 3000);


  @BeforeClass 
  public static void initEvoSuiteFramework() { 
    org.evosuite.runtime.RuntimeSettings.className = "eus.solaris.solaris.service.multithreading.Gatherer"; 
    org.evosuite.runtime.GuiSupport.initialize(); 
    org.evosuite.runtime.RuntimeSettings.maxNumberOfThreads = 100; 
    org.evosuite.runtime.RuntimeSettings.maxNumberOfIterationsPerLoop = 10000; 
    org.evosuite.runtime.RuntimeSettings.mockSystemIn = true; 
    org.evosuite.runtime.RuntimeSettings.sandboxMode = org.evosuite.runtime.sandbox.Sandbox.SandboxMode.RECOMMENDED; 
    org.evosuite.runtime.sandbox.Sandbox.initializeSecurityManagerForSUT(); 
    org.evosuite.runtime.classhandling.JDKClassResetter.init();
    setSystemProperties();
    initializeClasses();
    org.evosuite.runtime.Runtime.getInstance().resetRuntime(); 
  } 

  @AfterClass 
  public static void clearEvoSuiteFramework(){ 
    Sandbox.resetDefaultSecurityManager(); 
    java.lang.System.setProperties((java.util.Properties) defaultProperties.clone()); 
  } 

  @Before 
  public void initTestCase(){ 
    threadStopper.storeCurrentThreads();
    threadStopper.startRecordingTime();
    org.evosuite.runtime.jvm.ShutdownHookHandler.getInstance().initHandler(); 
    org.evosuite.runtime.sandbox.Sandbox.goingToExecuteSUTCode(); 
    setSystemProperties(); 
    org.evosuite.runtime.GuiSupport.setHeadless(); 
    org.evosuite.runtime.Runtime.getInstance().resetRuntime(); 
    org.evosuite.runtime.agent.InstrumentingAgent.activate(); 
  } 

  @After 
  public void doneWithTestCase(){ 
    threadStopper.killAndJoinClientThreads();
    org.evosuite.runtime.jvm.ShutdownHookHandler.getInstance().safeExecuteAddedHooks(); 
    org.evosuite.runtime.classhandling.JDKClassResetter.reset(); 
    resetClasses(); 
    org.evosuite.runtime.sandbox.Sandbox.doneWithExecutingSUTCode(); 
    org.evosuite.runtime.agent.InstrumentingAgent.deactivate(); 
    org.evosuite.runtime.GuiSupport.restoreHeadlessMode(); 
  } 

  public static void setSystemProperties() {
 
    java.lang.System.setProperties((java.util.Properties) defaultProperties.clone()); 
    java.lang.System.setProperty("user.dir", "E:\\Desktop\\Eskola\\PBL5\\PBL5-Solaris"); 
    java.lang.System.setProperty("java.io.tmpdir", "C:\\Users\\Rkolay\\AppData\\Local\\Temp\\"); 
  }

  private static void initializeClasses() {
    org.evosuite.runtime.classhandling.ClassStateSupport.initializeClasses(Gatherer_ESTest_scaffolding.class.getClassLoader() ,
      "eus.solaris.solaris.service.multithreading.Gatherer",
      "eus.solaris.solaris.service.multithreading.InitialBuffer",
      "eus.solaris.solaris.domain.SolarPanelDataEntry",
      "eus.solaris.solaris.service.multithreading.GatherBuffer",
      "eus.solaris.solaris.domain.SolarPanel"
    );
  } 

  private static void resetClasses() {
    org.evosuite.runtime.classhandling.ClassResetter.getInstance().setClassLoader(Gatherer_ESTest_scaffolding.class.getClassLoader()); 

    org.evosuite.runtime.classhandling.ClassStateSupport.resetClasses(
      "eus.solaris.solaris.service.multithreading.Gatherer",
      "eus.solaris.solaris.service.multithreading.InitialBuffer",
      "eus.solaris.solaris.domain.SolarPanelDataEntry",
      "eus.solaris.solaris.domain.SolarPanel",
      "eus.solaris.solaris.service.multithreading.ThreadService",
      "eus.solaris.solaris.service.multithreading.GatherBuffer",
      "eus.solaris.solaris.service.multithreading.DataBuffer",
      "eus.solaris.solaris.domain.Provincia",
      "eus.solaris.solaris.service.multithreading.conversions.ConversionToTempF",
      "eus.solaris.solaris.service.multithreading.conversions.ConversionToNMInc",
      "eus.solaris.solaris.domain.SolarPanelModel",
      "eus.solaris.solaris.service.multithreading.conversions.ConversionToTempC",
      "eus.solaris.solaris.domain.Comunidad",
      "eus.solaris.solaris.service.multithreading.conversions.ConversionToGBP",
      "eus.solaris.solaris.service.multithreading.conversions.ConversionToUSD",
      "eus.solaris.solaris.service.multithreading.conversions.ConversionToEUR"
    );
  }
}
