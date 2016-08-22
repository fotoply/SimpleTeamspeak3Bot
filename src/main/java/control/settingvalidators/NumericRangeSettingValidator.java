package control.settingvalidators;

public class NumericRangeSettingValidator extends AbstractSettingValidator {
    private double upperLimit;
    private double lowerLimit;

    public NumericRangeSettingValidator(double upperLimit, double lowerLimit) {
        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
    }

    @Override
    public boolean isValidValue(String value) {
        double valueDouble;
        try {
            valueDouble = Double.valueOf(value);
        } catch (NumberFormatException e){
            return false;
        }
        if(valueDouble < lowerLimit) {
            return false;
        }
        if (valueDouble > upperLimit) {
            return false;
        }
        return true;
    }

    @Override
    public String validSettings() {
        return "[" + lowerLimit + ";" + upperLimit + "] (Real numbers)";
    }
}
