/*
 * This file was automatically generated by EvoSuite
 * Tue Jan 25 13:39:45 GMT 2022
 */

package eus.solaris.solaris.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class AddressTest {

  @Test
  @Timeout(4)
  void test00() throws Throwable {
    Long long0 = (0L);
    Country country0 = new Country();
    Province province0 = new Province();
    Boolean boolean0 = Boolean.TRUE;
    User user0 = new User();
    Address address0 = new Address(long0, country0, province0, "", "", "", "", user0, false, boolean0,
        (Integer) null);
    boolean boolean1 = address0.isEnabled();
    assertFalse(boolean1);
  }

  @Test
  @Timeout(4)
  void test01() throws Throwable {
    Long long0 = (0L);
    Country country0 = new Country();
    Province province0 = new Province();
    Boolean boolean0 = Boolean.valueOf("7=");
    User user0 = new User();
    Integer integer0 = (0);
    Address address0 = new Address(long0, country0, province0, "?2-,-Y&%c^.LTj%S", "?2-,-Y&%c^.LTj%S",
        "?2-,-Y&%c^.LTj%S", ",'", user0, true, boolean0, integer0);
    address0.getVersion();
    assertEquals(
        "Address(id=0, country=Country(id=null, code=null, i18n=null, version=null), province=Province(id=null, code=null, i18n=null, capitalProvincia=null, comunidad=null), city=?2-,-Y&%c^.LTj%S, postcode=?2-,-Y&%c^.LTj%S, street=?2-,-Y&%c^.LTj%S, number=,', enabled=true, defaultAddress=false)",
        address0.toString());
  }

  @Test
  @Timeout(4)
  void test02() throws Throwable {
    Address address0 = new Address();
    Integer integer0 = Integer.getInteger("", 79);
    address0.setVersion(integer0);
    address0.getVersion();
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=null, street=null, number=null, enabled=true, defaultAddress=false)",
        address0.toString());
  }

  @Test
  @Timeout(4)
  void test03() throws Throwable {
    Long long0 = ((-1176L));
    Country country0 = new Country();
    Comunidad comunidad0 = new Comunidad();
    Integer integer0 = ((-2818));
    Province province0 = new Province(long0, "+>()i{", "+>()i{", "+>()i{", comunidad0, integer0);
    Boolean boolean0 = Boolean.valueOf(true);
    Address address0 = new Address(long0, country0, province0, "+>()i{", "+>()i{", "+>()i{", "+>()i{", (User) null,
        true, boolean0, integer0);
    address0.getVersion();
    assertTrue(address0.isEnabled());
  }

  @Test
  @Timeout(4)
  void test04() throws Throwable {
    Address address0 = new Address();
    address0.getStreet();
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=null, street=null, number=null, enabled=true, defaultAddress=false)",
        address0.toString());
  }

  @Test
  @Timeout(4)
  void test05() throws Throwable {
    Long long0 = (1L);
    Integer integer0 = (0);
    Country country0 = new Country(long0, "", "", integer0);
    Comunidad comunidad0 = new Comunidad();
    Province province0 = new Province(long0, "eus.solaris.solaris.domain.Address", "", "", comunidad0, integer0);
    Boolean boolean0 = Boolean.valueOf("");
    User user0 = new User();
    Address address0 = new Address(long0, country0, province0, "eus.solaris.solaris.domain.Product",
        "eus.solaris.solaris.domain.Address", "eus.solaris.solaris.domain.Product",
        "eus.solaris.solaris.domain.Product", user0, false, boolean0, integer0);
    String string0 = address0.getStreet();
    assertEquals("eus.solaris.solaris.domain.Product", address0.getNumber());
    assertFalse(address0.isEnabled());
    assertEquals("eus.solaris.solaris.domain.Product", address0.getCity());
    assertEquals("eus.solaris.solaris.domain.Address", address0.getPostcode());
    assertEquals("eus.solaris.solaris.domain.Product", string0);
  }

  @Test
  @Timeout(4)
  void test06() throws Throwable {
    Long long0 = ((-1176L));
    Country country0 = new Country();
    Comunidad comunidad0 = new Comunidad();
    Integer integer0 = ((-2818));
    Province province0 = new Province(long0, "+>()i{", "+>()i{", "+>()i{", comunidad0, integer0);
    Boolean boolean0 = Boolean.valueOf(true);
    Address address0 = new Address(long0, country0, province0, "+>()i{", "+>()i{", "+>()i{", "+>()i{", (User) null,
        true, boolean0, integer0);
    address0.getProvince();
    assertTrue(address0.isEnabled());
  }

  @Test
  @Timeout(4)
  void test07() throws Throwable {
    Address address0 = new Address();
    address0.getPostcode();
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=null, street=null, number=null, enabled=true, defaultAddress=false)",
        address0.toString());
  }

  @Test
  @Timeout(4)
  void test08() throws Throwable {
    Long long0 = (0L);
    Country country0 = new Country();
    Province province0 = new Province();
    Boolean boolean0 = Boolean.TRUE;
    User user0 = new User();
    Address address0 = new Address(long0, country0, province0, "", "", "", "", user0, false, boolean0,
        (Integer) null);
    address0.getPostcode();
    assertFalse(address0.isEnabled());
  }

  @Test
  @Timeout(4)
  void test09() throws Throwable {
    Address address0 = new Address();
    address0.setNumber("e$:E`Zt=dlM63`WA)");
    address0.getNumber();
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=null, street=null, number=e$:E`Zt=dlM63`WA), enabled=true, defaultAddress=false)",
        address0.toString());
  }

  @Test
  @Timeout(4)
  void test10() throws Throwable {
    Address address0 = new Address();
    address0.setNumber("");
    address0.getNumber();
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=null, street=null, number=, enabled=true, defaultAddress=false)",
        address0.toString());
  }

  @Test
  @Timeout(4)
  void test11() throws Throwable {
    Long long0 = (0L);
    Country country0 = new Country();
    Province province0 = new Province();
    Boolean boolean0 = Boolean.TRUE;
    User user0 = new User();
    Integer integer0 = (192);
    Address address0 = new Address(long0, country0, province0,
        "eus.solaris.solaris.service.multithreading.conversions.ConversionToTempC", "1d_AlkX",
        "eus.solaris.solaris.domain.Address", "eus.solaris.solaris.domain.Address", user0, false, boolean0,
        integer0);
    address0.getId();
    assertEquals(
        "Address(id=0, country=Country(id=null, code=null, i18n=null, version=null), province=Province(id=null, code=null, i18n=null, capitalProvincia=null, comunidad=null), city=eus.solaris.solaris.service.multithreading.conversions.ConversionToTempC, postcode=1d_AlkX, street=eus.solaris.solaris.domain.Address, number=eus.solaris.solaris.domain.Address, enabled=false, defaultAddress=true)",
        address0.toString());
  }

  @Test
  @Timeout(4)
  void test12() throws Throwable {
    Long long0 = (1L);
    Country country0 = new Country();
    Comunidad comunidad0 = new Comunidad();
    Integer integer0 = (0);
    Province province0 = new Province(long0, "A\"m,4|", "eus.solaris.solaris.domain.Address",
        "eus.solaris.solaris.domain.Address", comunidad0, integer0);
    Boolean boolean0 = Boolean.TRUE;
    User user0 = new User();
    Address address0 = new Address(long0, country0, province0, "J_?5BGi7-a>C", "A\"m,4|", "b@G~&no!18nuZ&UQ",
        "J_?5BGi7-a>C", user0, true, boolean0, integer0);
    address0.getId();
    assertEquals("b@G~&no!18nuZ&UQ", address0.getStreet());
    assertEquals("J_?5BGi7-a>C", address0.getNumber());
    assertEquals("A\"m,4|", address0.getPostcode());
    assertTrue(address0.isEnabled());
    assertEquals("J_?5BGi7-a>C", address0.getCity());
  }

  @Test
  @Timeout(4)
  void test13() throws Throwable {
    Address address0 = new Address();
    address0.getId();
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=null, street=null, number=null, enabled=true, defaultAddress=false)",
        address0.toString());
  }

  @Test
  @Timeout(4)
  void test14() throws Throwable {
    Long long0 = (1790L);
    Country country0 = new Country(long0, "", "", (Integer) null);
    User user0 = new User();
    Boolean boolean0 = Boolean.TRUE;
    Address address0 = new Address(long0, country0, (Province) null, "", "", "", "", user0, true, boolean0,
        (Integer) null);
    address0.getDefaultAddress();
    assertEquals(
        "Address(id=1790, country=Country(id=1790, code=, i18n=, version=null), province=null, city=, postcode=, street=, number=, enabled=true, defaultAddress=true)",
        address0.toString());
  }

  @Test
  @Timeout(4)
  void test15() throws Throwable {
    Address address0 = new Address();
    address0.setDefaultAddress((Boolean) null);
    address0.getDefaultAddress();
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=null, street=null, number=null, enabled=true, defaultAddress=null)",
        address0.toString());
  }

  @Test
  @Timeout(4)
  void test16() throws Throwable {
    Long long0 = ((-1176L));
    Country country0 = new Country();
    Comunidad comunidad0 = new Comunidad();
    Integer integer0 = ((-2818));
    Province province0 = new Province(long0, "+>()i{", "+>()i{", "+>()i{", comunidad0, integer0);
    Boolean boolean0 = Boolean.valueOf(true);
    Address address0 = new Address(long0, country0, province0, "+>()i{", "+>()i{", "+>()i{", "+>()i{", (User) null,
        true, boolean0, integer0);
    address0.getCountry();
    assertTrue(address0.isEnabled());
  }

  @Test
  @Timeout(4)
  void test17() throws Throwable {
    Long long0 = ((-2045L));
    Country country0 = new Country();
    Integer integer0 = (43);
    Province province0 = new Province(long0, "2DzphJEG|2BbR'@i", "eus.solaris.solaris.domain.Address",
        "2DzphJEG|2BbR'@i", (Comunidad) null, integer0);
    User user0 = new User();
    Boolean boolean0 = Boolean.valueOf(false);
    Address address0 = new Address(long0, country0, province0, "2DzphJEG|2BbR'@i", "2DzphJEG|2BbR'@i",
        "eus.solaris.solaris.domain.Address", "k%/q=", user0, false, boolean0, integer0);
    address0.getCity();
    assertEquals(
        "Address(id=-2045, country=Country(id=null, code=null, i18n=null, version=null), province=Province(id=-2045, code=2DzphJEG|2BbR'@i, i18n=eus.solaris.solaris.domain.Address, capitalProvincia=2DzphJEG|2BbR'@i, comunidad=null), city=2DzphJEG|2BbR'@i, postcode=2DzphJEG|2BbR'@i, street=eus.solaris.solaris.domain.Address, number=k%/q=, enabled=false, defaultAddress=false)",
        address0.toString());
  }

  @Test
  @Timeout(4)
  void test18() throws Throwable {
    Long long0 = (859L);
    Province province0 = new Province();
    User user0 = new User();
    Boolean boolean0 = Boolean.TRUE;
    Integer integer0 = (1);
    Address address0 = new Address(long0, (Country) null, province0, "", "", "p>G8MX.AZEMd9h:z", "", user0, false,
        boolean0, integer0);
    address0.getCity();
    assertEquals(
        "Address(id=859, country=null, province=Province(id=null, code=null, i18n=null, capitalProvincia=null, comunidad=null), city=, postcode=, street=p>G8MX.AZEMd9h:z, number=, enabled=false, defaultAddress=true)",
        address0.toString());
  }

  @Test
  @Timeout(4)
  void test19() throws Throwable {
    Address address0 = new Address();
    address0.canEqual(address0);
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=null, street=null, number=null, enabled=true, defaultAddress=false)",
        address0.toString());
  }

  @Test
  @Timeout(4)
  void test20() throws Throwable {
    Address address0 = new Address();
    Object object0 = new Object();
    address0.canEqual(object0);
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=null, street=null, number=null, enabled=true, defaultAddress=false)",
        address0.toString());
  }

  @Test
  @Timeout(4)
  void test21() throws Throwable {
    Long long0 = ((-1176L));
    Country country0 = new Country();
    Comunidad comunidad0 = new Comunidad();
    Integer integer0 = ((-2818));
    Province province0 = new Province(long0, "+>()i{", "+>()i{", "+>()i{", comunidad0, integer0);
    Boolean boolean0 = Boolean.valueOf(true);
    Address address0 = new Address(long0, country0, province0, "+>()i{", "+>()i{", "+>()i{", "+>()i{", (User) null,
        true, boolean0, integer0);
    address0.getId();
    assertTrue(address0.isEnabled());
  }

  @Test
  @Timeout(4)
  void test22() throws Throwable {
    Address address0 = new Address();
    address0.getProvince();
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=null, street=null, number=null, enabled=true, defaultAddress=false)",
        address0.toString());
  }

  @Test
  @Timeout(4)
  void test23() throws Throwable {
    Address address0 = new Address();
    address0.getNumber();
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=null, street=null, number=null, enabled=true, defaultAddress=false)",
        address0.toString());
  }

  @Test
  @Timeout(4)
  void test24() throws Throwable {
    Address address0 = new Address();
    address0.getUser();
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=null, street=null, number=null, enabled=true, defaultAddress=false)",
        address0.toString());
  }

  @Test
  @Timeout(4)
  void test25() throws Throwable {
    Country country0 = new Country();
    Province province0 = new Province();
    User user0 = new User();
    Boolean boolean0 = Boolean.FALSE;
    Integer integer0 = ((-4595));
    Address address0 = new Address((Long) null, country0, province0, "Oz", "Oz", "Oz", "x%X@dgH$^*DMj,8cZ?", user0,
        false, boolean0, integer0);
    address0.getPostcode();
    assertFalse(address0.isEnabled());
  }

  @Test
  @Timeout(4)
  void test26() throws Throwable {
    Address address0 = new Address();
    address0.getDefaultAddress();
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=null, street=null, number=null, enabled=true, defaultAddress=false)",
        address0.toString());
  }

  @Test
  @Timeout(4)
  void test27() throws Throwable {
    Address address0 = new Address();
    address0.getCity();
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=null, street=null, number=null, enabled=true, defaultAddress=false)",
        address0.toString());
  }

  @Test
  @Timeout(4)
  void test28() throws Throwable {
    Long long0 = (0L);
    Country country0 = new Country();
    Province province0 = new Province();
    Boolean boolean0 = Boolean.TRUE;
    User user0 = new User();
    Address address0 = new Address(long0, country0, province0, "", "", "", "", user0, false, boolean0,
        (Integer) null);
    address0.getStreet();
    assertEquals(
        "Address(id=0, country=Country(id=null, code=null, i18n=null, version=null), province=Province(id=null, code=null, i18n=null, capitalProvincia=null, comunidad=null), city=, postcode=, street=, number=, enabled=false, defaultAddress=true)",
        address0.toString());
  }

  @Test
  @Timeout(4)
  void test29() throws Throwable {
    Address address0 = new Address();
    boolean boolean0 = address0.isEnabled();
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=null, street=null, number=null, enabled=true, defaultAddress=false)",
        address0.toString());
    assertTrue(boolean0);
  }

  @Test
  @Timeout(4)
  void test30() throws Throwable {
    Address address0 = new Address();
    address0.getCountry();
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=null, street=null, number=null, enabled=true, defaultAddress=false)",
        address0.toString());
  }

  @Test
  @Timeout(4)
  void test31() throws Throwable {
    Address address0 = new Address();
    Address address1 = new Address();
    User user0 = new User();
    address0.setUser(user0);
    assertFalse(address0.equals((Object) address1));

    address1.setUser(user0);
    boolean boolean0 = address1.equals(address0);
    assertTrue(address0.equals((Object) address1));
    assertTrue(boolean0);
  }

  @Test
  @Timeout(4)
  void test32() throws Throwable {
    Address address0 = new Address();
    Address address1 = new Address();
    assertTrue(address1.equals((Object) address0));

    User user0 = new User();
    address1.setUser(user0);
    boolean boolean0 = address0.equals(address1);
    assertFalse(boolean0);
  }

  @Test
  @Timeout(4)
  void test33() throws Throwable {
    Address address0 = new Address();
    Address address1 = new Address();
    assertTrue(address1.equals((Object) address0));

    User user0 = new User();
    address1.setUser(user0);
    boolean boolean0 = address1.equals(address0);
    assertFalse(boolean0);
  }

  @Test
  @Timeout(4)
  void test34() throws Throwable {
    Address address0 = new Address();
    Address address1 = new Address();
    address1.setNumber("eus.solaris.solaris.domain.Address");
    boolean boolean0 = address0.equals(address1);
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=null, street=null, number=eus.solaris.solaris.domain.Address, enabled=true, defaultAddress=false)",
        address1.toString());
    assertFalse(boolean0);
  }

  @Test
  @Timeout(4)
  void test35() throws Throwable {
    Address address0 = new Address();
    Address address1 = new Address();
    address1.setNumber("s7T>)Ga[`,M/w");
    boolean boolean0 = address1.equals(address0);
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=null, street=null, number=s7T>)Ga[`,M/w, enabled=true, defaultAddress=false)",
        address1.toString());
    assertFalse(boolean0);
  }

  @Test
  @Timeout(4)
  void test36() throws Throwable {
    Address address0 = new Address();
    Address address1 = new Address();
    address1.setStreet("e$:E`Zt=dlM63`WA)");
    address0.setStreet("e$:E`Zt=dlM63`WA)");
    boolean boolean0 = address1.equals(address0);
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=null, street=e$:E`Zt=dlM63`WA), number=null, enabled=true, defaultAddress=false)",
        address1.toString());
    assertTrue(boolean0);
  }

  @Test
  @Timeout(4)
  void test37() throws Throwable {
    Address address0 = new Address();
    Address address1 = new Address();
    address1.setStreet("");
    boolean boolean0 = address0.equals(address1);
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=null, street=, number=null, enabled=true, defaultAddress=false)",
        address1.toString());
    assertFalse(boolean0);
  }

  @Test
  @Timeout(4)
  void test38() throws Throwable {
    Address address0 = new Address();
    Address address1 = new Address();
    address1.setStreet("e$:E`Zt=dlM63`WA)");
    boolean boolean0 = address1.equals(address0);
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=null, street=e$:E`Zt=dlM63`WA), number=null, enabled=true, defaultAddress=false)",
        address1.toString());
    assertFalse(boolean0);
  }

  @Test
  @Timeout(4)
  void test39() throws Throwable {
    Address address0 = new Address();
    address0.setPostcode("Fu%y,Hf{9tNXn}");
    Address address1 = new Address();
    address1.setPostcode("Fu%y,Hf{9tNXn}");
    boolean boolean0 = address0.equals(address1);
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=Fu%y,Hf{9tNXn}, street=null, number=null, enabled=true, defaultAddress=false)",
        address1.toString());
    assertTrue(boolean0);
  }

  @Test
  @Timeout(4)
  void test40() throws Throwable {
    Address address0 = new Address();
    Address address1 = new Address();
    address0.setPostcode("{}uay_9");
    boolean boolean0 = address1.equals(address0);
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode={}uay_9, street=null, number=null, enabled=true, defaultAddress=false)",
        address0.toString());
    assertFalse(boolean0);
  }

  @Test
  @Timeout(4)
  void test41() throws Throwable {
    Address address0 = new Address();
    address0.setCity("eus.solaris.solaris.domain.Address");
    Address address1 = new Address();
    address1.setCity("eus.solaris.solaris.domain.Address");
    boolean boolean0 = address0.equals(address1);
    assertEquals(
        "Address(id=null, country=null, province=null, city=eus.solaris.solaris.domain.Address, postcode=null, street=null, number=null, enabled=true, defaultAddress=false)",
        address1.toString());
    assertTrue(boolean0);
  }

  @Test
  @Timeout(4)
  void test42() throws Throwable {
    Address address0 = new Address();
    address0.setCity("eus.solaris.solaris.domain.Address");
    Address address1 = new Address();
    boolean boolean0 = address1.equals(address0);
    assertEquals(
        "Address(id=null, country=null, province=null, city=eus.solaris.solaris.domain.Address, postcode=null, street=null, number=null, enabled=true, defaultAddress=false)",
        address0.toString());
    assertFalse(boolean0);
  }

  @Test
  @Timeout(4)
  void test43() throws Throwable {
    Address address0 = new Address();
    Province province0 = new Province();
    address0.setProvince(province0);
    Address address1 = new Address();
    address1.setProvince(province0);
    boolean boolean0 = address1.equals(address0);
    assertEquals(
        "Address(id=null, country=null, province=Province(id=null, code=null, i18n=null, capitalProvincia=null, comunidad=null), city=null, postcode=null, street=null, number=null, enabled=true, defaultAddress=false)",
        address1.toString());
    assertTrue(boolean0);
  }

  @Test
  @Timeout(4)
  void test44() throws Throwable {
    Address address0 = new Address();
    Province province0 = new Province();
    Address address1 = new Address();
    address1.setProvince(province0);
    boolean boolean0 = address1.equals(address0);
    assertEquals(
        "Address(id=null, country=null, province=Province(id=null, code=null, i18n=null, capitalProvincia=null, comunidad=null), city=null, postcode=null, street=null, number=null, enabled=true, defaultAddress=false)",
        address1.toString());
    assertFalse(boolean0);
  }

  @Test
  @Timeout(4)
  void test45() throws Throwable {
    Address address0 = new Address();
    Address address1 = new Address();
    Country country0 = new Country();
    address1.setCountry(country0);
    boolean boolean0 = address0.equals(address1);
    assertEquals(
        "Address(id=null, country=Country(id=null, code=null, i18n=null, version=null), province=null, city=null, postcode=null, street=null, number=null, enabled=true, defaultAddress=false)",
        address1.toString());
    assertFalse(boolean0);
  }

  @Test
  @Timeout(4)
  void test46() throws Throwable {
    Address address0 = new Address();
    Country country0 = new Country();
    Address address1 = new Address();
    address1.setCountry(country0);
    boolean boolean0 = address1.equals(address0);
    assertEquals(
        "Address(id=null, country=Country(id=null, code=null, i18n=null, version=null), province=null, city=null, postcode=null, street=null, number=null, enabled=true, defaultAddress=false)",
        address1.toString());
    assertFalse(boolean0);
  }

  @Test
  @Timeout(4)
  void test47() throws Throwable {
    Address address0 = new Address();
    Address address1 = new Address();
    assertFalse(address1.getDefaultAddress());

    Boolean boolean0 = Boolean.TRUE;
    address1.setDefaultAddress(boolean0);
    boolean boolean1 = address0.equals(address1);
    assertFalse(boolean1);
  }

  @Test
  @Timeout(4)
  void test48() throws Throwable {
    Address address0 = new Address();
    Address address1 = new Address();
    address1.setDefaultAddress((Boolean) null);
    boolean boolean0 = address1.equals(address0);
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=null, street=null, number=null, enabled=true, defaultAddress=null)",
        address1.toString());
    assertFalse(boolean0);
  }

  @Test
  @Timeout(4)
  void test49() throws Throwable {
    Address address0 = new Address();
    Long long0 = (0L);
    address0.setId(long0);
    Address address1 = new Address();
    address1.setId(long0);
    boolean boolean0 = address1.equals(address0);
    assertEquals(
        "Address(id=0, country=null, province=null, city=null, postcode=null, street=null, number=null, enabled=true, defaultAddress=false)",
        address1.toString());
    assertTrue(boolean0);
  }

  @Test
  @Timeout(4)
  void test50() throws Throwable {
    Address address0 = new Address();
    Long long0 = (3974L);
    address0.setId(long0);
    Address address1 = new Address();
    boolean boolean0 = address1.equals(address0);
    assertEquals(
        "Address(id=3974, country=null, province=null, city=null, postcode=null, street=null, number=null, enabled=true, defaultAddress=false)",
        address0.toString());
    assertFalse(boolean0);
  }

  @Test
  @Timeout(4)
  void test51() throws Throwable {
    Address address0 = new Address();
    Long long0 = (0L);
    Address address1 = new Address();
    address1.setId(long0);
    boolean boolean0 = address1.equals(address0);
    assertEquals(
        "Address(id=0, country=null, province=null, city=null, postcode=null, street=null, number=null, enabled=true, defaultAddress=false)",
        address1.toString());
    assertFalse(boolean0);
  }

  @Test
  @Timeout(4)
  void test52() throws Throwable {
    Address address0 = new Address();
    User user0 = new User();
    Long long0 = ((-957L));
    Comunidad comunidad0 = new Comunidad();
    Integer integer0 = (1);
    Province province0 = new Province(long0, "z", "z", "z", comunidad0, integer0);
    Country country0 = new Country();
    Boolean boolean0 = Boolean.valueOf(false);
    Address address1 = new Address(long0, country0, province0, "", "eus.solaris.solaris.domain.Product",
        "xY)3ZF-(b", "eus.solaris.solaris.domain.Product", user0, false, boolean0, integer0);
    boolean boolean1 = address0.equals(address1);
    assertFalse(boolean1);
    assertEquals("eus.solaris.solaris.domain.Product", address1.getNumber());
    assertEquals("eus.solaris.solaris.domain.Product", address1.getPostcode());
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=null, street=null, number=null, enabled=true, defaultAddress=false)",
        address0.toString());
    assertFalse(address1.isEnabled());
    assertEquals("", address1.getCity());
    assertEquals("xY)3ZF-(b", address1.getStreet());
  }

  @Test
  @Timeout(4)
  void test53() throws Throwable {
    Address address0 = new Address();
    boolean boolean0 = address0.equals("e$:E`Zt=dlM63`WA)");
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=null, street=null, number=null, enabled=true, defaultAddress=false)",
        address0.toString());
    assertFalse(boolean0);
  }

  @Test
  @Timeout(4)
  void test54() throws Throwable {
    Address address0 = new Address();
    boolean boolean0 = address0.equals(address0);
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=null, street=null, number=null, enabled=true, defaultAddress=false)",
        address0.toString());
    assertTrue(boolean0);
  }

  @Test
  @Timeout(4)
  void test55() throws Throwable {
    Address address0 = new Address();
    address0.hashCode();
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=null, street=null, number=null, enabled=true, defaultAddress=false)",
        address0.toString());
  }

  @Test
  @Timeout(4)
  void test56() throws Throwable {
    Country country0 = new Country();
    Province province0 = new Province();
    User user0 = new User();
    Integer integer0 = (1);
    Address address0 = new Address((Long) null, country0, province0, "M4N!HoW?9(4/Ku'y", "M4N!HoW?9(4/Ku'y",
        "M4N!HoW?9(4/Ku'y", "NYQz)", user0, false, (Boolean) null, integer0);
    address0.hashCode();
    assertEquals(
        "Address(id=null, country=Country(id=null, code=null, i18n=null, version=null), province=Province(id=null, code=null, i18n=null, capitalProvincia=null, comunidad=null), city=M4N!HoW?9(4/Ku'y, postcode=M4N!HoW?9(4/Ku'y, street=M4N!HoW?9(4/Ku'y, number=NYQz), enabled=false, defaultAddress=null)",
        address0.toString());
  }

  @Test
  @Timeout(4)
  void test57() throws Throwable {
    Address address0 = new Address();
    Country country0 = new Country();
    address0.setCountry(country0);
    Address address1 = new Address();
    address1.setCountry(country0);
    boolean boolean0 = address1.equals(address0);
    assertEquals(
        "Address(id=null, country=Country(id=null, code=null, i18n=null, version=null), province=null, city=null, postcode=null, street=null, number=null, enabled=true, defaultAddress=false)",
        address1.toString());
    assertTrue(boolean0);
  }

  @Test
  @Timeout(4)
  void test58() throws Throwable {
    Address address0 = new Address();
    address0.setCity("");
    Address address1 = new Address();
    boolean boolean0 = address0.equals(address1);
    assertEquals(
        "Address(id=null, country=null, province=null, city=, postcode=null, street=null, number=null, enabled=true, defaultAddress=false)",
        address0.toString());
    assertFalse(boolean0);
  }

  @Test
  @Timeout(4)
  void test59() throws Throwable {
    Address address0 = new Address();
    Long long0 = (0L);
    address0.setId(long0);
    address0.hashCode();
  }

  @Test
  @Timeout(4)
  void test60() throws Throwable {
    Address address0 = new Address();
    String string0 = address0.toString();
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=null, street=null, number=null, enabled=true, defaultAddress=false)",
        string0);
  }

  @Test
  @Timeout(4)
  void test61() throws Throwable {
    Address address0 = new Address();
    address0.setPostcode("Fu%y,Hf{9tNXn}");
    Address address1 = new Address();
    boolean boolean0 = address0.equals(address1);
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=Fu%y,Hf{9tNXn}, street=null, number=null, enabled=true, defaultAddress=false)",
        address0.toString());
    assertFalse(boolean0);
  }

  @Test
  @Timeout(4)
  void test62() throws Throwable {
    Address address0 = new Address();
    address0.setEnabled(true);
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=null, street=null, number=null, enabled=true, defaultAddress=false)",
        address0.toString());
  }

  @Test
  @Timeout(4)
  void test63() throws Throwable {
    Address address0 = new Address();
    Province province0 = new Province();
    address0.setProvince(province0);
    Address address1 = new Address();
    boolean boolean0 = address1.equals(address0);
    assertEquals(
        "Address(id=null, country=null, province=Province(id=null, code=null, i18n=null, capitalProvincia=null, comunidad=null), city=null, postcode=null, street=null, number=null, enabled=true, defaultAddress=false)",
        address0.toString());
    assertFalse(boolean0);
  }

  @Test
  @Timeout(4)
  void test64() throws Throwable {
    Address address0 = new Address();
    address0.setNumber("s7T>)Ga[`,M/w");
    Address address1 = new Address();
    address1.setNumber("s7T>)Ga[`,M/w");
    boolean boolean0 = address1.equals(address0);
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=null, street=null, number=s7T>)Ga[`,M/w, enabled=true, defaultAddress=false)",
        address1.toString());
    assertTrue(boolean0);
  }

  @Test
  @Timeout(4)
  void test65() throws Throwable {
    Long long0 = (1790L);
    Country country0 = new Country(long0, "", "", (Integer) null);
    User user0 = new User();
    Boolean boolean0 = Boolean.TRUE;
    Address address0 = new Address(long0, country0, (Province) null, "", "", "", "", user0, true, boolean0,
        (Integer) null);
    address0.getVersion();
    assertEquals(
        "Address(id=1790, country=Country(id=1790, code=, i18n=, version=null), province=null, city=, postcode=, street=, number=, enabled=true, defaultAddress=true)",
        address0.toString());
  }

  @Test
  @Timeout(4)
  void test66() throws Throwable {
    Address address0 = new Address();
    User user0 = new User();
    address0.setUser(user0);
    address0.getUser();
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=null, street=null, number=null, enabled=true, defaultAddress=false)",
        address0.toString());
  }

  @Test
  @Timeout(4)
  void test67() throws Throwable {
    Address address0 = new Address();
    address0.setDefaultAddress((Boolean) null);
    Address address1 = new Address();
    address1.setDefaultAddress((Boolean) null);
    boolean boolean0 = address1.equals(address0);
    assertEquals(
        "Address(id=null, country=null, province=null, city=null, postcode=null, street=null, number=null, enabled=true, defaultAddress=null)",
        address1.toString());
    assertTrue(boolean0);
  }
}
