package io.quarkus.arc.test.tck.tests.extensions.annotated.delivery.broken;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.WithAnnotations;

public class ApplicationObserver {
    public void observeAnything(@WithAnnotations(RequestScoped.class) @Observes String event) {
    }
}
