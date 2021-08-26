package io.quarkus.arc.test.tck.tests.interceptors.definition.broken.observer.async;

import javax.enterprise.inject.spi.DefinitionException;

import io.quarkus.arc.test.ArcTestContainer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InterceptorWithAsyncObserverMethodTest {
    // @ShouldThrowException(DefinitionException.class)
    @RegisterExtension
    public ArcTestContainer container = ArcTestContainer.builder()
            .beanClasses(TransactionalService.class)
            .additionalClasses(TransactionalInterceptor.class)
            .shouldFail()
            .build();

    @Test
    //@SpecAssertion(section = OBSERVES, id = "h")
    public void testInterceptorWithAsyncObserverMethodNotOk() {
        Throwable error = container.getFailure();
        assertNotNull(error);
        assertTrue(error instanceof DefinitionException);
    }
}
