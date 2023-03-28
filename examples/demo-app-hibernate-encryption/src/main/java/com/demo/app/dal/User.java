package com.demo.app.dal;

import com.piiano.vault.orm.encryption.Encrypted;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

import static com.piiano.vault.orm.encryption.Encrypted.*;

@Table(name = "users")
@Entity
@TypeDef(name = "Encrypted", typeClass = Encrypted.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    public static final String USERS = "users";
    public static final String NAME = "name";
    public static final String PHONE_NUMBER = "phone_number";

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    @Type(type = "Encrypted", parameters = {@Parameter(name = TYPE, value = "deterministic"), @Parameter(name = COLLECTION, value = USERS), @Parameter(name = PROPERTY, value = NAME)})
    private String name;

    @Column(name = "phone_number")
    @Type(type = "Encrypted", parameters = {@Parameter(name = TYPE, value = "deterministic"), @Parameter(name = COLLECTION, value = USERS), @Parameter(name = PROPERTY, value = PHONE_NUMBER)})
    private String phoneNumber;

    @Column(name = "country")
    private String country;
}
