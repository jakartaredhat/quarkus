package io.quarkus.arc.tck;

import jakarta.el.ELContext;
import jakarta.enterprise.inject.spi.BeanManager;
import org.jboss.cdi.tck.spi.EL;

public class ELImpl implements EL {
    @Override
    public <T> T evaluateValueExpression(BeanManager beanManager, String expression, Class<T> expectedType) {
        return null;
    }

    @Override
    public <T> T evaluateMethodExpression(BeanManager beanManager, String expression, Class<T> expectedType, Class<?>[] expectedParamTypes, Object[] expectedParams) {
        return null;
    }

    @Override
    public ELContext createELContext(BeanManager beanManager) {
        return null;
    }
}
