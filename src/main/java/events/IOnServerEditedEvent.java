package events;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.ServerEditedEvent;

public interface IOnServerEditedEvent extends IGenericEvent {

    /**
     * Fired when the virtual server is edited.
     * @param api
     * @param editedEvent
     */
    void onServerEdit(TS3Api api, ServerEditedEvent editedEvent);
}
