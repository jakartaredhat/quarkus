package io.quarkus.arc.tck;

import org.jboss.arquillian.container.test.spi.client.deployment.ApplicationArchiveProcessor;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.asset.StringAsset;

public class ArcArchiveProcessor implements ApplicationArchiveProcessor {

    /**
     * Add the application properties resource
     * @param appArchive - test archive
     * @param testClass - test class
     */
    @Override
    public void process(Archive<?> appArchive, TestClass testClass) {
        StringAsset appProps = new StringAsset("quarkus.arc.remove-unused-beans=none");
        appArchive.add(appProps, "application.properties");
    }
}
