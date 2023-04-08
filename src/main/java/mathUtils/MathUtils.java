package mathUtils;

import java.util.List;
import java.util.function.ToDoubleFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

import pt.com.ingenium.ingeniumtest.dto.Quote;

public final class MathUtils {
    
	/**
	 * 
	 * @param quotes
	 * @param field
	 * @return
	 */
	public static double getMax(List<Quote> quotes, ToDoubleFunction<Quote> field) {
        return quotes.stream().mapToDouble(field).max().orElse(0.0);
    }

    /**
     * 
     * @param quotes
     * @param field
     * @return
     */
    public static double getMin(List<Quote> quotes, ToDoubleFunction<Quote> field) {
        return quotes.stream().mapToDouble(field).min().orElse(0.0);
    }

    /**
     * 
     * @param quotes
     * @param field
     * @return
     */
    public static double getAverage(List<Quote> quotes, ToDoubleFunction<Quote> field) {
        return quotes.stream().mapToDouble(field).average().orElse(0.0);
    }
    
    /**
     * 
     * @param quotes
     * @param field
     * @return
     */
    public static double getMedian(List<Quote> quotes, ToDoubleFunction<Quote> field) {
        List<Double> values = quotes.stream().mapToDouble(field).boxed().sorted().collect(Collectors.toList());
        int size = values.size();
        if (size % 2 == 0) {
            return (values.get(size / 2 - 1) + values.get(size / 2)) / 2;
        } else {
            return values.get(size / 2);
        }
    }

    /**
     * 
     * @param quotes
     * @param field
     * @return
     */
    public static long getTotal(List<Quote> quotes, ToLongFunction<Quote> field) {
        return quotes.stream().mapToLong(field).sum();
    }
}
