package ru.javaops.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;

/**
 * GKislin
 * 20.08.2015.
 */
@ConfigurationProperties("app")
public class AppProperties {

    /**
     * Test email
     */
    @NotNull
    private String testEmail;

    /**
     * Interval for update templates
     */
    @NotNull
    private int cacheSeconds;

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
}
