package commands;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import control.utils.MapPersistence;
import events.IOnBotInitializedEvent;
import events.IOnBotShutdownEvent;
import events.IOnJoinEvent;

import java.io.File;
import java.util.HashMap;

public class LastSeenCommand implements ICommand, IOnBotInitializedEvent, IOnBotShutdownEvent, IOnJoinEvent {
    private static final String SAVE_PATH = "data/lastseen.data";
    private static HashMap<String, HashMap<String, String>> lastseenInfoMap;
    private static LastSeenCommand instance;
    private LastSeenCommand() {
        lastseenInfoMap = new HashMap<>();
    }

    public static LastSeenCommand getInstance() {
        if(instance == null) {
            instance = new LastSeenCommand();
        }
        return instance;
    }

    @Override
    public void run(TS3Api api, String[] args, TextMessageEvent event) {

    }

    @Override
    public String getCommand() {
        return "!lastseen";
    }

    @Override
    public String getHelpText() {
        return "Get information about when a username was last seen";
    }

    @Override
    public String getExtendedHelpText() {
        return "!lastseen <username>";
    }

    @Override
    public int getPowerRequired() {
        return 0;
    }

    @Override
    public void onBotInitialized(TS3Api api) {
        File dataFile = new File(SAVE_PATH);
        if (dataFile.exists()) {
            lastseenInfoMap.putAll(MapPersistence.readStringNestedMap(dataFile));
        }
    }

    @Override
    public void onBotShutdown(TS3Api api, int shutdownCode) {
        MapPersistence.saveGenericMap(new File(SAVE_PATH), lastseenInfoMap);
    }

    @Override
    public void onJoin(TS3Api api, ClientJoinEvent joinEvent) {
        HashMap<String, String> info = new HashMap<>();
        info.put("username", joinEvent.getClientNickname());
        info.put("lastseen", String.valueOf(System.currentTimeMillis()));
        lastseenInfoMap.put(joinEvent.getUniqueClientIdentifier(), info);
    }
    //TODO Make this command give back the latest seen for a given username, with UID's attached for usernames seen over multiple
}
