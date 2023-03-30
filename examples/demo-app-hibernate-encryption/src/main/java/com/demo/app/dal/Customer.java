package com.demo.app.dal;

import com.piiano.vault.orm.encryption.Encrypted;
import lombok.*;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

import static com.piiano.vault.orm.encryption.Encrypted.*;

@Table(name = "customers")
@Entity
@TypeDef(name = "Encrypted", typeClass = Encrypted.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    @Type(type = "Encrypted", parameters = {@Parameter(name = PROPERTY, value = "name")})
    private String name;

    @Column(name = "email")
    @Type(type = "Encrypted", parameters = {@Parameter(name = PROPERTY, value = "email")})
    private String email;

    @Column(name = "phone")
    @Type(type = "Encrypted", parameters = {@Parameter(name = PROPERTY, value = "phone")})
    private String phone;

    @Column(name = "ssn")
    @Type(type = "Encrypted", parameters = {@Parameter(name = PROPERTY, value = "ssn")})
    private String ssn;

    @Column(name = "ssn_masked")
    @Type(type = "Encrypted", parameters = {@Parameter(name = PROPERTY, value = "ssn.mask")})
    private String ssnMasked;

    @Column(name = "dob")
    @Type(type = "Encrypted", parameters = {@Parameter(name = PROPERTY, value = "dob")})
    private String dob;

    @Column(name = "state")
    private String state;

    public void setSsn(String ssn) {
        this.ssn = ssn;
        this.ssnMasked = ssn;
    }
}
