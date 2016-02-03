package ru.javaops.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.google.common.base.Preconditions.checkState;

/**
 * GKislin
 * 20.08.2015.
 */
@ConfigurationProperties("app")
public class AppProperties {

    /**
     * Root directory
     */
    @NotNull
    private String rootDir;

    /**
     * Test email
     */
    @NotNull
    private String testEmail;

    /**
     * Interval for update cached config
     */
    @NotNull
    private int cacheSeconds;

    public void setRootDir(String rootDir) {
        this.rootDir = rootDir;
        Path path = Paths.get(rootDir);
        checkState(Files.isDirectory(path) && Files.isReadable(path), "%s is not readable directory", rootDir);
    }

    public void setCacheSeconds(int cacheSeconds) {
        this.cacheSeconds = cacheSeconds;
    }

    public String getTestEmail() {
        return testEmail;
    }

    public void setTestEmail(String testEmail) {
        this.testEmail = testEmail;
    }

    public int getCacheSeconds() {
        return cacheSeconds;
    }

    public Path getRelatedPath(String path) {
        return Paths.get(rootDir).resolve(path).normalize().toAbsolutePath();
    }

    public String getRootDir() {
        return rootDir;
    }
}
