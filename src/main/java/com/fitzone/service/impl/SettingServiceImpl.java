package com.fitzone.service.impl;

import com.fitzone.dao.SettingDAO;
import com.fitzone.dao.impl.SettingDAOImpl;
import com.fitzone.service.SettingService;
import java.util.Map;

public class SettingServiceImpl implements SettingService {
    private final SettingDAO settingDAO;

    public SettingServiceImpl() {
        this.settingDAO = new SettingDAOImpl();
    }

    public SettingServiceImpl(SettingDAO settingDAO) {
        this.settingDAO = settingDAO;
    }

    @Override
    public Map<String, String> getAllSettings() {
        return settingDAO.getAllSettings();
    }

    @Override
    public String getSetting(String key, String defaultValue) {
        return settingDAO.getSetting(key, defaultValue);
    }

    @Override
    public boolean saveSetting(String key, String value) {
        return settingDAO.saveSetting(key, value);
    }
}
