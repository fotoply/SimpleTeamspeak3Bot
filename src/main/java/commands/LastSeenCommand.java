package commands;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import events.IOnBotInitializedEvent;
import events.IOnBotShutdownEvent;

public class LastSeenCommand implements ICommand, IOnBotInitializedEvent, IOnBotShutdownEvent {
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

    }

    @Override
    public void onBotShutdown(TS3Api api, int shutdownCode) {

    }
    //TODO Make this command give back the latest seen for a given username, with UID's attached for usernames seen over multiple
}
