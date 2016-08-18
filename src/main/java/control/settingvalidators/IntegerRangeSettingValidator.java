package control.settingvalidators;

/**
 * Allows any integer within a pre-specified range
 * This range is inclusive on both ends
 */
public class IntegerRangeSettingValidator extends AbstractSettingValidator {
    int upperLimit;
    int lowerLimit;

    public IntegerRangeSettingValidator(int upperLimit, int lowerLimit) {
        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
    }

    @Override
    public boolean isValidValue(String value) {
        int numberValue;
        try {
            numberValue = Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return false;
        }
        if(numberValue < lowerLimit) {
            return false;
        }
        if (numberValue > upperLimit) {
            return false;
        }
        return true;
    }

    @Override
    public String validSettings() {
        return "[" + lowerLimit + ";" + upperLimit + "] (Whole numbers)";
    }


}
