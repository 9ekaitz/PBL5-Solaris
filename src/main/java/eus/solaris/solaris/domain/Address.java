package eus.solaris.solaris.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor 
@Generated
public class Address implements Serializable{

  private static final long serialVersionUID = 918119632427824096L;

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

  @Column(name = "street", nullable = false, length = 128)
  private String street;

  @Column(name = "number", nullable = false, length = 9)
  private String number;

  @ManyToOne(fetch = FetchType.EAGER)
  private User user;

  @Column(name = "enabled")
  private boolean enabled = true;

  @Column
  private Boolean defaultAddress = false;

  @Version
  @lombok.EqualsAndHashCode.Exclude
  private Integer version;
}
