package selectorSpecificity;

import selectorSpecificity.tuples.SpecificityData;
import selectorSpecificity.tuples.WeightData;

import java.util.function.Function;

public interface Weight {
    static Function<SpecificityData, SpecificityData> weigh(WeightData weights) {
        return data -> new SpecificityData(
            weights.idWeight * data.idsCount,
            weights.classWeight * data.classesCount,
            weights.elementWeight * data.elementsCount,
            weights.validRestWeight * data.validRestCount
        );
    }

    static Function<SpecificityData, SpecificityData> noop(WeightData weights) {
        return data -> data;
    }
}
