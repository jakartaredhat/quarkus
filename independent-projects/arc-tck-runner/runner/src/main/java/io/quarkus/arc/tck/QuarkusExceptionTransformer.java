package io.quarkus.arc.tck;

import io.quarkus.builder.BuildException;
import jakarta.enterprise.inject.spi.DefinitionException;
import jakarta.enterprise.inject.spi.DeploymentException;
import org.jboss.arquillian.container.spi.client.container.DeploymentExceptionTransformer;

public class QuarkusExceptionTransformer implements DeploymentExceptionTransformer {
    public Throwable transform(Throwable throwable) {

        Throwable root = throwable;
        if (root instanceof RuntimeException) {
            root = root.getCause();
        }
        if (root instanceof BuildException) {
            BuildException be = (BuildException) root;
            root = be.getCause();
        }

        if (root instanceof DeploymentException || root instanceof DefinitionException) {
            return root;
        }

        return throwable;
    }
}
