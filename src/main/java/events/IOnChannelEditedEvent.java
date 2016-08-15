package events;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.ChannelCreateEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;

public interface IOnChannelEditedEvent extends IGenericEvent {
    void onChannelEdited(TS3Api api, ChannelEditedEvent editedEvent);
}
