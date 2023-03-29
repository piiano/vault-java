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
    @Type(type = "Encrypted", parameters = {@Parameter(name = TYPE, value = "deterministic"), @Parameter(name = COLLECTION, value = "customers"), @Parameter(name = PROPERTY, value = "name")})
    private String name;

    @Column(name = "phone_number")
    @Type(type = "Encrypted", parameters = {@Parameter(name = TYPE, value = "deterministic"), @Parameter(name = COLLECTION, value = "customers"), @Parameter(name = PROPERTY, value = "phone_number")})
    private String phoneNumber;

    @Column(name = "masked_phone_number")
    @Type(type = "Encrypted", parameters = {@Parameter(name = TYPE, value = "deterministic"), @Parameter(name = COLLECTION, value = "customers"), @Parameter(name = PROPERTY, value = "phone_number.mask")})
    private String maskedPhoneNumber;

    @Column(name = "country")
    private String country;

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.maskedPhoneNumber = phoneNumber;
    }
}
