package eus.solaris.solaris.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Address {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  private Country country;

  @OneToOne(fetch = FetchType.LAZY)
  private Province province;

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