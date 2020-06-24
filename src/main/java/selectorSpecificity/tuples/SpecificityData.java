package selectorSpecificity.tuples;

import java.util.Objects;

public class SpecificityData {
    public final double idsCount;
    public final double classesCount;
    public final double elementsCount;
    public final double validRestCount;

    public SpecificityData() {
        this(0.0, 0.0, 0.0, 0.0);
    }

    public SpecificityData(double idsCount, double classesCount, double elementsCount, double validRestCount) {
        this.idsCount = Math.max(Math.abs(idsCount), 0.0);
        this.classesCount = Math.max(Math.abs(classesCount), 0.0);
        this.elementsCount = Math.max(Math.abs(elementsCount), 0.0);
        this.validRestCount = Math.max(Math.abs(validRestCount), 0.0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (SpecificityData) o;
        return (
            (Double.compare(that.idsCount, idsCount) == 0) &&
            (Double.compare(that.classesCount, classesCount) == 0) &&
            (Double.compare(that.elementsCount, elementsCount) == 0) &&
            (Double.compare(that.validRestCount, validRestCount) == 0)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(idsCount, classesCount, elementsCount);
    }

    @Override
    public String toString() {
        return "SpecificityData{" +
            "idsCount=" + idsCount +
            ", classesCount=" + classesCount +
            ", elementsCount=" + elementsCount +
            ", validRestCount=" + validRestCount +
            '}';
    }
}
