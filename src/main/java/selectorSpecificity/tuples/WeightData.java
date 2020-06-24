package selectorSpecificity.tuples;

import java.util.Objects;

public class WeightData {
    public final double idWeight;
    public final double classWeight;
    public final double elementWeight;
    public final double validRestWeight;

    public WeightData(double idWeight, double classWeight, double elementWeight, double validRestWeight) {
        this.idWeight = idWeight;
        this.classWeight = classWeight;
        this.elementWeight = elementWeight;
        this.validRestWeight = validRestWeight;
    }

    public WeightData() {
        this(0.0, 0.0, 0.0, 0.0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (WeightData) o;
        return (
            Double.compare(that.idWeight, idWeight) == 0 &&
            Double.compare(that.classWeight, classWeight) == 0 &&
            Double.compare(that.elementWeight, elementWeight) == 0 &&
            Double.compare(that.validRestWeight, validRestWeight) == 0
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(idWeight, classWeight, elementWeight, validRestWeight);
    }
}
