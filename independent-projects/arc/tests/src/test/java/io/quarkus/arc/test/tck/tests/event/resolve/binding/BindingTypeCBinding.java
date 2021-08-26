package io.quarkus.arc.test.tck.tests.event.resolve.binding;

import javax.enterprise.context.Dependent;
import javax.enterprise.util.AnnotationLiteral;

@Dependent
public class BindingTypeCBinding extends AnnotationLiteral<BindingTypeC> implements BindingTypeC {
    private String value;

    public BindingTypeCBinding(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
