package selectorSpecificity.validators;

import selectorSpecificity.tuples.NormalizeData;

import java.util.Objects;

public interface Normalize {
    static boolean validate(NormalizeData data) {
        return (
            !Objects.isNull(data) &&
            !Objects.isNull(data.pattern) &&
            !Objects.isNull(data.normalizer) &&
            !Objects.isNull(data.replacer)
        );
    }
}
