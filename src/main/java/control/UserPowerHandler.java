package control;

import com.github.theholywaffle.teamspeak3.TS3Api;
import commands.ICommand;
import control.utils.MapPersistence;
import events.IOnBotInitializedEvent;
import events.IOnBotShutdownEvent;

import java.io.File;
import java.util.HashMap;

public class UserPowerHandler implements IOnBotShutdownEvent, IOnBotInitializedEvent {
    private static final String SAVE_PATH = "data/powerLevel.data";
    private static HashMap<String, Integer> powerLevel = new HashMap<>();
    private static UserPowerHandler instance;
    private static TS3Api tsAPI = null;

    private UserPowerHandler() {
    }

    public static UserPowerHandler getInstance() {
        if (instance == null) {
            instance = new UserPowerHandler();
        }

        return instance;
    }

    @Override
    public void onBotShutdown(TS3Api api, int shutdownCode) {
        MapPersistence.saveGenericMap(new File(SAVE_PATH), powerLevel);
    }

    public int getUserPowerLevel(String UID) {
        return powerLevel.getOrDefault(UID, getUserPowerLevelFromRank(UID));
    }

    public void setUserPowerLevel(String UID, int level) {
        powerLevel.put(UID, level);
    }

    public boolean canExecuteCommand(ICommand command, String UID) {
        return getUserPowerLevel(UID) >= command.getPowerRequired();
    }

    public int getUserPowerLevelFromRank(String UID) {
        System.out.println("Unknown power found, checking");
        int powerLevel = 0;

        if (tsAPI != null) {
            System.out.println("API is not null");
            int[] groups = tsAPI.getClientByUId(UID).getServerGroups();
            for (int i = 0; i < groups.length; i++) {
                if (groups[i] == 10) {
                    if (powerLevel < 75) {
                        powerLevel = 75;
                    }
                } else if (groups[i] == 6) {
                    powerLevel = 100;
                    System.out.println("Server admin found who is not in the power list");
                }
            }
        }

        return powerLevel;
    }

    @Override
    public void onBotInitialized(TS3Api api) {
        tsAPI = api;
        File dataFile = new File(SAVE_PATH);
        if (dataFile.exists()) {
            powerLevel.putAll(MapPersistence.readStringIntegerMap(dataFile));
        }
    }
}
