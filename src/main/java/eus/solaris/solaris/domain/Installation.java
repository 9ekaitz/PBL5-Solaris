package eus.solaris.solaris.domain;

import java.util.List;

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
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
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
  @EqualsAndHashCode.Exclude
  private User installer;

  @OneToMany(mappedBy = "installation", fetch = FetchType.LAZY)
  @OrderBy("id ASC")
  @EqualsAndHashCode.Exclude
  private List<Task> tasks;

  @Column
  @EqualsAndHashCode.Exclude
  private String signature;

  @Version
  @EqualsAndHashCode.Exclude
  private Integer version;
}