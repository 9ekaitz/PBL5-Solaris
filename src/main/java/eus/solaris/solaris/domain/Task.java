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
public class Task implements Comparable<Task>{
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String description;

  @Column
  private Boolean completed = false;

  @ManyToOne
  @JoinColumn(name = "installation", nullable = false)
  private Installation installation;

  @Version
  private Integer version;

  @Override
  public int compareTo(Task arg0) {
    return (int) (this.id - arg0.getId());
  }


}
