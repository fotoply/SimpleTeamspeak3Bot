package control;

public class Setting {
    private ISettingValidator validator;
    private String defaultValue;

    public String getDefaultValue() {
        return defaultValue;
    }

    public boolean isValueValid(String value) {
        return validator.isValidValue(value);
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setValidator(ISettingValidator validator) {
        this.validator = validator;
    }

    /*TODO Implement setting class and make it have the following:
    The ability to have an unrestricted setting, where the values can be any programmer-chosen defaultValue
    The ability to have a semi-restricted numeric defaultValue, where the event/command pre-defines a range
    The ability to have a semi-restricted string defaultValue where the event/command pre-defines this
    The ability to have a restricted string defaultValue where the event/command pre-defines all values possible

    It should also contain a way to register a default defaultValue.
    */
}
