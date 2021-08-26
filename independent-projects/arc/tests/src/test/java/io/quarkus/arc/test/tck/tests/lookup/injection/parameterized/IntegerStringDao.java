package io.quarkus.arc.test.tck.tests.lookup.injection.parameterized;

import javax.enterprise.context.Dependent;

@IntegerPowered
@Dependent
public class IntegerStringDao extends Dao<Integer, String> {

    @Override
    public String getId() {
        return IntegerStringDao.class.getName();
    }

}