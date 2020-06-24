package selectorSpecificity.tuples;

import selectorSpecificity.validators.Adjust;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class AdjustData {
    public final BiFunction<SpecificityData, Double, SpecificityData> adjuster;
    public final WeightData weights;
    public final Function<WeightData, UnaryOperator<SpecificityData>> weigher;
    public final Predicate<AdjustData> validator;

    public AdjustData(BiFunction<SpecificityData, Double, SpecificityData> adjuster, WeightData weights, Function<WeightData, UnaryOperator<SpecificityData>> weigher, Predicate<AdjustData> validator) {
        this.adjuster = adjuster;
        this.weights = weights;
        this.weigher = weigher;
        this.validator = validator;
    }

    public AdjustData(BiFunction<SpecificityData, Double, SpecificityData> adjuster) {
        this(adjuster, null, null, Adjust::validate);
    }

    public AdjustData() {
        this(null, null, null, Adjust::validate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (AdjustData) o;
        return (
            Objects.equals(adjuster, that.adjuster) &&
            Objects.equals(weights, that.weights) &&
            Objects.equals(weigher, that.weigher) &&
            Objects.equals(validator, that.validator)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(adjuster, weights, weigher, validator);
    }
}
