package info.knigoed.util;

import java.text.NumberFormat;
import java.util.Locale;

public class PriceUtils {
    /**
     * Форматирует цены, пример: 1 444
     * <p>
     * @param price
     * @param country
     * @return
     */
    public static String priceFormat(double price, String country) {
        NumberFormat n = NumberFormat.getNumberInstance(Locale.US);
        n.setMaximumFractionDigits(2);
        switch (country) {
            case "KZ":
            case "UA":
                n = NumberFormat.getNumberInstance(Locale.FRENCH);
                n.setMaximumFractionDigits(0);
                break;
        }
        return n.format(price);
    }
}
