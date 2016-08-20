package events;

import com.github.theholywaffle.teamspeak3.TS3Api;

public interface IOnBotShutdownEvent extends IGenericEvent {
    /**
     * Event fired when the bot is gracefully shut down. Should always have the shutdownCode set to 1 for safe shutdowns.
     * @param api
     * @param shutdownCode
     */
    void onBotShutdown(TS3Api api, int shutdownCode);
}
