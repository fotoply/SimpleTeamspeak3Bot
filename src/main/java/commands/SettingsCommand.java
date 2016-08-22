package commands;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import control.SettingsHandler;

public class SettingsCommand implements ICommand {

    @Override
    public void run(TS3Api api, String[] args, TextMessageEvent event) {
        SettingsHandler settingsHandler = SettingsHandler.getInstance();

        if(args.length > 2 && settingsHandler.isSettingValid(args[2])) {
            if(args[1].equalsIgnoreCase("get")) {
                api.sendPrivateMessage(event.getInvokerId(), String.format("%s - %s", args[2], settingsHandler.getSettingOrDefault(event.getInvokerUniqueId(), args[2])));
            } else if(args[1].equalsIgnoreCase("set")) {
                if(settingsHandler.isValueValid(args[2], args[3])) {
                    String extraArgs = event.getMessage().substring(event.getMessage().indexOf(" ", event.getMessage().indexOf(" ")+1)+1); // Removes the first two arguments
                    settingsHandler.setSetting(event.getInvokerUniqueId(), args[2], extraArgs);
                    api.sendPrivateMessage(event.getInvokerId(), "Setting was saved");
                } else {
                    api.sendPrivateMessage(event.getInvokerId(), "Invalid value for setting");
                }
            } else {
                api.sendPrivateMessage(event.getInvokerId(), "Please either use set or get");
            }
        } else if(args[1].equalsIgnoreCase("list")) {
            settingsHandler.getValidSettings().forEach((key, setting) -> {
                StringBuilder message = new StringBuilder();
                message.append(" - ");
                message.append(key);
                message.append(" | Valid: ");
                message.append(setting.describeValids());
                api.sendPrivateMessage(event.getInvokerId(), message.toString());
            });
        } else {
            api.sendPrivateMessage(event.getInvokerId(), "No such setting exists");
        }
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
        return "!settings <get|set|list> <setting> <settingValue>";
    }

    @Override
    public int getPowerRequired() {
        return 0;
    }
}
