package control.settingvalidators;

public abstract class AbstractSettingValidator {
    public abstract boolean isValidValue(String value);
    public abstract String validSettings();
}
