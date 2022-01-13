package eus.solaris.solaris.domain;

import java.io.Serializable;

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
public class PaymentMethod implements Serializable{

    private static final long serialVersionUID = -8995643663615104937L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Column(name = "card_holder_name")
    private String cardHolderName;

    @Column(name = "card_number", nullable = false, length = 16)
    private String cardNumber;

    @Column(name = "expire_date_month", nullable = false)
    private Long expirationMonth;

    @Column(name = "expire_date_year", nullable = false)
    private Long expirationYear;

    @Column(name = "security_code", nullable = false, length = 3)
    private String securityCode;

    @Column(name = "default_method")
    private Boolean defaultMethod = false;

    @Column(name = "enabled")
    private boolean enabled = true;
}
