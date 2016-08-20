package events;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.ChannelEditedEvent;

public interface IOnChannelEditedEvent extends IGenericEvent {
    /**
     * Event fired when any channel on the server is edited
     * @param api
     * @param editedEvent
     */
    void onChannelEdited(TS3Api api, ChannelEditedEvent editedEvent);
}
