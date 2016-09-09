package eventhandlers;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import events.IOnBotInitializedEvent;
import events.IOnJoinEvent;

import java.util.ArrayList;

public class MessageAllOnStart implements IOnBotInitializedEvent, IOnJoinEvent {
    @Override
    public void onBotInitialized(TS3Api api) {
        ArrayList<Client> clients = (ArrayList<Client>) api.getClients();
        for (Client client : clients) {
            api.sendPrivateMessage(client.getId(), "Welcome to the server, type !help for commands");
        }
    }

    @Override
    public void onJoin(TS3Api api, ClientJoinEvent joinEvent) {
        api.sendPrivateMessage(joinEvent.getClientId(), "Welcome to the server, type !help for commands");
    }
}
