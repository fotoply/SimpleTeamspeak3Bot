package eventhandlers;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import events.IOnJoinEvent;

public class BanishmentOnJoin implements IOnJoinEvent {

    public static final int BANISHMENT_CHANNEL_ID = 9;

    @Override
    public void onJoin(TS3Api api, ClientJoinEvent joinEvent) {
        if(OldChannelOnJoin.getLastChannel(joinEvent.getUniqueClientIdentifier()) == BANISHMENT_CHANNEL_ID) {
            api.moveClient(joinEvent.getClientId(), BANISHMENT_CHANNEL_ID);
        }
    }
}
