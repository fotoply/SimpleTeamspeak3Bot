package events;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.ClientMovedEvent;

public interface IOnMovedEvent {
    /**
     * Event fired when a client is moved either by an admin or by the user itself.
     * @param api
     * @param movedEvent
     */
    void onMoved(TS3Api api, ClientMovedEvent movedEvent);
}
