package eus.solaris.solaris.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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

  @Column
  private String country;

  @Column
  private String province;

  @Column
  private String city;

  @Column(name="postcode")
  private String postcode;

  @Column
  private String street;
  
  @Column
  private Boolean completed = false;

  @ManyToOne
  @JoinColumn(name = "`user`", nullable = false)
  private User user;

  @Version
  private Integer version;
}
