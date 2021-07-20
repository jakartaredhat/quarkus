package io.quarkus.arc.test.tck.tests.event.select;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

public class SecuritySensor {
    @Inject
    @Any
    Event<SecurityEvent> securityEvent;
}
