package io.quarkus.arc.test.tck.tests.lookup.injection.parameterized;

import javax.enterprise.inject.Typed;

@Typed(NumberDao.class)
public class NumberDao<T1 extends Number, T2 extends Number> extends Dao<T1, T2> {

    @Override
    public String getId() {
        return NumberDao.class.getName();
    }

}