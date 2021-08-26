package io.quarkus.arc.test.tck.interceptors.tests.order.overriden.lifecycleCallback;

import java.util.concurrent.atomic.AtomicInteger;
import javax.enterprise.context.Dependent;

@Dependent
public class Falcon extends Bird {

    private static AtomicInteger initFalconCalled = new AtomicInteger();
    private static AtomicInteger destroyFalconCalled = new AtomicInteger();

    @Override
    public void init() {
        initFalconCalled.incrementAndGet();
        System.out.printf("Falcon.postConstruct(%d)\n", initFalconCalled.get());
    }

    @Override
    public void destroy() {
        destroyFalconCalled.incrementAndGet();
        System.out.printf("Falcon.preDestroy(%d)\n", destroyFalconCalled.get());
    }

    public void ping() {
    }

    public static AtomicInteger getInitFalconCalled() {
        return initFalconCalled;
    }

    public static AtomicInteger getDestroyFalconCalled() {
        return destroyFalconCalled;
    }

    public static void reset() {
        initFalconCalled.set(0);
        destroyFalconCalled.set(0);
        System.out.printf("Falcon.reset(%d, %d)\n", initFalconCalled.get(), destroyFalconCalled.get());
    }

}
