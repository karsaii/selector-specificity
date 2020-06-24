package selectorSpecificity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import selectorSpecificity.constants.Strategy;
import selectorSpecificity.tuples.SelectorSpecificsData;
import selectorSpecificity.tuples.SpecificityData;

import java.util.stream.Stream;

public class SpecificityCompareTests {
    public static final SelectorSpecificsData left = Specificity.getSelectorSpecificity("ul#nav li.active a", Strategy.CSS_SELECTOR);
    public static final SelectorSpecificsData left2 = Specificity.getSelectorSpecificity("ul#nav li.active a", Strategy.CSS_SELECTOR);
    public static final SelectorSpecificsData idData = Specificity.getSelectorSpecificity("#thisisanid", Strategy.CSS_SELECTOR);
    public static final SelectorSpecificsData classData = Specificity.getSelectorSpecificity(".classic", Strategy.CSS_SELECTOR);
    public static final SelectorSpecificsData xpath = Specificity.getSelectorSpecificity("", Strategy.XPATH);
    public static final SelectorSpecificsData other = Specificity.getSelectorSpecificity("", Strategy.OTHER);
    public static final SelectorSpecificsData idAndClassData = Specificity.getSelectorSpecificity(".classic #ID", Strategy.CSS_SELECTOR);

    public static Stream<Arguments> compareProvider() {
        return Stream.of(
            Arguments.of("Equal To Self", left.specifics, left.specifics, 0, "SelectorSpecificData left wasn't equal to itself."),
            Arguments.of("Equal To Self(Different reference)", left.specifics, left2.specifics, 0, "SelectorSpecificData left wasn't equal to itself via different reference."),
            Arguments.of("Class smaller than Id", classData.specifics, idData.specifics, -1, "Class specifics weren't smaller than Id specifics."),
            Arguments.of("Id bigger than class", idData.specifics, classData.specifics, 1, "Id specifics weren't bigger than Class specifics."),
            Arguments.of("Id And Class bigger than Id", idAndClassData.specifics, idData.specifics, 1, "Id and Class specifics weren't bigger than Id specifics."),
            Arguments.of("Xpath smaller than Id", xpath.specifics, idData.specifics, -1, "Xpath specifics weren't smaller than Id specifics."),
            Arguments.of("Xpath smaller than Class", xpath.specifics, classData.specifics, -1, "Xpath specifics weren't smaller than Class specifics."),
            Arguments.of("Xpath smaller than Id And Class", xpath.specifics, idAndClassData.specifics, -1, "Xpath specifics weren't smaller than Id and Class specifics."),
            Arguments.of("Other smaller than Id", other.specifics, idData.specifics, -1, "Other specifics weren't smaller than Id specifics."),
            Arguments.of("Other smaller than Class", other.specifics, classData.specifics, -1, "Other specifics weren't smaller than Class specifics."),
            Arguments.of("Other smaller than Id And Class", other.specifics, idAndClassData.specifics, -1, "Other specifics weren't smaller than Id and Class specifics."),
            Arguments.of("Other smaller than Xpath", other.specifics, xpath.specifics, -1, "Other specifics weren't smaller than Xpath specifics.")
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("compareProvider")
    public void comparator(String name, SpecificityData left, SpecificityData right, int expected, String message) {
        final var result = Specificity.compare(left, right);
        Assertions.assertEquals(expected, result, message);
    }
}
