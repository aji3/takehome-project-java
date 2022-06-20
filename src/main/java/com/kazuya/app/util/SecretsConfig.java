package com.kazuya.app.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
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
    loadProp(
        Path.of(sanitizeWindowsDriveLetter(SecretsConfig.class.getResource("/secrets.properties").getPath())),
        prop);
    loadProp(Path.of("./secrets.properties"), prop);
    if (!prop.containsKey("api.key")) {
      throw new RuntimeException("Failed to load secrets.properties");
    }
    SecretsConfig.INSTANCE = new SecretsConfig(prop.getProperty("api.key"));
  }

  private static void loadProp(Path propFilePath, Properties prop) {
    try (InputStream in = Files.newInputStream(propFilePath)) {
      prop.load(in);
    } catch (IOException e) {
      System.out.println("Exception on load properties");
      e.printStackTrace();
    }
  }

  private static String sanitizeWindowsDriveLetter(String pathStr) {
    if (pathStr.matches("^/.\\:/.*")) {
      return pathStr.substring(3);
    }
    return pathStr;
  }
}
