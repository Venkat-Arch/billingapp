package com.bank.billing.security;

import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Singleton
public class ConjurSecretProvider {

    private static final Logger LOGGER = Logger.getLogger(ConjurSecretProvider.class);
    private final Properties cache = new Properties();

    @PostConstruct
    public void init() {
        try (InputStream stream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("conjur-secrets.properties")) {
            if (stream != null) {
                cache.load(stream);
                LOGGER.info("Loaded mock Conjur secrets for local testing");
            }
        } catch (IOException ex) {
            LOGGER.warn("Unable to load mock Conjur secrets", ex);
        }
    }

    public String getSecret(String key) {
        return cache.getProperty(key);
    }
}
