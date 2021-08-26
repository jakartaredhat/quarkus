package io.quarkus.arc.test.tck.util;


import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A very simple JDK logger wrapper. It should be used in complex tests only as logging in integration tests is not a good idea.
 *
 * @author Martin Kouba
 */
public final class SimpleLogger implements Serializable {

    private static final long serialVersionUID = -2461635783455781420L;
    private final Logger logger;

    public SimpleLogger(String name) {
        this.logger = Logger.getLogger(name);
    }

    public SimpleLogger(Class<?> clazz) {
        this.logger = Logger.getLogger(clazz.getName());
    }

    /**
     * Log message with specified parameters. {@link Level#FINE} is always used since the message is considered to be tracing
     * information.
     *
     * @param message
     * @param parameters
     */
    public void log(String message, Object... parameters) {
        logger.log(Level.FINE, message, parameters);
    }

    /**
     * Log message with specified throwed exception. {@link Level#FINE} is always used since the message is considered to be tracing information.
     *
     * @param message
     * @param parameters
     */
    public void log(Throwable thrown) {
        logger.log(Level.FINE, thrown.getMessage(), thrown);
    }
}
