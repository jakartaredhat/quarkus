package io.quarkus.arc.test.tck.interceptors.tests.contract.lifecycleCallback;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.interceptor.Interceptors;

@Interceptors(AnimalInterceptor.class)
@ApplicationScoped
class Hen extends Animal {
    protected static final String HEN = "Hen";

    private static boolean postConstructInterceptorCalled = false;
    private static boolean preDestroyInterceptorCalled = false;

    @PostConstruct
    public void postConstruct() {
        postConstructInterceptorCalled = true;
    }

    public String echo(String message) {
        return message;
    }

    @PreDestroy
    public void preDestroy() {
        preDestroyInterceptorCalled = true;
    }

    public static boolean isPostConstructInterceptorCalled() {
        return postConstructInterceptorCalled;
    }

    public static boolean isPreDestroyInterceptorCalled() {
        return preDestroyInterceptorCalled;
    }

    @Override
    public String getAnimalType() {
        return HEN;
    }
}
