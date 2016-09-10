package commands;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import control.Bot;
import control.UserPowerHandler;

public class HelpCommand implements ICommand {
    @Override
    public void run(TS3Api api, String[] args, TextMessageEvent event) {
        if (args.length == 1) {
            Bot.getCommandMap().forEach((key, command) -> {
                String UID = event.getInvokerUniqueId();
                if (isValidCommand(command) && canExecuteCommand(command, UID)) {
                    api.sendPrivateMessage(event.getInvokerId(), String.format("%-12s - %s", command.getCommand(), command.getHelpText()));
                }
            });
            api.sendPrivateMessage(event.getInvokerId(), "Use !help <command> to get more help for that specific command");
        } else {
            Bot.getCommandMap().forEach((key, command) -> {
                if (isExistingCommand(args[1], command)) {
                    int clientId = event.getInvokerId();
                    api.sendPrivateMessage(clientId, command.getExtendedHelpText());
                }
            });
        }
    }

    private boolean isExistingCommand(String commandText, ICommand command) {
        return isValidCommand(command) &&
               command.getCommand().equalsIgnoreCase(commandText) || command.getCommand().substring(1).equalsIgnoreCase(commandText);
    }

    private boolean isValidCommand(ICommand command) {
        return !command.getCommand().equalsIgnoreCase("");
    }

    private boolean canExecuteCommand(ICommand command, String UID) {
        return UserPowerHandler.getInstance().canExecuteCommand(command, UID);
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
