package com.qaroni.demoitests.support.json;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@ExtendWith({JsonAssertionParameterResolver.class})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface JsonAssertions {
    String basePath() default "src/test/resources";
}
