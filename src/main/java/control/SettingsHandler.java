package control;

import com.github.theholywaffle.teamspeak3.TS3Api;
import control.utils.MapPersistence;
import events.IOnBotInitializedEvent;
import events.IOnBotShutdownEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SettingsHandler implements IOnBotShutdownEvent, IOnBotInitializedEvent {
    private static final String SAVE_PATH = "data/settings.data";
    private static HashMap<String, HashMap<String, String>> userSettingsMap;
    private static HashMap<String, String> validSettings;
    private static SettingsHandler instance;
    //TODO Allow commands and events to register valid settings

    private SettingsHandler() {
        userSettingsMap = new HashMap<>();
        validSettings = new HashMap<>();
    }

    public static SettingsHandler getInstance() {
        if (instance == null) {
            instance = new SettingsHandler();
        }
        return instance;
    }

    @Override
    public void onBotInitialized(TS3Api api) {
        File dataFile = new File(SAVE_PATH);
        if (dataFile.exists()) {
            userSettingsMap.putAll(MapPersistence.readStringNestedMap(dataFile));
        }
    }

    @Override
    public void onBotShutdown(TS3Api api, int shutdownCode) {
        MapPersistence.saveGenericMap(new File(SAVE_PATH), userSettingsMap);
    }

    public void setSetting(String UID, String setting, String value) {
        HashMap<String, String> user = userSettingsMap.get(UID);
        if (user == null) {
            userSettingsMap.put(UID, new HashMap<String, String>());
        }
        user.put(setting, value);
    }

    public String getSetting(String UID, String setting) {
        return getSettingOrDefault(UID, setting);
    }

    public String getSettingOrDefault(String UID, String setting) {
        HashMap<String, String> user = userSettingsMap.get(UID);
        if (user == null) {
            return validSettings.get(setting);
        }
        String value = user.get(setting);
        if (value == null) {
            return validSettings.get(setting);
        }
        return value;
    }

    public void registerSetting(String setting, String defaultValue) {
        if(!validSettings.containsKey(setting.toLowerCase())) {
            validSettings.put(setting.toLowerCase(), defaultValue);
        }
    }

    public boolean isSettingValid(String setting) {
        return validSettings.containsKey(setting.toLowerCase());
    }

    public HashMap<String, String> getValidSettings() {
        return validSettings;
    }
}
