package events;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.ClientMovedEvent;

public interface IOnMovedEvent {
    void onMoved(TS3Api api, ClientMovedEvent movedEvent);
}
