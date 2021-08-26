package io.quarkus.arc.test.tck.interceptors.tests.contract.lifecycleCallback;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public abstract class Animal {

    @Inject
    protected Bar bar;

    void foo() {
    }

    public Bar getBar() {
        return bar;
    }

    public abstract String getAnimalType();
}
