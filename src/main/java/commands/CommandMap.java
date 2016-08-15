package commands;

import java.util.HashMap;

public class CommandMap extends HashMap<String, ICommand> {
    @Override
    public ICommand put(String key, ICommand value) {
        return super.put(key.toLowerCase(), value);
    }

    @Override
    public ICommand get(Object key) {
        return super.get(((String) key).toLowerCase());
    }

    @Override
    public boolean containsKey(Object key) {
        return super.containsKey(((String) key).toLowerCase());
    }

    @Override
    public ICommand remove(Object key) {
        return super.remove(((String) key).toLowerCase());
    }

    @Override
    public ICommand getOrDefault(Object key, ICommand defaultValue) {
        return super.getOrDefault(((String) key).toLowerCase(), defaultValue);
    }

    public void addCommand(ICommand command) {
        put(command.getCommand(), command);
    }
}
