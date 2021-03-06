package eus.solaris.solaris.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;

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
  @OrderBy("id ASC")
  Set<OrderProduct> products;

  @ManyToOne
  private Address address;

  @ManyToOne
  private PaymentMethod paymentMethod;

  @Temporal(value=TemporalType.TIMESTAMP)
  @CreationTimestamp
  @Column
  private Date creationTime;

  @Column
  private Double installationCost;

  @Version
  private Integer version;

}