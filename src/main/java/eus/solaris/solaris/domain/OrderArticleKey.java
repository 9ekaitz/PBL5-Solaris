package eus.solaris.solaris.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderArticleKey implements Serializable{
  
  private static final long serialVersionUID = -6042347061246458317L;

  @Column(name="order_id")
  Long orderId;

  @Column(name= "article_id")
  Long articleId;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof OrderArticleKey)) return false;
    OrderArticleKey key = (OrderArticleKey) obj;
    return key.orderId == this.orderId && key.articleId == this.articleId;
  }

  @Override
  public int hashCode() {
    String  key = String.valueOf(articleId) + String.valueOf(orderId);
    return key.hashCode();
  }
}
