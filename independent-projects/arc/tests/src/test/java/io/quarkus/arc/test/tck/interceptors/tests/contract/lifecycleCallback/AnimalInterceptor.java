package io.quarkus.arc.test.tck.interceptors.tests.contract.lifecycleCallback;


import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AnimalInterceptor {
    private static Set<String> postConstructInterceptorCalledFor = new HashSet<String>();
    private static Set<String> preDestroyInterceptorCalledFor = new HashSet<String>();

    @PostConstruct
    public void postConstruct(InvocationContext ctx) {
        Animal target = (Animal) ctx.getTarget();
        assertNotNull(target.getBar());
        postConstructInterceptorCalledFor.add((target).getAnimalType());
        try {
            ctx.proceed();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AroundInvoke
    public Object intercept(InvocationContext ctx) throws Exception {
        if (ctx.getMethod().getName().equals("echo")) {
            return ctx.proceed() + ctx.getParameters()[0].toString();
        } else {
            return ctx.proceed();
        }
    }

    @PreDestroy
    public void preDestroy(InvocationContext ctx) {
        preDestroyInterceptorCalledFor.add(((Animal) ctx.getTarget()).getAnimalType());
        try {
            ctx.proceed();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isPostConstructInterceptorCalled(String animalType) {
        return postConstructInterceptorCalledFor.contains(animalType);
    }

    public static boolean isPreDestroyInterceptorCalled(String animalType) {
        return preDestroyInterceptorCalledFor.contains(animalType);
    }
}
