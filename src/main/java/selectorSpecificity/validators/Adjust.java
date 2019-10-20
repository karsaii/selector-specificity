package selectorSpecificity.validators;

import selectorSpecificity.tuples.AdjustData;

import java.util.Objects;

public interface Adjust {
    static boolean validate(AdjustData data) {
        return !Objects.isNull(data);
    }
}
