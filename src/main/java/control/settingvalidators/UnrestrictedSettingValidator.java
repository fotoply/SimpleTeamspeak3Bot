package control.settingvalidators;

/**
 * Allows any and all settings
 */
public class UnrestrictedSettingValidator extends AbstractSettingValidator {
    @Override
    public boolean isValidValue(String value) {
        return true;
    }

    @Override
    public String validSettings() {
        return "Anything";
    }
}
