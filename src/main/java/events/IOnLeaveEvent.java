package events;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.ClientLeaveEvent;

public interface IOnLeaveEvent extends IGenericEvent {
    /**
     * Event fired when a client leaves the server
     * @param api
     * @param leaveEvent
     */
    void onLeave(TS3Api api, ClientLeaveEvent leaveEvent);
}
