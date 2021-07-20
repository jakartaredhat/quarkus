package io.quarkus.arc.test.tck.tests.event.select;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Qualifier;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE, METHOD, PARAMETER, FIELD })
@Retention(RUNTIME)
@Documented
@Qualifier
public @interface SystemTest {

    String value() default "";

    public static class SystemTestLiteral extends AnnotationLiteral<SystemTest> implements SystemTest {

        private String value;

        public SystemTestLiteral(String value){
            this.value=value;
        }

        public String value(){
            return value;
        }
    }
}