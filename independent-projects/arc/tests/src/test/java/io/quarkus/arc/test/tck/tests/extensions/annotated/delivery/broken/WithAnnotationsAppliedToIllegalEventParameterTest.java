package io.quarkus.arc.test.tck.tests.extensions.annotated.delivery.broken;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.quarkus.arc.test.ArcTestContainer;
import javax.enterprise.inject.spi.DefinitionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class WithAnnotationsAppliedToIllegalEventParameterTest {
    // @ShouldThrowException(DefinitionException.class)
    @RegisterExtension
    public ArcTestContainer container = ArcTestContainer.builder()
            .beanClasses(ApplicationObserver.class)
            .shouldFail()
            .build();

    @Test
    public void testDeploymentFails() {
        Throwable error = container.getFailure();
        assertNotNull(error);
        assertTrue(error instanceof DefinitionException);
    }
}
