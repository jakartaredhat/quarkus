package io.quarkus.arc.test.tck.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.inject.AmbiguousResolutionException;
import javax.enterprise.inject.UnsatisfiedResolutionException;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.TypeLiteral;

import io.quarkus.arc.Arc;

public class AbstractTestUtils {

    /**
     * Note that TCK uses Arquillian Servlet protocol and so the test class is instantiated/enriched from within the WAR module.
     * This affects most of the {@link BeanManager} methods functionality. For instance the method
     * {@link BeanManager#getBeans(Type, Annotation...)} returns the set of beans which are available for injection in the
     * module or library containing the class into which the {@link BeanManager} was injected (simplified).
     *
     * @return the current {@link BeanManager}
     */
    public BeanManager getCurrentManager() {
        return Arc.container().beanManager();
    }

    /**
     * Checks if all annotations are in a given set of annotations
     *
     * @param annotations The annotation set
     * @param requiredAnnotationTypes The annotations to match
     * @return True if match, false otherwise
     */
    public boolean annotationSetMatches(Set<? extends Annotation> annotations,
                                           Class<? extends Annotation>... requiredAnnotationTypes) {
        Set<Class<? extends Annotation>> annotationsTypeSet = new HashSet<Class<? extends Annotation>>();
        for (Annotation annotation : annotations) {
            annotationsTypeSet.add(annotation.annotationType());
        }
        return typeSetMatches(annotationsTypeSet, requiredAnnotationTypes);
    }

    /**
     * @param annotations The annotation set
     * @param requiredAnnotations The required annotations
     * @return <code>true</code> if the specified set matches required annotations, <code>false</code> otherwise
     */
    public boolean annotationSetMatches(Set<? extends Annotation> annotations, Annotation... requiredAnnotations) {
        List<Annotation> requiredAnnotationList = new ArrayList<Annotation>();
        return requiredAnnotations.length == annotations.size() && annotations.containsAll(requiredAnnotationList);
    }

    public boolean rawTypeSetMatches(Set<Type> types, Class<?>... requiredTypes) {
        Set<Type> typesRawSet = new HashSet<Type>();
        for (Type type : types) {
            if (type instanceof Class<?>) {
                typesRawSet.add(type);
            } else if (type instanceof ParameterizedType) {
                typesRawSet.add(((ParameterizedType) type).getRawType());
            }
        }
        return typeSetMatches(typesRawSet, requiredTypes);
    }

    public boolean typeSetMatches(Collection<? extends Type> types, Type... requiredTypes) {
        List<Type> typeList = Arrays.asList(requiredTypes);
        return requiredTypes.length == types.size() && types.containsAll(typeList);
    }

    public <T> Bean<T> getUniqueBean(Class<T> type, Annotation... bindings) {
        Set<Bean<T>> beans = getBeans(type, bindings);
        return resolveUniqueBean(type, beans);
    }

    public <T> Bean<T> getUniqueBean(TypeLiteral<T> type, Annotation... bindings) {
        Set<Bean<T>> beans = getBeans(type, bindings);
        return resolveUniqueBean(type.getType(), beans);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T> Set<Bean<T>> getBeans(Class<T> type, Annotation... bindings) {
        return (Set) getCurrentManager().getBeans(type, bindings);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T> Set<Bean<T>> getBeans(TypeLiteral<T> type, Annotation... bindings) {
        return (Set) getCurrentManager().getBeans(type.getType(), bindings);
    }

    public <T> T getContextualReference(Class<T> beanType, Annotation... qualifiers) {
        return BeanLookupUtils.<T>getContextualReference(getCurrentManager(), beanType, qualifiers);
    }

    public <T> T getContextualReference(TypeLiteral<T> beanType, Annotation... qualifiers) {
        return BeanLookupUtils.<T>getContextualReference(getCurrentManager(), beanType, qualifiers);
    }

    public <T> T getContextualReference(String name, Class<T> beanType) {
        return BeanLookupUtils.<T>getContextualReference(getCurrentManager(), name, beanType);
    }

    private <T> Bean<T> resolveUniqueBean(Type type, Set<Bean<T>> beans) {
        if (beans.size() == 0) {
            throw new UnsatisfiedResolutionException("Unable to resolve any beans of " + type);
        } else if (beans.size() > 1) {
            throw new AmbiguousResolutionException("More than one bean available (" + beans + ")");
        }
        return beans.iterator().next();
    }

    /**
     * Extracted from test harness. We need this since testng expected exceptions feature is not working with exception cause -
     * see org.testng.internal.Invoker.isExpectedException(Throwable, ExpectedExceptionsHolder).
     *
     * @param throwableType
     * @param throwable
     * @return <code>true</code> if throwable type is assignable from specified throwable or any cause in stack (works
     *         recursively), <code>false</code> otherwise
     */
    public boolean isThrowablePresent(Class<? extends Throwable> throwableType, Throwable throwable) {
        if (throwable == null) {
            return false;
        } else if (throwableType.isAssignableFrom(throwable.getClass())) {
            return true;
        } else {
            return isThrowablePresent(throwableType, throwable.getCause());
        }
    }
}
