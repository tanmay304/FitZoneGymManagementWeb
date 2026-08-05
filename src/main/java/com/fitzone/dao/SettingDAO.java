package com.fitzone.dao;

import java.util.Map;

public interface SettingDAO {
    Map<String, String> getAllSettings();
    String getSetting(String key, String defaultValue);
    boolean saveSetting(String key, String value);
}
