package commands;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import control.UserPowerHandler;

public class PowerCommand implements ICommand {
    @Override
    public void run(TS3Api api, String[] args, TextMessageEvent event) {
        if (args.length < 3 || args.length > 4) {
            api.sendPrivateMessage(event.getInvokerId(), "Insufficient parameters for !power");
            return;
        }

        if (args[1].equalsIgnoreCase("get") && args.length == 3) {
            if (api.getClientsByName(args[2]).size() > 0) {
                String targetUID = api.getClientsByName(args[2]).get(0).getUniqueIdentifier();
                int powerLevel = UserPowerHandler.getInstance().getUserPowerLevel(targetUID);
                api.sendPrivateMessage(api.getClientByUId(targetUID).getId(), String.format("%s has a power level of %d", api.getClientByUId(targetUID).getNickname(), powerLevel));
            }

        } else if (args[1].equalsIgnoreCase("set") && args.length == 4) {
            if (api.getClientsByName(args[2]).size() > 0) {
                String targetUID = api.getClientsByName(args[2]).get(0).getUniqueIdentifier();
                int powerLevel = Integer.parseInt(args[3]);
                UserPowerHandler.getInstance().setUserPowerLevel(targetUID, powerLevel);
                api.sendPrivateMessage(api.getClientByUId(targetUID).getId(), String.format("Power level for %s was set to %d", api.getClientByUId(targetUID).getNickname(), powerLevel));
            }
        } else {
            api.sendPrivateMessage(event.getInvokerId(), "Unknown subcommand for !power");
        }
    }

    @Override
    public String getCommand() {
        return "!power";
    }

    @Override
    public String getHelpText() {
        return "Modifies the power level of a user";
    }

    @Override
    public String getExtendedHelpText() {
        return "!power <get|set> <target> [new level]";
    }

    @Override
    public int getPowerRequired() {
        return 100;
    }
}
