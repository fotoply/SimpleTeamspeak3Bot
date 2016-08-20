package control;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import commands.CommandMap;
import commands.ICommand;
import commands.InvalidCommand;
import events.IOnMessageRecievedEvent;

public class CommandHandler implements IOnMessageRecievedEvent {
    private static CommandHandler instance;
    private CommandHandler() {

    }

    public static CommandHandler getInstance() {
        if(instance == null) {
            instance = new CommandHandler();
        }
        return instance;
    }

    @Override
    public void onMessage(TS3Api api, TextMessageEvent event) {
        if (!event.getMessage().startsWith("!")) {
            return;
        }

        String[] args = event.getMessage().split(" ");
        ICommand command = CommandMap.getInstance().getOrDefault(args[0], new InvalidCommand());

        if (!UserPowerHandler.getInstance().canExecuteCommand(command, event.getInvokerUniqueId())) {
            api.sendPrivateMessage(event.getInvokerId(), "Insufficient permissions for command");
            return;
        }

        Thread thread = new Thread(() -> {
            command.run(api, args, event);
        });
        thread.start();
    }
}
