package io.quarkus.arc.test.tck.tests.event.fires.nonbinding;

import static org.junit.jupiter.api.Assertions.assertThrows;

import io.quarkus.arc.test.ArcTestContainer;
import io.quarkus.arc.test.tck.util.AbstractTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class NonBindingTypePassedToFireTest extends AbstractTestUtils {

    @RegisterExtension
    public ArcTestContainer container = new ArcTestContainer(AnimalStereotype.class, AnimalStereotypeAnnotationLiteral.class,
            OwlFinch_Broken.class);

    @Test
    public void testExceptionThrownIfNonBindingTypePassedToFire() {
        assertThrows(IllegalArgumentException.class, this::dotestExceptionThrownIfNonBindingTypePassedToFire,
                "Did not see IllegalArgumentException");
    }

    private void dotestExceptionThrownIfNonBindingTypePassedToFire() throws IllegalArgumentException {
        OwlFinch_Broken bean = getContextualReference(OwlFinch_Broken.class);
        bean.methodThatFiresEvent();
    }
}
