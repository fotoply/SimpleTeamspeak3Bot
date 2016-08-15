package commands;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

public interface ICommand {
    void run(TS3Api api, String[] args, TextMessageEvent event);

    String getCommand();

    String getHelpText();

    String getExtendedHelpText();

    int getPowerRequired();
}
