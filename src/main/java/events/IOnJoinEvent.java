package events;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;

public interface IOnJoinEvent extends IGenericEvent {
    /**
     * Event fired when a client joins the server
     * @param api
     * @param joinEvent
     */
    void onJoin(TS3Api api, ClientJoinEvent joinEvent);
}
