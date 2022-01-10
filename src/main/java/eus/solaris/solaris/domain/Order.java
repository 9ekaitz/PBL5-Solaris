package eus.solaris.solaris.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "`order`")
@Getter @Setter
public class Order {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(mappedBy = "order", fetch = FetchType.LAZY)
  private Installation installation;

  @ManyToOne
  @JoinColumn(name = "owner", nullable = false)
  private User owner;
  
  @OneToMany(mappedBy = "order")
  Set<OrderArticle> articles;


  @ManyToOne
  private Address address;

  @Version
  private Integer version;

}
