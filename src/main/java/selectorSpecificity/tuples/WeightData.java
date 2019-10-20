package selectorSpecificity.tuples;

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
}
