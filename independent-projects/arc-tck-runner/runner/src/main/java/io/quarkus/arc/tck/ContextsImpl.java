package io.quarkus.arc.tck;

import jakarta.enterprise.context.spi.Context;
import org.jboss.cdi.tck.spi.Contexts;

public class ContextsImpl implements Contexts<Context> {
    @Override
    public void setActive(Context context) {

    }

    @Override
    public void setInactive(Context context) {

    }

    @Override
    public Context getRequestContext() {
        return null;
    }

    @Override
    public Context getDependentContext() {
        return null;
    }

    @Override
    public void destroyContext(Context context) {

    }
}
