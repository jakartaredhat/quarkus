package porting;

import java.io.IOException;

import org.jboss.cdi.tck.spi.Beans;

public class DummyBeans implements Beans {

    @Override
    public boolean isProxy(Object instance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte[] passivate(Object instance) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object activate(byte[] bytes) throws IOException, ClassNotFoundException {
        throw new UnsupportedOperationException();
    }

}

