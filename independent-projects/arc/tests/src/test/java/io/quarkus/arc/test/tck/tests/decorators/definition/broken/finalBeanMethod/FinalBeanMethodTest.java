package io.quarkus.arc.test.tck.tests.decorators.definition.broken.finalBeanMethod;

import javax.enterprise.inject.spi.DeploymentException;

import io.quarkus.arc.test.ArcTestContainer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FinalBeanMethodTest {
    // @ShouldThrowException(DeploymentException.class)
    @RegisterExtension
    public ArcTestContainer container = ArcTestContainer.builder()
            .beanClasses(MockLogger.class, TimestampLogger.class)
            .additionalClasses(Logger.class)
            .shouldFail()
            .build();

    @Test
    //@SpecAssertion(section = DECORATOR_RESOLUTION, id = "ac")
    public void testAppliesToFinalMethodOnManagedBeanClass() {
        Throwable error = container.getFailure();
        assertNotNull(error, "There should be a build failure");
        System.out.printf("Error: %s\n", error);
        assertTrue(error instanceof DeploymentException, "Error is a DeploymentException");
    }
}
