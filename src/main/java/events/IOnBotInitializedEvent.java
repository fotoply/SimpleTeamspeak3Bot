package events;

import com.github.theholywaffle.teamspeak3.TS3Api;

public interface IOnBotInitializedEvent extends IGenericEvent {
    /**
     * Event fired when the bot is finished initializing and connecting to the server.
     * @param api
     */
    void onBotInitialized(TS3Api api);
}
