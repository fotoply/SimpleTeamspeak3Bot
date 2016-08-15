package events;

import com.github.theholywaffle.teamspeak3.TS3Api;

public interface IOnBotInitializedEvent extends IGenericEvent {
    void onBotInitialized(TS3Api api);
}
