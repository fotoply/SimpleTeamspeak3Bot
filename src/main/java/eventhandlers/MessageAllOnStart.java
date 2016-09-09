package eventhandlers;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import control.Setting;
import control.SettingsHandler;
import control.settingvalidators.PredefinedSettingValidator;
import events.IOnBotInitializedEvent;
import events.IOnJoinEvent;

import java.util.ArrayList;

public class MessageAllOnStart implements IOnBotInitializedEvent, IOnJoinEvent {

    public static final String SETTING_NAME = "pm_on_join";

    @Override
    public void onBotInitialized(TS3Api api) {
        PredefinedSettingValidator validator = new PredefinedSettingValidator();
        validator.addAllowedOption("on");
        validator.addAllowedOption("off");
        validator.addAllowedOption("enable");
        validator.addAllowedOption("disable");
        SettingsHandler.getInstance().registerSetting(SETTING_NAME, new Setting(validator, "on"));

        ArrayList<Client> clients = (ArrayList<Client>) api.getClients();
        for (Client client : clients) {
            if(isSettingEnabled(client.getUniqueIdentifier())) {
                sendWelcomeMessage(api, client.getId());
            }
        }
    }

    private boolean isSettingEnabled(String UID) {
        return SettingsHandler.getInstance().getSettingOrDefault(UID, SETTING_NAME).equalsIgnoreCase("on") ||
               SettingsHandler.getInstance().getSettingOrDefault(UID, SETTING_NAME).equalsIgnoreCase("enable");
    }

    private void sendWelcomeMessage(TS3Api api, int id) {
        api.sendPrivateMessage(id, "Welcome to the server, type !help for commands");
    }

    @Override
    public void onJoin(TS3Api api, ClientJoinEvent joinEvent) {
        if(isSettingEnabled(joinEvent.getUniqueClientIdentifier())) {
            sendWelcomeMessage(api, joinEvent.getClientId());
        }
    }
}
