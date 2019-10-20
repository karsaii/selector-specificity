package selectorSpecificity.tuples;

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
}
