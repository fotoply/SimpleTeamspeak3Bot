package commands;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import events.IOnJoinEvent;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PokeCommand implements ICommand, IOnJoinEvent {
    private static HashMap<String, Integer> pokesRemaining = new HashMap<>();

    private static void pokeTarget(TS3Api api, String targetUID, int targetID, int pokes) {
        boolean targetValid = true;

        for (int i = 0; i < pokes; i++) {
            if (pokesRemaining.getOrDefault(targetUID, 0) == 0) {
                return;
            }

            if (targetValid) {
                targetValid = api.pokeClient(targetID, "Badaladaladala");
                pokesRemaining.replace(targetUID, pokes - (i + 1));
                Logger.getGlobal().log(Level.INFO, String.format("Pokes remaining: %d", pokesRemaining.get(targetUID)));
            }
        }
    }

    @Override
    public void run(TS3Api api, String[] args, TextMessageEvent event) {
        Client target; // Always assume that the first match is the one the user wants
        int pokes;

        try {
            pokes = Integer.parseInt(args[2]);
            target = api.getClientsByName(args[1]).get(0);
        } catch (NumberFormatException e) {
            api.sendPrivateMessage(event.getInvokerId(), "Invalid poke amount");
            return;
        } catch (Exception e) {
            api.sendPrivateMessage(event.getInvokerId(), "No such user");
            return;
        }
        pokesRemaining.put(target.getUniqueIdentifier(), pokes);

        api.sendPrivateMessage(event.getInvokerId(), String.format("Poking %s %d times", target.getNickname(), pokes));
        pokeTarget(api, target.getUniqueIdentifier(), target.getId(), pokes);
    }

    @Override
    public String getCommand() {
        return "!poke";
    }

    @Override
    public String getHelpText() {
        return "Pokes a target n amount of times.";
    }

    @Override
    public String getExtendedHelpText() {
        return "!poke <target> <amount>";
    }

    @Override
    public int getPowerRequired() {
        return 75;
    }

    @Override
    public void onJoin(TS3Api api, ClientJoinEvent joinEvent) {
        if (pokesRemaining.getOrDefault(joinEvent.getUniqueClientIdentifier(), 0) != 0) {
            Logger.getGlobal().log(Level.INFO, joinEvent.getClientNickname() + " joined with pokes remaining. Poking!");
            Thread thread = new Thread(() -> {
                pokeTarget(api, joinEvent.getUniqueClientIdentifier(), joinEvent.getClientId(), pokesRemaining.get(joinEvent.getUniqueClientIdentifier()));
            });
            thread.start();
        }
    }
}
