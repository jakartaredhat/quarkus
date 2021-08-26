package io.quarkus.arc.test.tck.tests.event.resolve.binding;

import javax.enterprise.context.Dependent;
import javax.enterprise.util.AnnotationLiteral;

@Dependent
public class BindingTypeABinding extends AnnotationLiteral<BindingTypeA> implements BindingTypeA {

    private String value;

    public BindingTypeABinding(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}