package events;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.ClientLeaveEvent;
import com.github.theholywaffle.teamspeak3.api.event.ServerEditedEvent;

public interface IOnServerEditedEvent extends IGenericEvent {
    void onServerEdit(TS3Api api, ServerEditedEvent editedEvent);
}
