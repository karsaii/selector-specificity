package selectorSpecificity.tuples;

import java.util.Objects;

public class WeightedSpecificityData {
    public final double idValue;
    public final double classValue;
    public final double elementValue;
    public final double validRestValue;

    public WeightedSpecificityData(double idValue, double classValue, double elementValue, double validRestValue) {
        this.idValue = idValue;
        this.classValue = classValue;
        this.elementValue = elementValue;
        this.validRestValue = validRestValue;
    }

    public WeightedSpecificityData() {
        this(0.0, 0.0, 0.0, 0.0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (WeightedSpecificityData) o;
        return (
            (Double.compare(that.idValue, idValue) == 0) &&
            (Double.compare(that.classValue, classValue) == 0) &&
            (Double.compare(that.elementValue, elementValue) == 0) &&
            (Double.compare(that.validRestValue, validRestValue) == 0)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(idValue, classValue, elementValue, validRestValue);
    }
}
