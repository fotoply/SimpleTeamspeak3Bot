package control.settingvalidators;

public abstract class AbstractSettingValidator {

    /**
     * Returns if a given value is valid in correspondence to the setting validator.
     * @param value the value to check
     * @return true if valid otherwise false
     */
    public abstract boolean isValidValue(String value);

    /**
     * Returns a textual description of the valid settings for the setting validator
     * @return a short string
     */
    public abstract String validSettings();
}
