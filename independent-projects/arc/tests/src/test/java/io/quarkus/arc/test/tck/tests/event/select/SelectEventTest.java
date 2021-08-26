package io.quarkus.arc.test.tck.tests.event.select;

import static org.junit.jupiter.api.Assertions.assertThrows;

import io.quarkus.arc.test.ArcTestContainer;
import io.quarkus.arc.test.tck.util.AbstractTestUtils;
import javax.enterprise.util.AnnotationLiteral;
import javax.enterprise.util.TypeLiteral;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class SelectEventTest extends AbstractTestUtils {

    @RegisterExtension
    public ArcTestContainer container = ArcTestContainer.builder()
            .beanClasses(AlarmSystem.class, BreakInEvent.class, NotABindingType.class,
                    SecurityEvent.class, SecurityEvent_Illegal.class,
                    SecuritySensor.class, SystemTest.class, Violent.class)
            .build();

    @Test
    public void testEventSelectReturnsEventOfSameType() {
        AlarmSystem alarm = getContextualReference(AlarmSystem.class);
        alarm.reset();
        SecuritySensor sensor = getContextualReference(SecuritySensor.class);

        sensor.securityEvent.fire(new SecurityEvent());
        assert alarm.getNumSecurityEvents() == 1;
        assert alarm.getNumSystemTests() == 0;
        assert alarm.getNumBreakIns() == 0;
        assert alarm.getNumViolentBreakIns() == 0;

        sensor.securityEvent.select(new SystemTest.SystemTestLiteral("") {
        }).fire(new SecurityEvent());
        assert alarm.getNumSecurityEvents() == 2;
        assert alarm.getNumSystemTests() == 1;
        assert alarm.getNumBreakIns() == 0;
        assert alarm.getNumViolentBreakIns() == 0;

        sensor.securityEvent.select(BreakInEvent.class).fire(new BreakInEvent());
        assert alarm.getNumSecurityEvents() == 3;
        assert alarm.getNumSystemTests() == 1;
        assert alarm.getNumBreakIns() == 1;
        assert alarm.getNumViolentBreakIns() == 0;

        sensor.securityEvent.select(BreakInEvent.class, new AnnotationLiteral<Violent>() {
        }).fire(new BreakInEvent());
        assert alarm.getNumSecurityEvents() == 4;
        assert alarm.getNumSystemTests() == 1;
        assert alarm.getNumBreakIns() == 2;
        assert alarm.getNumViolentBreakIns() == 1;
    }

    @Test
    public <T> void testEventSelectThrowsExceptionIfEventTypeHasTypeVariable() {
        assertThrows(IllegalArgumentException.class, this::dotestEventSelectThrowsExceptionIfEventTypeHasTypeVariable,
                "Did not see IllegalArgumentException");
    }

    private <T> void dotestEventSelectThrowsExceptionIfEventTypeHasTypeVariable() {
        SecuritySensor sensor = getContextualReference(SecuritySensor.class);
        sensor.securityEvent.select(new TypeLiteral<SecurityEvent_Illegal<T>>() {
        });
    }

    @Test
    public void testEventSelectThrowsExceptionForDuplicateBindingType() {
        assertThrows(IllegalArgumentException.class, this::dotestEventSelectThrowsExceptionForDuplicateBindingType,
                "Did not see IllegalArgumentException");
    }

    private void dotestEventSelectThrowsExceptionForDuplicateBindingType() {
        SecuritySensor sensor = getContextualReference(SecuritySensor.class);
        sensor.securityEvent.select(new SystemTest.SystemTestLiteral("a") {
        }, new SystemTest.SystemTestLiteral("b") {
        });
    }

    @Test
    public void testEventSelectWithSubtypeThrowsExceptionForDuplicateBindingType() {
        assertThrows(IllegalArgumentException.class, this::dotestEventSelectWithSubtypeThrowsExceptionForDuplicateBindingType,
                "Did not see IllegalArgumentException");
    }

    private void dotestEventSelectWithSubtypeThrowsExceptionForDuplicateBindingType() {
        SecuritySensor sensor = getContextualReference(SecuritySensor.class);
        sensor.securityEvent.select(BreakInEvent.class, new SystemTest.SystemTestLiteral("a") {
        }, new SystemTest.SystemTestLiteral("b") {
        });
    }

    @Test
    public void testEventSelectThrowsExceptionIfAnnotationIsNotBindingType() {
        assertThrows(IllegalArgumentException.class, this::dotestEventSelectThrowsExceptionIfAnnotationIsNotBindingType,
                "Did not see IllegalArgumentException");
    }

    private void dotestEventSelectThrowsExceptionIfAnnotationIsNotBindingType() {
        SecuritySensor sensor = getContextualReference(SecuritySensor.class);
        sensor.securityEvent.select(new AnnotationLiteral<NotABindingType>() {
        });
    }

    @Test
    public void testEventSelectWithSubtypeThrowsExceptionIfAnnotationIsNotBindingType() {
        assertThrows(IllegalArgumentException.class,
                this::dotestEventSelectWithSubtypeThrowsExceptionIfAnnotationIsNotBindingType,
                "Did not see IllegalArgumentException");
    }

    private void dotestEventSelectWithSubtypeThrowsExceptionIfAnnotationIsNotBindingType() {
        SecuritySensor sensor = getContextualReference(SecuritySensor.class);
        sensor.securityEvent.select(BreakInEvent.class, new AnnotationLiteral<NotABindingType>() {
        });
    }
}
