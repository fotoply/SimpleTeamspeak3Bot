package events;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

public interface IOnMessageRecievedEvent extends IGenericEvent {
    /**
     * Event fired when the bot receives a message, either in the public channel, in the current channel or in a private message
     * @param api
     * @param event
     */
    void onMessage(TS3Api api, TextMessageEvent event);
}
