package io.quarkus.arc.test.tck.tests.lookup.injection.parameterized;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.quarkus.arc.Arc;
import io.quarkus.arc.test.ArcTestContainer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ParameterizedTypesInjectionToParameterizedWithWildcardTest {
    @RegisterExtension
    public ArcTestContainer container = new ArcTestContainer(Dao.class, IntegerStringDao.class, StringDao.class, NumberDao.class,
                                                             IntegerPowered.class, ConsumerWildcard.class, InjectContainer.class);

    @Singleton
    public static class InjectContainer {
        @Inject
        ConsumerWildcard consumer;
    }

    @Test
    public void testInjection() {
        ParameterizedTypesInjectionToParameterizedWithWildcardTest.InjectContainer bean = Arc.container().instance(ParameterizedTypesInjectionToParameterizedWithWildcardTest.InjectContainer.class).get();
        ConsumerWildcard consumer = bean.consumer;

        assertNotNull(consumer.getDao());
        assertEquals(consumer.getDao().getId(), Dao.class.getName());

        assertNotNull(consumer.getIntegerStringDao());
        assertEquals(consumer.getIntegerStringDao().getId(), IntegerStringDao.class.getName());

        assertNotNull(consumer.getNumberDao());
        assertEquals(consumer.getNumberDao().getId(), NumberDao.class.getName());
    }

}
