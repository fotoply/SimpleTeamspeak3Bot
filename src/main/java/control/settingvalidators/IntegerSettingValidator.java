package control.settingvalidators;

/**
 * Allows any integer value to be used
 */
public class IntegerSettingValidator extends AbstractSettingValidator {
    @Override
    public boolean isValidValue(String value) {
        try {
            Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public String validSettings() {
        return "Any whole number";
    }
}
