package eus.solaris.solaris.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Address {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "country", nullable = false)
  private String country;

  @Column(name = "province", nullable = false)
  private String province;

  @Column(name = "city", nullable = false)
  private String city;

  @Column(name = "postcode", nullable = false)
  private String postcode;

  @Column(name = "address", nullable = false, length = 128)
  private String address;

  @Column(name = "number", nullable = false, length = 9)
  private String number;

  @ManyToOne(fetch = FetchType.EAGER)
  private User user;
  
  @Column
  private Boolean completed = false;

  @Column
  private Boolean defaultAddress = false;

  @Version
  private Integer version;
}