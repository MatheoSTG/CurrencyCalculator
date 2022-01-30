import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import java.util.SortedMap;

/**
 * Calculator class for converting euros to other currencies.
 * <p>It also storing currency selected by user and map with all currencies and their rates.</p>
 *
 * @author Mateusz Prill
 * @version 1.0
 */
public class Calculator {

    private String currentCurrency;
    private final SortedMap<String, BigDecimal> currencyRates;

    /**
     * Constructor initializing map of currency rates and set current currency for first key in map.
     *
     * @param filePath File path to xml file with currencies and their rates
     */
    public Calculator(String filePath) {
        this.currencyRates = XMLParser.getCurrencies(filePath);
        this.currentCurrency = currencyRates.firstKey();
    }

    /**
     * Converts euros to other currency selected by user
     *
     * @param amountInEuros Amount of Euros to convert to other currency in BigDecimal format.
     *                      Should be greater than 0 and not null.
     * @return Converted value of Euros in other currency in BigDecimal format.
     * If bad value given, return BigDecimal.ZERO
     */
    public BigDecimal convertEuros(BigDecimal amountInEuros){
        //If given null object return 0
        if (amountInEuros == null) {
            return BigDecimal.ZERO;
        }

        //If given value is less than 0, than return 0
        if (amountInEuros.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }

        //Get rate for current currency from map
        BigDecimal returnCurrencyRate = currencyRates.get(currentCurrency);

        //returning amount in target currency, also setting precision and rounding up to 2nd decimal place
        return amountInEuros.multiply(returnCurrencyRate).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Get selected by user currency
     *
     * @return String containing selected currency tag
     */
    public String getCurrentCurrency() {
        return currentCurrency;
    }

    /**
     * Set new currency for calculations
     *
     * @param currentCurrency String containing currency tag for changing selected currency
     * @return If currentCurrency is in xml file - return 1, in other case - return 0
     */
    public int setCurrentCurrency(String currentCurrency) {
        if (currentCurrency == null) {
            System.out.println("Key can't be null object!");
            return 0;
        }

        if (currencyRates.containsKey(currentCurrency)) {
            this.currentCurrency = currentCurrency;
            return 1;
        } else {
            System.out.println("Key doesn't exist!");
            return 0;
        }
    }

    /**
     * Get list of all currencies given in xml file
     *
     * @return Set of Strings with currencies tags
     */
    public Set<String> getCurrenciesSet() {
        return currencyRates.keySet();
    }
}
