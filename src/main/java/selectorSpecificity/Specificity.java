package selectorSpecificity;

import selectorSpecificity.constants.Strategy;
import selectorSpecificity.tuples.*;
import selectorSpecificity.validators.Execute;
import selectorSpecificity.validators.SelectorSpecifics;
import selectorSpecificity.validators.Weigh;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface Specificity {
    @SafeVarargs
    static <T> UnaryOperator<T> execute(UnaryOperator<T>... steps) {
        return Execute.validate(steps.length) ? data -> {
            var lData = data;
            for(var step : steps) {
                lData = step.apply(lData);
            }

            return lData;
        } : data -> data;
    }

    static SelectorSpecificsData getSelectorSpecificity(String selector, Strategy strategy) {
        var specificityData = new SelectorSpecificsData(selector, new SpecificityData());
        if (Objects.equals(strategy, Strategy.XPATH)) {
            return new SelectorSpecificsData(selector, new SpecificityData(0.0, 0.0, 0.0, 1.0));
        }

        if (Objects.equals(strategy, Strategy.OTHER)) {
            return new SelectorSpecificsData(selector, new SpecificityData(0.0, 0.0, 0.0, 0.1));
        }

        return isNotBlank(selector) ? AlgorithmData.CALCULATION_ALGORITHM_NORMALIZATIONS.apply(specificityData) : specificityData;
    }

    static UnaryOperator<SelectorSpecificsData> get(NormalizeData normalizeData, AdjustData adjustData) {
        if (!(
            normalizeData.validator.test(normalizeData) &&
            adjustData.validator.test(adjustData)
        )) {
            return data -> data;
        }

        return data -> {
            if (!SelectorSpecifics.validate(data)) {
                return data;
            }

            final var matcher = normalizeData.pattern.matcher(data.selector);
            final var length = (int)matcher.results().count();
            return length > 0 ? new SelectorSpecificsData(
                data.originalSelector,
                normalizeData.normalizer.apply(normalizeData.replacer, length).apply(matcher),
                adjust(adjustData, length).apply(data.specifics)
            ) : data;
        };
    }

    static UnaryOperator<SpecificityData> adjust(AdjustData adjustData, double amount) {
        return data -> execute(
            adjust(adjustData.adjuster, amount),
            weigh(adjustData.weigher, adjustData.weights)
        ).apply(data);
    }

    static UnaryOperator<SpecificityData> adjust(BiFunction<SpecificityData, Double, SpecificityData> adjuster, double amount) {
        final var absAmount = Math.abs(amount);
        return absAmount > 0.0 ? (data -> !Objects.isNull(adjuster) ? adjuster.apply(data, amount) : data) : data -> data;
    }

    static UnaryOperator<SpecificityData> weigh(BiPredicate<Function<WeightData, UnaryOperator<SpecificityData>>, WeightData> validator, Function<WeightData, UnaryOperator<SpecificityData>> weigher, WeightData weights) {
        final UnaryOperator<SpecificityData> negative = data -> data;
        if (Objects.isNull(validator) || Objects.isNull(weigher) || Objects.isNull(weights)) {
            return negative;
        }

        return validator.test(weigher, weights) ? weigher.apply(weights) : negative;
    }

    static UnaryOperator<SpecificityData> weigh(Function<WeightData, UnaryOperator<SpecificityData>> weigher, WeightData weights) {
        return weigh(Weigh::validate, weigher, weights);
    }

    static UnaryOperator<SelectorSpecificsData> get(NormalizeData normalizeData) {
        return get(normalizeData, new AdjustData());
    }

    static UnaryOperator<SelectorSpecificsData> getWithAdjuster(NormalizeData normalizeData, BiFunction<SpecificityData, Double, SpecificityData> adjuster) {
        return get(normalizeData, new AdjustData(adjuster));
    }

    static UnaryOperator<SelectorSpecificsData> getWithValidRest(NormalizeData normalizeData) {
        return get(normalizeData, new AdjustData(Specificity::adjustValidRest));
    }

    static SpecificityData adjustIds(SpecificityData data, double amount) {
        final var absAmount = Math.abs(amount);
        return absAmount > 0.0 ? new SpecificityData(data.idsCount + amount, data.classesCount, data.elementsCount, data.validRestCount) : data;
    }

    static SpecificityData adjustClasses(SpecificityData data, double amount) {
        final var absAmount = Math.abs(amount);
        return absAmount > 0.0 ? new SpecificityData(data.idsCount, data.classesCount + amount, data.elementsCount, data.validRestCount) : data;
    }

    static SpecificityData adjustElements(SpecificityData data, double amount) {
        final var absAmount = Math.abs(amount);
        return absAmount > 0.0 ? new SpecificityData(data.idsCount, data.classesCount, data.elementsCount + amount, data.validRestCount) : data;
    }

    static SpecificityData adjustValidRest(SpecificityData data, double amount) {
        final var absAmount = Math.abs(amount);
        return absAmount > 0.0 ? new SpecificityData(data.idsCount, data.classesCount, data.elementsCount, data.validRestCount + amount) : data;
    }

    static SpecificityData adjust(SpecificityData data, SpecificityData amountData) {
        final var idsCount = amountData.idsCount;
        final var classesCount = amountData.classesCount;
        final var elementsCount = amountData.elementsCount;
        final var validRestCount = amountData.validRestCount;

        return new SpecificityData(data.idsCount + idsCount, data.classesCount + classesCount, data.elementsCount + elementsCount, data.validRestCount + validRestCount);
    }

    static SpecificityData reduce(SpecificityData... dataArray) {
        var base = new SpecificityData();
        if (!Execute.validate(dataArray.length)) {
            return base;
        }

        for (SpecificityData data : dataArray) {
            base = adjust(base, data);
        }

        return base;
    }

    static List<Double> getSpecificityValuesInOrder(SpecificityData data) {
        return Arrays.asList(data.idsCount, data.classesCount, data.elementsCount, data.validRestCount);
    }

    static int compare(SpecificityData left, SpecificityData right) {
        final var leftValues = getSpecificityValuesInOrder(left);
        final var rightValues = getSpecificityValuesInOrder(right);
        final var length = leftValues.size();
        double leftValue;
        double rightValue;
        for (var index = 0; index < length; ++index) {
            leftValue = leftValues.get(index);
            rightValue = rightValues.get(index);

            if (leftValue > rightValue) {
                return 1;
            }

            if (leftValue < rightValue) {
                return -1;
            }
        }

        return 0;
    }

    static <T> T compute(SpecificityData left, SpecificityData right, BiFunction<SpecificityData, SpecificityData, T> algorithm) {
        return algorithm.apply(left, right);
    }
}
