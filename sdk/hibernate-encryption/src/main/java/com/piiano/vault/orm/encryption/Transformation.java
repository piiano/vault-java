package com.piiano.vault.orm.encryption;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Transformation{

    // The property name of the field that is annotated as Encrypted and will be transformed (e.g. "ssn")
    String property() default "";
    // The name of the transformer to be used (e.g. "mask")
    String transformer() default "";
}