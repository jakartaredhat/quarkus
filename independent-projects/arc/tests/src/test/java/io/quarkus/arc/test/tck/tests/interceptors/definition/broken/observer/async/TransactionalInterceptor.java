package io.quarkus.arc.test.tck.tests.interceptors.definition.broken.observer.async;

import javax.annotation.Priority;
import javax.enterprise.event.ObservesAsync;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import io.quarkus.arc.test.tck.util.SimpleLogger;

@Transactional
@Interceptor
@Priority(2500)
public class TransactionalInterceptor {

    private static final SimpleLogger logger = new SimpleLogger(TransactionalInterceptor.class);

    @AroundInvoke
    public Object alwaysReturnThis(InvocationContext ctx) throws Exception {
        return ctx.proceed();
    }

    public void observeFoo(@ObservesAsync String message) {
        logger.log("OBSERVED");
    }
}
