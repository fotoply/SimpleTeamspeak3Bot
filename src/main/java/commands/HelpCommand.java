package commands;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import control.Bot;

public class HelpCommand implements ICommand {
    @Override
    public void run(TS3Api api, String[] args, TextMessageEvent event) {
        if(args.length == 1) {
            Bot.getCommandMap().forEach((key, command) -> {
                if (!command.getCommand().equalsIgnoreCase("")) {
                    api.sendPrivateMessage(event.getInvokerId(), String.format("%10s - %s", command.getCommand(), command.getHelpText()));
                }
            });
            api.sendPrivateMessage(event.getInvokerId(), "Use !help <command> to get more help for that specific command");
        } else {
            Bot.getCommandMap().forEach((key, command) -> {
                if((command.getCommand().equalsIgnoreCase(args[1]) || command.getCommand().substring(1).equalsIgnoreCase(args[1])) && !command.getCommand().equals("")) {
                    int clientId = event.getInvokerId();
                    api.sendPrivateMessage(clientId, command.getExtendedHelpText());
                }
            });
        }
    }

    @Override
    public String getCommand() {
        return "!help";
    }

    @Override
    public String getHelpText() {
        return "Returns a list of all commands and their help text";
    }

    @Override
    public String getExtendedHelpText() {
        return "!help [target command]";
    }

    @Override
    public int getPowerRequired() {
        return 0;
    }
}
