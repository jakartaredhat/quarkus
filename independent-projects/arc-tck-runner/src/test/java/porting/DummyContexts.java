package porting;

import jakarta.enterprise.context.spi.Context;

import org.jboss.cdi.tck.spi.Contexts;

public class DummyContexts implements Contexts<Context> {

    @Override
    public void setActive(Context context) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setInactive(Context context) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Context getRequestContext() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Context getDependentContext() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void destroyContext(Context context) {
        throw new UnsupportedOperationException();
    }

}