# CDI-lite TCK runner for ArC

Patches are needed to the following:
* https://github.com/eclipse/transformer.git
* https://github.com/mojohaus/exec-maven-plugin.git


## Running the transformations

1. Build Quarkus
2. cd arc-tck-runner/transformer
3. mvn -Dtransform=arc test


## Running the TCK

1. Build the current cdi-tck master branch to produce 4.1.0-SNAPSHOT
2. cd arc-tck-runnder/runner
3. mvn -Dtck test