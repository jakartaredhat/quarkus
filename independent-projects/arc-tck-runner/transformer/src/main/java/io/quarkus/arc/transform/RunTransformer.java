package io.quarkus.arc.transform;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.transformer.Transformer;
import org.eclipse.transformer.jakarta.JakartaTransformer;

/**
 * Run to generate ee9 artifacts from the ee8 versions
 */
public class RunTransformer {
    static class Version {
        String groupId;
        String artifactId;
        String version;

        public Version(String groupId, String artifactId, String version) {
            this.groupId = groupId;
            this.artifactId = artifactId;
            this.version = version;
        }
    }
    static boolean isJar(Path path) {
        //System.out.printf("isJar(%s), fileName=%s\n", path, path.getFileName().toString());
        return path.getFileName().toString().endsWith(".jar");
    }
    static Properties readProperties(JarFile jarFile , JarEntry entry) {
        Properties props = new Properties();
        try(InputStream is = jarFile.getInputStream(entry)) {
            props.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        File ee8Dir = new File("target/dependency/ee8");
        System.out.printf("ee8Dir: %s\n", ee8Dir.getAbsolutePath());
        ArrayList<File> jarFiles = new ArrayList<>();
        try (Stream<Path> walk = Files.walk(ee8Dir.toPath())) {
            List<File> result = walk.filter(RunTransformer::isJar).map(Path::toFile).collect(Collectors.toList());
            jarFiles.addAll(result);
        }

        System.out.printf("Jars: %s\n", jarFiles);
        ArrayList<Version> versions = new ArrayList<>();
        for (File jar : jarFiles) {
            JarFile jarFile = new JarFile(jar);
            System.out.printf("+ jar: %s\n", jarFile.getName());
            jarFile.versionedStream().forEach((JarEntry jarEntry) -> {
                if(!jarEntry.isDirectory()) {
                    //System.out.printf("...%s: \n", jarEntry.getName());
                    if(jarEntry.getName().endsWith("/pom.properties")) {
                        Properties props = readProperties(jarFile, jarEntry);
                        Version version = new Version(props.getProperty("groupId"), props.getProperty("artifactId"), props.getProperty("version"));
                        System.out.printf("...: %s:%s:%s\n", version.groupId, version.artifactId, version.version);
                        versions.add(version);
                    }
                }
            });
        }

        String home = System.getProperty("user.home");
        File m2Dir = new File(home, ".m2/repository");
        if(m2Dir.exists() == false) {
            System.err.printf("No .m2/repository found: %s\n", m2Dir.getAbsolutePath());
        }


        ArrayList<String> tmp = new ArrayList<>();
        // These first two are place holders for the input and output files
        tmp.add("INPUT");
        tmp.add("OUTPUT");
        tmp.add("--"+Transformer.AppOption.DRYRUN.getLongTag());
        tmp.add("--"+Transformer.AppOption.LOG_VERBOSE.getLongTag());
        tmp.add("--"+Transformer.AppOption.OVERWRITE.getLongTag());
        tmp.add("--"+Transformer.AppOption.LOG_FILE.getLongTag());
        tmp.add("target/ee9-xform.log");
        tmp.addAll(Arrays.asList(args));
        System.out.printf("+++ Common xform args: %s\n", tmp);

        String[] transformArgs = new String[tmp.size()];
        tmp.toArray(transformArgs);

        for (Version v : versions) {
            File groupDir = new File(m2Dir, v.groupId.replace('.', '/'));
            File artifactDir = new File(groupDir, v.artifactId);
            File versionDir = new File(artifactDir, v.version);
            File jarFile = new File(versionDir, String.format("%s-%s.jar", v.artifactId, v.version));
            File ee9JarFile = new File(versionDir, String.format("%s-%s-ee9.jar", v.artifactId, v.version));
            System.out.printf("ee8.exists(%s): %s\n", jarFile.exists(), jarFile.getName());
            System.out.printf("ee9.exists(%s): %s\n", ee9JarFile.exists(), ee9JarFile.getName());
            transformArgs[0] = jarFile.getAbsolutePath();
            transformArgs[1] = ee9JarFile.getAbsolutePath();
            try {
                JakartaTransformer.main(transformArgs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
