package io.quarkus.arc.test.tck.tests.lookup.injection.parameterized;

@IntegerPowered
public class IntegerStringDao extends Dao<Integer, String> {

    @Override
    public String getId() {
        return IntegerStringDao.class.getName();
    }

}