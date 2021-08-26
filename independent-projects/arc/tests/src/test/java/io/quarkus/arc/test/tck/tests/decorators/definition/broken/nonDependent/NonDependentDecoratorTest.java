package io.quarkus.arc.test.tck.tests.decorators.definition.broken.nonDependent;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.quarkus.arc.test.ArcTestContainer;
import javax.enterprise.inject.spi.DefinitionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class NonDependentDecoratorTest {
    // @ShouldThrowException(DefinitionException.class)
    @RegisterExtension
    public ArcTestContainer container = ArcTestContainer.builder()
            .beanClasses(FooServiceDecorator.class)
            .additionalClasses(FooService.class)
            .shouldFail()
            .build();

    @Test
    //@SpecAssertion(section = DECORATOR_BEAN, id = "f")
    public void testDeploymentWithScopedDecorator() {
        Throwable error = container.getFailure();
        assertNotNull(error);
        System.out.printf("Error: %s\n", error);
        assertTrue(error instanceof DefinitionException, "Error is a DefinitionException");
    }
}
