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
import java.util.concurrent.TimeUnit;

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
        final boolean[] seen = {false};
        lastseenInfoMap.forEach((uid, user) -> {
            if(user.get("username").contains(args[1].toLowerCase())) {
                String lastSeen = calculateLastSeenTime(user.get("lastseen"));
                api.sendPrivateMessage(event.getInvokerId(), String.format("%s (%s) was last seen %s ago", user.get("username"), uid, lastSeen));
                seen[0] = true;
            }
        });
        if(!seen[0]) {
            api.sendPrivateMessage(event.getInvokerId(), "No such username has been seen");
        }
    }

    private String calculateLastSeenTime(String lastseen) {
        long lastSeenTime = Long.valueOf(lastseen);
        long dif = lastSeenTime - System.currentTimeMillis();
        return Math.abs(TimeUnit.MILLISECONDS.toDays(dif)) + " days and " + Math.abs(TimeUnit.MILLISECONDS.toHours(dif))%24 + " hours";
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
        info.put("username", joinEvent.getClientNickname().toLowerCase());
        info.put("lastseen", String.valueOf(System.currentTimeMillis()));
        lastseenInfoMap.put(joinEvent.getUniqueClientIdentifier(), info);
    }
}
