package control.settingvalidators;

/**
 * Allows any and all settings
 */
public class UnrestrictedSettingValidator extends SettingValidator {
    @Override
    public boolean isValidValue(String value) {
        return true;
    }
}
