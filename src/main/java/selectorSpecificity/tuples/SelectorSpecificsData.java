package selectorSpecificity.tuples;

import java.util.Objects;

public class SelectorSpecificsData {
    public final String originalSelector;
    public final String selector;
    public final SpecificityData specifics;

    public SelectorSpecificsData(String originalSelector, String selector, SpecificityData specifics) {
        this.originalSelector = originalSelector;
        this.selector = selector;
        this.specifics = specifics;
    }

    public SelectorSpecificsData(String selector, SpecificityData specifics) {
        this(selector, selector, specifics);
    }

    public SelectorSpecificsData(String selector) {
        this(selector, new SpecificityData());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (SelectorSpecificsData) o;
        return Objects.equals(selector, that.selector) && Objects.equals(specifics, that.specifics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(selector, specifics);
    }
}
