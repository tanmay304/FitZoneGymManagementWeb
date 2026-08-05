package com.fitzone.service;

import java.util.Map;

public interface SettingService {
    Map<String, String> getAllSettings();
    String getSetting(String key, String defaultValue);
    boolean saveSetting(String key, String value);
}
