package io.quarkus.arc.test.tck.tests.lookup.injection.parameterized;

import javax.enterprise.context.Dependent;

@Dependent
public class StringDao extends Dao<String, String> {

}