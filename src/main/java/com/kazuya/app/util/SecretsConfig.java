package com.kazuya.app.util;

import java.io.IOException;
import java.util.Properties;

public class SecretsConfig {

  private static SecretsConfig INSTANCE;
  private String apiKey;

  private SecretsConfig(String apiKey) {
    this.apiKey = apiKey;
  }

  public static SecretsConfig getInstance() {
    if (INSTANCE == null) {
      init();
    }
    return INSTANCE;
  }

  public String getApiKey() {
    return apiKey;
  }

  private static void init() {
    Properties prop = new Properties();
    try {
      prop.load(SecretsConfig.class.getResourceAsStream("/secrets.properties"));
    } catch (IOException e) {
      throw new RuntimeException("Failed to load secrets.properties", e);
    }
    SecretsConfig.INSTANCE = new SecretsConfig(prop.getProperty("api.key"));
  }
}
