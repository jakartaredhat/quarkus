package io.quarkus.arc.test.tck.interceptors.tests.order.overriden.lifecycleCallback;

import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Dependent;

@Dependent
public class Eagle extends Bird {

    private static AtomicInteger initEagleCalled = new AtomicInteger();
    private static AtomicInteger destroyEagleCalled = new AtomicInteger();

    @Override
    @PostConstruct
    public void init() {
        initEagleCalled.incrementAndGet();
        System.out.printf("Eagle.postConstruct(%d)\n", initEagleCalled.get());
    }

    @Override
    @PreDestroy
    public void destroy() {
        destroyEagleCalled.incrementAndGet();
        System.out.printf("Eagle.preDestroy(%d)\n", destroyEagleCalled.get());
    }

    public void ping() {
    }

    public static AtomicInteger getInitEagleCalled() {
        return initEagleCalled;
    }

    public static AtomicInteger getDestroyEagleCalled() {
        return destroyEagleCalled;
    }

    public static void reset() {
        initEagleCalled.set(0);
        destroyEagleCalled.set(0);
        System.out.printf("Eagle.reset(%d, %d)\n", initEagleCalled.get(), destroyEagleCalled.get());
    }

}
