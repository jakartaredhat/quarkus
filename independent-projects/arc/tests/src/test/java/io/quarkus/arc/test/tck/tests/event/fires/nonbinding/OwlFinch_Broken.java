package io.quarkus.arc.test.tck.tests.event.fires.nonbinding;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

@Dependent
public class OwlFinch_Broken {
    @Inject
    @Any
    private Event<String> simpleEvent;

    public void methodThatFiresEvent() {
        simpleEvent.select(new AnimalStereotypeAnnotationLiteral()).fire("An event");
    }
}
