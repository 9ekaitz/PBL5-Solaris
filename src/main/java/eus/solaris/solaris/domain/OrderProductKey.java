package eus.solaris.solaris.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode
public class OrderProductKey implements Serializable{
  
  private static final long serialVersionUID = -6042347061246458317L;

  @Column(name="order_id")
  Long orderId;

  @Column(name= "product_id")
  Long productId;

}
