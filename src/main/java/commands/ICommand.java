package commands;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

public interface ICommand {
    /**
     * Is called when the associated basis command is sent to the server.
     * @param api
     * @param args
     * @param event
     */
    void run(TS3Api api, String[] args, TextMessageEvent event);

    /**
     * Gets a string representing the command, including any prefix
     * @return
     */
    String getCommand();

    /**
     * Should return a short descriptive String for the command
     * @return
     */
    String getHelpText();

    /**
     * Should return a short command-structure String
     * @return
     */
    String getExtendedHelpText();

    /**
     * Should return the power required for executing this command
     * @return
     */
    int getPowerRequired();
}
