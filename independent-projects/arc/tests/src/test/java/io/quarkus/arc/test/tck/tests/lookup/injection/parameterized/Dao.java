package io.quarkus.arc.test.tck.tests.lookup.injection.parameterized;

public class Dao<T1, T2> {

    public String getId() {
        return Dao.class.getName();
    }

}
