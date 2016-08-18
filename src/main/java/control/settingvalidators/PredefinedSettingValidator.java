package control.settingvalidators;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple settings validator which will return true for any setting in the list of allowed options
 */
public class PredefinedSettingValidator extends AbstractSettingValidator {
    private ArrayList<String> allowedOptions = new ArrayList<>();

    @Override
    public boolean isValidValue(String value) {
        return allowedOptions.contains(value.toLowerCase());
    }

    @Override
    public String validSettings() {
        return Arrays.toString(allowedOptions.toArray());
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
