package control.config;

import com.github.theholywaffle.teamspeak3.TS3Query;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class BotConfigReader {
    public static final String DEFAULT_CONFIG_PATH = "config.ini";
    private File configFile;
    private HashMap<String,String> configData = new HashMap<>();

    public void loadConfig() {
        try {
            loadConfig(new File(DEFAULT_CONFIG_PATH));
        } catch (FileNotFoundException e) {
            System.out.println("No config file found, creating default example");
            createDefault();
        }
    }

    private void createDefault() {
        File defaultFile = new File("config.ini.example");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(defaultFile));
            writer.write("# Default config, fill in the missing information." +
                    "# Lines starting with # are ignored" +
                    "# Also rename this file to config.ini to use it" +
                    "ip=127.0.0.1\n" +
                    "username=\n" +
                    "password=\n" +
                    "nickname=\n" +
                    "floodrate=unlimited");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig(File configFile) throws FileNotFoundException {
        Scanner scanner = new Scanner(configFile);
        while (scanner.hasNextLine()) {
            String[] input = scanner.nextLine().split("=");
            if(input.length == 2 && !input[0].startsWith("#")) {
                configData.put(input[0],input[1]);
            }
        }
    }

    public String getPassword() {
        return configData.get("password");
    }

    public String getServerIP() {
        return configData.get("ip");
    }

    public String getUsername() {
        return configData.get("username");
    }

    public String getNickname() {
        return configData.get("nickname");
    }

    public TS3Query.FloodRate getFloodRate() {
        switch (configData.get("floodrate")) {
            case "unlimited":
                return TS3Query.FloodRate.UNLIMITED;

            case "default":
            default:
                return TS3Query.FloodRate.DEFAULT;

        }
    }
}
