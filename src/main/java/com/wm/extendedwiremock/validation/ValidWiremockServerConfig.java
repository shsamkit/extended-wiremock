package com.wm.extendedwiremock.validation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.wm.extendedwiremock.validation.implementation.WiremockServerConfigValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = WiremockServerConfigValidator.class)
@Documented
public @interface ValidWiremockServerConfig {
	String message() default "Provide a valid wiremock server config: If stub initializr is used, wm.server.mappingsFilesDir can not be blank";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}