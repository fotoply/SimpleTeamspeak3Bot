package control.settingvalidators;

/**
 * Allows any numeric value to be used, both integer and double
 */
public class NumericSettingValidator extends AbstractSettingValidator {
    @Override
    public boolean isValidValue(String value) {
        try {
            Double.valueOf(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public String validSettings() {
        return "Any real number";
    }
}
