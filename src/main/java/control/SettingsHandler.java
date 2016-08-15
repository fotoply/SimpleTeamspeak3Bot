package control;

import com.github.theholywaffle.teamspeak3.TS3Api;
import control.utils.MapPersistence;
import events.IOnBotInitializedEvent;
import events.IOnBotShutdownEvent;

import java.io.File;
import java.util.HashMap;

public class SettingsHandler implements IOnBotShutdownEvent, IOnBotInitializedEvent {
    private static final String SAVE_PATH = "data/settings.data";
    private static HashMap<String, HashMap<String, String>> userSettingsMap;
    private static SettingsHandler instance;

    private SettingsHandler() {

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
        return getSettingOrDefault(UID, setting, "");
    }

    public String getSettingOrDefault(String UID, String setting, String defaultValue) {
        HashMap<String, String> user = userSettingsMap.get(UID);
        if (user == null) {
            return defaultValue;
        }
        String value = user.get(setting);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }
}
