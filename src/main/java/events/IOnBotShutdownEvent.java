package events;

import com.github.theholywaffle.teamspeak3.TS3Api;

public interface IOnBotShutdownEvent extends IGenericEvent {
    void onBotShutdown(TS3Api api, int shutdownCode);
}
