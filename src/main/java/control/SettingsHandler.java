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
    private static HashMap<String, Setting> validSettings;
    private static SettingsHandler instance;

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
            userSettingsMap.put(UID, new HashMap<>());
            user = userSettingsMap.get(UID);
        }
        user.put(setting.toLowerCase(), value);
    }

    public String getSettingOrDefault(String UID, String setting) {
        HashMap<String, String> user = userSettingsMap.get(UID);
        if (user == null) {
            return validSettings.get(setting.toLowerCase()).getDefaultValue();
        }
        String value = user.get(setting.toLowerCase());
        if (value == null) {
            return validSettings.get(setting.toLowerCase()).getDefaultValue();
        }
        return value;
    }

    /**
     * Register a setting with the settings handler to allow it to be retrieved for later usage.
     *
     * Setting objects are <u>not</u> stored between restarts but user settings are.
     * @param settingName the name of the setting
     * @param settingManager the setting manager object
     */
    public void registerSetting(String settingName, Setting settingManager) {
        if(!validSettings.containsKey(settingName.toLowerCase())) {
            validSettings.put(settingName.toLowerCase(), settingManager);
        }
    }

    public boolean isSettingValid(String setting) {
        return validSettings.containsKey(setting.toLowerCase());
    }

    /**
     * Returns if a given value is valid for a given setting.
     * Does not check for validity of the setting itself and <u>will</u> throw a NullPointerException if used on an invalid settingName
     * @param settingName
     * @param value
     * @return true if valid otherwise false
     */
    public boolean isValueValid(String settingName, String value) {
        return validSettings.get(settingName.toLowerCase()).isValueValid(value);
    }

    public HashMap<String, Setting> getValidSettings() {
        return validSettings;
    }
}
