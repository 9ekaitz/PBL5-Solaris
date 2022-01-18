package eus.solaris.solaris.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderProductKey implements Serializable{
  
  private static final long serialVersionUID = -6042347061246458317L;

  @Column(name="order_id")
  Long orderId;

  @Column(name= "product_id")
  Long productId;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof OrderProductKey)) return false;
    OrderProductKey key = (OrderProductKey) obj;
    return key.orderId == this.orderId && key.productId == this.productId;
  }

  @Override
  public int hashCode() {
    String  key = String.valueOf(productId) + String.valueOf(orderId);
    return key.hashCode();
  }
}
