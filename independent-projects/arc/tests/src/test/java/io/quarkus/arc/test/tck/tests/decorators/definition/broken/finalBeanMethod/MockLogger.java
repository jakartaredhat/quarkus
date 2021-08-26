package io.quarkus.arc.test.tck.tests.decorators.definition.broken.finalBeanMethod;

import javax.enterprise.context.Dependent;

@Dependent
public class MockLogger implements Logger {

    private static String log = "";

    public final void log(String string) {
        log += string;
    }

    /**
     * @return the log
     */
    public static String getLog() {
        return log;
    }

}