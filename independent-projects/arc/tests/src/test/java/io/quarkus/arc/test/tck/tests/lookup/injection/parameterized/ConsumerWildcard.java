package io.quarkus.arc.test.tck.tests.lookup.injection.parameterized;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@SuppressWarnings("rawtypes")
@Dependent
public class ConsumerWildcard {

    @Inject
    Dao<? extends Integer, ?> dao;

    @Inject
    @IntegerPowered
    Dao<? extends Integer, ? super String> integerStringDao;

    @Inject
    NumberDao<? extends Serializable, ? super Integer> numberDao;

    public Dao getDao() {
        return dao;
    }

    public Dao<? extends Integer, ? super String> getIntegerStringDao() {
        return integerStringDao;
    }

    public NumberDao<? extends Number, ? extends Number> getNumberDao() {
        return numberDao;
    }

}
