package eus.solaris.solaris.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
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
  Product product;

  @Column
  private Integer amount;

  @Column
  private Double price;

  @Version
  @EqualsAndHashCode.Exclude
  private Integer version;
}
