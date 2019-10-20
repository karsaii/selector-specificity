package selectorSpecificity.tuples;

import selectorSpecificity.constants.PatternConstants;
import selectorSpecificity.Normalizer;
import selectorSpecificity.validators.Normalize;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NormalizeData {
    public final Pattern pattern;
    public final String replacer;
    public final BiFunction<String, Integer, Function<Matcher, String>> normalizer;
    public final Predicate<NormalizeData> validator;

    public NormalizeData(Pattern pattern, String replacer, BiFunction<String, Integer, Function<Matcher, String>> normalizer, Predicate<NormalizeData> validator) {
        this.pattern = pattern;
        this.replacer = replacer;
        this.normalizer = normalizer;
        this.validator = validator;
    }

    public NormalizeData(Pattern pattern, String replacer, BiFunction<String, Integer, Function<Matcher, String>> normalizer) {
        this(pattern, replacer, normalizer, Normalize::validate);
    }

    public NormalizeData(Pattern pattern, BiFunction<String, Integer, Function<Matcher, String>> normalizer, Predicate<NormalizeData> validator) {
        this(pattern, PatternConstants.MATCH_SINGLE_SPACE_REPLACE, normalizer, validator);
    }

    public NormalizeData(Pattern pattern, BiFunction<String, Integer, Function<Matcher, String>> normalizer) {
        this(pattern, PatternConstants.MATCH_SINGLE_SPACE_REPLACE, normalizer, Normalize::validate);
    }

    public NormalizeData(Pattern pattern, String replacer) {
        this(pattern, replacer, Normalizer::normalizeAllMatches, Normalize::validate);
    }

    public NormalizeData(Pattern pattern) {
        this(pattern, PatternConstants.MATCH_SINGLE_SPACE_REPLACE, Normalizer::normalizeAllMatches);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NormalizeData that = (NormalizeData) o;
        return Objects.equals(pattern, that.pattern) &&
            Objects.equals(replacer, that.replacer) &&
            Objects.equals(normalizer, that.normalizer) &&
            Objects.equals(validator, that.validator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pattern, replacer, normalizer, validator);
    }
}
