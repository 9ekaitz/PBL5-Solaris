package eus.solaris.solaris.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Installation {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String name;

  @Column
  private String description;

  @Column
  private Boolean completed = false;

  @OneToOne
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @ManyToOne
  @JoinColumn(name = "installer", nullable = false)
  private User installer;

  @Version
  private Integer version;
}