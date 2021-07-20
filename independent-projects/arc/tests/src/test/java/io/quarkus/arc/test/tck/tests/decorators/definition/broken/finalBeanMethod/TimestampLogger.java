package io.quarkus.arc.test.tck.tests.decorators.definition.broken.finalBeanMethod;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

@Decorator
public abstract class TimestampLogger implements Logger {

    @Inject
    @Delegate
    private Logger logger;

    public void log(String string) {
        logger.log(string);
    }

}