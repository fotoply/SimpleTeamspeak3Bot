package control.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MapPersistence {
    public static Map<String, Integer> readStringIntegerMap(File file) {
        Map<String, Integer> loadedMap = new HashMap<>();
        try {
            FileInputStream f = new FileInputStream(file);
            ObjectInputStream s = new ObjectInputStream(f);
            loadedMap = (HashMap<String, Integer>) s.readObject();
            s.close();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, "Something went wrong in reading a map!");
            e.printStackTrace();
        }
        return loadedMap;
    }

    public static void saveGenericMap(File file, Map<?, ?> map) {
        try {
            FileOutputStream f = new FileOutputStream(file);
            ObjectOutputStream s = new ObjectOutputStream(f);
            s.writeObject(map);
            s.close();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, "Something went wrong in saving a generic map!");
            e.printStackTrace();
        }
    }

    public static Map<String, HashMap<String,String>> readStringNestedMap(File file) {
        Map<String, HashMap<String, String>> loadedMap = new HashMap<>();
        try {
            FileInputStream f = new FileInputStream(file);
            ObjectInputStream s = new ObjectInputStream(f);
            loadedMap = (HashMap<String, HashMap<String, String>>) s.readObject();
            s.close();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, "Something went wrong in reading a nested map!");
            e.printStackTrace();
        }
        return loadedMap;
    }

}
