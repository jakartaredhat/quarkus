package container;

import jakarta.inject.Inject;

public class SomeBean {
    @VersionInfo
    static String VERSION = "1.0";

    @Inject
    private String name;

    public String getName() {
        return name;
    }
}
