package control;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.event.*;
import commands.*;
import control.config.BotConfigReader;
import eventhandlers.BanishmentOnJoin;
import eventhandlers.MessageAllOnStart;
import eventhandlers.OldChannelOnJoin;
import events.*;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bot {
    public static Logger logger = Logger.getGlobal();
    private static CommandMap commandMap;
    private static TS3Api api;
    private static ArrayList<IGenericEvent> eventList = new ArrayList<>();

    public static CommandMap getCommandMap() {
        return commandMap;
    }

    public void init() {
        for (IGenericEvent event : eventList) {
            if (event instanceof IOnBotStartingEvent) {
                ((IOnBotStartingEvent) event).onBotStarting();
            }
        }

        initCommands();
        initEvents();

        final TS3Config config = new TS3Config();
        BotConfigReader configReader = new BotConfigReader();
        configReader.loadConfig();

        config.setHost(configReader.getServerIP());
        config.setDebugLevel(Level.WARNING);
        config.setFloodRate(configReader.getFloodRate());

        logger.log(Level.INFO, "Connecting to server at " + configReader.getServerIP());
        TS3Query query = new TS3Query(config);
        query.connect();
        logger.log(Level.INFO, "Successfully connected to server");

        api = query.getApi();
        api.login(configReader.getUsername(), configReader.getPassword());
        api.selectVirtualServerById(1);
        api.setNickname(configReader.getNickname());
        api.sendServerMessage("Systems online!");
        logger.log(Level.INFO, "Successfully logged into bot account");

        api.registerEvent(TS3EventType.TEXT_CHANNEL, 0);
        api.registerEvent(TS3EventType.CHANNEL);
        api.registerAllEvents();
        api.addTS3Listeners(new InternalEventDelegator());
        logger.log(Level.INFO, "All TS3 events registered");

        for (IGenericEvent event : eventList) {
            if (event instanceof IOnBotInitializedEvent) {
                ((IOnBotInitializedEvent) event).onBotInitialized(api);
            }
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                logger.log(Level.INFO, "Shutting down safely");
                api.sendServerMessage("Shutting down");
                for (IGenericEvent event : eventList) {
                    if (event instanceof IOnBotShutdownEvent) {
                        ((IOnBotShutdownEvent) event).onBotShutdown(api, 0);
                    }
                }
                logger.log(Level.INFO, "Finished shutting down safely");
            }
        });
    }

    private void initEvents() {

        eventList.add(CommandHandler.getInstance());
        eventList.add(new OldChannelOnJoin());
        eventList.add(new PokeCommand());
        eventList.add(UserPowerHandler.getInstance());
        eventList.add(SettingsHandler.getInstance());
        eventList.add(new BanishmentOnJoin());
        eventList.add(LastSeenCommand.getInstance());
        eventList.add(new MessageAllOnStart());
    }

    private void initCommands() {
        commandMap = CommandMap.getInstance();

        commandMap.addCommand(new PokeCommand());
        commandMap.addCommand(new HelpCommand());
        commandMap.addCommand(new PowerCommand());
        commandMap.addCommand(new SettingsCommand());
        commandMap.addCommand(LastSeenCommand.getInstance());
    }

    private static class InternalEventDelegator implements TS3Listener {
        public void onTextMessage(TextMessageEvent textMessageEvent) {
            eventList.forEach((event) -> {
                if (event instanceof IOnMessageRecievedEvent) {
                    ((IOnMessageRecievedEvent) event).onMessage(api, textMessageEvent);
                }
            });
        }

        public void onClientJoin(ClientJoinEvent clientJoinEvent) {
            eventList.forEach((event) -> {
                if (event instanceof IOnJoinEvent) {
                    ((IOnJoinEvent) event).onJoin(api, clientJoinEvent);
                }
            });
        }

        public void onClientLeave(ClientLeaveEvent clientLeaveEvent) {
            eventList.forEach((event) -> {
                if (event instanceof IOnLeaveEvent) {
                    ((IOnLeaveEvent) event).onLeave(api, clientLeaveEvent);
                }
            });
        }

        public void onServerEdit(ServerEditedEvent serverEditedEvent) {
            eventList.forEach((event) -> {
                if (event instanceof IOnServerEditedEvent) {
                    ((IOnServerEditedEvent) event).onServerEdit(api, serverEditedEvent);
                }
            });
        }

        public void onChannelEdit(ChannelEditedEvent channelEditedEvent) {
            eventList.forEach((event) -> {
                if (event instanceof IOnChannelEditedEvent) {
                    ((IOnChannelEditedEvent) event).onChannelEdited(api, channelEditedEvent);
                }
            });
        }

        public void onChannelDescriptionChanged(ChannelDescriptionEditedEvent channelDescriptionEditedEvent) {

        }

        public void onClientMoved(ClientMovedEvent clientMovedEvent) {
            eventList.forEach((event) -> {
                if (event instanceof IOnMovedEvent) {
                    ((IOnMovedEvent) event).onMoved(api, clientMovedEvent);
                }
            });
        }

        public void onChannelCreate(ChannelCreateEvent channelCreateEvent) {

        }

        public void onChannelDeleted(ChannelDeletedEvent channelDeletedEvent) {

        }

        public void onChannelMoved(ChannelMovedEvent channelMovedEvent) {

        }

        public void onChannelPasswordChanged(ChannelPasswordChangedEvent channelPasswordChangedEvent) {

        }

        public void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent privilegeKeyUsedEvent) {

        }
    }
}
