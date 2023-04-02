package com.demo.app.dal;

import com.piiano.vault.orm.encryption.Encrypted;
import com.piiano.vault.orm.encryption.Transformation;
import lombok.*;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

import static com.piiano.vault.orm.encryption.Encrypted.COLLECTION;

@Table(name = "customers")
@Entity
@TypeDef(name = "Encrypted", typeClass = Encrypted.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    // The following properties are persisted in the database.
    // Those that are annotated with @Type(type = "Encrypted") will be encrypted by Vault and persisted as ciphertext.
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    @Type(type = "Encrypted", parameters = {@Parameter(name = COLLECTION, value = "customers")})
    private String name;

    @Column(name = "ssn")
    @Type(type = "Encrypted")
    private String ssn;

    @Column(name = "email")
    @Type(type = "Encrypted")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "dob")
    private String dob;

    @Column(name = "state")
    private String state;

    // The following properties are not persisted in the database.
    // They are automatically calculated by calling Vault to decrypt the field named by "property",
    // applying the transformer named by "transformer".
    @Transient
    @Transformation(property = "ssn", transformer = "mask")
    private String ssnMask;

    @Transient
    @Transformation(property = "email", transformer = "mask")
    private String emailMask;
}
