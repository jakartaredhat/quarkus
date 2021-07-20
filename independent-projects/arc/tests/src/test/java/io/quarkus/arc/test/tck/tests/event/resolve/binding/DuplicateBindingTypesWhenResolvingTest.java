package io.quarkus.arc.test.tck.tests.event.resolve.binding;

import java.util.Set;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.ObserverMethod;

import io.quarkus.arc.test.ArcTestContainer;
import io.quarkus.arc.test.tck.util.AbstractTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DuplicateBindingTypesWhenResolvingTest extends AbstractTestUtils {
    @RegisterExtension
    public ArcTestContainer container = new ArcTestContainer(BindingTypeA.class, BindingTypeABinding.class,
                                                             BindingTypeB.class, BindingTypeBBinding.class,
                                                             BindingTypeC.class, BindingTypeCBinding.class,
                                                             AnEventType.class, AnObserver.class);

    public static class AnEventType {
    }

    public static class AnObserver {
        public boolean wasNotified = false;

        public void observer(@Observes AnEventType event) {
            wasNotified = true;
        }
    }

    @Test
    public void testDuplicateBindingTypesWhenResolvingFails() {
        assertThrows(IllegalArgumentException.class, this::dotestDuplicateBindingTypesWhenResolvingFails, "Did not see IllegalArgumentException");
    }
    private void dotestDuplicateBindingTypesWhenResolvingFails() {
        Set<ObserverMethod<? super AnEventType>>  methods = getCurrentManager().resolveObserverMethods(new AnEventType(), new BindingTypeABinding("a1"), new BindingTypeABinding("a2"));
        System.out.printf("resolved methods: %s\n", methods);
    }
}
