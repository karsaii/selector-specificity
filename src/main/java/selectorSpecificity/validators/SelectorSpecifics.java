package selectorSpecificity.validators;

import selectorSpecificity.tuples.SelectorSpecificsData;

import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isBlank;

public interface SelectorSpecifics {
    static boolean validate(SelectorSpecificsData data) {
        return (!(
            Objects.isNull(data) ||
            Objects.isNull(data.specifics) ||
            isBlank(data.selector)
        ));
    }
}
