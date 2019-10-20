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
        SpecificityData that = (SpecificityData) o;
        return idsCount == that.idsCount &&
            classesCount == that.classesCount &&
            elementsCount == that.elementsCount &&
            validRestCount == that.validRestCount;
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
