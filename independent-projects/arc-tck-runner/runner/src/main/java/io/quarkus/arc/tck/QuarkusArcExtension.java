package io.quarkus.arc.tck;

import org.jboss.arquillian.container.spi.client.container.DeploymentExceptionTransformer;
import org.jboss.arquillian.container.test.spi.client.deployment.ApplicationArchiveProcessor;
import org.jboss.arquillian.core.spi.LoadableExtension;

public class QuarkusArcExtension  implements LoadableExtension {
    @Override
    public void register(ExtensionBuilder extensionBuilder) {
        extensionBuilder.service(ApplicationArchiveProcessor.class, ArcArchiveProcessor.class);

        // Override the default NOOP exception transformer
        extensionBuilder.service(DeploymentExceptionTransformer.class, QuarkusExceptionTransformer.class);
    }
}
