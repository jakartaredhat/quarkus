package io.quarkus.arc.test.tck.interceptors.tests.contract.lifecycleCallback;

import io.quarkus.arc.test.ArcTestContainer;
import io.quarkus.arc.test.tck.util.AbstractTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;


import static org.junit.jupiter.api.Assertions.assertTrue;

public class LifecycleCallbackInterceptorTest extends AbstractTestUtils {
    @RegisterExtension
    public ArcTestContainer container = new ArcTestContainer(Animal.class, AnimalInterceptor.class, Bar.class, Hen.class);

    @Test
    public void testPostConstructInterceptor() {
        getContextualReference(Hen.class).toString();
        assertTrue(Hen.isPostConstructInterceptorCalled());
        assertTrue(AnimalInterceptor.isPostConstructInterceptorCalled(Hen.HEN));
    }
}
