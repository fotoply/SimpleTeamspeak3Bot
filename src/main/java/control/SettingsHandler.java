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

    public void registerSetting(String setting, Setting settingManager) {
        if(!validSettings.containsKey(setting.toLowerCase())) {
            validSettings.put(setting.toLowerCase(), settingManager);
        }
    }

    public boolean isSettingValid(String setting) {
        return validSettings.containsKey(setting.toLowerCase());
    }

    public boolean isValueValid(String setting, String value) {
        return validSettings.get(setting.toLowerCase()).isValueValid(value);
    }

    public HashMap<String, Setting> getValidSettings() {
        return validSettings;
    }
}
