package io.quarkus.arc.test.tck.tests.decorators.definition.broken.nonDependent;

import javax.annotation.Priority;
import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@Decorator
@RequestScoped
@Priority(2000)
public class FooServiceDecorator implements FooService {

    @Inject
    @Delegate
    private FooService service;

    public void ping() {
    }
}
