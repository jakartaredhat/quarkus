package io.quarkus.arc.test.tck.interceptors.tests.order.overriden.lifecycleCallback;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.quarkus.arc.test.ArcTestContainer;
import io.quarkus.arc.test.tck.util.AbstractTestUtils;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

//@SpecVersion(spec = "interceptors", version = "1.2")
// This test is really about how @PostConstruct/@PreDestroy are called
public class OverridenLifecycleCallbackInterceptorTest extends AbstractTestUtils {

    @RegisterExtension
    public ArcTestContainer container = new ArcTestContainer(Bird.class, Eagle.class, Falcon.class);

    @Test
    //@SpecAssertion(section = INT_ORDERING_RULES, id = "j")
    public void testCallbackOverridenByCallback() {

        Bird.reset();
        Eagle.reset();

        Bean<Eagle> eagleBean = getUniqueBean(Eagle.class);
        CreationalContext<Eagle> ctx = getCurrentManager().createCreationalContext(eagleBean);
        Eagle foo = eagleBean.create(ctx);

        foo.ping();
        eagleBean.destroy(foo, ctx);

        assertEquals(0, Bird.getInitBirdCalled().get());
        assertEquals(1, Eagle.getInitEagleCalled().get());
        assertEquals(0, Bird.getDestroyBirdCalled().get());
        assertEquals(1, Eagle.getDestroyEagleCalled().get());
    }

    @Test
    //@SpecAssertion(section = INT_ORDERING_RULES, id = "j")
    public void testCallbackOverridenByNonCallback() {

        Bird.reset();
        Falcon.reset();

        Bean<Falcon> falconBean = getUniqueBean(Falcon.class);
        CreationalContext<Falcon> ctx = getCurrentManager().createCreationalContext(falconBean);
        Falcon baz = falconBean.create(ctx);

        baz.ping();
        falconBean.destroy(baz, ctx);

        assertEquals(Bird.getInitBirdCalled().get(), 0);
        assertEquals(Falcon.getInitFalconCalled().get(), 0);
        assertEquals(Bird.getDestroyBirdCalled().get(), 0);
        assertEquals(Falcon.getDestroyFalconCalled().get(), 0);
    }
}
