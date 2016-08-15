package events;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.ChannelEditedEvent;

public interface IOnChannelEditedEvent extends IGenericEvent {
    void onChannelEdited(TS3Api api, ChannelEditedEvent editedEvent);
}
