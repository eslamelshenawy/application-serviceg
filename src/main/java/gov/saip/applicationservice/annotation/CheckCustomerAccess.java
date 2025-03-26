package gov.saip.applicationservice.annotation;

import gov.saip.applicationservice.common.enums.ValidationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckCustomerAccess {
    long supportServiceIdParamIndex() default 0;

    long applicationIdParamIndex() default 0;
    ValidationType type() default ValidationType.APPLICATION;

    long categoryCodeParamIndex() default -1;
}
