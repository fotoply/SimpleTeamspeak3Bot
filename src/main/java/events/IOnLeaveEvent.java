package events;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.ClientLeaveEvent;

public interface IOnLeaveEvent extends IGenericEvent {
    void onLeave(TS3Api api, ClientLeaveEvent leaveEvent);
}
