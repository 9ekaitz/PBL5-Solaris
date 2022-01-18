package eus.solaris.solaris.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class OrderProduct {
  
  @EmbeddedId
  OrderProductKey id;

  @ManyToOne
  @MapsId("orderId")
  @JoinColumn(name="order_id")
  Order order;

  @ManyToOne
  @MapsId("product_id")
  @JoinColumn(name="product_id")
  Article article;

  @Column
  private Integer amount;

  @Version
  private Integer version;
}
