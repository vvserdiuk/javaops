package ru.javaops.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javaops.config.AppProperties;
import ru.javaops.util.PasswordUtil;

@Service
public class SubscriptionService {

    @Autowired
    private AppProperties appProperties;

    public String getSubscriptionUrl(String email, String activationKey, boolean active) {
        return appProperties.getHostUrl() + "/activate?email=" + email + "&key=" + activationKey + "&activate=" + active;
    }

    public String generateActivationKey(String email) {
        return PasswordUtil.getPasswordEncoder().encode(getSalted(email));
    }

    public boolean isActivationKeyMatch(String email, String key) {
        return PasswordUtil.isMatch(getSalted(email), key);
    }

    private String getSalted(String email) {
        return email + appProperties.getActivationSecretSalt();
    }
}
