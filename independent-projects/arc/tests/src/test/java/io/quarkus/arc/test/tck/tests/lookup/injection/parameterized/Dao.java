package io.quarkus.arc.test.tck.tests.lookup.injection.parameterized;

import javax.enterprise.context.Dependent;

@Dependent
public class Dao<T1, T2> {

    public String getId() {
        return Dao.class.getName();
    }

}
