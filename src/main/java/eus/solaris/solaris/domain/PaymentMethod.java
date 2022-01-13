package eus.solaris.solaris.domain;


import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payment_method")
@Getter @Setter
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Column(name = "number", nullable = false, length = 16)
    private String number;

    @Column(name = "expire_date")
    private Date expireDate;

    @Column(name = "cvc", nullable = false, length = 3)
    private String cvc;

    @Column(name = "default_method")
    private Boolean defaultMethod;

}
