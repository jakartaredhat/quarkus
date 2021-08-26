package io.quarkus.arc.test.tck.tests.event.resolve.binding;

import javax.enterprise.context.Dependent;
import javax.enterprise.util.AnnotationLiteral;

@Dependent
public class BindingTypeBBinding extends AnnotationLiteral<BindingTypeB> implements BindingTypeB {
}