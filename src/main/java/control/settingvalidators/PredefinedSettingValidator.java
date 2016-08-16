package control.settingvalidators;

import java.util.ArrayList;

/**
 * A simple settings validator which will return true for any setting in the list of allowed options
 */
public class PredefinedSettingValidator extends AbstractSettingValidator {
    private ArrayList<String> allowedOptions = new ArrayList<>();

    @Override
    public boolean isValidValue(String value) {
        return allowedOptions.contains(value.toLowerCase());
    }

    public void addAllowedOptions(ArrayList<String> options) {
        for (String option: options) {
            allowedOptions.add(option.toLowerCase());
        }
    }

    public void addAllowedOption(String option) {
        allowedOptions.add(option.toLowerCase());
    }
}
