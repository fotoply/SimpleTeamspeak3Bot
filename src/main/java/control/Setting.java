package control;

import control.settingvalidators.AbstractSettingValidator;

public class Setting {
    private AbstractSettingValidator validator;
    private String defaultValue;

    public Setting(AbstractSettingValidator validator, String defaultValue) {
        this.validator = validator;
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public boolean isValueValid(String value) {
        return validator.isValidValue(value);
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setValidator(AbstractSettingValidator validator) {
        this.validator = validator;
    }
}
