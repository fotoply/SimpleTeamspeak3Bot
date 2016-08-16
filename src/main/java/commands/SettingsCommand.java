package commands;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

public class SettingsCommand implements ICommand {
    @Override
    public void run(TS3Api api, String[] args, TextMessageEvent event) {
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
