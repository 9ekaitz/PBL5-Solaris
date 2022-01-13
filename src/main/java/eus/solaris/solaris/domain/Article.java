package eus.solaris.solaris.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Article implements Comparable<Article>{
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String name;

  @Version
  private Integer version;

  @Override
  public int compareTo(Article arg0) {
    return (int)(this.id - arg0.getId());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Article)) return false;
    Article a = (Article) obj;
    return a.id == this.id;
  }
}
