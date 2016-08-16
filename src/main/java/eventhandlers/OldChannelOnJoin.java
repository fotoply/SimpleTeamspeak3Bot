package eventhandlers;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientMovedEvent;
import control.SettingsHandler;
import control.utils.MapPersistence;
import events.IOnBotInitializedEvent;
import events.IOnBotShutdownEvent;
import events.IOnJoinEvent;
import events.IOnMovedEvent;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OldChannelOnJoin implements IOnJoinEvent, IOnMovedEvent, IOnBotShutdownEvent, IOnBotInitializedEvent {
    private static final String SAVE_PATH = "data/oldChannel.data";
    public static final String SETTING_NAME = "autojoin_channel";
    private static HashMap<String, Integer> lastChannel = new HashMap<>();

    @Override
    public void onJoin(TS3Api api, ClientJoinEvent joinEvent) {
        if (lastChannel.getOrDefault(joinEvent.getUniqueClientIdentifier(), 1) != 1) {
            if(SettingsHandler.getInstance().getSettingOrDefault(joinEvent.getInvokerUniqueId(), SETTING_NAME).equalsIgnoreCase("on")) {
                api.moveClient(joinEvent.getClientId(), lastChannel.get(joinEvent.getUniqueClientIdentifier()));
            }
        }
    }

    @Override
    public void onMoved(TS3Api api, ClientMovedEvent movedEvent) {
        String targetUID = api.getClientInfo(movedEvent.getClientId()).getUniqueIdentifier();
        Logger.getGlobal().log(Level.INFO, String.format("%s was moved to %d%n", api.getClientByUId(targetUID).getNickname(), movedEvent.getTargetChannelId()));
        lastChannel.put(targetUID, movedEvent.getTargetChannelId());
    }

    @Override
    public void onBotShutdown(TS3Api api, int shutdownCode) {
        Logger.getGlobal().log(Level.INFO, "Saving last channel data");
        MapPersistence.saveGenericMap(new File(SAVE_PATH), lastChannel);
        Logger.getGlobal().log(Level.INFO, "Saved last channel data successfully");
    }

    @Override
    public void onBotInitialized(TS3Api api) {
        File dataFile = new File(SAVE_PATH);
        if (dataFile.exists()) {
            lastChannel.putAll(MapPersistence.readStringIntegerMap(dataFile));
        }
        SettingsHandler.getInstance().registerSetting(SETTING_NAME, "on");
    }
}

