package commands;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import control.SettingsHandler;

public class SettingsCommand implements ICommand {
    SettingsHandler settingsHandler;

    @Override
    public void run(TS3Api api, String[] args, TextMessageEvent event) {
        settingsHandler = SettingsHandler.getInstance();

        if(settingsHandler.isSettingValid(args[1])) {
            if(args[2].equalsIgnoreCase("get")) {

            } else if(args[2].equalsIgnoreCase("set")) {

            } else {
                api.sendPrivateMessage(event.getInvokerId(), "Please either use set or get");
            }
        } else {
            api.sendPrivateMessage(event.getInvokerId(), "No such setting exists");
        }
        //TODO Implement a functional settings command.
    }

    @Override
    public String getCommand() {
        return "!settings";
    }

    @Override
    public String getHelpText() {
        return "Change personal settings";
    }

    @Override
    public String getExtendedHelpText() {
        return "!settings <settingName|list> <settingValue>";
    }

    @Override
    public int getPowerRequired() {
        return 0;
    }
}
