package commands;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

public class InvalidCommand implements ICommand {
    public void run(TS3Api api, String[] args, TextMessageEvent event) {
        api.sendPrivateMessage(event.getInvokerId(), "No such command");
    }

    @Override
    public String getCommand() {
        return "";
    }

    @Override
    public String getHelpText() {
        return "";
    }

    @Override
    public String getExtendedHelpText() {
        return "";
    }

    @Override
    public int getPowerRequired() {
        return 0;
    }


}
