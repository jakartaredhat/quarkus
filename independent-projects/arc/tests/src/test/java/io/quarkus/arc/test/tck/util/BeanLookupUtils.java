package io.quarkus.arc.test.tck.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Set;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.UnsatisfiedResolutionException;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.TypeLiteral;

public final class BeanLookupUtils {

    private BeanLookupUtils() {
    }

    /**
     *
     * @param beanManager
     * @param beanType
     * @param qualifiers
     * @return
     */
    public static <T> T getContextualReference(BeanManager beanManager, Class<T> beanType, Annotation... qualifiers) {
        Set<Bean<?>> beans = getBeans(beanManager, beanType, qualifiers);
        return BeanLookupUtils.<T>getContextualReference(beanManager, beanType, beans);
    }

    /**
     *
     * @param beanManager
     * @param beanTypeLiteral
     * @param qualifiers
     * @return
     */
    public static <T> T getContextualReference(BeanManager beanManager, TypeLiteral<T> beanTypeLiteral,
                                               Annotation... qualifiers) {
        Type beanType = beanTypeLiteral.getType();
        Set<Bean<?>> beans = getBeans(beanManager, beanType, qualifiers);
        return BeanLookupUtils.<T>getContextualReference(beanManager, beanType, beans);
    }

    /**
     *
     * @param beanManager
     * @param beanType
     * @param name
     * @return
     */
    public static <T> T getContextualReference(BeanManager beanManager, String name, Class<T> beanType) {

        Set<Bean<?>> beans = beanManager.getBeans(name);

        if (beans == null || beans.isEmpty()) {
            return null;
        }
        return BeanLookupUtils.<T>getContextualReference(beanManager, beanType, beans);
    }

    private static Set<Bean<?>> getBeans(BeanManager beanManager, Type beanType, Annotation... qualifiers) {

        Set<Bean<?>> beans = beanManager.getBeans(beanType, qualifiers);

        if (beans == null || beans.isEmpty()) {
            throw new UnsatisfiedResolutionException(String.format(
                    "No bean matches required type %s and required qualifiers %s", beanType, Arrays.toString(qualifiers)));
        }
        return beans;
    }

    @SuppressWarnings("unchecked")
    private static <T> T getContextualReference(BeanManager beanManager, Type beanType, Set<Bean<?>> beans) {

        Bean<?> bean = beanManager.resolve(beans);

        if (bean.getScope().equals(Dependent.class)) {
            System.out.println("Dependent contextual instance cannot be properly destroyed!");
        }

        CreationalContext<?> creationalContext = beanManager.createCreationalContext(bean);

        return ((T) beanManager.getReference(bean, beanType, creationalContext));
    }

}
